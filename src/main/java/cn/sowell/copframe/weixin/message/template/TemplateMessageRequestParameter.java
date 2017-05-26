package cn.sowell.copframe.weixin.message.template;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.alibaba.fastjson.annotation.JSONField;

public class TemplateMessageRequestParameter {
	@JSONField(name="touser")
	private String toUserOpenId;
	
	@JSONField(name="template_id")
	private String templateId;
	
	@JSONField(name="url")
	private String url;
	
	private Map<String, TemplateMessageDataItem> data = new LinkedHashMap<String, TemplateMessageDataItem>();

	public String getToUserOpenId() {
		return toUserOpenId;
	}

	public void setToUserOpenId(String toUserOpenId) {
		this.toUserOpenId = toUserOpenId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, TemplateMessageDataItem> getData() {
		return data;
	}

	public void setData(Map<String, TemplateMessageDataItem> data) {
		this.data = data;
	}

	public TemplateMessageRequestParameter putData(String dataKey, String dataValue, String color) {
		Assert.hasText(dataKey);
		Assert.notNull(dataValue);
		TemplateMessageDataItem item = new TemplateMessageDataItem();
		item.setValue(dataValue);
		data.put(dataKey, item);
		return this;
		
	}
	public TemplateMessageRequestParameter putData(String dataKey, String dataValue) {
		return this.putData(dataKey, dataValue, "#173177");
	}
	
	
}
