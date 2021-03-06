package cn.sowell.ddxyz.model.drink.dao;

import java.util.List;
import java.util.Map;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.criteria.OrderCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.OrderStatisticsListItem;
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
	List<PlainOrder> getOrderList(OrderCriteria criteria, CommonPageInfo pageInfo);
	
	Map<Long, Integer> getOrderCupCount(List<Long> orderIdList);
	
	List<OrderStatisticsListItem> statisticsOrder(OrderCriteria criteria, CommonPageInfo pageInfo);

	void updateOrderPrinted(Long orderId);

	/**
	 * 根据用户id和分页数据获得订单列表
	 * @param id
	 * @param pageInfo
	 * @return
	 */
	List<PlainOrder> getOrderPageList(long userId, CommonPageInfo pageInfo);
}
