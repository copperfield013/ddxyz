package cn.sowell.copframe.weixin.config;

public class WxApp {
	private String id;
	private String cname;
	private String appid;
	private String secret;
	private String merchantId;
	private String payKey;
	private String pkcs12FilePath;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	public String getPkcs12FilePath() {
		return pkcs12FilePath;
	}
	public void setPkcs12FilePath(String pkcs12FilePath) {
		this.pkcs12FilePath = pkcs12FilePath;
	}
}
