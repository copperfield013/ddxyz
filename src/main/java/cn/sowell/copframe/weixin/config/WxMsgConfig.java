package cn.sowell.copframe.weixin.config;

public class WxMsgConfig implements Cloneable{
	private String token;
	private String encodingAESKey;
	
	@Override
	public WxMsgConfig clone() throws CloneNotSupportedException {
		WxMsgConfig clone = new WxMsgConfig();
		clone.setToken(token);
		clone.setEncodingAESKey(encodingAESKey);
		return clone;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEncodingAESKey() {
		return encodingAESKey;
	}
	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}
}
