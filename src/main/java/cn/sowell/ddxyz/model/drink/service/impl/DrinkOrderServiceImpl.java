package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.dao.DrinkAdditionDao;
import cn.sowell.ddxyz.model.drink.dao.DrinkOrderDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkOrder;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkOrderService;

@Service
public class DrinkOrderServiceImpl implements DrinkOrderService{

	@Resource
	DrinkOrderDao drinkOrderDao;
	
	@Resource
	DrinkAdditionDao drinkAdditionDao;
	
	@Override
	public List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId) {
		return drinkOrderDao.getOrderDrinkItemList(orderId);
	}
	
	
	@Override
	public List<PlainDrinkAddition> getDrinkAdditionList(Long productId) {
		return drinkAdditionDao.getDrinkAdditionList(productId);
	}
	
	@Override
	public List<PlainOrder> getOrderList(Long userId) {
		return drinkOrderDao.getOrderList(userId);
	}
	
	public List<PlainOrder> getOrderPageList(Long userId, CommonPageInfo pageInfo){
		return drinkOrderDao.getOrderList(userId, pageInfo);
	}
	
	
	@Override
	public List<PlainDrinkOrder> getDrinkList(UserIdentifier user) {
		List<PlainOrder> orderList = getOrderList((Long)user.getId());
		List<PlainDrinkOrder> drinkList = getNewDrinkOrderList(orderList);
		return drinkList;
	}
	
	public List<PlainDrinkOrder> getDrinkPageList(UserIdentifier user, CommonPageInfo pageInfo){
		List<PlainOrder> orderList = getOrderPageList((Long)user.getId(), pageInfo);
		List<PlainDrinkOrder> drinkList = getNewDrinkOrderList(orderList);
		return drinkList;
	}
	
	private List<PlainDrinkOrder> getNewDrinkOrderList(List<PlainOrder> orderList){
		List<PlainDrinkOrder> drinkList = new ArrayList<PlainDrinkOrder>();
		if(orderList != null && orderList.size() > 0){
			for(PlainOrder plainOrder : orderList){
				List<PlainOrderDrinkItem> orderDrinkItemList = getOrderDrinkItemList(plainOrder.getId());
				PlainDrinkOrder plainOrderDrink = new PlainDrinkOrder();
				plainOrderDrink.setId(plainOrder.getId());
				plainOrderDrink.setOrderCode(plainOrder.getOrderCode());
				plainOrderDrink.setCanceledStatus(plainOrder.getCanceledStatus());
				plainOrderDrink.setOrderStatus(plainOrder.getOrderStatus());
				plainOrderDrink.setTotalPrice(plainOrder.getTotalPrice());
				plainOrderDrink.setOrderTime(plainOrder.getCreateTime());
				plainOrderDrink.setOrderDrinkItems(orderDrinkItemList);
				plainOrderDrink.setCupCount(0);
				if(orderDrinkItemList != null && orderDrinkItemList.size() > 0){
					for(PlainOrderDrinkItem item : orderDrinkItemList){
						List<PlainDrinkAddition> additions = getDrinkAdditionList(item.getDrinkProductId());
						item.setAdditions(additions);
					}
					plainOrderDrink.setCupCount(orderDrinkItemList.size());
				}
				drinkList.add(plainOrderDrink);
			}
		}
		return drinkList;
	}
	
	
	
}
