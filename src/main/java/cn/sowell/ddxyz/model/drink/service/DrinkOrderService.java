package cn.sowell.ddxyz.model.drink.service;

import java.util.List;
import java.util.Map;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkOrder;
import cn.sowell.ddxyz.model.drink.pojo.criteria.OrderCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.OrderStatisticsListItem;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

import com.alibaba.fastjson.JSONObject;

public interface DrinkOrderService {

	
	List<PlainOrder> getOrderPageList(OrderCriteria criteria, CommonPageInfo pageInfo);
	
	
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
	
	Map<Long, Integer> getOrderCupCount(List<PlainOrder> list);
	
	/**
	 * 订单统计 
	 * @param criteria
	 * @param pageInfo 可为null
	 * @return
	 */
	List<OrderStatisticsListItem> statisticOrder(OrderCriteria criteria, CommonPageInfo pageInfo);
}
