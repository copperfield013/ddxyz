package cn.sowell.ddxyz.model.common.dao;

import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderLog;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderRefund;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;

public interface DataPersistenceDao {
	/**
	 * 根据主键从数据库获得订单对象
	 * @param orderKey
	 * @return
	 */
	PlainOrder getOrder(long orderId);
	/**
	 * 根据订单的id获得所有属于这个订单的产品
	 * @param orderId
	 * @return
	 */
	List<PlainProduct> getProductsOfOrder(long orderId);
	/**
	 * 更新产品在数据库中的状态字段
	 * @param id 产品的id
	 * @param status 更改后的状态值
	 */
	void updateProductStatus(long id, int status);
	/**
	 
	 * @param ids
	 * @param status
	 */
	void updateProductStatusInOrder(long orderId, int status);
	/**
	 * 根据产品的id获得产品对象
	 * @param productId
	 * @return
	 */
	PlainProduct getProduct(long productId);
	/**
	 * 更新产品在数据库中的状态
	 * @param key
	 * @param statusPayed
	 */
	void updateOrderStatus(long key, int statusPayed);
	/**
	 * 在数据库中创建订单记录，包括订单以及产品记录
	 * @param order
	 * @return 创建成功返回true， 否则返回false
	 * @throws OrderException 
	 */
	void createOrder(Order order) throws OrderException;
	/**
	 * 修改数据库中的订单信息
	 * @param order
	 * @return
	 */
	void updateOrder(Order order);
	/**
	 * 根据时间和配送地点的id获得配送对象
	 * @param datetime 配送时间点
	 * @param locationId 配送地点的id
	 * @return
	 */
	PlainDelivery getDelivery(Date datetime, long locationId);
	
	/**
	 * 持久化日志
	 * @param log
	 */
	void saveLog(PlainOrderLog log);
	/**
	 * 从数据库获取最新的配送地址对象
	 * @param locationId
	 * @return
	 */
	PlainLocation getDeliveryLocation(long locationId);
	/**
	 * 根据配送id获得对应的配送信息对象
	 * @param deliveryId
	 * @return
	 */
	PlainDelivery getDelivery(long deliveryId);
	/**
	 * 更新产品的分发号
	 * @param serializable 
	 * @param code
	 */
	void updateProductDispenseCodeAndOrdered(long productId, String code);
	/**
	 * 持久化订单的退款状态和退款金额
	 * @param orderId 订单主键
	 * @param refundFee 退款金额
	 */
	void updateOrderRefund(long orderId, int refundFee);
	/**
	 * 持久化订单下所有产品的退款状态，退款金额为产品的价格
	 * @param orderId
	 */
	void updateOrderProductRefund(long orderId);
	/**
	 * 持久化退款信息对象
	 * @param refund
	 */
	void saveRefund(PlainOrderRefund refund);
	
	/**
	 * 更新订单的取消状态
	 * @param key
	 * @param canStatus
	 */
	void updateOrderCanceledStatus(long orderId, String canStatus);
	
	/**
	 * 更新订单内所有产品的取消状态
	 * @param orderId 订单主键
	 * @param canStatusAbandon 
	 */
	void updateProductCanceledStatusInOrder(long orderId, String canStatus);
	
	

}
