package cn.sowell.copframe.weixin.common.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.service.WxCredentialService;
import cn.sowell.copframe.weixin.common.utils.TransientData;

@Service
public class WxCredentialServiceImpl implements WxCredentialService{
	
	
	Logger logger = Logger.getLogger(WxCredentialService.class);
	
	@Resource
	WxConfigService configService;
	
	
	TransientData<String> accessToken = new TransientData<String>();
	TransientData<String> jsApiTicket = new TransientData<String>();
	
	
	@Override
	public String getAccessToken() {
		if(!accessToken.hasInit()){
			String reqURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
			reqURL = reqURL.replace("APPID", configService.getAppid())
					.replace("APPSECRET", configService.getSecret())
					;
			accessToken.init(reqURL, "access_token");
		}
		return accessToken.getValue();
		
		
	}
	
	@Override
	public String getJsApiTicket(){
		if(!jsApiTicket.hasInit()){
			String reqUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
			reqUrl = reqUrl.replace("ACCESS_TOKEN", getAccessToken());
			jsApiTicket.init(reqUrl, "ticket");
		}
		return jsApiTicket.getValue();
	}
	
}
