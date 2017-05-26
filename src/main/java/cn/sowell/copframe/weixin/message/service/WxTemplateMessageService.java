package cn.sowell.copframe.weixin.message.service;

import cn.sowell.copframe.weixin.message.exception.WeiXinMessageException;
import cn.sowell.copframe.weixin.message.template.TemplateMessageRequestParameter;
/**
 * 
 * <p>Title: WxTemplateMessageService</p>
 * <p>Description: </p><p>
 * 用于微信的模板信息发送
 * </p>
 * @author Copperfield Zhang
 * @date 2017年5月24日 下午6:47:37
 */
public interface WxTemplateMessageService {
	/**
	 * 发送信息
	 * @param parameter
	 * @throws WeiXinMessageException
	 */
	void sendMessage(TemplateMessageRequestParameter parameter) throws WeiXinMessageException;
}
