package cn.sowell.ddxyz.model.common.core.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.pay.refund.RefundRequest;
import cn.sowell.copframe.weixin.pay.refund.RefundResult;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderDispenseResource;
import cn.sowell.ddxyz.model.common.core.OrderLog;
import cn.sowell.ddxyz.model.common.core.OrderManager;
import cn.sowell.ddxyz.model.common.core.OrderParameter;
import cn.sowell.ddxyz.model.common.core.OrderPayParameter;
import cn.sowell.ddxyz.model.common.core.OrderRefundParameter;
import cn.sowell.ddxyz.model.common.core.OrderToken;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.ProductManager;
import cn.sowell.ddxyz.model.common.core.ReceiverInfo;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderLog;
import cn.sowell.ddxyz.model.common.service.DataPersistenceService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

@Repository
public class DefaultOrderManager implements OrderManager, InitializingBean {

	/**
	 * 产品管理器
	 */
	@Resource
	ProductManager productManager;
	
	@Resource
	DataPersistenceService dpService;
	
	@Resource
	DeliveryManager dManager;
	
	@Resource
	WxPayService payService;
	
	@Resource
	WxConfigService configService;
	/**
	 * 
	 */
	byte[] keyLock = new byte[0];
	Map<Serializable, Order> orderMap = new LinkedHashMap<Serializable, Order>();
	/**
	 * 经验证无效的key，如果key被放在这里面，那么不需要再去持久化层访问
	 */
	Set<Serializable> invalidKeySet = new TreeSet<Serializable>();
	
	Logger logger = Logger.getLogger(OrderManager.class);
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("初始化订单管理器");
		
		//TODO: 设置其他初始数据
		
