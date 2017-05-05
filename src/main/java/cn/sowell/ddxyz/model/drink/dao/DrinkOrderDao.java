package cn.sowell.ddxyz.model.drink.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

public interface DrinkOrderDao {
	/**
	 * 根据订单id获取订单详情列表
	 * @param orderId
	 * @return
	 */
	List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId);

	/**
	 * 根据用户id获取订单列表
	 * @param userId
	 * @return
	 */
	List<PlainOrder> getOrderList(Long userId);
	
	List<PlainOrder> getOrderList(Long userId, CommonPageInfo pageInfo);
}
