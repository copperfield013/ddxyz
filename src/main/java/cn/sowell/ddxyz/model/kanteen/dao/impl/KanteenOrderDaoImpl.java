package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenOrderDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenSection;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;

@Repository
public class KanteenOrderDaoImpl implements KanteenOrderDao{
	
	@Resource
	SessionFactory sFactory;
	
	@Override
	public Integer statCount(KanteenOrderStatCriteria criteria) {
		String sql = "select count(o.id) from t_order_kanteen o where o.id is not null @condition";
		SQLQuery query = buildStatQuery(sql, criteria);
		return FormatUtils.toInteger(query.uniqueResult());
	}

	private SQLQuery buildStatQuery(String sql, KanteenOrderStatCriteria criteria) {
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		DeferedParamSnippet conditionSnippet = dQuery.createSnippet("condition", null);
		
		if(criteria.getDeliveryId() != null){
			conditionSnippet.append("and o.delivery_id = :deliveryId");
			dQuery.setParam("deliveryId", criteria.getDeliveryId());
		}else if(criteria.getDistributionId() != null){
			conditionSnippet.append("and o.distribution_id = :distributionId");
			dQuery.setParam("distributionId", criteria.getDistributionId());
		}
		
		if(criteria.getAllCanceled()){
			conditionSnippet.append("and o.c_canceled_status is not null");
		}else{
			if(criteria.getCanceledStatus() != null && !criteria.getCanceledStatus().isEmpty()){
				conditionSnippet.append("and o.c_canceled_status in (:canceledStatus)");
				dQuery.setParam("canceledStatus", criteria.getCanceledStatus());
			}else{
				conditionSnippet.append("and o.c_canceled_status is null");
			}
		}
		if(criteria.getStatus() != null && !criteria.getStatus().isEmpty()){
			conditionSnippet.append("and o.c_status in (:status)");
			dQuery.setParam("status", criteria.getStatus());
		}else{
			conditionSnippet.append("and o.c_status is not null and o.c_status <> :defaultStatus");
			dQuery.setParam("defaultStatus", PlainKanteenOrder.STATUS_DEFAULT);
		}
		if(TextUtils.hasText(criteria.getPayway())){
			conditionSnippet.append("and o.c_payway = :payway");
			dQuery.setParam("payway", criteria.getPayway());
		}
		return dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
	}

	@Override
	public Integer statOrderAmount(KanteenOrderStatCriteria criteria) {
		String sql = "select COALESCE(sum(o.c_total_price), 0) from t_order_kanteen o where o.id is not null @condition";
		SQLQuery query = buildStatQuery(sql, criteria);
		return FormatUtils.toInteger(query.uniqueResult());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<Long> getAllExpiredOrderIds(Date now) {
		String sql = "select o.id from t_order_kanteen o where o.c_pay_expired_time < :now and o.c_pay_time is null and o.c_canceled_status is null";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setTimestamp("now", now);
		List list = query.list();
		Set<Long> set = new HashSet<Long>();
		list.forEach(id->set.add(FormatUtils.toLong(id)));
		return set;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Set<PlainKanteenSection> getAllSections(Set<Long> orderIds) {
		if(orderIds != null && !orderIds.isEmpty()){
			String hql = "from PlainKanteenSection s where s.orderId in  (:orderIds)";
			Query query = sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("orderIds", orderIds);
			return new HashSet<PlainKanteenSection>(query.list());
		}else{
			return new HashSet<PlainKanteenSection>();
		}
	}
	
	@Override
	public void setOrderPayExpired(Set<Long> expiredOrderIds) {
		if(expiredOrderIds != null && !expiredOrderIds.isEmpty()){
			String sql = "update t_order_kanteen o set o.c_canceled_status = :payExpiredStatus where id in (:expiredOrderIds)";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameterList("expiredOrderIds", expiredOrderIds, StandardBasicTypes.LONG)
					.setString("payExpiredStatus", PlainKanteenOrder.CANSTATUS_PAYEXPIRED)
					.executeUpdate();
		}
		
	}
	
}
