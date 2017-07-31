package cn.sowell.ddxyz.api.controller.weixin;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import cn.sowell.copframe.dto.xml.XMLResponse;
import cn.sowell.copframe.spring.binder.XMLNodeConverter;
import cn.sowell.copframe.utils.xml.Dom4jNode;
import cn.sowell.copframe.utils.xml.XMLException;
import cn.sowell.copframe.utils.xml.XmlNode;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.ddxyz.api.dto.message.WeiXinMessageEncryptedData;
import cn.sowell.ddxyz.api.dto.message.WeiXinMessageParameter;
import cn.sowell.ddxyz.model.message.pojo.MessageConfig;
import cn.sowell.ddxyz.model.message.service.MessageConfigService;

@Controller
@RequestMapping("/api/weixin")
public class WeixinApiController {
	
	@Resource
	WxConfigService configService;
	
	@Resource
	MessageConfigService messageConfigService;
	
	Logger logger = Logger.getLogger(WeixinApiController.class);
	
	@ResponseBody
	@RequestMapping("/message")
	public XMLResponse message(WeiXinMessageParameter msgParam,
			@RequestBody(required=false) String xml){
		XMLResponse xmlResponse = new XMLResponse();
		if(xml != null){
			try {
				WXBizMsgCrypt pc = new WXBizMsgCrypt(configService.getMsgToken(), configService.getMsgEncodingAESKey(), configService.getAppid());
				logger.info(xml);
				String msg = pc.decryptMsg(msgParam.getMsg_signature(), msgParam.getTimestamp(), msgParam.getNonce(), xml);
				XmlNode msgXml = new Dom4jNode(msg);
				WeiXinMessageEncryptedData data = XMLNodeConverter.parseObject(msgXml, new WeiXinMessageEncryptedData());
				logger.info("解析后消息：");
				logger.info(data.getContent());
				
				xmlResponse.putElement("ToUserName", data.getFromUserName());
				xmlResponse.putElement("FromUserName", data.getToUserName());
				xmlResponse.putElement("CreateTime", String.valueOf(new Date().getTime()));
				xmlResponse.putElement("MsgType", "text");
				int curLevel = 0;
				for(MessageConfig messageConfig : messageConfigService.getListFromCache()){
					if(data.getContent()!= null){
						if(data.getContent().contains("客服")){
							xmlResponse.putElement("MsgType", "transfer_customer_service");
						}
						if(data.getContent().matches(messageConfig.getRuleExpression())){
							if(messageConfig.getLevel() != null && messageConfig.getLevel() >= curLevel){
								xmlResponse.putElement("Content", messageConfig.getBackContent());
								curLevel = messageConfig.getLevel();
							}
						}
					}else{
						return null;
					}
				}
				if(xmlResponse.getRoot().getFirstElement("Content") == null){
					xmlResponse.putElement("Content", "暂时没有您想要的信息！");
				}
			} catch (AesException e) {
				logger.error("微信解析消息时发生错误", e);
			} catch (XMLException e) {
				logger.error("解析转换后的消息时发生错误");
			}
			logger.info(xml);
		}
		return xmlResponse;
	}
	
}
