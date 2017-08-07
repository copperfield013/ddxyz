package cn.sowell.ddxyz.model.canteen.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.canteen.dao.CanteenDao;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDeliveyWares;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Repository
public class CanteenDaoImpl implements CanteenDao{

	@Resource
	SessionFactory sFactory;
	
	@Override
	public void updateCurrentCount(Long deliveryWaresId, int updateCount) {
		String sql = "update t_delivery_wares set c_current_count = :count where id = :dWaresId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("count", updateCount)
				.setLong("dWaresId", deliveryWaresId)
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDeliveryWares> getDeliveryWaresList(Long deliveryId) {
		return sFactory.getCurrentSession().createCriteria(PlainDeliveryWares.class)
			.add(Restrictions.eq("deliveryId", deliveryId))
			.list();
	}

	@Override
	public PlainDelivery getDelivery(Long deliveryId) {
		return sFactory.getCurrentSession().get(PlainDelivery.class, deliveryId);
	}

	@Override
	public PlainWares getWares(Long waresId) {
		return sFactory.getCurrentSession().get(PlainWares.class, waresId);
	}

	@Override
	public void createOrder(PlainCanteenOrder cOrder,
			List<PlainProduct> products) {
		Session session = sFactory.getCurrentSession();
		Long orderId = (Long) session.save(cOrder.getpOrder());
		cOrder.setOrderId(orderId);
		session.save(cOrder);
		products.forEach(product->{
			product.setOrderId(orderId);
			session.save(product);
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDeliveryPlan> getThedayNullWaresIdPlans(Date theDay) {
		String hql = "from PlainDeliveryPlan plan where waresId is null and plan.startDate <= :theDay and plan.endDate > :theDay and (plan.disabled is null or plan.disabled <> 1)";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setTimestamp("theDay", theDay);
		return query.list();
	}
	
	@Override
	public Map<PlainDeliveryPlan, List<PlainDeliveryPlanWares>> getPlanWaresMap(
			List<PlainDeliveryPlan> plans) {
		LinkedHashMap<PlainDeliveryPlan, List<PlainDeliveryPlanWares>> result = new LinkedHashMap<PlainDeliveryPlan, List<PlainDeliveryPlanWares>>();
		Map<Long, PlainDeliveryPlan> planMap = CollectionUtils.toMap(plans, plan->plan.getId());
		Criteria criteria = sFactory.getCurrentSession().createCriteria(PlainDeliveryPlanWares.class);
		@SuppressWarnings("unchecked")
		List<PlainDeliveryPlanWares> planWaresList = 
				criteria.add(Restrictions.in("planId", planMap.keySet()))
				.list();
		planWaresList.forEach(pw->{
			List<PlainDeliveryPlanWares> pWaresList = result.get(planMap.get(pw.getPlanId()));
			if(pWaresList == null){
				pWaresList = new ArrayList<PlainDeliveryPlanWares>();
				result.put(planMap.get(pw.getPlanId()), pWaresList);
			}
			pWaresList.add(pw);
		});
		return result;
	}
	
	
	@Override
	public void saveDelivery(Map<PlainDelivery, List<PlainDeliveryWares>> dWaresMap) {
		Session session = sFactory.getCurrentSession();
		dWaresMap.forEach((delivery, dWaresList) -> {
			Long id = (Long) session.save(delivery);
			dWaresList.forEach(dWares->{
				dWares.setDeliveryId(id);
				session.save(dWares);
			});
		});
	}
	
	@Override
	public void savePlan(PlainDeliveryPlan plan,
			List<PlainDeliveryPlanWares> planWaresList) {
		Session session = sFactory.getCurrentSession();
		Long planId = (Long) session.save(plan);
		planWaresList.forEach(planWares->{
			planWares.setPlanId(planId);
			session.save(planWares);
		});
	}

	@Override
	public void saveWares(PlainWares wares) {
		sFactory.getCurrentSession().save(wares);
	}

	@Override
	public PlainDeliveryWares getDeliveryWare(Long deliveryWaresId) {
		return sFactory.getCurrentSession().get(PlainDeliveryWares.class, deliveryWaresId);
	}
	
	
	@Override
	public PlainDelivery getDeliveryOfThisWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		Date start = cal.getTime();
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		cal.add(Calendar.MILLISECOND, -1);
		Date end = cal.getTime();
		
		Criteria criteria = sFactory.getCurrentSession().createCriteria(PlainDelivery.class);
		criteria.add(Restrictions.between("timePoint", start, end))
			.add(Restrictions.eq("type", "canteen"))
			.setMaxResults(1);
		
		return (PlainDelivery) criteria.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenDeliveyWares> getCanteenDeliveryWares(Long deliveryId) {
		String sql = 
				"SELECT" +
						"	w.id dwaresid," +
						"	wb.id waresid," +
						"	wb.c_name waresname," +
						"	wb.c_base_price price," +
						"   wb.c_price_unit price_unit," +
						"	w.c_max_count maxcount," +
						"	w.c_current_count currencount" +
						" FROM" +
						"	t_delivery_base d" +
						" LEFT JOIN t_delivery_wares w ON d.id = w.delivery_id" +
						" left join t_wares_base wb on w.wares_id = wb.id" +
						" where d.id = :deliveryId";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("deliveryId", deliveryId);
		SQLQuery query = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(CanteenDeliveyWares.class));
		return query.list();
		
	}
	
	@Override
	public PlainCanteenOrder getLastOrderOfUser(Long userId) {
		
		String sql = 
				"SELECT" +
						"	*" +
						" FROM" +
						"	t_canteen_order co" +
						" LEFT JOIN t_order_base o ON co.order_id = o.id" +
						" WHERE" +
						"	o.order_user_id = : userId" +
						" ORDER BY" +
						"	o.create_time DESC";
		
		Session session = sFactory.getCurrentSession();
		
		SQLQuery query = session.createSQLQuery(sql);
		query.setLong("userId", userId)
			.setMaxResults(1);
		query.setResultTransformer(new ResultTransformer() {
			
			private static final long serialVersionUID = 1L;
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				PlainCanteenOrder cOrder = HibernateRefrectResultTransformer.getInstance(PlainCanteenOrder.class).transformTuple(tuple, aliases);
				PlainOrder pOrder = HibernateRefrectResultTransformer.getInstance(PlainOrder.class).transformTuple(tuple, aliases);
				cOrder.setpOrder(pOrder);
				return cOrder;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		return (PlainCanteenOrder) query.uniqueResult();
	}
	
}
