package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.utils.range.DateRange;
import cn.sowell.copframe.weixin.pay.paied.WxPayStatus;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.impl.DefaultOrder;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

/**
 * 
 * <p>Title: OrderManager</p>
 * <p>Description: </p><p>
 * <h1>订单管理器</h1>
 * 主要功能:
 * <ul>
 * 	<li>根据分配的资源和请求信息，创建订单</li>
 * 	<li>管理订单之间的联系</li>
 * </ul>
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月30日 上午8:48:51
 */
public interface OrderManager {
	
	
	/**
	 * 根据订单的标识获得订单对象
	 * @param orderKey 订单标识
	 * @return 返回标识对象的订单对象
	 */
	Order getOrder(Serializable orderKey);
	
	
	
	
	/**
	 * 根据请求创建订单，但是不持久化，也不生成order的key
	 * @param param 订单请求对象
	 * @param user 下单用户
	 * @param orderToken 创建订单用的token
	 * @return
	 */
	Order createOrder(OrderParameter param, WeiXinUser user,
			OrderToken orderToken);

	/**
	 * 将订单同步到数据库中。如果订单对象不存在key，那么将生成key
	 * @param order 订单对象，需要注意的是，必须包含预付款订单的id，以及订单的标识key
	 * @throws OrderException 
	 */
	void persistOrder(Order order) throws OrderException;

	/**
	 * 将订单缓存到内存当中，包括订单的所有产品。<br/>
	 * 需要注意的是，订单和产品都必须包含key。
	 * 如果订单或者其中一个产品没有key的话，那么就取消缓存
	 * @param order
	 */
	void cacheOrder(Order order);
	
	/**
	 * 记录订单日志。
	 * @param order 必须。系统中存在并且持久化了的订单对象
	 * @param user 必须。执行操作的微信用户
	 * @param description 操作的描述
	 * @param operateType 操作的类型
	 */
	void doLog(Order order, UserIdentifier user, String description, String operateType);

	/**
	 * 
	 * @param defaultOrder
	 */
	void removeOrderFromCache(Order order);



	/**
	 * <h1>支付订单</h1>
	 * 订单支付需要首先拿到之前创建的原始订单对象（该对象应该已经创建或者从持久化层加载到内存中），<br/>
	 * 根据订单对象可以去占用原本该获取的资源，如果资源还足够，那么根据订单支付请求，更改订单状态，并持久化。
	 * @param payParameter 
	 * @return
	 * @throws OrderException 
	 */
	void payOrder(DefaultOrder order, OrderPayParameter payParameter)
			throws OrderException;



	/**
	 * 订单退款<br/>
	 * 调用微信接口发送退款请求到微信服务器<br/>
	 * 请求成功之后会修改订单状态
	 * @param defaultOrder
	 * @param refundParam
	 */
	void refundOrder(DefaultOrder defaultOrder, OrderRefundParameter refundParam) throws OrderException;



	/**
	 * 调用微信接口查询支付订单的付款状态
	 * @param defaultOrder
	 * @return
	 */
	WxPayStatus checkWxPayStatus(DefaultOrder defaultOrder);

	/**
	 * 清除最近操作时间在某个时间范围内的所有订单
	 * @param range
	 */
	void clearCache(DateRange range);
}
