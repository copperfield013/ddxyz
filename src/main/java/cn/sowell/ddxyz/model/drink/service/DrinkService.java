package cn.sowell.ddxyz.model.drink.service;

import java.util.List;
import java.util.Map;

import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkTeaAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkType;

public interface DrinkService {
	/**
	 * 根据商品的id获得所有饮料类型，包括可用和不可用
	 * @param waresId
	 * @return
	 */
	List<PlainDrinkType> getAllDrinkTypes(long waresId);

	/**
	 * 获得商品对应的所有饮料茶类加料，包括可用和不可用。返回的map的key是对应饮料类型的id
	 * @param merchantId
	 * @return
	 */
	Map<Long, List<PlainDrinkTeaAdditionType>> getTeaAdditionMap(long waresId);

	/**
	 * 获得商品对应的所有饮料的所有可用加料，包括可用和不可用。返回的map的key是对应饮料类型的id
	 * @param merchantId
	 * @return
	 */
	Map<Long, List<PlainDrinkAdditionType>> getAdditionMap(long waresId);
	
	
	
}
