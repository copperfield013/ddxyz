package cn.sowell.copframe.weixin.pay.paied;

import cn.sowell.copframe.weixin.pay.prepay.SignSetable;
import cn.sowell.copframe.xml.XMLTag;

public class WxPayStatusParam implements SignSetable{
	@XMLTag(required=true, lengthLimit=32)
	private String appid;
	
	@XMLTag(tagName="mch_id", lengthLimit=32, required=true)
	private String merchantId;
	
	@XMLTag(tagName="transaction_id", lengthLimit=32)
	private String transactionId;
	
	@XMLTag(tagName="out_trade_no", lengthLimit=32)
	private String outTradeNo;
	
	
	@XMLTag(tagName="nonce_str", lengthLimit=32, required=true)
	private String nonceStr;
	
	@XMLTag(lengthLimit=32, required=true)
	private String sign;
	
	@XMLTag(tagName="sign_type", lengthLimit=32)
	private String signType;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
}
