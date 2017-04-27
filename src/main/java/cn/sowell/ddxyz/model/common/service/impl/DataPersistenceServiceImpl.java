package cn.sowell.ddxyz.model.common.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;



import javax.annotation.Resource;



import org.springframework.stereotype.Service;
import org.springframework.util.Assert;



import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryKey;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.DispenseCode;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderManager;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.ProductManager;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.core.impl.DefaultDelivery;
import cn.sowell.ddxyz.model.common.core.impl.DefaultDispenseCode;
import cn.sowell.ddxyz.model.common.core.impl.DefaultOrder;
import cn.sowell.ddxyz.model.common.core.impl.DefaultProduct;
import cn.sowell.ddxyz.model.common.dao.DataPersistenceDao;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderLog;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderRefund;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.common.service.DataPersistenceService;
import cn.sowell.ddxyz.model.weixin.dao.WeiXinUserDao;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

@Service
public class DataPersistenceServiceImpl implements DataPersistenceService{
	@Resource
	DataPersistenceDao dpDao;
	@Resource
	DeliveryManager dManager;
	@Resource
	OrderManager oManage;
	@Resource
	ProductManager pManager;
	
	@Resource
	WeiXinUserDao userDao;
	
	@Override
	public Order getOrder(Serializable orderKey) {
		PlainOrder pOrder =  dpDao.getOrder((Long) orderKey);
		if(pOrder != null){
			DefaultOrder order = new DefaultOrder(pOrder, oManage, pManager, this);
			//从数据库拿到这个订单的所有产品对象
			List<PlainProduct> productList = dpDao.getProductsOfOrder((Long) orderKey);
			Set<Product> productSet = new LinkedHashSet<Product>();
			for (PlainProduct pProduct : productList) {
				DefaultProduct product = new DefaultProduct(pProduct, this);
				productSet.add(product);
			}
			//将订单内所有产品放到订单对象内
			order.setProductSet(productSet);
			return order;
		}
		return null;
	}

	@Override
	public void updateProductStatus(Product product, int status) {
		Assert.notNull(product);
		if(product.getId() != null){
			dpDao.updateProductStatus((long) product.getId(), status);
		}
	}

	@Override
	public Product getProduct(Serializable productId) {
		PlainProduct pProduct = dpDao.getProduct((long) productId);
		if(pProduct != null){
			return new DefaultProduct(pProduct, this);
		}
		return null;
	}
	@Override
	public void setOrderPayed(Order order) {
		Assert.notNull(order);
		dpDao.updateOrderStatus((long) order.getKey(), Order.STATUS_PAYED);
	}

	@Override
	public void setOrderCanceled(Order order) {
		Assert.notNull(order);
		//更新订单的状态为取消
		dpDao.updateOrderCanceledStatus((long) order.getKey(), Order.CAN_STATUS_CANCELED);
		//更新订单下所有产品的状态STATUS_CANCELED
		dpDao.updateProductCanceledStatusInOrder((long) order.getKey(), Product.CAN_STATUS_ABANDON);
	}

	@Override
	public void setOrderCompleted(Order order) {
		Assert.notNull(order);
		dpDao.updateOrderStatus((long) order.getKey(), Order.STATUS_COMPLETED);
	}

	@Override
	public void setOrderClosed(Order order) {
		Assert.notNull(order);
		dpDao.updateOrderStatus((long) order.getKey(), Order.STATUS_PAYED);
	}

	@Override
	public void setProductsStatus(Order order, int status) {
		Assert.notNull(order);
		dpDao.updateProductStatusInOrder((long) order.getKey(), status);
	}

	@Override
	public void createOrder(Order order) throws OrderException{
		Assert.notNull(order);
		dpDao.createOrder(order);
	}

	@Override
	public void updateOrder(Order order) throws OrderException {
		Assert.notNull(order);
		dpDao.updateOrder(order);
	}

	@Override
	public Delivery getDelivery(long waresId, DeliveryTimePoint timePoint,
			DeliveryLocation location) {
		Assert.notNull(timePoint);
		Assert.notNull(location);
		PlainDelivery pDelivery = dpDao.getDelivery(waresId, timePoint.getDatetime(), (long) location.getId());
		if(pDelivery != null){
			return new DefaultDelivery(pDelivery, dManager, this);
		}
		return null;
	}
	
	@Override
	public void saveLog(PlainOrderLog log) {
		Assert.notNull(log);
		dpDao.saveLog(log);
	}
	
	@Override
	public PlainLocation getDeliveryLocation(Long id) {
		Assert.notNull(id);
		return dpDao.getDeliveryLocation(id);
	}
	
