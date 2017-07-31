package cn.sowell.copframe.weixin.config;

public class WxApp implements Cloneable, WxAppReadOnly{
	private String id;
	private String wxaccount;
	private String cname;
	private String appid;
	private String secret;
	private String merchantId;
	private String payKey;
	private String pkcs12FilePath;
	private WxMsgConfig msgConfig = new WxMsgConfig();
	
	@Override
	public WxAppReadOnly clone() throws CloneNotSupportedException {
		WxApp clone = new WxApp();
		clone.setId(id);
		clone.setWxAccount(wxaccount);
		clone.setCname(cname);
		clone.setAppid(appid);
		clone.setSecret(secret);
		clone.setMerchantId(merchantId);
		clone.setPayKey(payKey);
		clone.setPkcs12FilePath(pkcs12FilePath);
		clone.setMsgConfig(msgConfig.clone());
		return clone;
	}
	@Override
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	@Override
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	@Override
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	@Override
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	@Override
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	@Override
	public String getPkcs12FilePath() {
		return pkcs12FilePath;
	}
	public void setPkcs12FilePath(String pkcs12FilePath) {
		this.pkcs12FilePath = pkcs12FilePath;
	}
	@Override
	public String getWxAccount() {
		return wxaccount;
	}
	public void setWxAccount(String wxaccount) {
		this.wxaccount = wxaccount;
	}
	@Override
	public WxMsgConfig getMsgConfig() {
		return msgConfig;
	}
	public void setMsgConfig(WxMsgConfig msgConfig) {
		this.msgConfig = msgConfig;
	}
}
