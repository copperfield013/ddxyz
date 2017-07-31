package cn.sowell.copframe.weixin.pay.refund;

import cn.sowell.copframe.utils.xml.XMLTag;
import cn.sowell.copframe.weixin.pay.prepay.SignSetable;

public class RefundRequest implements SignSetable{
	
	
	/**
	 * 公账号id
	 */
	@XMLTag(required=true, lengthLimit=32)
	private String appid;
	/**
	 * 商户号
	 */
	@XMLTag(tagName="mch_id", required=true, lengthLimit=32)
	private String merchantId;
	/**
	 * 设备号
	 */
	@XMLTag(tagName="device_info", lengthLimit=32)
	private String deviceInfo;
	/**
	 * 随机字符串
	 */
	@XMLTag(tagName="nonce_str", required=true, lengthLimit=32)
	private String nonceStr;
	/**
	 * 签名
	 */
	@XMLTag(tagName="sign", required=true, lengthLimit=32)
	private String sign;
	/**
	 * 签名类型
	 */
	@XMLTag(tagName="sign_type", lengthLimit=32)
	private String signType = "MD5";
	/**
	 * 微信订单号
	 */
	@XMLTag(tagName="transaction_id", lengthLimit=28)
	private String transactionId;
	/**
	 * 商户订单号
	 */
	@XMLTag(tagName="out_trade_no", lengthLimit=32)
	private String outTradeNo;
	/**
	 * 商户退款单号
	 */
	@XMLTag(tagName="out_refund_no",required=true, lengthLimit=32)
	private String outRefundNo;
	/**
	 * 订单金额
	 */
	@XMLTag(tagName="total_fee", required=true)
	private Integer totalFee;
	/**
	 * 退款金额
	 */
	@XMLTag(tagName="refund_fee", required=true)
	private Integer refundFee;
	/**
	 * 货币种类
	 */
	@XMLTag(tagName="refund_fee_type", lengthLimit=8)
	private String refundFeeType = "CNY";
	/**
	 * 操作员
	 */
	@XMLTag(tagName="op_user_id", required=true, lengthLimit=32)
	private String operateUserId;
	/**
	 * 退款资金来源
	 */
	@XMLTag(tagName="refund_account", lengthLimit=30)
	private String refundAccount;
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
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
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
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getOutRefundNo() {
		return outRefundNo;
	}
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public Integer getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}
	public String getRefundFeeType() {
		return refundFeeType;
	}
	public void setRefundFeeType(String refundFeeType) {
		this.refundFeeType = refundFeeType;
	}
	public String getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getRefundAccount() {
		return refundAccount;
	}
	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}
}
