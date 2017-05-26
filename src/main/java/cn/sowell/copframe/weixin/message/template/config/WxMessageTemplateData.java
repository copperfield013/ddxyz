package cn.sowell.copframe.weixin.message.template.config;

import java.util.LinkedHashMap;
import java.util.Map;

public class WxMessageTemplateData {
	private Class<?> bindClass;
	private Map<String, WxMessageTemplateProperty> propertyMap = new LinkedHashMap<String, WxMessageTemplateProperty>();
	public Class<?> getBindClass() {
		return bindClass;
	}
	public void setBindClass(Class<?> bindClass) {
		this.bindClass = bindClass;
	}
	public Map<String, WxMessageTemplateProperty> getPropertyMap() {
		return propertyMap;
	}
	public void setPropertyMap(Map<String, WxMessageTemplateProperty> propertyMap) {
		this.propertyMap = propertyMap;
	}
	public void addProperty(WxMessageTemplateProperty tProperty) {
		propertyMap.put(tProperty.getName(), tProperty);
	}
}
