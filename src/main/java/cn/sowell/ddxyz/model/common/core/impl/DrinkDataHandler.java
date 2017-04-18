package cn.sowell.ddxyz.model.common.core.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.Session;

import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.ProductDataHandler;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkProduct;

public class DrinkDataHandler implements ProductDataHandler{

	private PlainDrinkProduct drink = new PlainDrinkProduct();
	private Set<PlainDrinkAddition> additions = new LinkedHashSet<PlainDrinkAddition>();
	
	
	/**
	 * 获得饮料的一般信息
	 * @return
	 */
	public PlainDrinkProduct getDrink(){
		return drink;
	}
	
	/**
	 * 添加一个加料对象
	 * @param additionTypeId 加料类的主键
	 * @param additionTypeName 加料的名称
	 * @return 返回添加的加料对象
	 */
	public PlainDrinkAddition addAddition(Long additionTypeId, String additionTypeName){
		PlainDrinkAddition addition = new PlainDrinkAddition();
		addition.setAdditionTypeId(additionTypeId);
		addition.setAdditionTypeName(additionTypeName);
		additions.add(addition);
		return addition;
	}
	
	@Override
	public void saveAuxiliary(Long productId, Session session, Product product,
			Order order) throws ProductException {
		drink.setProductId(productId);
		Long drinkProductId = null;
		try {
			drinkProductId = (Long) session.save(drink);
		} catch (Exception e) {
			throw new ProductException("持久化饮料信息时发生异常", e);
		}
		if(drinkProductId != null){
			for (PlainDrinkAddition addition : additions) {
				addition.setDrinkProductId(drinkProductId);
				try {
					session.save(addition);
				} catch (Exception e) {
					throw new ProductException("持久化饮料的加料信息时发生异常", e);
				}
			}
		}else{
			throw new ProductException("持久化饮料信息时发生异常，持久化后返回的主键为null");
		}
	}
	
	@Override
	public Integer calculateProductPrice(Product product) {
		
		return null;
	}

}
