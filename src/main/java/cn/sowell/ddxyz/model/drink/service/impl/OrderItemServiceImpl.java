package cn.sowell.ddxyz.model.drink.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.ddxyz.model.common.dao.CommonProductDao;
import cn.sowell.ddxyz.model.drink.service.OrderItemService;
import cn.sowell.ddxyz.model.drink.term.OrderItem;

@Service
public class OrderItemServiceImpl implements OrderItemService{

	@Resource
	CommonProductDao productDao;
	
	
	
	@Override
	public String getThumbUri(OrderItem oItem) {
		Long drinkTypeId = oItem.getDrinkTypeId();
		String thumbUri = productDao.getDrinkThumbUri(drinkTypeId);
		return thumbUri;
	}
	
}
