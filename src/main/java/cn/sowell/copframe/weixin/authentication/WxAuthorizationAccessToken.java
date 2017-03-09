package cn.sowell.copframe.weixin.authentication;

import com.alibaba.fastjson.annotation.JSONField;

public class WxAuthorizationAccessToken {
	@JSONField(name="access_token")
	private String accessToken;
	
	@JSONField(name="expires_in")
	private Long expiresIn;
	
	@JSONField(name="refresh_token")
	private String refreshToken;
	private String openid;
	private String scope;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	} 
	
}
