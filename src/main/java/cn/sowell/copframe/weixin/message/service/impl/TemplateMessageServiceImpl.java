package cn.sowell.copframe.weixin.message.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.weixin.common.service.WxCredentialService;
import cn.sowell.copframe.weixin.message.exception.WeiXinMessageException;
import cn.sowell.copframe.weixin.message.service.WxTemplateMessageService;
import cn.sowell.copframe.weixin.message.template.TemplateMessageRequestParameter;

@Service
public class TemplateMessageServiceImpl implements WxTemplateMessageService{
	
	@Resource
	WxCredentialService credentialService;

	@Override
	public void sendMessage(TemplateMessageRequestParameter parameter) throws WeiXinMessageException {
		JSONObject jo = (JSONObject) JSON.toJSON(parameter);
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + credentialService.getAccessToken();
		JSONObject result = HttpRequestUtils.postJsonAndReturnJson(url , jo);
		Integer errcode = result.getInteger("errcode");
		String errmsg = result.getString("errmsg"),
				msgid = result.getString("msgid");
		
		if(!Integer.valueOf(0).equals(errcode)){
			throw new WeiXinMessageException(errcode, errmsg, msgid);
		}
	}

}
