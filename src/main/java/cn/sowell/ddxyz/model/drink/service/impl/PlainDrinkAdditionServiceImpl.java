package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.ddxyz.model.drink.dao.PlainDrinkAdditionDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.service.PlainDrinkAdditionService;

@Service
public class PlainDrinkAdditionServiceImpl implements PlainDrinkAdditionService {

	@Resource
	PlainDrinkAdditionDao drinkAdditionDao;
	
	@Override
	public List<PlainDrinkAddition> getDrinkAdditionList(Long productId) {
		return drinkAdditionDao.getDrinkAdditionList(productId);
	}

}
