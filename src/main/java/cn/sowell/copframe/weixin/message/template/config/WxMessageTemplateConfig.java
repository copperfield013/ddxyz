package cn.sowell.copframe.weixin.message.template.config;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;

import cn.sowell.copframe.utils.xml.Dom4jNode;
import cn.sowell.copframe.utils.xml.XmlNode;
import cn.sowell.copframe.weixin.message.exception.WxMessageConfigException;

public class WxMessageTemplateConfig {
	private Map<String, WxMessageTemplate> templateMap = new LinkedHashMap<String, WxMessageTemplate>();
	Logger logger = Logger.getLogger(WxMessageTemplateConfig.class);
	public WxMessageTemplateConfig(Resource configFile){
		if(configFile.exists()){
			SAXReader reader = new SAXReader();
			try {
				Document document = reader.read(configFile.getInputStream());
				XmlNode node = new Dom4jNode(document.getRootElement());
				node.getElements("template").forEach(cnode->{
					WxMessageTemplate template = new WxMessageTemplate();
					template.setKey(cnode.getStrictAttribute("key"));
					template.setCode(cnode.getStrictAttribute("code"));
					template.setContent(cnode.getFirstElementText("content"));
					//遍历所有app标签
					cnode.getElements("app").forEach(appTag -> {
						template.addAppTemplateId(appTag.getStrictAttribute("key"), appTag.getStrictAttribute("tid"));
					});
					cnode.getElements("data").forEach(data->{
						String bindClass = data.getStrictAttribute("class");
						WxMessageTemplateData tdata = new WxMessageTemplateData();
						try {
							tdata.setBindClass(Class.forName(bindClass));
						} catch (Exception e) {
							throw new WxMessageConfigException("没有找到class[" + bindClass + "]", e);
						}
						data.getElements("property").forEach(property->{
							WxMessageTemplateProperty tProperty = new WxMessageTemplateProperty();
							tProperty.setName(property.getStrictAttribute("name"));
							tProperty.setKey(property.getAttribute("key", tProperty.getName()));
							tProperty.setColor(property.getAttribute("color", "#173177"));
							tdata.addProperty(tProperty);
						});
						template.addData(tdata);
					});
					templateMap.put(template.getKey(), template);
				});
				
			} catch (DocumentException|IOException e) {
				throw new WxMessageConfigException("XML文件异常", e);
			}
		}
	}

	public WxMessageTemplateHandler getHandler(String templateKey, String appKey){
		WxMessageTemplate template = templateMap.get(templateKey);
		if(template != null && template.mapApp(appKey)){
			WxMessageTemplateHandler handler = new WxMessageTemplateHandler(template, appKey);
			return handler;
		}
		return null;
	}
	
	
}
