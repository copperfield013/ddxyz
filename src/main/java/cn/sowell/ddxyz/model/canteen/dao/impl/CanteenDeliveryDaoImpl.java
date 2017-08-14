package cn.sowell.ddxyz.model.canteen.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.ddxyz.model.canteen.dao.CanteenDeliveryDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenDeliveryWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekDeliveryCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryWaresListItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekDeliveryWaresItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Repository
public class CanteenDeliveryDaoImpl implements CanteenDeliveryDao {
	@Resource
	SessionFactory sFactory;

	@Resource
	FrameDateFormat dateFormat;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainLocation> getDeliveryLocations(String type,
			Boolean disabled) {
		Criteria criteria = sFactory.getCurrentSession().createCriteria(PlainLocation.class).add(Restrictions.eq("type", type));
		
		if(disabled != null){
			if(disabled){
				criteria.add(Restrictions.eqOrIsNull("disabled", 1));
			}else{
				criteria.add(Restrictions.isNull("disabled"));
			}
		}
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenDeliveryWaresListItem> queryDeliveryWaresList(
			CanteenDeliveryWaresListCriteria criteria) {
		String sql = 
				"	SELECT" +
						"		w.c_name wares_name," +
						"		w.c_base_price," +
						"		w.c_price_unit," +
						"		d.c_time_point," +
						"		d.c_clain_end_time," +
						"		d.c_open_time," +
						"		d.c_close_time," +
						"		d.id delivery_id," +
						"		dw.id dwares_id," +
						"		d.c_location_name," +
						"		dw.c_max_count," + 
						"		dw.c_current_count" +
						"	FROM" +
						"		t_delivery_base d" +
						"	LEFT JOIN t_delivery_wares dw ON d.id = dw.delivery_id" +
						"	LEFT JOIN t_wares_base w ON dw.wares_id = w.id" +
						"	WHERE" +
						"		dw.id IS NOT NULL" +
						"	AND d.c_type = :type";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("type", "canteen");
		
		if(criteria.getStartDate() != null) {
			dQuery.appendCondition("and d.c_time_point >= :startDate")
					.setParam("startDate", criteria.getStartDate());
		}
		if(criteria.getEndDate() != null) {
			dQuery.appendCondition("and d.c_time_point < :endDate")
					.setParam("endDate", criteria.getEndDate())
					;
		}
		SQLQuery query = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(CanteenDeliveryWaresListItem.class));
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainWares> getWaresList(long merchantId, Boolean disabled) {
		String hql = "from PlainWares w where w.merchantId = :merchantId";
		if(disabled != null){
			if(disabled){
				hql += " and w.disabled = 1";
			}else{
				hql += " and (w.disabled is null or w.disabled <> 1)";
			}
		}
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("merchantId", merchantId);
		return query.list();
	}
	
	@Override
	public Long saveDelivery(PlainDelivery delivery) {
		return (Long) sFactory.getCurrentSession().save(delivery);
	}
	
	@Override
	public Long saveDeliveryWares(PlainDeliveryWares dWares) {
		return (Long) sFactory.getCurrentSession().save(dWares);
	}
	
	@Override
	public PlainLocation getDeliveryLocation(Long locationId) {
		return sFactory.getCurrentSession().get(PlainLocation.class, locationId);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenWeekDeliveryWaresItem> getCanteenDeliveryWaresItems(
			Long deliveryId) {
		String sql = 
				"	SELECT" +
						"		w.c_name wares_name," +
						"		w.c_base_price," +
						"		w.c_price_unit," +
						"		w.id wares_id," +
						"		dw.id dwares_id," +
						"		dw.c_max_count," + 
						"		dw.c_current_count," +
						"		dw.c_disabled" +
						"	FROM" +
						"	t_delivery_wares dw " +
						"	LEFT JOIN t_wares_base w ON dw.wares_id = w.id" +
						"	WHERE" +
						"		dw.delivery_id = :deliveryId" ;
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("deliveryId", deliveryId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(CanteenWeekDeliveryWaresItem.class));
		return query.list();
	}

	@Override
	public PlainDelivery getCanteenDelivery(
			CanteenWeekDeliveryCriteria criteria) {
		return getCanteenDelivery(criteria.getStartDate(), criteria.getEndDate());
	}
	
	
	@Override
	public PlainDelivery getCanteenDelivery(Date startTime, Date endTime) {
		if(startTime != null && endTime != null){
			String hql = "from PlainDelivery d where d.timePoint >= :startTime and d.timePoint < :endTime and d.type = :type";
			Query query = sFactory.getCurrentSession().createQuery(hql);
				query.setTimestamp("startTime", startTime)
					.setTimestamp("endTime", endTime)
					.setString("type", "canteen")
					.setMaxResults(1)
				;
			return (PlainDelivery) query.uniqueResult();
		}else{
			throw new RuntimeException("startTime和endTime条件都不能为空");
		}
	}
	
	@Override
	public PlainDelivery getDelivery(Long deliveryId) {
		return sFactory.getCurrentSession().get(PlainDelivery.class, deliveryId);
	}
	
	@Override
	public void update(Object obj) {
		sFactory.getCurrentSession().update(obj);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDeliveryWares> getDeliveryWaresList(Long deliveryId) {
		Criteria criteria = sFactory.getCurrentSession().createCriteria(PlainDeliveryWares.class);
		return criteria.add(Restrictions.eq("deliveryId", deliveryId)).list();
	}
	
	@Override
	public void removeDeliveryWares(Set<Long> delIdSet) {
		String sql = "delete from t_delivery_wares where id in (:delIds)";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameterList("delIds", delIdSet);
		query.executeUpdate();
	}
	
	@Override
	public void create(Object dWares) {
		sFactory.getCurrentSession().save(dWares);
	}

	@Override
	public void disableDeliveryWares(Long deliveryWaresId, boolean disable) {
		String sql = "update t_delivery_wares set c_disabled = :disable where id = :dwId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("dwId", deliveryWaresId);
		if(disable){
			query.setLong("disable", 1);
		}else{
			query.setParameter("disable", null, StandardBasicTypes.LONG);
		}
		query.executeUpdate();
	}
}
