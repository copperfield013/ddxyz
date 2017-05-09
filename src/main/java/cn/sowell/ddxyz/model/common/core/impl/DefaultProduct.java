package cn.sowell.ddxyz.model.common.core.impl;

import java.io.Serializable;

import org.springframework.util.Assert;

import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.ddxyz.model.common.core.DispenseCode;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.ProductDataHandler;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.common.service.DataPersistenceService;

public class DefaultProduct implements Product{

	private PlainProduct pProduct;
	private DataPersistenceService dpService;
	private ProductDataHandler optionHandler;
	
	
	public DefaultProduct(PlainProduct pProduct, DataPersistenceService dpService) {
		Assert.notNull(pProduct);
		this.pProduct = pProduct;
		this.dpService = dpService;
	}
	@Override
	public Serializable getId() {
		return pProduct.getId();
	}

	@Override
	public String getName() {
		return pProduct.getName();
	}
	
	@Override
	public PlainProduct getProductInfo() {
		return pProduct;
	}

	@Override
	public void setProductDetail(PlainProduct productDetail) {
		this.pProduct = productDetail;
	}

	@Override
	public Integer getPrice() {
		return pProduct.getPrice();
	}

	@Override
	public void setPrice(Integer price) {
		pProduct.setPrice(price);
	}

	@Override
	public DispenseCode getDispenseCode() {
		String code = pProduct.getDispenseCode();
		DefaultDispenseCode dCode = new DefaultDispenseCode();
		dCode.setCode(code);
		dCode.setKey(pProduct.getDispenseKey());
		return dCode;
		
	}

	@Override
	public void setDispenseCode(DispenseCode dispenseCode) {
		if(dispenseCode == null){
			pProduct.setDispenseCode(null);
		}else{
			pProduct.setDispenseCode(dispenseCode.getCode());
			pProduct.setDispenseKey((Integer) dispenseCode.getKey());
		}
	}

	@Override
	public int getStatus() {
		return pProduct.getStatus();
	}
	
	@Override
	public String getCanceledStatus() {
		return pProduct.getCanceledStatus();
	}
	
	@Override
	public boolean isClosed() {
		return getCanceledStatus() != null;
	}

	private synchronized void normalSetProductStatus(int toStatus) throws ProductException{
		CheckResult result = Product.checkProductToStatus(this, toStatus);
		if(result.isSuc()){
			try {
				dpService.updateProductStatus(this, toStatus);
				setStatusWithoutPersistence(toStatus);
			} catch (Exception e) {
				throw new ProductException("持久化产品状态时发生异常", e);
			}
		}else{
			throw new ProductException("检测产品状态修改时发现错误，原因是【" + result.getReason() + "】");
		}
	}
	
	@Override
	public void make() throws ProductException {
		normalSetProductStatus(STATUS_MAKING);
	}
	@Override
	public void complete() throws ProductException {
		normalSetProductStatus(STATUS_COMPLETED);
		
	}
	@Override
	public void pack() throws ProductException {
		normalSetProductStatus(STATUS_PACKED);
	}
	@Override
	public void delivery() throws ProductException {
		normalSetProductStatus(STATUS_DELIVERING);
	}
	@Override
	public void deliveried() throws ProductException {
		normalSetProductStatus(STATUS_DELIVERIED);
	}
	@Override
	public void setStatusWithoutPersistence(int toStatus) throws ProductException {
		CheckResult result = Product.checkProductToStatus(this, toStatus);
		if(result.isSuc()){
			pProduct.setStatus(toStatus);
		}else{
			throw new ProductException("修改产品状态失败，原因是[" + result.getReason() + "]");
		}
	}
	
	Integer cancelStatusSetedKey = null;
	@Override
	public synchronized int setCancelStatusWithoutPersistence(String canceledStatus)
			throws ProductException {
		CheckResult checkResult = Product.checkProductToCancelStatus(this, canceledStatus);
		if(checkResult.isSuc()){
			//检测成功
			pProduct.setCanceledStatus(canceledStatus);
		}else{
			throw new ProductException("无法设置产品的取消状态，原因为[" + checkResult.getReason() + "]");
		}
		//返回随机数
		return cancelStatusSetedKey = FormatUtils.toInteger(System.currentTimeMillis() % 10000);
	}
	
	@Override
	public boolean setCancelStatusNull(int key) {
		if(cancelStatusSetedKey != null){
			if(cancelStatusSetedKey.equals(key)){
				pProduct.setCanceledStatus(null);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void setRefundFeeWithoutPersistence(int refundFee) {
		if(refundFee < 0){
			Integer price = getPrice();
			pProduct.setRefundFee(price==null? null: price);
		}else{
			pProduct.setRefundFee(refundFee);
		}
	}
	
	
	public void setDpService(DataPersistenceService dpService) {
		this.dpService = dpService;
	}
	
	
	@Override
	public ProductDataHandler getOptionHandler() {
		return optionHandler;
	}
	public void setOptionHandler(ProductDataHandler optionHandler) {
		this.optionHandler = optionHandler;
	}
}
