package cn.sowell.ddxyz.model.common.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.weixin.pay.exception.WeiXinPayException;
import cn.sowell.copframe.weixin.pay.prepay.PrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.PrepayResult;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.model.common.core.DefaultOrderPayParameter;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderLog;
import cn.sowell.ddxyz.model.common.core.OrderManager;
import cn.sowell.ddxyz.model.common.core.OrderOperateResult;
import cn.sowell.ddxyz.model.common.core.OrderParameter;
import cn.sowell.ddxyz.model.common.core.OrderPayParameter;
import cn.sowell.ddxyz.model.common.core.OrderRefundParameter;
import cn.sowell.ddxyz.model.common.core.OrderToken;
import cn.sowell.ddxyz.model.common.core.ProductManager;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.dao.CommonOrderDao;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderReceiver;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

@Service
public class OrderServiceImpl implements OrderService{
	@Resource
	DeliveryManager dManager;
	
	@Resource
	OrderManager oManager;
	
	@Resource
	ProductManager pManager;
	
	@Resource
	WxPayService payService;
	
	@Resource
	CommonOrderDao oDao;
	
	Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Transactional
	@Override
	public Order applyForOrder(OrderParameter orderParameter, WeiXinUser user,
			OrderToken orderToken) {
		//先检查订单请求是否可以通过，检查通过的话才能创建订单
		CheckResult checkResult = dManager.checkOrderParameterAvailable(orderParameter, orderToken);
		if(checkResult.isSuc()){
			//根据请求创建订单对象，但是不持久化
			Order order = oManager.createOrder(orderParameter, user, orderToken);
			if(order != null){
				try {
					//根据订单构造预付款订单的参数对象
					PrepayParameter parameter = payService.buildPrepayParameter(order);
					//向微信接口发送预付款请求，并且返回预付款请求的结果
					PrepayResult prepayResult = payService.sendPrepay(parameter);
					//判断预付款结果是否成功
					if(prepayResult.getPrepayId() != null){
						//将预付款订单的id放到订单对象中
						order.setPrepayId(prepayResult.getPrepayId());
						order.setOutTradeNo(prepayResult.getSubmitUOrder().getOutTradeNo());
						//将订单对象同步到数据库
						oManager.persistOrder(order);
						//更新该微信用户的常用收货信息
						oDao.updateReceiverInfo(user.getId(), orderParameter.getReceiver());
						//订单和产品对象缓存到内存中
						oManager.cacheOrder(order);
						oManager.doLog(order, user, "订单创建成功，订单描述[" + order.getDescription() + "]", OrderLog.TYPE_ORDER_CREATE);
						return order;
					}
				} catch (WeiXinPayException e) {
					logger.error("调用微信支付统一下单接口创建预付款订单失败", e);
				} catch (OrderException e) {
					logger.error("持久化订单数据时发生异常", e);
					//回滚整个事务
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			}else{
				logger.error("构造订单对象失败");
			}
		}else{
			logger.info("检查订单请求的时无法通过，错误原因【" + checkResult.getReason() + "】");
		}
		return null;
	}
	
	@Override
	public Order getOrder(Serializable orderKey) {
		return oManager.getOrder(orderKey);
	}

	

	@Override
	public Set<Delivery> generateDeliveries(Date theDay) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public PlainOrderReceiver getLastReceiverInfo(Serializable userId) {
		return oDao.getOrderReceiver((long) userId);
	}
	
	@Transactional
	@Override
	public void payOrder(Long orderId, WeiXinUser user) throws Exception {
		Order order = getOrder(orderId);
		OrderPayParameter payParam = new DefaultOrderPayParameter(user);
		order.pay(payParam);
	}
	
	
	@Transactional
	@Override
	public void completeOrder(Order order, UserIdentifier user) throws OrderException {
		order.complete(user);
	}
	
	
	@Override
	public OrderOperateResult operateOrder(Long orderId, String operateType,
			UserIdentifier operateUser) throws Exception {
		Order order = getOrder(orderId);
		OrderOperateResult result = new OrderOperateResult();
		if(order != null){
			switch (operateType) {
				case OrderOperateResult.OPERATE_PAY:
					//支付
					payOrder(orderId, (WeiXinUser) operateUser);
					result.setStatus("suc");
					break;
				case OrderOperateResult.OPERATE_CONFIRM:
					//确认收货
					break;
				default:
					throw new UnsupportedOperationException("不支持的操作类型[" + operateType + "]");
				}
		}
		return result;
	}
	
	@Override
	public void refundOrder(Order order, OrderRefundParameter refundParam)
			throws OrderException {
		order.refund(refundParam);
	}
}
