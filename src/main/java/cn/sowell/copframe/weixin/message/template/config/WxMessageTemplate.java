package cn.sowell.copframe.weixin.message.template.config;

import java.util.LinkedHashMap;
import java.util.Map;

public class WxMessageTemplate {
	private String key;
	private String code;
	private String content;
	private Map<Class<?>, WxMessageTemplateData> dataMap = new LinkedHashMap<Class<?>, WxMessageTemplateData>();
	private Map<String, String> appMap = new LinkedHashMap<String, String>();
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<Class<?>, WxMessageTemplateData> getDatas() {
		return dataMap;
	}
	public void setDatas(Map<Class<?>, WxMessageTemplateData> datas) {
		this.dataMap = datas;
	}
	public void addData(WxMessageTemplateData tdata) {
		dataMap.put(tdata.getBindClass(), tdata);
	}
	
	public WxMessageTemplateData getData(Class<?> bindClass){
		return dataMap.get(bindClass);
	}
	
	public boolean containsData(Class<?> bindClass){
		return dataMap.containsKey(bindClass);
	}
	
	public void addAppTemplateId(String key, String templateId){
		this.appMap.put(key, templateId);
	}
	
	public String getAppTemplateId(String key){
		return this.appMap.get(key);
	}
	
	public boolean mapApp(String appKey){
		return this.appMap.containsKey(appKey);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
