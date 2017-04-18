package cn.sowell.ddxyz.model.common.service;

import java.io.Serializable;
import java.util.Set;

import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryKey;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderLog;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderRefund;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

public interface DataPersistenceService {

	/**
	 * 根据订单的key从持久化层获得订单对象，并将其封装成Order对象
	 * @param orderKey
	 * @return
	 */
	Order getOrder(Serializable orderKey);
	
	/**
	 * 更新产品的状态
	 * @param id
	 * @param statusOrdered
	 */
	void updateProductStatus(Product product, int status);

	/**
	 * 根据产品id从持久化层获得产品对象
	 * @param productId
	 * @return 
	 */
	Product getProduct(Serializable productId);
	
	/**
	 * 持久化订单为已支付
	 * @param order
	 */
	void setOrderPayed(Order order);
	
	/**
	 * 持久化设置订单状态为取消，，并且修改订单内产品的状态为遗弃
	 * @param order
	 */
	void setOrderCanceled(Order order);
	/**
	 * 持久化设置订单状态为完成，并且设置订单内所有产品状态为用户已确认
	 * @param order
	 */
	void setOrderCompleted(Order order);
	/**
	 * 持久化设置订单状态为关闭，并且设置
	 * @param order
	 */
	void setOrderClosed(Order order);
	/**
	 * 持久化订单的所有产品的状态
	 * @param order
	 * @param statusOrdered
	 */
	void setProductsStatus(Order order, int statusOrdered);
	/**
	 * 在数据库中创建订单对象
	 * @param order
	 * @return
	 * @throws OrderException 
	 */
	void createOrder(Order order) throws OrderException;
	
	/**
	 * 在数据库中更新订单对象，要求订单在数据库和当前内存中都已经存在
	 * @param order
	 * @return
	 * @throws OrderException 
	 */
	void updateOrder(Order order) throws OrderException;

	/**
	 * 根据时间点和地点获得配送对象
	 * @param timePoint
	 * @param location
	 * @return
	 */
	Delivery getDelivery(DeliveryTimePoint timePoint, DeliveryLocation location);

	/**
	 * 持久化日志信息
	 * @param log 构造的日志对象
	 */
	void saveLog(PlainOrderLog log);

	/**
	 * 根据id从数据库获取配送地址信息
	 * @param id 配送地址的id
	 * @return
	 */
	PlainLocation getDeliveryLocation(Long id);
	
	/**
	 * 根据传入的配送键获得对应的配送信息对象
	 * @param deliveryKey
	 * @return
	 */
	Delivery getDelivery(DeliveryKey deliveryKey);

	/**
	 * 根据微信用户的id获得对应的微信对象
	 * @param userId 微信用户本地id
	 * @return
	 */
	WeiXinUser getUser(long orderUserId);

	/**
	 * 持久化所有产品的状态已下单，并且将更新分配号
	 * @param productSet
	 * @throws ProductException
	 */
	void setProductsOrdered(Set<Product> productSet) throws ProductException;
	
	/**
	 * 持久化订单退款金额，并将订单下所有产品的价格的退款金额持久化为其实际价格
	 * @param refund 退款信息对象
	 */
	void setOrderRefund(PlainOrderRefund refund) throws OrderException;

	

}
