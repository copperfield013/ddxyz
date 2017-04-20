package cn.sowell.ddxyz.model.drink.service;

import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

public interface OrderDrinkService {
	
	/**
	 * 根据订单id获取订单详情列表
	 * @return
	 */
	List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId);
}
