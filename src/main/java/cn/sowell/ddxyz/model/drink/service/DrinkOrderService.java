package cn.sowell.ddxyz.model.drink.service;

import java.util.List;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkOrder;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

public interface DrinkOrderService {

	/**
	 * 根据用户id获取订单列表
	 * @param userId
	 * @return
	 */
	List<PlainOrder> getOrderList(Long userId);
	
	
	/**
	 * 根据订单id获取订单详情列表
	 * @return
	 */
	List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId);
	
	/**
	 * 
	 * @param productId
	 * @return
	 */
	List<PlainDrinkAddition> getDrinkAdditionList(Long productId);
	
	List<PlainDrinkOrder> getDrinkList(UserIdentifier user);
}
