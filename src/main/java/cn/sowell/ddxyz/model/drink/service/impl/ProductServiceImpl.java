package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.common.core.ProductManager;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.drink.dao.DrinkAdditionDao;
import cn.sowell.ddxyz.model.drink.dao.ProductDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductCriteria;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;
import cn.sowell.ddxyz.model.drink.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Resource
	ProductDao productDao;
	
	@Resource
	DrinkAdditionDao drinkAdditionDao;
	
	@Resource
	ProductManager pManager;
	

	@Override
	public List<ProductInfoItem> getProductInfoItemList(ProductionCriteria criteria) {
		List<ProductInfoItem> list = productDao.getProductInfoItemPageList(criteria, null);
		return setProductInfoItemAdditions(list);
	}

	@Override
	public List<ProductInfoItem> getProductInfoItemListByProductIds(List<Long> productIdsList) throws ProductException {
		List<ProductInfoItem> list = productDao.getProductInfoItemListByProductIds(productIdsList);
		List<ProductInfoItem> result = setProductInfoItemAdditions(list);
		List<Long> productIds = CollectionUtils.toList(result, item->item.getProductId());
		pManager.setProductsPrinted(productIds);
		return result;
	}

	@Override
	public List<ProductInfoItem> getProductInfoItemPageList(ProductionCriteria criteria, CommonPageInfo pageInfo) {
		List<ProductInfoItem> list = productDao.getProductInfoItemPageList(criteria, pageInfo);
		return setProductInfoItemAdditions(list);
	}
	
	private List<ProductInfoItem> setProductInfoItemAdditions(List<ProductInfoItem> list){
		if(list != null && list.size()>0){
			for(ProductInfoItem productInfoItem : list){
				List<PlainDrinkAddition> additions = drinkAdditionDao.getDrinkAdditionList(productInfoItem.getDrinkProductId());
				productInfoItem.setAdditions(additions);
			}
		}
		return list;
	}

	@Override
	public int getProductPrintedCountByStatus(Integer status, Date timePoint) {
		return productDao.getProductPrintedCountByStatus(status, timePoint);
	}

	@Override
	public int getProductNotPrintCountByStatus(Integer status, Date date) {
		return productDao.getProductNotPrintCountByStatus(status, date);
	}

	@Override
	public List<ProductInfoItem> getProductInfoItemList(ProductCriteria criteria) {
		List<ProductInfoItem> list = productDao.getProductInfoItemPageList(criteria, null);
		return setProductInfoItemAdditions(list);
	}

	@Override
	public List<ProductInfoItem> getProductInfoItemPageList(ProductCriteria criteria, CommonPageInfo pageInfo) {
		List<ProductInfoItem> list = productDao.getProductInfoItemPageList(criteria, pageInfo);
		return setProductInfoItemAdditions(list);
	}

}
