package cn.sowell.copframe.weixin.common.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.utils.WXHTTPClient;
import cn.sowell.copframe.weixin.common.service.WxCredentialService;

@Service
public class WxCredentialServiceImpl implements WxCredentialService{
	private static Byte lock = 1;
	private static String accessToken;
	private static Long accessExpiresIn;
	private static long lastGetTokenTime;
	
	Logger logger = Logger.getLogger(WxCredentialService.class);
	
	@Override
	public String getAccessToken() {
		synchronized (lock) {
			if(accessToken == null || (System.currentTimeMillis() - lastGetTokenTime) > accessExpiresIn){
				String reqURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
				reqURL = reqURL.replace("APPID", PropertyPlaceholder.getProperty("appid"))
						.replace("APPSECRET", PropertyPlaceholder.getProperty("wxsecret"))
						;
				String res = WXHTTPClient.request(reqURL, "GET", null);
				JSONObject json = JSON.parseObject(res);
				String thisToken = json.getString("access_token");
				if(thisToken != null){
					accessToken = thisToken;
					accessExpiresIn = json.getLong("expires_in") * 1000;
					lastGetTokenTime = System.currentTimeMillis();
				}else{
					logger.error("请求微信AccessToken时发生错误:返回数据(" + res + ")，请求地址(" 
							+ reqURL.substring(0, reqURL.length() - 10) 
							+ "***隐藏10位secret***)" );
				}
			}
		}
		return accessToken;
	}
	
}
