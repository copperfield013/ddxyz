package cn.sowell.copframe.weixin.message.template.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.BeanUtils;

import cn.sowell.copframe.utils.PojoUtils;
import cn.sowell.copframe.weixin.message.exception.WxMessageConfigException;
import cn.sowell.copframe.weixin.message.template.TemplateMessageDataItem;
import cn.sowell.copframe.weixin.message.template.TemplateMessageRequestParameter;

/**
 * 
 * <p>Title: WxMessageTemplateHandler</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @param <T>
 * @date 2017年5月25日 下午2:26:12
 */
public class WxMessageTemplateHandler {
	private WxMessageTemplate template;
	private String appKey;
	public WxMessageTemplateHandler(WxMessageTemplate template, String appKey) {
		this.appKey = appKey;
		this.template = template;
	}
	
	public <T> TemplateMessageRequestParameter build(String toUserOpenid, Class<T> bindClass, Consumer<T> compiler){
		WxMessageTemplateData tData = this.template.getData(bindClass);
		if(tData != null){
			T obj = BeanUtils.instantiate(bindClass);
			compiler.accept(obj);
			TemplateMessageRequestParameter parameter = new TemplateMessageRequestParameter();
			parameter.setTemplateId(template.getAppTemplateId(appKey));
			parameter.setToUserOpenId(toUserOpenid);
			Map<String, TemplateMessageDataItem> data = new LinkedHashMap<String, TemplateMessageDataItem>();
			tData.getPropertyMap().forEach((name, property)->{
				TemplateMessageDataItem item = new TemplateMessageDataItem();
				item.setValue(String.valueOf(PojoUtils.getPropertyValue(obj, name)));
				item.setColor(property.getColor());
				data.put(property.getKey(), item);
			});
			parameter.setData(data);
			return parameter;
		}else{
			throw new WxMessageConfigException("没有找到class[" + bindClass + "]对应的data");
		}
	}
	
	
	
	
	

}
