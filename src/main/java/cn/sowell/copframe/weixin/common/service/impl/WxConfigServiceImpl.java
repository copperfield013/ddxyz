package cn.sowell.copframe.weixin.common.service.impl;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.config.WxApp;
import cn.sowell.copframe.weixin.config.WxConfig;
import cn.sowell.copframe.xml.ValueUnprovidedExcepetion;
import cn.sowell.copframe.xml.XmlNode;

@Service
public class WxConfigServiceImpl implements WxConfigService{

	Logger logger = Logger.getLogger(WxConfigService.class);
	
	@Resource
	WxConfig wxConfig;
	
	WxApp APP(){
		String wxapp = getAppKey();
		WxApp app = wxConfig.getApp(wxapp);
		if(app == null){
			throw new ValueUnprovidedExcepetion("没有找到" + wxapp +"对应的微信公众号");
		}
		return app;
	}
	
	@Override
	public String getAppWxAcount() {
		return APP().getWxAcount();
	}
	
	@Override
	public String getAppName() {
		return APP().getCname();
	}
	
	@Override
	public String getAppKey() {
		return PropertyPlaceholder.getProperty("wxapp");
	}
	
	@Override
	public String getAppid() {
		return APP().getAppid();
	}

	@Override
	public String getSecret() {
		return APP().getSecret();
	}
	
	@Override
	public String getWxPayKey() {
		return APP().getPayKey();
	}
	
	@Override
	public String getMerchantId() {
		return APP().getMerchantId();
	}
	
	@Override
	public String getMerchantPKCS12FilePath() {
		return APP().getPkcs12FilePath();
	}

	@Override
	public String getProjectURL(){
		return PropertyPlaceholder.getProperty("project_url");
	}
	@Override
	public String getPayNotifyURL() {
		String url = PropertyPlaceholder.getProperty("wxpay_notify_url");
		if(!url.matches("^https*://.+")){
			return getProjectURL() + url;
		}
		return url;
	}
	
	
	@Override
	public boolean checkSignature(String signature, XmlNode xml) {
		if(xml != null){
			XmlNode signNode = xml.getFirstElement("sign");
			if(signNode != null){
				if(signature == null){
					signature = signNode.getText();
				}
				signNode.empty();
			}
			if(signature != null 
					&& signature.equals(getMd5Signature(getWxPayKey(), xml.toTagTextMap()))){
				return true;
			}
		}
		return false;
	}
	
	public String getSignature(String key, String encodeType, Map<String, String> parameters) {
		TreeMap<String, String> map = new TreeMap<String, String>(new Comparator<String>() {

			@Override
			public int compare(String name1, String name2) {
				return name1.compareTo(name2);
			}
		});
		map.putAll(parameters);
		StringBuffer concat = new StringBuffer();
		map.forEach((name, value) -> {
			if(value != null && !value.isEmpty()){
				concat.append(name + "=" + value + "&");
			}
		});
		int keyLength = 0;
		if(key != null){
			concat.append("key=" + key);
			keyLength = key.length();
		}else{
			concat.deleteCharAt(concat.length() - 1);
		}
		logger.debug(concat.substring(0, concat.length() - keyLength - 1));
		if("md5".equalsIgnoreCase(encodeType)){
			return TextUtils.md5Encode(concat.toString()).toUpperCase();
		}else if("sha1".equalsIgnoreCase(encodeType)){
			return TextUtils.sha1Encode(concat.toString()).toUpperCase();
		}
		return null;
	}
	
	
	@Override
	public String getMd5Signature(String key, Map<String, String> parameters) {
		return getSignature(key, "md5", parameters);
	}
	
	@Override
	public String getSha1Signature(Map<String, String> signParam) {
		return getSignature(null, "sha1", signParam).toLowerCase();
	}

	@Override
	public long getCurrentTimestamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	
	@Override
	public String getMsgEncodingAESKey() {
		return APP().getMsgConfig().getEncodingAESKey();
	}
	
	
	@Override
	public String getMsgToken() {
		return APP().getMsgConfig().getToken();
	}
	
	
	@Override
	public boolean isDebug() {
		return "true".equals(PropertyPlaceholder.getProperty("debug"));
	}
	
}
