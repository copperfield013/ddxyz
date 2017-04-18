package cn.sowell.copframe.weixin.pay.prepay;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class H5PayParameter {
	private String appId;
	private String timeStamp;
	private String nonceStr;
	
	@JSONField(name="package")
	private String packageStr;
	
	private String signType = "MD5";
	private String paySign;
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
		JSONObject jo = (JSONObject) JSON.toJSON(this);
		Map<String, String> map = new LinkedHashMap<String, String>();
		jo.forEach((name, value) -> {
			map.put(name, String.valueOf(value));
		});
		return map;
	}
}
