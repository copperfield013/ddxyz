package cn.sowell.ddxyz.model.canteen.dao.impl;

import java.util.ArrayList;
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
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.canteen.dao.CanteenDao;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
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

}
