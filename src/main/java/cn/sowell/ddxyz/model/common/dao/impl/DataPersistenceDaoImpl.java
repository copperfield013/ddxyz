package cn.sowell.ddxyz.model.common.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.ddxyz.model.common.core.DeliveryKey;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderDetail;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.ProductDataHandler;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.dao.DataPersistenceDao;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderLog;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderRefund;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
@Repository
public class DataPersistenceDaoImpl implements DataPersistenceDao{

	@Resource
	SessionFactory sFactory;
	
	@Override
	public PlainOrder getOrder(long orderId) {
		return sFactory.getCurrentSession().get(PlainOrder.class, orderId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainProduct> getProductsOfOrder(long orderId) {
		String hql = "from PlainProduct p where p.orderId = :orderId";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("orderId", orderId);
		return  query.list();
	}

	@Override
	public void updateProductStatus(long id, int status) {
		String sql = "update t_product_base set c_status = :status where id = :id";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("status", status);
		query.setLong("id", id);
		query.executeUpdate();
	}
	
	@Override
	public void updateProductStatusInOrder(long orderId, int status) {
		String sql = "update t_product_base set c_status = :status where order_id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("status", status);
		query.setLong("orderId", orderId);
		query.executeUpdate();
	}
	
	
	@Override
	public PlainProduct getProduct(long productId) {
		return sFactory.getCurrentSession().get(PlainProduct.class, productId);
	}
	
	@Override
	public void updateOrderStatus(long orderId, int status) {
		String sql = "update t_order_base set c_status = :status where id = :id";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("status", status);
		query.setLong("id", orderId);
		query.executeUpdate();
	}
	
	@Override
	public void createOrder(Order order) throws OrderException{
		OrderDetail detail = order.getOrderDetail();
		Session session = sFactory.getCurrentSession();
		PlainOrder plainOrder = (PlainOrder) detail;
		//判断详情对象类型
		Long orderId = (Long) session.save(detail);
		if(orderId != null){
			//创建成功
			plainOrder.setId(orderId);
			
			//创建订单下所有产品的记录
			Set<Product> pSet = order.getProductSet();
			for (Product product : pSet) {
				PlainProduct productInfo = product.getProductInfo();
				productInfo.setOrderId(orderId);
				Long productId = (Long) session.save(productInfo);
				if(productId != null){
					ProductDataHandler handler = product.getOptionHandler();
					try {
						if(handler != null){
							handler.saveAuxiliary(productId, session, product, order);
						}
						productInfo.setId(productId);
					} catch (ProductException e) {
						throw new OrderException("调用类[" + handler.getClass() + "]的persistOption方法时发生错误", e);
					}
				}
			}
		}
	}
	
	
	@Override
	public void updateOrder(Order order) {
		//TODO 更新订单信息和订单下的所有产品的信息
	}
	
	@Override
	public PlainDelivery getDelivery(long waresId, Date timePoint, long locationId) {
		Assert.notNull(timePoint);
		String hql = "from PlainDelivery p where p.locationId = :locationId and p.timePoint = :timePoint and p.waresId = :waresId";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("locationId", locationId)
			.setTimestamp("timePoint", timePoint)
			.setLong("waresId", waresId);
		return (PlainDelivery) query.uniqueResult();
	}
	
	
	@Override
	public void saveLog(PlainOrderLog log) {
		Assert.notNull(log);
		sFactory.getCurrentSession().save(log);
	}
	
	@Override
	public PlainLocation getDeliveryLocation(long id) {
		return sFactory.getCurrentSession().get(PlainLocation.class, id);
	}
	
	
	@Override
	public PlainDelivery getDelivery(long deliveryId) {
		return sFactory.getCurrentSession().get(PlainDelivery.class, deliveryId);
	}
	
	@Override
	public void updateProductDispenseCodeAndOrdered(long productId, String code) {
		String sql = "update t_product_base set c_dispense_code = :dispenseCode, c_status = :status where id = :productId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("dispenseCode", code)
			.setInteger("status", Product.STATUS_ORDERED)
			.setLong("productId", productId);
		query.executeUpdate();
	}
	
	
	@Override
	public void updateOrderRefund(long orderId, int refundFee) {
		String sql = "update t_order_base set c_canceled_status = :canStatus, c_refund_fee = :refundFee where id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("canStatus", Order.CAN_STATUS_REFUNDED)
				.setInteger("refundFee", refundFee)
				.setLong("orderId", orderId)
				.executeUpdate();
		
	}
	
	@Override
	public void updateOrderProductRefund(long orderId) {
		String sql = "update t_product_base set c_canceled_status = :canStatus, c_refund_fee = c_price where order_id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("canStatus", Product.CAN_STATUS_ORDER_CANCEL)
			.setLong("orderId", orderId)
			.executeUpdate();
	}
	
	@Override
	public void saveRefund(PlainOrderRefund refund) {
		sFactory.getCurrentSession().save(refund);
	}
	
	
	@Override
	public void updateOrderCanceledStatus(long orderId, String canStatusCanceled) {
		String sql = "update t_order_base set c_canceled_status = :canStatus where id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("canStatus", canStatusCanceled)
				.setLong("orderId", orderId)
				.executeUpdate();
	}
	
	
	@Override
	public void updateProductCanceledStatusInOrder(long orderId,
			String canStatus) {
		String sql = "update t_product_base set c_canceled_status = :canStatus where order_id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("canStatus", canStatus)
			.setLong("orderId", orderId)
			.executeUpdate();
	}
	
	@Override
	public void updateOrderActualPaied(long orderId, Integer actualPay) {
		String sql = "update t_order_base set c_actual_pay = @actualPay, c_pay_time = :payTime where id = :orderId";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("orderId", orderId);
		if(actualPay != null){
			dQuery.setSnippet("actualPay", ":actualPay");
			dQuery.setParam("actualPay", actualPay);
		}else{
			dQuery.setSnippet("actualPay", "c_total_price");
		}
		dQuery.setParam("payTime", new Date());
		Query query = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDeliveryPlan> getTheDayUsablePlan(Date theDay) {
		String hql = "from PlainDeliveryPlan plan where plan.startDate <= :theDay and plan.endDate > :theDay";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setTimestamp("theDay", theDay);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<PlainDelivery> getDeliveries(Set<DeliveryKey> keySet) {
		Map<Long, Map<DeliveryLocation, Set<DeliveryTimePoint>>> map = new HashMap<Long, Map<DeliveryLocation, Set<DeliveryTimePoint>>>();
		for (DeliveryKey key : keySet) {
			if(key.getWaresId() != null && key.getLocation() != null && key.getTimePoint() != null){
				Map<DeliveryLocation, Set<DeliveryTimePoint>> innerMap = map.get(key.getWaresId());
				if(innerMap == null){
					innerMap = new HashMap<DeliveryLocation, Set<DeliveryTimePoint>>();
					map.put(key.getWaresId(), innerMap);
				}
				Set<DeliveryTimePoint> timeSet = innerMap.get(key.getLocation());
				if(timeSet == null){
					timeSet = new LinkedHashSet<DeliveryTimePoint>();
					innerMap.put(key.getLocation(), timeSet);
				}
				timeSet.add(key.getTimePoint());
			}
		}
		Set<PlainDelivery> result = new LinkedHashSet<PlainDelivery>();
		
		if(!map.isEmpty()){
			StringBuffer buffer= new StringBuffer();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			int pWaresId = 0, pLocation = 0, pTimePoint = 0;
			for (Entry<Long, Map<DeliveryLocation, Set<DeliveryTimePoint>>> entry : map.entrySet()) {
				buffer.append("or (p.waresId = :waresId" + pWaresId + " and(");
				paramMap.put("waresId" + pWaresId++ , entry.getKey());
				StringBuffer buffer1 = new StringBuffer();
				for (Entry<DeliveryLocation, Set<DeliveryTimePoint>> innerEntry : entry.getValue().entrySet()) {
					Set<DeliveryTimePoint> timeSet = innerEntry.getValue();
					buffer1.append("or p.locationId = :locationId" + pLocation + " and (");
					paramMap.put("locationId" + pLocation++, innerEntry.getKey().getId());
					StringBuffer buffer2 = new StringBuffer();
					for (DeliveryTimePoint timePoint : timeSet) {
						buffer2.append("or p.timePoint = :timePoint" + pTimePoint);
						paramMap.put("timePoint" + pTimePoint++, timePoint.getDatetime());
					}
					buffer1.append(buffer2.delete(0, 2));
					buffer1.append(")");
				}
				buffer.append(buffer1.delete(0, 2));
				buffer.append(")");
				buffer.append(")");
			}
			buffer.delete(0, 2);
			buffer.insert(0, "from PlainDelivery p where ");
			
			DeferedParamQuery dQuery = new DeferedParamQuery(buffer.toString());
			paramMap.forEach((paramName, paramValue) -> {
				dQuery.setParam(paramName, paramValue);
			});
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), false, null);
			result.addAll(query.list());
		}
		return result;
	}
	
	
	@Override
	public void saveDelivery(PlainDelivery pDelivery) {
		Serializable id = sFactory.getCurrentSession().save(pDelivery);
		pDelivery.setId((Long) id);
	}
	
	@Override
	public void updateDeliveryDispensedCount(long deliveryId,
			int currentCount) {
		String sql = "update t_delivery_base set c_current_count = :currentCount where id = :deliveryId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query
			.setInteger("currentCount", currentCount)
			.setLong("deliveryId", deliveryId);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainProduct> getAllUsableProducts(long deliveryId) {
		Session session = sFactory.getCurrentSession();
		PlainDelivery pDelivery = session.get(PlainDelivery.class, deliveryId);
		if(pDelivery != null){
			String sql = "SELECT p.*" +
					" FROM" +
					"	t_product_base p" +
					" LEFT JOIN t_order_base o ON p.order_id = o.id" +
					" WHERE" +
					"	o.location_id = :locationId" +
					" AND o.c_time_point = :timePoint" +
					" AND p.wares_id = :waresId" +
					" AND p.c_status > :defProductStatus" +
					" AND p.c_canceled_status is null";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query
				.setLong("locationId", pDelivery.getLocationId())
				.setTimestamp("timePoint", pDelivery.getTimePoint())
				.setLong("waresId", pDelivery.getWaresId())
				.setInteger("defProductStatus", Product.STATUS_DEFAULT)
				.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainProduct.class));
			return query.list();
		}
		return new ArrayList<PlainProduct>();
	}
	
}
