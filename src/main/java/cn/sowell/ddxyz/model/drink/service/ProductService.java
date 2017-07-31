package cn.sowell.ddxyz.model.drink.service;

import java.util.Date;
import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductCriteria;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;

public interface ProductService {
	
	List<ProductInfoItem> getProductInfoItemList(ProductionCriteria criteria);
	
	List<ProductInfoItem> getProductInfoItemPageList(ProductionCriteria criteria,CommonPageInfo pageInfo);
	
	List<ProductInfoItem> getProductInfoItemListByProductIds(List<Long> productIdsList) throws ProductException;
	
	int getProductPrintedCountByStatus(Integer status, Date timePoint);
	
	int getProductNotPrintCountByStatus(Integer status, Date date);
	
	List<ProductInfoItem> getProductInfoItemList(ProductCriteria criteria);
	
	List<ProductInfoItem> getProductInfoItemPageList(ProductCriteria criteria,CommonPageInfo pageInfo);

	/**
	 * 将商品的分配号转换成二维码的原始编码
	 * @param dispenseCode
	 * @return
	 */
	String encodeQrCode(String dispenseCode);
	
	/**
	 * 将二维码的原始编码解析成产品的分配号
	 * @param qrCode
	 * @return
	 */
	String decodeQrCode(String qrCode);
	
	/**
	 * 根据二维码的原始编码，获得对应的产品对象
	 * @param qrCode
	 * @return
	 */
	PlainProduct getProductByQrCode(String qrCode);
	
	
}
