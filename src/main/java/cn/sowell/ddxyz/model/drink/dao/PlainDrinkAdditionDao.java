package cn.sowell.ddxyz.model.drink.dao;

import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;

public interface PlainDrinkAdditionDao {
		
	List<PlainDrinkAddition> getDrinkAdditionList(Long productId);

}
