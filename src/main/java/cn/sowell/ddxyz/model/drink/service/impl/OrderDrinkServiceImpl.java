package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.ddxyz.model.drink.dao.OrderDrinkDao;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.OrderDrinkService;

@Service
public class OrderDrinkServiceImpl implements OrderDrinkService{

	@Resource
	OrderDrinkDao orderDrinkDao;
	
	@Override
	public List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId) {
		return orderDrinkDao.getOrderDrinkItemList(orderId);
	}
}
