package cn.sowell.ddxyz.model.drink.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.criteria.DeliveryCriteria;

public interface DrinkDeliveryDao {
	
	List<PlainOrder> getOrderList(DeliveryCriteria criteria, CommonPageInfo pageInfo);
	
}
