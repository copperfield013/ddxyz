package cn.sowell.ddxyz.model.drink.dao;

import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkTeaAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkType;

public interface DrinkDao {
	/**
	 * 根据商品的id获得所有饮料类型，包括可用和不可用
	 * @param waresId
	 * @return
	 */
	List<PlainDrinkType> getAllDrinkTypes(long waresId);
	/**
	 * 根据商品的id获得所有加茶类型，包括可用和不可用
	 * @param merchantId
	 * @return
	 */
	List<PlainDrinkTeaAdditionType> getAllTeaAdditionTypes(long waresId);
	/**
	 * 根据商品的id获得所有加料类型，包括可用和不可用
	 * @param waresId
	 * @return
	 */
	List<PlainDrinkAdditionType> getAllAdditionTypes(long waresId);
	
	
}
