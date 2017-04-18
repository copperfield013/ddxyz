package cn.sowell.copframe.weixin.pay.refund;

import cn.sowell.copframe.xml.XMLTag;

public class RefundResult {
	/**
	 * 返回状态码
	 */
	@XMLTag(tagName="return_code", required=true,lengthLimit=16)
	private String returnCode;
	
	/**
	 * 返回信息
	 */
	@XMLTag(tagName="return_msg", lengthLimit=128)
	private String returnMsg;
	
	/**
	 * 业务结果
	 */
	@XMLTag(tagName="result_code")
	private String resultCode;
	
	/**
	 * 错误代码
	 */
	@XMLTag(tagName="err_code")
	private String errorCode;
	/**
	 * 错误代码描述
	 */
	@XMLTag(tagName="err_code_des")
	private String errorCodeDesc;
	
	/**
	 * 公众号id
	 */
	@XMLTag(tagName="appid")
	private String appid;
	
	/**
	 * 商户号
	 */
	@XMLTag(tagName="mch_id")
	private String merchantId;
	
	/**
	 * 设备号
	 */
	@XMLTag(tagName="device_info")
	private String deviceInfo;
	
	/**
	 * 随机字符串
	 */
	@XMLTag(tagName="nonce_str")
	private String nonceStr;
	
	/**
	 * 签名
	 */
	@XMLTag(tagName="sign")
	private String sign;
	
	/**
	 * 微信订单号
	 */
	@XMLTag(tagName="transaction_id")
	private String transactionId;
	
	/**
	 * 商户订单号
	 */
	@XMLTag(tagName="out_trade_no")
	private String outTradeno;
	
	/**
	 * 商户退款单号
	 */
	@XMLTag(tagName="out_refund_no")
	private String outRefundNo;
	
	/**
	 * 微信退款单号
	 */
	@XMLTag(tagName="refund_id")
	private String refundId;
	
	/**
	 * 退款金额
	 */
	@XMLTag(tagName="refund_fee")
	private Integer refundFee;
	
	@XMLTag(tagName="settlement_total_fee")
	private Integer settlementRefundFee;
	
	/**
	 * 标价金额
	 */
	@XMLTag(tagName="total_fee")
	private Integer totalFee;
	
	/**
	 * 应结订单金额
	 */
	@XMLTag(tagName="settlement_total_fee")
	private Integer settlementTotalFee;
	
	/**
	 * 标记币种
	 */
	@XMLTag(tagName="fee_type")
	private String feeType;
	
	/**
	 * 现金支付金额
	 */
	@XMLTag(tagName="cash_fee")
	private Integer cashFee;
	
	/**
	 * 现金支付币种
	 */
	@XMLTag(tagName="cash_fee_type")
	private String cashFeeType;
	/**
	 * 现金退款金额
	 */
	@XMLTag(tagName="cash_refund_fee")
	private Integer cashRefundFee;
	
	/**
	 * 代金券类型
	 */
	@XMLTag(tagName="coupon_type_$n")
	private String couponType$n;
	
	/**
	 * 代金券退款总金额
	 */
	@XMLTag(tagName="coupon_refund_fee")
	private Integer couponRefundFee;
	
	/**
	 * 单个代金券退款金额
	 */
	@XMLTag(tagName="coupon_refund_fee_$n")
	private Integer couponRefundFee$n;
	
	/**
	 * 退款代金券使用数量
	 */
	@XMLTag(tagName="coupon_refund_count")
	private Integer couponRefundCount;
	
	/**
	 * 退款代金券id
	 */
	@XMLTag(tagName="coupon_refund_id_$n")
	private String couponRefundId$n;

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutTradeno() {
		return outTradeno;
	}

	public void setOutTradeno(String outTradeno) {
		this.outTradeno = outTradeno;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	public Integer getSettlementRefundFee() {
		return settlementRefundFee;
	}

	public void setSettlementRefundFee(Integer settlementRefundFee) {
		this.settlementRefundFee = settlementRefundFee;
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

	public Integer getCashRefundFee() {
		return cashRefundFee;
	}

	public void setCashRefundFee(Integer cashRefundFee) {
		this.cashRefundFee = cashRefundFee;
	}

	public String getCouponType$n() {
		return couponType$n;
	}

	public void setCouponType$n(String couponType$n) {
		this.couponType$n = couponType$n;
	}

	public Integer getCouponRefundFee() {
		return couponRefundFee;
	}

	public void setCouponRefundFee(Integer couponRefundFee) {
		this.couponRefundFee = couponRefundFee;
	}

	public Integer getCouponRefundFee$n() {
		return couponRefundFee$n;
	}

	public void setCouponRefundFee$n(Integer couponRefundFee$n) {
		this.couponRefundFee$n = couponRefundFee$n;
	}

	public Integer getCouponRefundCount() {
		return couponRefundCount;
	}

	public void setCouponRefundCount(Integer couponRefundCount) {
		this.couponRefundCount = couponRefundCount;
	}

	public String getCouponRefundId$n() {
		return couponRefundId$n;
	}

	public void setCouponRefundId$n(String couponRefundId$n) {
		this.couponRefundId$n = couponRefundId$n;
	}
	
	
	
}
