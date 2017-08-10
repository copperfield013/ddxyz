package cn.sowell.ddxyz.model.canteen.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.ddxyz.model.canteen.dao.CanteenConfigDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenDeliveryWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekDeliveryCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryWaresListItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Repository
public class CanteenConfigDaoImpl implements CanteenConfigDao {
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
	
	@Override
	public void saveWares(PlainWares wares) {
		sFactory.getCurrentSession().save(wares);
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
	
	@Override
	public PlainDelivery getCanteenDeliveryOfTheWeek(CanteenWeekDeliveryCriteria criteria) {
		
		
	}
	
}
