package cn.sowell.copframe.weixin.common.service;

import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.copframe.weixin.authentication.WxAuthorizationAccessToken;


public interface WxUserApiService {
	/**
	 * 根据oauth2请求的code号去微信服务器请求用户详情的accessToken和openid
	 * @param code
	 * @return
	 */
	WxAuthorizationAccessToken getAuthorizationAccessToken(String code);
	
	
	/**
	 * 从微信服务器拉取最新的该关注用户信息
	 * @param openid
	 * @return
	 */
	boolean loadFollowingUserFromServer(String openid, WxUserPrincipal wxUser);
	
	/**
	 * 从微信服务器拉取最新的用户信息，用户不一定要关注当前公众号
	 * @param openid
	 * @param accessToken
	 * @return
	 */
	boolean loadUserFromServer(String openid, String accessToken, WxUserPrincipal wxUser);

	/**
	 * 从微信服务器拉去最新的用户信息，用户不一定要关注当前公众号
	 * @param token
	 * @param user
	 * @return
	 */
	boolean loadUserFromServer(WxAuthorizationAccessToken token, WxUserPrincipal user);

	/**
	 * 构造微信授权的链接地址
	 * @param state 该字符串会在授权成功后返回
	 * @return
	 */
	String getOauthRedirectURL(String state);

	 
}
