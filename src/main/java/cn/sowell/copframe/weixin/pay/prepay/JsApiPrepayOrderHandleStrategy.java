package cn.sowell.copframe.weixin.pay.prepay;

import javax.annotation.Resource;

import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;

/**
 * 
 * <p>Title: JsApiPrepayOrderHandleStrategy</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月10日 下午2:02:21
 */
public class JsApiPrepayOrderHandleStrategy implements PrepayOrderProcessStrategy<JsApiPrepayParameter> {

	@Resource
	WxConfigService configService;
	
	@Resource(name="prepayTradeNoGenerator")
	TradeNoGenerator tradeNoGenerator; 
	
	@Override
	public boolean support(JsApiPrepayParameter parameter) {
		return parameter instanceof JsApiPrepayParameter && parameter != null;
	}

	@Override
	public UnifiedOrder preprocess(UnifiedOrder order) {
		order.setAppid(configService.getAppid());
		order.setMerchantId(configService.getMerchantId());
		order.setNonceStr(TextUtils.uuid());
		order.setNotifyURL(configService.getPayNotifyURL());
		order.setTradeType(TradeType.JSAPI);
		return order;
	}

	@Override
	public UnifiedOrder setParameter(UnifiedOrder order,
			JsApiPrepayParameter parameter) {
		order.setTotalFee(parameter.getTotalFee());
		order.setOpenid(parameter.getOpenId());
		order.setBody(parameter.getBody());
		order.setDetail(parameter.getOrderDetail());
		order.setOutTradeNo(tradeNoGenerator.generate(order, parameter));
		order.setSpbillCreateIp("14.23.150.211");
		return order;
	}

}
