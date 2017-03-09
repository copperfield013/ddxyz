package cn.sowell.copframe.weixin.common.service.impl;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.weixin.common.service.WxConfigService;

@Service
public class WxConfigServiceImpl implements WxConfigService{

	@Override
	public String getAppid() {
		return PropertyPlaceholder.getProperty("appid");
	}

	@Override
	public String getSecret() {
		return PropertyPlaceholder.getProperty("wxsecret");
	}
	
}
