package cn.sowell.copframe.weixin.message.exception;

public class WxMessageConfigException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2078398809427753743L;
	
	public WxMessageConfigException(String msg) {
		super(msg);
	}
	
	
	public WxMessageConfigException(String msg, Throwable e) {
		super(msg, e);
	}
	

}
