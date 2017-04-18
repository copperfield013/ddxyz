package cn.sowell.ddxyz.model.common.core;

import org.hibernate.Session;

import cn.sowell.ddxyz.model.common.core.exception.ProductException;

public interface ProductDataHandler {
	/**
	 * 产品基础信息持久化之后，可以对持久化产品的其他信息
	 * @param productId 产品基础信息持久化后获得的主键
	 * @param session 用于持久化的Hibernate会话对象
	 * @param product 用于持久化的产品基础信息对象
	 * @param order 用于持久化的订单基础信息对象
	 * @throws ProductException 如果抛出异常，那么整个订单的持久化都会回滚
	 */
	void saveAuxiliary(Long productId, Session session, Product product, Order order) throws ProductException;
	
	/**
	 * 计算产品的价格。调用的时机是根据产品参数构造产品对象之后
	 * 如果返回null，那么按照原本传入的价格参数。
	 * 如果返回非null，那么将把计算的结果放到产品中
	 * @param product
	 * @return
	 */
	Integer calculateProductPrice(Product product);

}
