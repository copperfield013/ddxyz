package cn.sowell.copframe.weixin.common.service;

/**
 * 
 * <p>Title: WxCredentialService</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月12日 下午10:22:03
 */
public interface WxCredentialService {
	/**
	 * 获得当前公众号的accessToken
	 * @return
	 */
	String getAccessToken();

	/**
	 * 获得微信JsApiTicket
	 * @return
	 */
	String getJsApiTicket();
}
