package cn.sowell.copframe.weixin.message.exception;

import cn.sowell.copframe.utils.text.TextHandler;

public class WeiXinMessageException extends Exception{

	private Integer errcode;
	private String errmsg;
	private String msgid;
	

	public WeiXinMessageException(Integer errcode, String errmsg, String msgid) {
		super(
			(new TextHandler("微信模板消息接口返回错误信息[msgid=#{msgid}]，s错误码[#{errcode}]， 错误信息[#{errmsg}]"))
				.setParameter("errcode", errcode)
				.setParameter("errmsg", errmsg)
				.setParameter("msgid", msgid)
				.getText()
				);
		
	}
	
	public WeiXinMessageException(String msg) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Integer getErrcode() {
		return errcode;
	}


	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}


	public String getErrmsg() {
		return errmsg;
	}


	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}


	public String getMsgid() {
		return msgid;
	}


	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

}
