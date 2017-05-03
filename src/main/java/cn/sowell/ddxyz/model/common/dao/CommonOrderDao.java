package cn.sowell.ddxyz.model.common.dao;

import cn.sowell.ddxyz.model.common.core.ReceiverInfo;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderReceiver;

public interface CommonOrderDao {
	
	/**
	 * 根据用户id查询对应的订单收货人信息
	 * @param userId
	 * @return
	 */
	PlainOrderReceiver getOrderReceiver(long userId);
	/**
	 * 更新用户常用的收货人信息
	 * @param id
	 * @param receiver
	 */
	void updateReceiverInfo(long id, ReceiverInfo receiver) throws OrderException;
	
	/**
	 * 根据订单id获得订单信息
	 * @param orderId
	 * @return
	 */
	PlainOrder getPlainOrder(long orderId);

}
