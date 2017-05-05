package cn.sowell.ddxyz.model.drink.service;

import java.util.List;
import java.util.Map;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.criteria.DeliveryCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

public interface DrinkDeliveryService {
	
	List<PlainOrder> getOrderList(DeliveryCriteria criteria);
	
	List<PlainOrder> getOrderPageList(DeliveryCriteria criteria, CommonPageInfo pageInfo);
	
	Map<PlainOrder, List<PlainOrderDrinkItem>> getOrderItems(List<PlainOrder> list);
	
	Map<PlainOrderDrinkItem, List<PlainDrinkAddition>> getDrinkItemAdditions(Map<PlainOrder,List<PlainOrderDrinkItem>> map);

}
