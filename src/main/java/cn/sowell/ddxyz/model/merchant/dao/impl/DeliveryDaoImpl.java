package cn.sowell.ddxyz.model.merchant.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;
import cn.sowell.ddxyz.model.merchant.dao.DeliveryDao;

@Repository
public class DeliveryDaoImpl implements DeliveryDao{

	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDelivery> getAllDelivery(long waresId, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		String hql = "from PlainDelivery d where d.waresId = :waresId and d.timePoint >= :theDayZero and d.timePoint < :theSecondZero and d.disabled is null order by d.timePoint asc";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("waresId", waresId)
				.setTimestamp("theDayZero", cal.getTime())
				;
		cal.add(Calendar.DATE, 1);
		query.setTimestamp("theSecondZero", cal.getTime());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<PlainLocation> getAllLocation(long merchantId) {
		String hql = "from PlainLocation l where l.merchantId = :merchantId and l.disabled is null";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("merchantId", merchantId);
		return new LinkedHashSet<PlainLocation>(query.list());
	}
	
	@Override
	public void savePlan(PlainDeliveryPlan plan) {
		sFactory.getCurrentSession().save(plan);
	}
	
	@Override
	public PlainDelivery getPlainDelivery(long deliveryId) {
		return sFactory.getCurrentSession().get(PlainDelivery.class, deliveryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDeliveryPlan> getPlainDeliveryPlanPageList(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String hql = "from PlainDeliveryPlan p @mainWhere order by updateTime desc, createTime desc";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		DeferedParamSnippet mainWhere = dQuery.createConditionSnippet("mainWhere");
		if(criteria.getLocationName() != null && !criteria.getLocationName().trim().equals("")){
			mainWhere.append("and p.location.name like :locationName");
			dQuery.setParam("locationName", "%" + criteria.getLocationName() + "%", StandardBasicTypes.STRING);
		}
		
		if(pageInfo == null){
			Query query = dQuery.createQuery(session, false, null);
			return query.list();
		}else{
			Query countQuery = dQuery.createQuery(session, true, new WrapForCountFunction());
			Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
			if(count > 0){
				pageInfo.setCount(count);
				Query query = dQuery.createQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				return query.list();
			}
			return new ArrayList<PlainDeliveryPlan>();
		}
	}

	@Override
	public boolean changePlanDisabled(Long planId, Integer disabled) {
		Session session = sFactory.getCurrentSession();
		String sql = "update t_delivery_plan p set p.c_disabled = :disabled where p.id = :planId";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("disabled", disabled, StandardBasicTypes.INTEGER);
		query.setLong("planId", planId);
		int updated = query.executeUpdate();
		if(updated > 0){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, PlainKanteenDelivery> getDeliveryMap(Set<Long> deliveryIds) {
		if(deliveryIds != null){
			String hql = "from PlainKanteenDelivery d where w.id in (:deliveryIds)";
			Query query = sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("deliveryIds", deliveryIds, StandardBasicTypes.LONG);
			List<PlainKanteenDelivery> list = query.list();
			return CollectionUtils.toMap(list, item->item.getId());
		}else{
			return new HashMap<Long, PlainKanteenDelivery>();
		}
	}

}
