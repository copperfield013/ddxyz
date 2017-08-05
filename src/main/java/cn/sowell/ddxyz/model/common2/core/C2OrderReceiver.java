package cn.sowell.ddxyz.model.common2.core;

public interface C2OrderReceiver {
	/**
	 * 获得用户的id
	 * @return
	 */
	Long getUserId();
	
	/**
	 * 获得微信用户的openid
	 */
	String getOpenid();
	
	/**
	 * 获得收件人姓名
	 * @return
	 */
	String getName();
	
	/**
	 * 获得收货人的联系号码
	 * @return
	 */
	String getContactNumber();
	
	/**
	 * 获得收货人的地址
	 * @return
	 */
	String getAddress();
}
