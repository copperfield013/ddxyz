package cn.sowell.ddxyz.model.drink.service;

import cn.sowell.ddxyz.model.drink.term.OrderItem;

public interface OrderItemService {

	/**
	 * 根据产品请求获得其产品缩略图
	 * @param oItem
	 * @return
	 */
	String getThumbUri(OrderItem oItem);

}
