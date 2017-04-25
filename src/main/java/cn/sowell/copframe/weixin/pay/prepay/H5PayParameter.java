package cn.sowell.copframe.weixin.pay.prepay;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class H5PayParameter {
	private String appId;
	@JSONField(name="timestamp")
	private String timeStamp;//支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
	private String nonceStr;// 支付签名随机串，不长于 32 位
	
	@JSONField(name="package")
	private String packageStr;// 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
	
	private String signType = "MD5";// 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
	private String paySign;// 支付签名
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public String getPackageStr() {
		return packageStr;
	}
	public void setPackageStr(String packageStr) {
		this.packageStr = packageStr;
	}
	public Map<String, String> toMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("appId", getAppId());
		map.put("timeStamp", getTimeStamp());
		map.put("nonceStr", getNonceStr());
		map.put("package", getPackageStr());
		map.put("signType", getSignType());
		return map;
	}
}
