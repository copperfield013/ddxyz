package cn.sowell.ddxyz.api.dto.message;

import cn.sowell.copframe.xml.XMLTag;

/**
 * 
 * <p>Title: WeiXinMessageEncryptedData</p>
 * <p>Description: </p><p>
 * 微信消息解析后的数据
 * </p>
 * @author Copperfield Zhang
 * @date 2017年7月4日 下午5:15:48
 */
public class WeiXinMessageEncryptedData {
	/**
	 * 开发者微信号
	 */
	@XMLTag(tagName="ToUserName")
	private String toUserName;
	
	/**
	 * 发送方账号（一个OpenID）
	 */
	@XMLTag(tagName="FromUserName")
	private String fromUserName;
	/**
	 * 消息创建时间（整型）
	 */
	@XMLTag(tagName="CreateTime")
	private String createTime;
	
	/**
	 * 消息类型（text）
	 */
	@XMLTag(tagName="MsgType")
	private String msgType;
	
	/**
	 * 消息内容
	 */
	@XMLTag(tagName="Content")
	private String content;
	
	/**
	 * 消息id，64位整型
	 */
	@XMLTag(tagName="MsgId")
	private String msgId;
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
}
