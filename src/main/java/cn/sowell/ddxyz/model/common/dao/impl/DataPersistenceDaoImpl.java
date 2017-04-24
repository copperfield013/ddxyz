package cn.sowell.ddxyz.model.common.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderDetail;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.ProductDataHandler;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.dao.DataPersistenceDao;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
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
	public PlainDelivery getDelivery(Date timePoint, long locationId) {
		Assert.notNull(timePoint);
		String hql = "from PlainDelivery p where p.locationId = :locationId and p.timePoint = :timePoint";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("locationId", locationId)
			.setTimestamp("timePoint", timePoint);
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
		String sql = "update t_order_base set c_actual_pay = @actualPay where id = :orderId";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("orderId", orderId);
		if(actualPay != null){
			dQuery.setSnippet("actualPay", ":actualPay");
			dQuery.setParam("actualPay", actualPay);
		}else{
			dQuery.setSnippet("actualPay", "c_total_price");
		}
		Query query = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
		query.executeUpdate();
	}
}
