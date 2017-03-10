package cn.sowell.copframe.weixin.pay.service;

import cn.sowell.copframe.weixin.pay.prepay.JsApiPrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.PrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.PrepayResult;
import cn.sowell.copframe.weixin.pay.prepay.UnifiedOrder;

/**
 * 
 * <p>Title: WxPayService</p>
 * <p>Description: </p><p>
 * 微信支付相关接口调用
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月10日 上午8:50:38
 */
public interface WxPayService {
	/**
	 * 发送预付款订单到微信服务器，并返回从微信服务器得到的结果对象
	 * @param order
	 * @param replaceSignature 是否重新计算签名并覆盖签名
	 * @return
	 */
	PrepayResult sendPrepay(UnifiedOrder order, boolean replaceSignature);
	/**
	 * 通过参数构建预付款订单对象，并发送订单到服务器，返回结果对象<br/>
	 * 预付款订单参数对象当前支持以下几个实现类
	 * <ul>
	 * 		<li>
	 * 			{@link JsApiPrepayParameter}:微信支付JS接口
	 * 		</li>
	 * </ul>
	 * 
	 * @param parameter
	 * @return
	 */
	PrepayResult sendPrepay(PrepayParameter parameter);
	
}
