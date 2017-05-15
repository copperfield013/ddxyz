package cn.sowell.ddxyz.model.common.dao;

import java.util.List;

import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.common.pojo.criteria.ProductCriteria;

public interface CommonProductDao {
	/**
	 * 根据产品条件从数据库中查询所有产品
	 * @param criteria
	 * @return
	 */
	List<PlainProduct> getProducts(ProductCriteria criteria);

	/**
	 * 
	 * @param drinkTypeId
	 * @return
	 */
	String getDrinkThumbUri(Long drinkTypeId);

}
