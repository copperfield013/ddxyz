package cn.sowell.ddxyz.model.drink.service;

import java.util.List;
import java.util.Map;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkOrder;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

import com.alibaba.fastjson.JSONObject;

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
	
	List<PlainDrinkOrder> getDrinkPageList(UserIdentifier user, CommonPageInfo pageInfo);

	/**
	 * 根据订单的主键集合，查询这些订单内有哪些订单是允许退款的
	 * @param drinkList
	 * @return
	 */
	Map<Long, Boolean> getRefundableMap(List<Long> orderIdList);

	/**
	 * 根据订单号获得
	 * @param orderId
	 * @return
	 */
	PlainDrinkOrder getOrderItem(Long orderId);

	/**
	 * 配送和订单转换成前台可以初始化的订单对象
	 * @param delivery
	 * @param order
	 * @return
	 */
	JSONObject converteInitOrder(Delivery delivery,
			List<PlainOrderDrinkItem> orderItems);
}
