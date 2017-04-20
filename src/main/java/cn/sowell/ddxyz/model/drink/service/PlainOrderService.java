package cn.sowell.ddxyz.model.drink.service;

import java.util.List;

import cn.sowell.ddxyz.model.common.pojo.PlainOrder;

public interface PlainOrderService {

	/**
	 * 根据用户id获取订单列表
	 * @param userId
	 * @return
	 */
	List<PlainOrder> getOrderList(Long userId);
}
