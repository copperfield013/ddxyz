package cn.sowell.copframe.weixin.pay.prepay;


import cn.sowell.copframe.utils.xml.XMLTag;

import com.alibaba.fastjson.JSON;
/**
 * 
 * <p>Title: PrepayResult</p>
 * <p>Description: </p><p>
 * 预支付请求返回数据
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月10日 上午8:47:51
 */
public class PrepayResult {
	static String RETURN_CODE_SUC = "SUCCESS",
					RETURN_CODE_FAIL = "FAIL";
	/**
	 * SUCCESS/FAIL。此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 */
	@XMLTag(tagName="return_code")
	private String returnCode;
	
	/**
	 * 返回信息，如非空，为错误原因
	 */
	@XMLTag(tagName="return_msg")
	private String returnMsg;
	
	private UnifiedOrder submitUOrder;
	
	/*************************************************
	 * 下面这些字段仅在return_code为SUCCESS的时候会返回
	 *
	 *************************************************/
	
	
	/**
	 * 公众账号ID。调用接口提交的公众账号ID
	 */
	@XMLTag()
	private String appid;
	
	/**
	 * 	商户号。自定义参数，可以为请求支付的终端设备号等
	 */
	@XMLTag(tagName="mch_id")
	private String merchantId;
	
	/**
	 * 设备号。自定义参数，可以为请求支付的终端设备号等
	 */
	@XMLTag(tagName="device_info")
	private String deviceInfo;
	
	/**
	 * 随机字符串。微信返回的随机字符串
	 */
	@XMLTag(tagName="nonce_str")
	private String nonceStr;
	
	/**
	 * 微信返回的签名值，详见签名算法
	 */
	@XMLTag(tagName="sign")
	private String sign;
	
	/**
	 * 业务结果。SUCCESS/FAIL
	 */
	@XMLTag(tagName="result_code")
	private String resultCode;
	
	/**
	 * 错误代码。详细参见下文错误列表
	 */
	@XMLTag(tagName="err_code")
	private String errorCode;
	
	/**
	 * 错误代码描述。错误信息描述
	 */
	@XMLTag(tagName="err_code_des")
	private String errorCodeDesc;
	
	/*******************************************
	 * 
	 * 下面的字段在在return_code 和result_code都为SUCCESS的时候有返回
	 * 
	 ******************************************/
	
	
	/**
	 * 交易类型。取值为：JSAPI，NATIVE，APP等，说明详见参数规定
	 */
	@XMLTag(tagName="trade_type")
	private String tradeType;
	
	/**
	 * 预支付交易会话标识。微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	 */
	@XMLTag(tagName="prepay_id")
	private String prepayId;
	
	/**
	 * 二维码链接。trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付
	 */
	@XMLTag(tagName="code_url")
	private String codeURL;
	
	
	/**
	 * 判断当前通信是否成功
	 * @return
	 */
	boolean returnSuccess(){
		return RETURN_CODE_SUC.equals(returnCode);
	}


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


	public String getTradeType() {
		return tradeType;
	}


	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}


	public String getPrepayId() {
		return prepayId;
	}


	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}


	public String getCodeURL() {
		return codeURL;
	}


	public void setCodeURL(String codeURL) {
		this.codeURL = codeURL;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}


	public UnifiedOrder getSubmitUOrder() {
		return submitUOrder;
	}


	public void setSubmitUOrder(UnifiedOrder submitUOrder) {
		this.submitUOrder = submitUOrder;
	}
}
