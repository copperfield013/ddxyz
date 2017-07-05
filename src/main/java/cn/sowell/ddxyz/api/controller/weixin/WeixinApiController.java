package cn.sowell.ddxyz.api.controller.weixin;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.exception.XMLException;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.xml.Dom4jNode;
import cn.sowell.copframe.xml.XmlNode;
import cn.sowell.ddxyz.api.dto.message.WeiXinMessageEncryptedData;
import cn.sowell.ddxyz.api.dto.message.WeiXinMessageParameter;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

@Controller
@RequestMapping("/api/weixin")
public class WeixinApiController {
	
	@Resource
	WxConfigService configService;
	
	Logger logger = Logger.getLogger(WeixinApiController.class);
	
	@ResponseBody
	@RequestMapping("/message")
	public String message(WeiXinMessageParameter msgParam,
			@RequestBody(required=false) String xml){
		if(xml != null){
			try {
				WXBizMsgCrypt pc = new WXBizMsgCrypt(configService.getMsgToken(), configService.getMsgEncodingAESKey(), configService.getAppid());
				logger.info(xml);
				String msg = pc.decryptMsg(msgParam.getMsg_signature(), msgParam.getTimestamp(), msgParam.getNonce(), xml);
				XmlNode msgXml = new Dom4jNode(msg);
				WeiXinMessageEncryptedData data = XmlNode.parseObject(msgXml, new WeiXinMessageEncryptedData());
				logger.info("解析后消息：");
				logger.info(data.getContent());
			} catch (AesException e) {
				logger.error("微信解析消息时发生错误", e);
			} catch (XMLException e) {
				logger.error("解析转换后的消息时发生错误");
			}
			logger.info(xml);
		}
		return msgParam.getEchostr();
	}
	
}
