package cn.sowell.ddxyz.model.drink.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;

public interface ProductDao {
	
	List<ProductInfoItem> getProductInfoItemPageList(ProductionCriteria criteria, CommonPageInfo pageInfo);
	
	List<ProductInfoItem> getProductInfoItemListByProductIds(List<Long> productIdList);
	
}
