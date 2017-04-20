package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.dao.OrderDao;
import cn.sowell.ddxyz.model.drink.service.PlainOrderService;

@Service
public class PlainOrderServiceImpl implements PlainOrderService {

	@Resource
	OrderDao orderDao;
	
	@Override
	public List<PlainOrder> getOrderList(Long userId) {
		return orderDao.getOrderList(userId);
	}

}
