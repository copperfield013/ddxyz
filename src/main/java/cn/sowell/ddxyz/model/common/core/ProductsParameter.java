package cn.sowell.ddxyz.model.common.core;

import java.util.List;
/**
 * 
 * <p>Title: ProductParameter</p>
 * <p>Description: </p><p>
 * 产品请求对象
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月30日 下午3:35:43
 */
public class ProductsParameter {
	
	private List<ProductItemParameter> productItemParameterList;
	private boolean requireBuildItemFull = true;

	public List<ProductItemParameter> getProductItemParameterList() {
		return productItemParameterList;
	}

	public void setProductItemParameterList(
			List<ProductItemParameter> productItemParameterList) {
		this.productItemParameterList = productItemParameterList;
	}
	/**
	 * 是否要求请求的产品都被构造
	 * @return
	 */
	public boolean isRequireBuildItemFull() {
		return requireBuildItemFull;
	}
	
	/**
	 * 设置是否要求请求的
	 * @param requireBuildItemFull
	 */
	public void setRequireBuildItemFull(boolean requireBuildItemFull) {
		this.requireBuildItemFull = requireBuildItemFull;
	}

	
	
	
}
