package cn.sowell.copframe.weixin.common.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.utils.WXHTTPClient;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.copframe.weixin.authentication.WxAuthorizationAccessToken;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.service.WxUserApiService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class WxUserApiServiceImpl implements WxUserApiService{
	
	@Resource
	WxConfigService wxConfigService;
	
	Logger logger = Logger.getLogger(WxUserApiService.class);
	
	@Override
	public WxAuthorizationAccessToken getAuthorizationAccessToken(String code) {
		//发送请求获得openid和accessToken
		String outputStr = "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		outputStr = outputStr
					.replace("APPID", PropertyPlaceholder.getProperty("appid"))
					.replace("SECRET", PropertyPlaceholder.getProperty("wxsecret"))
					.replace("CODE", code);
		String res = WXHTTPClient.request("https://api.weixin.qq.com/sns/oauth2/access_token?", 
				"POST", 
				outputStr);
		JSONObject json = JSON.parseObject(res);
		if(json.containsKey("access_token")){
			return JSON.parseObject(res, WxAuthorizationAccessToken.class);
		}else{
			logger.error("获取access_token失败，返回数据(" + res + ")");
			return null;
		}
		
	}
	
	@Override
	public boolean loadFollowingUserFromServer(String openid, WxUserPrincipal wxUser) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	@Override
	public boolean loadUserFromServer(String openid,
			String accessToken, WxUserPrincipal wxUser) {
		if(StringUtils.hasText(openid) && StringUtils.hasText(accessToken) && wxUser != null){
			String outputStr = "access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			outputStr = outputStr
					.replace("ACCESS_TOKEN", accessToken)
					.replace("OPENID", openid);
			logger.debug("发送请求到微信服务器获取用户信息");
			String userResponse = WXHTTPClient.request("https://api.weixin.qq.com/sns/userinfo", "POST", outputStr);
			logger.debug("从微信服务器获取用户信息" + userResponse);
			Object user = JSON.parseObject(userResponse, wxUser.getClass());
			BeanUtils.copyProperties(user, wxUser);
			return true;
		}
		return false;
	}
	
	
	@Override
	public boolean loadUserFromServer(WxAuthorizationAccessToken token,
			WxUserPrincipal user) {
		if(token != null){
			return loadUserFromServer(token.getOpenid(), token.getAccessToken(), user);
		}
		return false;
	}
	
	
	@Override
	public String getOauthRedirectURL(String state) {
		String afterOAuthURL = 
					PropertyPlaceholder.getProperty("project_url") 
					+ PropertyPlaceholder.getProperty("wx_authenticate_uri");
		String oAuthURL = "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + wxConfigService.getAppid()
				+ "&redirect_uri=" + afterOAuthURL
				+ "&response_type=code&scope=snsapi_userinfo"
				+ "&state="	+ state 
				+ "#wechat_redirect";
		return oAuthURL;
	}
	

}
