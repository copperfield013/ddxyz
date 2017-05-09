package cn.sowell.copframe.weixin.pay.paied;

import cn.sowell.copframe.xml.XMLTag;

public class WxPayStatus {
	/**
	 * 返回状态码（SUCCESS/FAIL）
	 */
	@XMLTag(tagName="return_code", required=true, lengthLimit=16)
	private String returnCode;
	
	/**
	 * 返回信息
	 */
	@XMLTag(tagName="return_msg", required=true)
	private String returnMsg;
	
	
	@XMLTag(tagName="appid")
	private String appid;
	
	@XMLTag(tagName="mch_id")
	private String merchantId;
	
	@XMLTag(tagName="nonce_str")
	private String nonceStr;
	
	private String sign;
	
	@XMLTag(tagName="return_code")
	private String resultCode;
	
	@XMLTag(tagName="err_code")
	private String errorCode;
	
	@XMLTag(tagName="err_code_des")
	private String errorCodeDesc;
	
	@XMLTag(tagName="device_info")
	private String deviceInfo;
	
	@XMLTag(tagName="openid")
	private String openid;
	
	@XMLTag(tagName="is_subscribe")
	private String isSubscribe;
	
	@XMLTag(tagName="trade_type")
	private String tradeType;
	
	@XMLTag(tagName="trade_state")
	private String tradeState;
	
	@XMLTag(tagName="bank_type")
	private String bankType;
	
	@XMLTag(tagName="total_fee")
	private Integer totalFee;
	
	@XMLTag(tagName="settlement_total_fee")
	private Integer settlementTotalFee;
	
	@XMLTag(tagName="fee_type")
	private String feeType;
	
	@XMLTag(tagName="cash_fee")
	private Integer cashFee;
	
	@XMLTag(tagName="cash_fee_type")
	private String cashFeeType;
	
	@XMLTag(tagName="coupon_fee")
	private Integer couponFee;
	
	@XMLTag(tagName="coupon_count")
	private Integer couponCount;
	
	@XMLTag(tagName="coupon_type_$n")
	private String couponType$n;
	
	@XMLTag(tagName="coupon_id_$n")
	private String couponId$n;
	
	@XMLTag(tagName="coupon_fee_$n")
	private Integer couponFee$n;
	
	@XMLTag(tagName="transaction_id")
	private String transactionId;
	
	@XMLTag(tagName="out_trade_no")
	private String outTradeNo;
	
	@XMLTag(tagName="attach")
	private String attach;
	
	@XMLTag(tagName="time_end")
	private String timeEnd;
	
	@XMLTag(tagName="trade_state_desc")
	private String tradeStateDesc;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

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

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCodeDesc() {
		return errorCodeDesc;
	}

	public void setErrorCodeDesc(String errorCodeDesc) {
		this.errorCodeDesc = errorCodeDesc;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}

	public void setSettlementTotalFee(Integer settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Integer getCashFee() {
		return cashFee;
	}

	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}

	public String getCashFeeType() {
		return cashFeeType;
	}

	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	public String getCouponType$n() {
		return couponType$n;
	}

	public void setCouponType$n(String couponType$n) {
		this.couponType$n = couponType$n;
	}

	public String getCouponId$n() {
		return couponId$n;
	}

	public void setCouponId$n(String couponId$n) {
		this.couponId$n = couponId$n;
	}

	public Integer getCouponFee$n() {
		return couponFee$n;
	}

	public void setCouponFee$n(Integer couponFee$n) {
		this.couponFee$n = couponFee$n;
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

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getTradeStateDesc() {
		return tradeStateDesc;
	}

	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}
}
