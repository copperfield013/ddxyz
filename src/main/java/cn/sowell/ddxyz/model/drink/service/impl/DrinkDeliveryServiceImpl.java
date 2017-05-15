package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.dao.DrinkAdditionDao;
import cn.sowell.ddxyz.model.drink.dao.DrinkDeliveryDao;
import cn.sowell.ddxyz.model.drink.dao.DrinkOrderDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.criteria.DeliveryCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkDeliveryService;

@Service
public class DrinkDeliveryServiceImpl implements DrinkDeliveryService {
	
	@Resource
	DrinkDeliveryDao dDeliveryDao;
	
	@Resource
	DrinkAdditionDao drinkAdditionDao;
	
	@Resource
	DrinkOrderDao drinkOrderDao;
	
	@Override
	public List<PlainOrder> getOrderList(DeliveryCriteria criteria) {
		List<PlainOrder> list = dDeliveryDao.getOrderList(criteria, null);
		return list;
	}

	@Override
	public List<PlainOrder> getOrderPageList(DeliveryCriteria criteria, CommonPageInfo pageInfo) {
		List<PlainOrder> orderList = dDeliveryDao.getOrderList(criteria, pageInfo);
		return orderList;
	}

	@Override
	public Map<PlainOrder, List<PlainOrderDrinkItem>> getOrderItems(List<PlainOrder> list) {
		Map<PlainOrder, List<PlainOrderDrinkItem>> map = new HashMap<>();
		if(list != null && list.size() >0){
			for(PlainOrder plainOrder : list){
				List<PlainOrderDrinkItem> drinkItemList = drinkOrderDao.getOrderDrinkItemList(plainOrder.getId());
				map.put(plainOrder, drinkItemList);
			}
		}
		
		return map;
	}

	@Override
	public Map<PlainOrderDrinkItem, List<PlainDrinkAddition>> getDrinkItemAdditions(Map<PlainOrder,List<PlainOrderDrinkItem>> map) {
		Set<PlainOrderDrinkItem> keys = new HashSet<PlainOrderDrinkItem>();
		map.forEach((key, value) -> keys.addAll(value));
		
		Map<PlainOrderDrinkItem, List<PlainDrinkAddition>> result = new HashMap<PlainOrderDrinkItem, List<PlainDrinkAddition>>();
		for (PlainOrderDrinkItem key : keys) {
			List<PlainDrinkAddition> additions = drinkAdditionDao.getDrinkAdditionList(key.getDrinkProductId());
			result.put(key, additions);
		}
		return result;
	}
	
	@Override
	public Map<PlainOrder, Integer> mapOrderMakedCount(
			Map<PlainOrder, List<PlainOrderDrinkItem>> map) {
		Map<PlainOrder, Integer> result = new HashMap<PlainOrder, Integer>();
		map.forEach((order, products) -> {
			int makedCount = 0;
			for (PlainOrderDrinkItem product : products) {
				if(product.getStatus() >= Product.STATUS_MAKING){
					makedCount++;
				}
			}
			result.put(order, makedCount);
		});
		
		return result;
	}
	
	@Override
	public void updateOrderPrinted(Long orderId) {
		drinkOrderDao.updateOrderPrinted(orderId);
	}

}
