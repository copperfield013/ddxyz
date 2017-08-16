package cn.sowell.copframe.weixin.config;

public interface WxAppReadOnly {

	public String getId();

	public String getCname();

	public String getAppid();

	public String getSecret();

	public String getMerchantId();

	public String getPayKey();

	public String getPkcs12FilePath();

	public String getWxAccount();

	public WxMsgConfig getMsgConfig();
	
	public Long getDebugUserId();

}