	@Override
	public Delivery getDelivery(DeliveryKey deliveryKey) {
		Assert.notNull(deliveryKey);
		PlainDelivery pDelivery = null;
		if(deliveryKey.getDeliveryId() != null){
			pDelivery = dpDao.getDelivery((long) deliveryKey.getDeliveryId());
		}else if(deliveryKey.getLocation() != null && deliveryKey.getTimePoint() != null && deliveryKey.getWaresId() != null){
			Date deliveryDate = deliveryKey.getTimePoint().getDatetime();
			if(deliveryDate != null){
				pDelivery = dpDao.getDelivery(deliveryKey.getWaresId(), deliveryKey.getTimePoint().getDatetime(), (long) deliveryKey.getLocation().getId());
			}
		}
		if(pDelivery != null){
			return new DefaultDelivery(pDelivery, dManager, this);
		}
		return null;
	}
	
	@Override
	public WeiXinUser getUser(long orderUserId) {
		return userDao.getUser(orderUserId);
	}
	
	@Override
	public void setProductsOrdered(Set<Product> productSet)
			throws ProductException {
		try {
			Assert.notNull(productSet);
			for (Product product : productSet) {
				//遍历所有产品，获得产品分发号，并且更新所有产品的分发号
				DispenseCode dispenseCode = product.getDispenseCode();
				dpDao.updateProductDispenseCodeAndOrdered((long) product.getId(), dispenseCode.getCode());
			}
		} catch (Exception e) {
			throw new ProductException("持久化产品下单状态时发生异常", e);
		}
	}
	
	
	@Override
	public void setOrderRefund(PlainOrderRefund refund) throws OrderException {
		try {
			//持久化订单的退款状态和退款金额
			dpDao.updateOrderRefund(refund.getOrderId(), refund.getRefundFee());
			dpDao.updateOrderProductRefund(refund.getOrderId());
			dpDao.saveRefund(refund);
		} catch (Exception e) {
			throw new OrderException("持久化订单的退款时发生错误", e);
		}
	}
	
	@Override
	public void updateOrderActualPaied(Serializable orderId, Integer actualPay) {
		dpDao.updateOrderActualPaied((long) orderId, actualPay);
	}
	
	
	@Override
	public List<PlainDeliveryPlan> getTheDayUsablePlan() {
		return dpDao.getTheDayUsablePlan(new Date());
	}
	
	
	@Override
	public Map<DeliveryKey, Delivery> mergeDeliveries(Map<DeliveryKey, PlainDelivery> map) {
		Map<DeliveryKey, Delivery> result = new LinkedHashMap<DeliveryKey, Delivery>();
		//根据所有map的key，从数据库查看其配送记录是否存在，如果存在，则不添加，并将其数据库中对应的id放到key中
		Set<PlainDelivery> existsDeliveries = dpDao.getDeliveries(map.keySet());
		for (PlainDelivery pDelivery : existsDeliveries) {
			DeliveryKey key = new DeliveryKey(pDelivery.getWaresId(), new DeliveryTimePoint(pDelivery.getTimePoint()), new DeliveryLocation(pDelivery.getLocationId()));
			map.remove(key);
			key.setDeliveryId(pDelivery.getId());
			result.put(key, new DefaultDelivery(pDelivery, dManager, this));
		}
		//将剩下的不存在的配送持久化
		for (Entry<DeliveryKey, PlainDelivery> entry : map.entrySet()) {
			PlainDelivery pDelivery = entry.getValue();
			pDelivery.setCreateTime(new Date());
			dpDao.saveDelivery(pDelivery);
			DeliveryKey key = new DeliveryKey(pDelivery.getId());
			result.put(key, new DefaultDelivery(pDelivery, dManager, this));
		}
		return result;
	}
	
	@Override
	public void updateDispensedCount(Serializable deliveryId, int currentCount) {
		dpDao.updateDeliveryDispensedCount((long) deliveryId, currentCount);
	}
	@Override
	public Map<Integer, DispenseCode> getDispenseCodeTree(Delivery delivery) {
		Assert.notNull(delivery);
		List<PlainProduct> products = dpDao.getAllUsableProducts((long) delivery.getId());
		Map<Integer, DispenseCode> result = new HashMap<Integer, DispenseCode>();
		products.forEach(product -> {
			Integer key = product.getDispenseKey();
			DefaultDispenseCode code = new DefaultDispenseCode();
			code.setBelong(delivery);
			code.setCode(product.getDispenseCode());
			code.setKey(key);
			if(key != null){
				result.put(key, code);
			}
		});
		return result;
	}
	
	@Override
	public PlainOrder getPlainOrder(long orderId) {
		return dpDao.getOrder(orderId);
	}
	
}
