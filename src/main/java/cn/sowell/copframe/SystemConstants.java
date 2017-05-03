package cn.sowell.copframe;

public interface SystemConstants {
	String WXAUTHEN_STATE_KEY = "wxauthen_state_key";
	String WXREDIRECT_URL_KEY = "Wxredirect_url_key";
	String WXUSER_KEY = "wxuser_key";
	
	/**
	 * 微信统一下单接口
	 */
	String WXPAY_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * 微信退款接口
	 */
	String WXPAY_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/**
	 * 微信订单查询接口
	 */
	String WXPAY_ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
	/**
	 * 微信支付的预付款订单的可支付的最长时间（单位：秒）
	 */
	Integer WX_PAY_MAX_INTERVAL = 2 * 60 * 60;
}
