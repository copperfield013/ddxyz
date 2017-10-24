package cn.sowell.ddxyz.model.common.core.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.util.Assert;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.weixin.pay.paied.WxPayStatus;
import cn.sowell.copframe.weixin.pay.prepay.GoodsDetail;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.DispenseResourceRequest;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderDetail;
import cn.sowell.ddxyz.model.common.core.OrderDispenseResource;
import cn.sowell.ddxyz.model.common.core.OrderLog;
import cn.sowell.ddxyz.model.common.core.OrderManager;
import cn.sowell.ddxyz.model.common.core.OrderParameter;
import cn.sowell.ddxyz.model.common.core.OrderPayParameter;
import cn.sowell.ddxyz.model.common.core.OrderRefundParameter;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.ProductManager;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderRefund;
import cn.sowell.ddxyz.model.common.service.DataPersistenceService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DefaultOrder implements Order{
	
	
	private PlainOrder pOrder;
	private Set<Product> productSet = new LinkedHashSet<Product>();
	private OrderDispenseResource dispenseResource;
	private WeiXinUser orderUser;
	private DispenseResourceRequest dispenseResourceRequest;
	private DataPersistenceService dpService;
	private ProductManager productManager;
	private OrderManager oManager;
	private Delivery delivery;
	
	public DefaultOrder(PlainOrder pOrder, Delivery delivery, OrderManager oManager, ProductManager productManager, DataPersistenceService dpService) {
		Assert.notNull(pOrder);
		Assert.notNull(delivery);
		Assert.notNull(oManager);
		Assert.notNull(productManager);
		Assert.notNull(dpService);
		this.pOrder = pOrder;
		this.oManager = oManager;
		this.productManager = productManager;
		this.dpService = dpService;
		this.delivery = delivery;
	}
	
	@Override
	public Serializable getKey() {
		return pOrder.getId();
	}
	
	
	@Override
	public void setKey(Serializable orderKey) {
		pOrder.setId((Long) orderKey);
	}
	
	@Override
	public String getOrderCode(){
		return pOrder.getOrderCode();
	}
	@Override
	public void setOrderCode(String orderCode){
		pOrder.setOrderCode(orderCode);
	}

	@Override
	public DeliveryLocation getDeliveryLocation() {
		DeliveryLocation location = new DeliveryLocation();
		location.setId(pOrder.getLocationId());
		location.setLocationName(pOrder.getLocationName());
		return location;
	}

	@Override
	public void setDeliveryLocation(DeliveryLocation location) {
		pOrder.setLocationId((Long) location.getId());
		pOrder.setLocationName(location.getLocationName());
	}

	@Override
	public DeliveryTimePoint getDeliveryTimePoint() {
		if(pOrder.getTimePoint() != null){
			DeliveryTimePoint timepoint = new DeliveryTimePoint(pOrder.getTimePoint());
			return timepoint;
		}
		return null;
	}

	@Override
	public void setDeliveryTimePoint(DeliveryTimePoint timePoint) {
		Assert.notNull(timePoint);
		pOrder.setTimePoint(timePoint.getDatetime());
	}

	@Override
	public Serializable getDeliveryId() {
		return pOrder.getDeliveryId();
	}
	
	@Override
	public void setDeliveryId(Serializable deliveryId) {
		pOrder.setDeliveryId((Long) deliveryId);
	}
	

	@Override
	public int getOrderStatus() {
		return pOrder.getOrderStatus();
	}
	
	@Override
	public String getCancelStatus() {
		return pOrder.getCanceledStatus();
	}

	@Override
	public synchronized void pay(OrderPayParameter payParam) throws OrderException{
		if(this.getCancelStatus() != null){
			throw new OrderException("订单已取消，无法支付。当前订单取消状态不为空，为[" + this.getCancelStatus() + "]");
		}
		if(this.getOrderStatus() >= Order.STATUS_PAYED){
			throw new OrderException("订单已经支付，无需再次支付，当前订单状态为[" + this.getOrderStatus() + "]");
		}
		if(!getPayExpired()){
			//从微信服务器获取当前订单的支付状态
			WxPayStatus payStatus = checkWxPayStatus();
			//调用微信支付查询订单接口，检查付款是否完成
			if(WxPayStatus.TRADESTATUE_SUC.equals(payStatus.getTradeState())){
				oManager.payOrder(this, payParam);
			}else{
				throw new OrderException("该订单尚未成功支付，不可设置订单状态。该订单微信交易状态[" + payStatus.getTradeState() + ":" + payStatus.getTradeStateDesc() + "]");
			}
		}else{
			throw new OrderException("订单已超出可支付时间，订单的最晚支付时间为[" + getPayExpireTime() + "]");
		}
	}
	
	public void doPay(OrderPayParameter payParameter) throws OrderException {
		//检查支付请求的合理性
		CheckResult checkResult = checkPayParameter(payParameter);
		if(checkResult.isSuc()){
			//持久化订单为已支付
			dpService.setOrderPayed(this);
			//更新订单的支付金额
			dpService.updateOrderActualPaied(this.getKey(), payParameter.getActualPay());
			try {
				//修改产品状态
				productManager.orderProducts(this, true);
				//修改订单状态
				pOrder.setOrderStatus(Order.STATUS_PAYED);
				PlainOrder persistedOrder = dpService.getPlainOrder((long) getKey());
				if(persistedOrder != null){
					pOrder.setActualPay(persistedOrder.getActualPay());
				}
			} catch (ProductException e) {
				throw new OrderException("订单的产品修改时发生异常", e);
			}
		}else{
			throw new OrderException("订单支付时检测发现错误，不能支付订单,原因【" + checkResult.getReason() + "】");
		}
	}
	
	@Override
	public synchronized void complete(UserIdentifier user) throws OrderException {
		CheckResult result = Order.checkOrderToStatus(this, Order.STATUS_COMPLETED);
		if(result.isSuc()){
			try {
				//持久化设置订单完成状态，并且设置订单内所有产品状态为用户已确认
				dpService.setOrderCompleted(this);
				//移除订单以及订单下所有产品的对象
				oManager.removeOrderFromCache(this);
				oManager.doLog(this, user, "订单确认成功", OrderLog.TYPE_COMPLETE);
			} catch (Exception e) {
				throw new OrderException("订单完成状态持久化时出现异常", e);
			}
		}else{
			throw new OrderException("订单完成时检测发现错误，不能完成订单, 原因【" + result.getReason() + "】");
		}
	}
	
	@Override
	public synchronized void cancel(UserIdentifier cancelUser) throws OrderException {
		CheckResult result = Order.checkOrderToCancelStatus(this, Order.CAN_STATUS_CANCELED);
		if(result.isSuc()){
			//持久化修改订单的状态为取消，并且修改订单内产品的状态为遗弃
			dpService.setOrderCanceled(this);
			//处理过的产品对象map，key是产品对象，value是处理后返回的用于回滚的key
			Map<Product, Integer> handledProductMap = new LinkedHashMap<Product, Integer>();
			
			for (Product product : productSet) {
				try {
					//设置产品的取消状态，并且将用于回退的key放到map中
					handledProductMap.put(product, product.setCancelStatusWithoutPersistence(Product.CAN_STATUS_ABANDON));
				} catch (ProductException e) {
					//回退产品的状态
					for (Entry<Product, Integer> rollbackEntry : handledProductMap.entrySet()) {
						rollbackEntry.getKey().setCancelStatusNull(rollbackEntry.getValue());
					}
					throw new OrderException("设置产品的状态时发生错误", e);
				}
			}
			//记录日志
			oManager.doLog(this, cancelUser, "取消订单成功，操作人【" + cancelUser.getNickname()
					+ "】，订单描述【" + this.getDescription() + "】", OrderLog.TYPE_CANCELED);
		}else{
			throw new OrderException("订单取消时检测发现错误，不能取消订单, 原因【" + result.getReason() + "】");
		}
	}
	
	@Override
	public synchronized void close() throws OrderException {
		CheckResult result = Order.checkOrderToCancelStatus(this, Order.CAN_STATUS_CLOSED);
		if(result.isSuc()){
			try {
				//持久化设置订单关闭状态，并且将订单内所有产品的状态设置为遗弃
				dpService.setOrderClosed(this);
				//处理过的产品对象map，key是产品对象，value是处理后返回的用于回滚的key
				Map<Product, Integer> handledProductMap = new LinkedHashMap<Product, Integer>();
				
				for (Product product : productSet) {
					try {
						//设置产品的关闭状态，并且将用于回退的key放到map中
						handledProductMap.put(product, product.setCancelStatusWithoutPersistence(Product.CAN_STATUS_ABANDON));
					} catch (ProductException e) {
						//回退产品的状态
						for (Entry<Product, Integer> rollbackEntry : handledProductMap.entrySet()) {
							rollbackEntry.getKey().setCancelStatusNull(rollbackEntry.getValue());
						}
						throw new OrderException("设置产品的状态时发生错误", e);
					}
				}
			} catch (Exception e) {
				throw new OrderException("订单关闭状态持久化时出现异常", e);
			}
		}else{
			throw new OrderException("订单关闭时检测发现错误，不能关闭订单, 原因【" + result.getReason() + "】");
		}
	}
	
	@Override
	public void refundTotal(UserIdentifier user) throws OrderException{
		//全额退款
		OrderRefundParameter param = new OrderRefundParameter();
		param.setRefundFee(getTotalPrice());
		param.setOperateUser(user);
		oManager.refundOrder(this, param);
	}
	
	@Override
	public void refund(OrderRefundParameter refundParam) throws OrderException {
		CheckResult cResult = checkRefundable(refundParam);
		if(cResult.isSuc()){
			oManager.refundOrder(this, refundParam);
		}else{
			throw new OrderException(cResult.getReason());
		}
		
		
	}
	@Override
	public CheckResult checkRefundable(OrderRefundParameter refundParam){
		CheckResult result = new CheckResult(true, "检测成功");
		//订单退款只要是在订单支付完成之后都可以操作
		//退款的金额只要小于等于原本订单支付的总价即可
		Integer actualPaied = pOrder.getActualPay();
		Integer refundAmount = refundParam.getRefundFee();
		if(actualPaied != null && refundAmount != null && actualPaied >= refundAmount){
			if(getCancelStatus() != null){
				return result.setResult(false, "订单已经被取消，不能退款");
			}
			if(getOrderStatus() != Order.STATUS_PAYED){
				return result.setResult(false, "当前订单状态[" + getOrderStatus() + "]禁止退款");
			}
			Set<Product> products = getProductSet();
			for (Product product : products) {
				if(product.getStatus() != Product.STATUS_ORDERED){
					return result.setResult(false, "订单内存在产品[" + product.getId() + "]的状态为[" + product.getStatus() + "]禁止退款");
				}
			}
		}else{
			result.setResult(false, "退款时检测到异常，退款金额为[" + refundAmount + "]，原支付金额为[" + actualPaied + "]");
		}
		return result;
	}
	
	/**
	 * 持久化退款金额，并且修改订单下所有产品的状态为退款
	 * @param refundFee
	 */
	public void doRefunded(int refundFee) throws OrderException{
		//构造退款对象
		PlainOrderRefund refund = new PlainOrderRefund();
		refund.setOrderId((Long) this.getKey());
		refund.setRefundFee(refundFee);
		//持久化订单退款金额，并将订单下所有产品的价格的退款金额持久化为其实际价格
		dpService.setOrderRefund(refund);
		//遍历所有产品
		for (Product product : productSet) {
			//将产品的退款设置为当前价格
			product.setRefundFeeWithoutPersistence(-1);
			delivery.removeDispenseCode(product.getDispenseCode());
		}
		pOrder.setRefundFee(refundFee);
	}
	
	/**
	 * 检查付款请求
	 * @param payParam
	 * @param order
	 * @return
	 */
	private CheckResult checkPayParameter(OrderPayParameter payParam) {
		CheckResult result = new CheckResult(true, "检测成功");
		int cStatus = this.getOrderStatus();
		if(cStatus != Order.STATUS_DEFAULT){
			return result.setResult(false, "不能支付状态为" + cStatus + "的订单");
		}
		//TODO: 检测其他支付信息，例如支付请求与订单内的价格
		return result;
	}

	@Override
	public String getPrepayId() {
		return pOrder.getPrepayId();
	}

	@Override
	public void setPrepayId(String prepayId) {
		pOrder.setPrepayId(prepayId);
	}

	@Override
	public Set<Product> getProductSet() {
		return productSet;
	}

	@Override
	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}

	@Override
	public Integer getTotalPrice() {
		return pOrder.getTotalPrice();
	}

	@Override
	public void setTotalPrice(Integer totalPrice) {
		pOrder.setTotalPrice(totalPrice);
	}

	@Override
	public void setOrderDetail(OrderDetail orderDetail) {
		this.pOrder = (PlainOrder) orderDetail;
	}
	
	@Override
	public OrderDetail getOrderDetail(){
		return pOrder;
	}
	

	@Override
	public OrderDispenseResource getOrderDispenseResource() {
		return this.dispenseResource;
	}
	
	@Override
	public WeiXinUser getOrderUser() {
		if(this.orderUser == null && pOrder.getOrderUserId() != null){
			this.orderUser = dpService.getUser(pOrder.getOrderUserId());
		}
		return this.orderUser;
	}
	
	@Override
	public void setOrderUser(WeiXinUser orderUser){
		this.orderUser = orderUser;
		if(orderUser != null){
			pOrder.setOrderUserId(orderUser.getId());
		}else{
			pOrder.setOrderUserId(null);
		}
	}
	
	@Override
	public void fromOrderParameter(OrderParameter orderParam) {
		if(pOrder != null){
			DeliveryLocation location = orderParam.getDeliveryLocation();
			if(location != null){
				pOrder.setLocationName(location.getLocationName());
			}
		}
		// TODO Auto-generated method stub
		Order.super.fromOrderParameter(orderParam);
	}
	
	@Override
	public String getDescription() {
		Map<String, Object> desc = new LinkedHashMap<String, Object>();
		desc.put("订单主键", this.getKey());
		desc.put("订单号", this.getOrderCode());
		desc.put("订单价格", this.getTotalPrice());
		desc.put("配送地点", this.getDeliveryLocation().getLocationName());
		StringBuffer buffer = new StringBuffer();
		desc.forEach((key, value) -> {
			buffer.append(key + ":" + value + ",");
		});
		return buffer.toString();
	}

	public void setDispenseResourceRequest(
			DispenseResourceRequest dispenseResourceRequest) {
		this.dispenseResourceRequest = dispenseResourceRequest;
		JSONObject jo = new JSONObject();
		jo.put("dispenseCount", dispenseResourceRequest.getDispenseCount());
		jo.put("locked", dispenseResourceRequest.isResourceLocked());
		pOrder.setDispenseResourceRequest(jo.toJSONString());
	}
	
	@Override
	public DispenseResourceRequest getDispenseResourceRequest() {
		if(this.dispenseResourceRequest == null ){
			String dispenseResourceReq = pOrder.getDispenseResourceRequest();
			if(dispenseResourceReq != null){
				JSONObject jo = JSON.parseObject(dispenseResourceReq);
				Integer dispenseCount = jo.getInteger("dispenseCount");
				if(dispenseCount != null){
					DispenseResourceRequest resourceReq = new DispenseResourceRequest();
					resourceReq.setDispenseCount(dispenseCount);
					Boolean locked = jo.getBoolean("locked");
					if(locked != null){
						resourceReq.setResourceLocked(locked);
					}
					this.dispenseResourceRequest = resourceReq;
				}
			}
		}
		return this.dispenseResourceRequest;
	}
	
	@Override
	public WxPayStatus checkWxPayStatus(){
		return oManager.checkWxPayStatus(this);
	}
	

	@Override
	public void setActualPay(Integer actualPay) {
		pOrder.setActualPay(actualPay);
	}

	public void setpOrder(PlainOrder pOrder) {
		this.pOrder = pOrder;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setReceiverContact(String contact) {
		pOrder.setReceiverContact(contact);
	}

	public void setReceiverName(String name) {
		pOrder.setReceiverName(name);
	}

	public void setReceiverAddress(String address) {
		pOrder.setReceiverAddress(address);
	}

	@Override
	public void setOutTradeNo(String outTradeNo) {
		pOrder.setOutTradeNo(outTradeNo);
	}

	@Override
	public String getOutTradeNo() {
		return pOrder.getOutTradeNo();
	}

	@Override
	public void setTransactionId(String transactionId) {
		pOrder.setTransactionId(transactionId);
	}

	@Override
	public String getTransactionId() {
		return pOrder.getTransactionId();
	}
	
	
	@Override
	public Date getPayExpireTime() {
		return pOrder.getPayExpireTime();
	}
	
	@Override
	public void setPayExpireTime(Date payExpireTime) {
		pOrder.setPayExpireTime(payExpireTime);
	}
	
	@Override
	public boolean getPayExpired() {
		return (new Date()).compareTo(getPayExpireTime()) > 0;
	}
	
	@Override
	public List<GoodsDetail> getGoodsDetails() {
		List<GoodsDetail> goodsDetails = new ArrayList<GoodsDetail>();
		for (Product product : productSet) {
			GoodsDetail gd = new GoodsDetail();
			gd.setGoodsId(FormatUtils.toString(product.getId()));
			gd.setGoodsName(product.getName());
			gd.setQuantity(1);
			gd.setPrice(product.getPrice());
			goodsDetails.add(gd);
		}
		return goodsDetails;
	}

	@Override
	public String getPayTitle() {
		return "一点点奶茶";
	}
}