		logger.info("订单管理器初始化成功");
		
	}
	
	/**
	 * 私有方法，将order放到当前订单管理器中进行维护
	 * 但是不放到内存中
	 * @param order
	 */
	private void putOrder(Order order) {
		Serializable key = order.getKey();
		if(key != null){
			synchronized (orderMap) {
				if(!orderMap.containsKey(key)){
					orderMap.put(key, order);
					invalidKeySet.remove(key);
				}
			}
		}
	}
	
	@Override
	public Order getOrder(Serializable orderKey) {
		Assert.notNull(orderKey);
		Order order = orderMap.get(orderKey);
		if(order == null){
			//只当无效数组中不包含这个标识时
			if(!invalidKeySet.contains(orderKey)){
				//从持久化层获得订单
				order = dpService.getOrder(orderKey);
				if(order != null){
					synchronized (orderMap) {
						//再检查一遍map中是否包含key对应的订单对象
						Order temp = orderMap.get(orderKey);
						if(temp != null){
							return temp;
						}else{
							//将订单放到map中
							orderMap.put(orderKey, order);
						}
					}
				}else{
					//如果数据库中不存在这个key对应的订单，那么以后也找不到，此时把key放到数组当中
					invalidKeySet.add(orderKey);
				}
			}
		}
		return order;
	}
	/**
	 * 从内存中移除订单
	 * @param order
	 */
	@Override
	public void removeOrderFromCache(Order order){
		synchronized (orderMap) {
			orderMap.remove(order.getKey());
			//调用产品管理的方法，在map中移除产品
			productManager.removeProductFromMap(order.getProductSet());
		}
	}
	
	@Override
	public Order createOrder(OrderParameter orderParameter, WeiXinUser user,
			OrderToken orderToken) {
		Assert.notNull(orderParameter);
		Assert.notNull(user);
		Assert.notNull(orderToken);
		logger.debug("处理已经分配的订单");
		orderParameter.setDeliveryLocation(dManager.getDeliveryLocation((long) orderParameter.getDeliveryLocation().getId()));
		Delivery delivery = dManager.getDelivery(orderParameter.getTimePoint(), orderParameter.getDeliveryLocation());
		if(delivery != null){
			//构造订单对象
			Order order = buildOrder(orderParameter);
			if(order != null){
				//设置常用属性
				order.setDeliveryId(delivery.getId());
				order.setDeliveryLocation(orderParameter.getDeliveryLocation());
				order.setDeliveryTimePoint(orderParameter.getTimePoint());
				order.setTotalPrice(orderParameter.getTotalPrice());
				order.setOrderCode(generateOrderCode(orderParameter.getDeliveryLocation()));
				order.setOrderUser(user);
				//调用order的方法来设置或者覆盖属性
				order.fromOrderParameter(orderParameter);
				//产品管理器创建产品对象并且将产品对象放到订单对象中
				if(productManager.appendProduct(orderParameter.getProductParameter(), order)){
					//返回订单对象
					return order;
				}
			}
		}
		return null;
	}
	

	private String generateOrderCode(DeliveryLocation deliveryLocation) {
		StringBuffer buffer = new StringBuffer(deliveryLocation.getCode());
		buffer.append((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
		return buffer.toString();
	}

	@Override
	public void persistOrder(Order order) throws OrderException {
		Assert.notNull(order);
		if(order.getKey() == null){
			//如果key不存在，说明是要创建
			dpService.createOrder(order);
			//保存成功
			putOrder(order);
		}else{
			//如果key已经存在，那么就更新订单在数据库中的记录
			dpService.updateOrder(order);
			synchronized (orderMap) {
				orderMap.put(order.getKey(), order);
			}
		}
	}
	


	/**
	 * 构造订单对象示例
	 * @param orderParameter 
	 * @param resource
	 * @return
	 */
	private Order buildOrder(OrderParameter orderParameter) {
		PlainOrder pOrder = new PlainOrder();
		pOrder.setCreateTime(new Date());
		pOrder.setUpdateTime(pOrder.getCreateTime());
		DefaultOrder order = new DefaultOrder(pOrder, this, productManager, dpService);
		pOrder.setOrderStatus(Order.STATUS_DEFAULT);
		pOrder.setCommment(orderParameter.getComment());
		order.setDispenseResourceRequest(orderParameter.getDispenseResourceRequest());
		ReceiverInfo receiver = orderParameter.getReceiver();
		if(receiver != null){
			order.setReceiverContact(receiver.getContact());
			order.setReceiverName(receiver.getName());
			order.setReceiverAddress(receiver.getAddress());
		}
		return order;
	}

	
	private CheckResult checkOrderKey(Order order){
		CheckResult result = new CheckResult(true, "检查成功");
		Serializable key = order.getKey();
		if(key != null){
			Set<Product> productSet = order.getProductSet();
			if(productSet != null){
				for (Product product : productSet) {
					Serializable id = product.getId();
					if(id == null){
						return result.setResult(false, "订单对象中存在产品的id为null");
					}
				}
			}
			return result;
		}else{
			return result.setResult(false, "订单对象的key为null");
		}
	}
	
	
	@Override
	public void cacheOrder(Order order) {
		if(checkOrderKey(order).isSuc()){
			if(productManager.cacheProducts(order.getProductSet())){
				synchronized (orderMap) {
					orderMap.put(order.getKey(), order);
				}
			}
		}
	}
	
	@Transactional
	@Override
	public void payOrder(DefaultOrder order, OrderPayParameter payParameter) throws OrderException {
		Assert.notNull(order);
		Assert.notNull(payParameter);
		WeiXinUser user = payParameter.getPayUser();
		Assert.notNull(user);
		Delivery delivery = dManager.getDelivery(order.getDeliveryId());
		if(delivery != null){
			//向配送对象申请资源
			OrderDispenseResource resource = delivery.applyForDispenseResource(order.getDispenseResourceRequest());
			//锁定订单对象
			synchronized (order) {
				//将申请到的分配号放到产品对象中
				productManager.appendToProduct(resource, order);
				try {
					//调用订单管理器来设置订单的状态修改
					//包括订单产品和订单本身的状态修改
					order.doPay(payParameter);
					//记录日志
					doLog(order, user, 
							"订单" + order.getOrderCode()
							+ "已由用户" + user.getNickname() 
							+ "支付，实付金额" + payParameter.getActualPay() + "元",
							OrderLog.TYPE_ORDER_PAYED);
				} catch (Exception e) {
					//如果发生错误，那么释放占用的资源
					resource.releaseResource();
					//回滚所有持久化事务
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			}
		}
	}
	
	@Override
	public void refundOrder(DefaultOrder order,
			OrderRefundParameter refundParam) throws OrderException {
		//将请求转换为通用的退款请求对象
		RefundRequest refundReq = payService.buildRefundRequest(refundParam, order);
		
		//发送退款请求到微信服务器
		RefundResult result = payService.sendRefund(refundReq, true);
		if(result != null){
			if("SUCCESS".equals(result.getResultCode())){
				//退款请求成功
				//退款成功时设置订单的退款金额，同时设置订单下的所有产品状态为已退款
				order.doRefunded(refundParam.getRefundFee());
				
				
			}else if("FAIL".equals(result.getResultCode())){
				throw new OrderException("调用微信退款接口时返回失败，原因是[" + result.getReturnMsg() + "]");
			}else{
				throw new OrderException("微信退款接口返回未知状态码【" + result.getResultCode() + "】");
			}
		}else{
			//退款请求失败
			throw new OrderException("调用微信退款接口时发生异常，退款失败");
		}
		
	}
	
	@Override
	public void doLog(Order order, UserIdentifier user, String description,
			String operateType) {
		Assert.notNull(order);
		Assert.notNull(user);
		PlainOrderLog log = new PlainOrderLog();
		log.setOrderId((Long) order.getKey());
		log.setUserId((Long) user.getId());
		log.setDescription(description);
		log.setOperateType(operateType);
		log.setCreateTime(new Date());
		dpService.saveLog(log);
	}

	
	
}
