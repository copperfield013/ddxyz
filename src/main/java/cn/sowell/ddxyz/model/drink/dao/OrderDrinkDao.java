package cn.sowell.ddxyz.model.drink.dao;

import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

public interface OrderDrinkDao {
	/**
	 * 根据订单id获取订单详情列表
	 * @param orderId
	 * @return
	 */
	List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId);

}
