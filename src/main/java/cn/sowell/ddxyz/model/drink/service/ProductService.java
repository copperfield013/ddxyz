package cn.sowell.ddxyz.model.drink.service;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;

public interface ProductService {
	
	List<ProductInfoItem> getProductInfoItemList(ProductionCriteria criteria);
	
	List<ProductInfoItem> getProductInfoItemPageList(ProductionCriteria criteria,CommonPageInfo pageInfo);
	
	List<ProductInfoItem> getProductInfoItemListByProductIds(List productIdsList);

}
