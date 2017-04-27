package cn.sowell.ddxyz.model.common.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderManager;
import cn.sowell.ddxyz.model.common.core.OrderOperateResult;
import cn.sowell.ddxyz.model.common.core.OrderParameter;
import cn.sowell.ddxyz.model.common.core.OrderRefundParameter;
import cn.sowell.ddxyz.model.common.core.OrderToken;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderReceiver;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
/**
 * 
 * <p>Title: OrderService</p>
 * <p>Description: </p><p>
 * 用于处理订单的Service
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月4日 下午12:41:04
 */
public interface OrderService {
	
	/**
	 * 根据数据库中的配送计划，生成某一天的所有配送。注意如果配送计划对应的时间点
	 * 
	 * @return
	 */
	Set<Delivery> generateDeliveries(Date theDay);
	
	
	/**
	 * 订单由用户发起, 并且需要包含认证的token信息。<br/>
	 * 订单发起的过程是：配送管理检查订单所需的资源是否足够，<br/>
	 * 如果足够，判断是否要在创建订单之后立刻占用资源，<br/>
	 * 如果占用的话，那么就把资源锁定。<br/>
	 * 如果不锁定，那么获取之后也不会占用，其他线程的订单请求依然可以获取相同的资源。<br/>
	 * 资源检查之后，配送管理根据订单请求构建订单对象，<br/>
	 * 并且调用微信接口创建一个预支付订单，获得预支付订单的token后，将订单持久化。<br/>
	 * 最后将创建好的订单对象返回。<br/>
	 * @param orderParameter 订单请求
	 * @param user 用户信息
	 * @param orderToken 订单token信息
	 * @return
	 */
	Order applyForOrder(OrderParameter orderParameter, WeiXinUser user, OrderToken orderToken);
	
	
	
	/**
	 * 根据订单标识获得订单对象<br/>
	 * 现在内存中查找，如果内存中不存在，那么就去数据库中查找
	 * @param orderKey 订单标识
	 * @return 订单对象
	 * @see OrderManager#getOrder(Serializable)
	 */
	Order getOrder(Serializable orderKey);

	/**
	 * 根据用户的id获得其最近的收货信息
	 * @param userId
	 * @return
	 */
	PlainOrderReceiver getLastReceiverInfo(Serializable userId);

	/**
	 * 
	 * @param orderId
	 */
	void payOrder(Long orderId, WeiXinUser user) throws Exception;

	/**
	 * 订单操作的Service
	 * @param orderId 订单id
	 * @param operateType 操作类型
	 * @param operateUser 操作的用户
	 * @return
	 * @throws Exception 
	 */
	OrderOperateResult operateOrder(Long orderId, String operateType, UserIdentifier operateUser) throws Exception;

	/**
	 * 确认订单
	 * @param order
	 * @param user
	 * @throws OrderException 
	 */
	void completeOrder(Order order, UserIdentifier user) throws OrderException;


	void refundOrder(Order order, OrderRefundParameter refundParam) throws OrderException;



	

}
