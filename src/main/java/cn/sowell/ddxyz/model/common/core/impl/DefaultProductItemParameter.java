package cn.sowell.ddxyz.model.common.core.impl;

import cn.sowell.ddxyz.model.common.core.ProductItemParameter;
import cn.sowell.ddxyz.model.common.core.ProductDataHandler;

public class DefaultProductItemParameter implements ProductItemParameter {

	private Long waresId;
	private String wareName;
	private Integer price;
	private String thumbUri;
	private ProductDataHandler productOptionPersistHandler;
	
	@Override
	public Long getWaresId() {
		return waresId;
	}

	@Override
	public String getWaresName() {
		return wareName;
	}

	@Override
	public Integer getPrice() {
		return price;
	}

	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}

	public void setWaresName(String name) {
		this.wareName = name;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public ProductDataHandler getOptionPersistHandler() {
		return this.productOptionPersistHandler;
	}

	public void setProductDataHandler(ProductDataHandler productPersistHandler) {
		this.productOptionPersistHandler = productPersistHandler;
	}

	public String getThumbUri() {
		return thumbUri;
	}

	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}

}
