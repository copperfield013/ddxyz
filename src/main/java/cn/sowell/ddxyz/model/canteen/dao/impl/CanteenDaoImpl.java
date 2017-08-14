package cn.sowell.ddxyz.model.canteen.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.util.Assert;

import cn.sowell.copframe.dao.deferedQuery.ColumnMapResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.SimpleMapWrapper;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.canteen.dao.CanteenDao;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDeliveyWares;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenUserCacheInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenOrderInfoItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Repository
public class CanteenDaoImpl implements CanteenDao{

	@Resource
	SessionFactory sFactory;
	
	
	@Resource
	FrameDateFormat dateFormat;
	
	@Override
	public void updateCurrentCount(Long deliveryWaresId, int updateCount) {
		String sql = "update t_delivery_wares set c_current_count = :count where id = :dWaresId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("count", updateCount)
				.setLong("dWaresId", deliveryWaresId)
				.executeUpdate();
	}
	@Override
	public void addCurrentCount(long dWaresId, int addition) {
		String sql = "update t_delivery_wares set c_current_count = c_current_count + :addition where id = :dWaresId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("addition", addition)
				.setLong("dWaresId", dWaresId)
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
		Date
			start = dateFormat.getTheDayOfWeek(Calendar.MONDAY, 0),
			end = dateFormat.getTheDayOfWeek(new Date(), Calendar.MONDAY, 6, 23, 59, 59);
		
		Criteria criteria = sFactory.getCurrentSession().createCriteria(PlainDelivery.class);
		criteria.add(Restrictions.between("timePoint", start, end))
			.add(Restrictions.eq("type", "canteen"))
			.setMaxResults(1);
		
		return (PlainDelivery) criteria.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenDeliveyWares> getCanteenDeliveryWares(Long deliveryId, Boolean disabled) {
		String sql = 
				"SELECT" +
						"	w.id dwaresid," +
						"	wb.id waresid," +
						"	wb.c_name waresname," +
						"	wb.c_base_price price," +
						"   wb.c_price_unit price_unit," +
						"   wb.c_thumb_uri thumburi," +
						"	w.c_max_count maxcount," +
						"	w.c_current_count currencount" +
						" FROM" +
						"	t_delivery_base d" +
						" LEFT JOIN t_delivery_wares w ON d.id = w.delivery_id" +
						" left join t_wares_base wb on w.wares_id = wb.id" +
						" where d.id = :deliveryId";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("deliveryId", deliveryId);
		if(disabled != null){
			if(disabled){
				dQuery.appendCondition("and w.c_disabled = :disabled");
				dQuery.appendCondition("and wb.c_disabled = :disabled");
			}else{
				dQuery.appendCondition("and (w.c_disabled is null or w.c_disabled <> :disabled)");
				dQuery.appendCondition("and (wb.c_disabled is null or wb.c_disabled <> :disabled)");
			}
			dQuery.setParam("disabled", 1);
		}
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
						"	o.order_user_id = :userId" +
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainProduct> getProducts(Long orderId) {
		return sFactory.getCurrentSession()
					.createCriteria(PlainProduct.class)
					.add(Restrictions.eq("orderId", orderId))
					.list();
	}
	
	@Override
	public PlainCanteenOrder getCanteenOrder(Long orderId) {
		return sFactory.getCurrentSession().get(PlainCanteenOrder.class, orderId);
	}
	
	@Override
	public PlainOrder getOrder(Long orderId) {
		return sFactory.getCurrentSession().get(PlainOrder.class, orderId);
	}
	
	
	
	@Override
	public void appendProduct(Long orderId, Long dWaresId, int count) {
		Assert.notNull(orderId);
		PlainDeliveryWares deliveryWares = getDeliveryWare(dWaresId);
		if(deliveryWares != null){
			PlainWares wares = getWares(deliveryWares.getWaresId());
			Session session = sFactory.getCurrentSession();
			Date createTime = new Date();
			for (int i = 0; i < count; i++) {
				PlainProduct product = new PlainProduct();
				product.setDeliveryWaresId(dWaresId);
				product.setWaresId(wares.getId());
				product.setName(wares.getName());
				product.setPrice(wares.getBasePrice());
				product.setThumbUri(wares.getThumbUri());
				product.setCreateTime(createTime);
				product.setOrderId(orderId);
				session.save(product);
			}
		}
	}
	
	@Override
	public void deleteProducts(List<Long> delList) {
		String sql = "delete from t_product_base where id in (:ids)";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameterList("ids", delList);
		query.executeUpdate();
	}
	
	@SuppressWarnings("serial")
	@Override
	public Map<Long, List<Long>> getDeliveryWaresProductIdsMap(Long orderId) {
		String sql = "select p.id, p.delivery_wares_id from t_product_base p where p.order_id = :orderId";
		Session session = sFactory.getCurrentSession();
		
		SQLQuery productQuery = session.createSQLQuery(sql);
		productQuery.setLong("orderId", orderId);
		Map<Long, List<Long>> dWaresProductListMap = new HashMap<Long, List<Long>>();
		productQuery.setResultTransformer(new ColumnMapResultTransformer<byte[]>() {

			@Override
			protected byte[] build(SimpleMapWrapper mapWrapper) {
				Long dWaresId = mapWrapper.getLong("delivery_wares_id");
				List<Long> productIdList = dWaresProductListMap.get(dWaresId);
				if(productIdList == null){
					productIdList = new ArrayList<Long>();
					dWaresProductListMap.put(dWaresId, productIdList);
				}
				productIdList.add(mapWrapper.getLong("id"));
				return null;
			}
		}).list();
		
		return dWaresProductListMap;
	}
	
	
	@Override
	public Integer getDeliveryWaresRemain(long dWaresId) throws OrderResourceApplyException {
		PlainDeliveryWares deliveryWares = getDeliveryWare(dWaresId);
		if(deliveryWares == null){
			throw new OrderResourceApplyException("没有找到deliveryWaresId[" + dWaresId + "]对应的商品配送");
		}
		if(deliveryWares.getMaxCount() == null){
			return null;
		}else{
			return deliveryWares.getMaxCount() - deliveryWares.getCurrentCount();
		}
	}
	
	@SuppressWarnings("serial")
	@Override
	public CanteenUserCacheInfo getOrderUserInfo(Long orderId) {
		String sql = 
				"SELECT" +
				"	co.c_depart," +
				"	o.c_receiver_name," +
				"	o.order_user_id," +
				"	o.c_receiver_contact" +
				" FROM" +
				"	t_canteen_order co LEFT JOIN t_order_base o ON co.order_id = o.id" +
				" WHERE" +
				"	o.id = :orderId";
		
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("orderId", orderId);
		query.setResultTransformer(new ColumnMapResultTransformer<CanteenUserCacheInfo>() {

			@Override
			protected CanteenUserCacheInfo build(SimpleMapWrapper mapWrapper) {
				CanteenUserCacheInfo user = new CanteenUserCacheInfo();
				user.setUserId(mapWrapper.getLong("order_user_id"));
				user.setContact(mapWrapper.getString("c_receiver_contact"));
				user.setDepart(mapWrapper.getString("c_depart"));
				user.setName(mapWrapper.getString("c_receiver_name"));
				return user;
			}
		});
		return (CanteenUserCacheInfo) query.uniqueResult();
	}
	
	
	@Override
	public void updateOrder(PlainCanteenOrder cOrder) {
		Session session = sFactory.getCurrentSession();
		session.update(cOrder);
		session.update(cOrder.getpOrder());
	}
	
	@Override
	public void setOrderCanceled(long orderId, String cancelStatus) {
		String sql = "update t_order_base set c_canceled_status = :cancelStatus where id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("cancelStatus", cancelStatus)
			.setLong("orderId", orderId)
			.executeUpdate();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenOrderInfoItem> getOrderPageList(long userId, CommonPageInfo pageInfo) {
		String sql = "SELECT "
				+ "	co.order_id,"
				+ "	co.c_depart,"
				+ "	co.c_order_close_time,"
				+ "	ob.c_order_code,"
				+ "	ob.c_location_name,"
				+ "	ob.c_time_point,"
				+ "	ob.c_status,"
				+ "	ob.c_canceled_status,"
				+ "	ob.c_total_price,"
				+ "	ob.c_receiver_contact,"
				+ "	ob.c_receiver_address,"
				+ "	ob.c_receiver_name,"
				+ "	ob.create_time"
				+ "	FROM"
				+ "	t_canteen_order co"
				+ "	LEFT JOIN "
				+ "	t_order_base ob"
				+ "	on co.order_id = ob.id "
				+ "	WHERE"
				+ " ob.order_user_id = :userId "
				+ "	ORDER BY ob.create_time DESC";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("userId", userId);
		Session session = sFactory.getCurrentSession();
		Query countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
			QueryUtils.setPagingParamWithCriteria(query, pageInfo);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(CanteenOrderInfoItem.class));
			return query.list();
		}
		return new ArrayList<CanteenOrderInfoItem>();
	}
	
}
