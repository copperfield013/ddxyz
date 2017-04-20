package cn.sowell.ddxyz.model.drink.service;

import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;

public interface PlainDrinkAdditionService {
	
	List<PlainDrinkAddition> getDrinkAdditionList(Long productId);

}
