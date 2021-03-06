package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenOrderDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenSection;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenOrderItem;
import cn.sowell.ddxyz.model.merchant.dao.DeliveryDao;

@Repository
public class KanteenOrderDaoImpl implements KanteenOrderDao{
	
	@Resource
	SessionFactory sFactory;
	@Resource
	DeliveryDao deliveryDao;
	
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
	
	@Override
	public List<KanteenOrderItem> queryOrderList(
			KanteenOrderListCriteria criteria, PageInfo pageInfo) {
		String hql = "from PlainKanteenOrder o where o.merchantId = :merchantId @condition order by o.createTime desc";
		List<PlainKanteenOrder> orderList = QueryUtils.pagingQuery(hql, sFactory.getCurrentSession(), pageInfo, dQuery->{
			dQuery.setParam("merchantId", criteria.getMerchantId());
			DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
			if(criteria.isFilterEffectiveOrder()){
				snippet.append("and o.status <> :defaultOrderStatus and o.status is not null");
				dQuery.setParam("defaultOrderStatus", PlainKanteenOrder.STATUS_DEFAULT);
			}
			if(criteria.isFilterCanceled()){
				snippet.append("and o.canceledStatus is not null");
			}
			if(criteria.getDeliveryId() != null){
				snippet.append("and o.deliveryId = :deliveryId");
				dQuery.setParam("deliveryId", criteria.getDeliveryId());
			}else if(criteria.getDistributionId() != null){
				snippet.append("and o.distributionId = :distributionId");
				dQuery.setParam("distributionId", criteria.getDistributionId());
			}
			if(TextUtils.hasText(criteria.getOrderCode())){
				snippet.append("and o.code like :code");
				dQuery.setParam("code", "%" + criteria.getOrderCode() + "%");
			}
		});
		List<KanteenOrderItem> list = new ArrayList<KanteenOrderItem>();
		Map<Long, PlainKanteenDelivery> deliveryMap = deliveryDao.getDeliveryMap(CollectionUtils.toSet(orderList, order->order.getDeliveryId()));
		orderList.forEach(order->{
			KanteenOrderItem item = new KanteenOrderItem();
			item.setOrder(order);
			item.setDelivery(deliveryMap.get(order.getDeliveryId()));
			list.add(item);
		});
		return list;
	}
	
	@Override
	public Map<Long, Integer> getDistributionEffectiveOrderCountMap(
			Set<Long> distributionIdSet) {
		if(distributionIdSet != null && !distributionIdSet.isEmpty()){
			return QueryUtils.queryMap(
					"	SELECT" +
					"		k.distribution_id , count(k.id) order_count" +
					"	FROM" +
					"		t_order_kanteen k" +
					"	WHERE" +
					"		k.distribution_id IN (:distributionIds)" +
					"	AND k.c_status IS NOT NULL" +
					"	AND k.c_status <> :defaultStatus" +
					"	AND k.c_canceled_status IS NULL" +
					"	group by k.distribution_id", sFactory.getCurrentSession(), 
					mw->mw.getLong("distribution_id"), mw->mw.getInteger("order_count"), 
					dQuery->
							dQuery
							.setParam("distributionIds", distributionIdSet)
							.setParam("defaultStatus", PlainKanteenOrder.STATUS_DEFAULT));
		}else{
			return new HashMap<Long, Integer>();
		}
	}
	
}
