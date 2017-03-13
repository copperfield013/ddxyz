package cn.sowell.copframe.weixin.pay.service.impl;

import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.SystemConstants;
import cn.sowell.copframe.exception.XMLException;
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.pay.prepay.PrepayOrderFactory;
import cn.sowell.copframe.weixin.pay.prepay.PrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.PrepayResult;
import cn.sowell.copframe.weixin.pay.prepay.UnifiedOrder;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.copframe.xml.XMLConvertConfig;
import cn.sowell.copframe.xml.XMLConverter;
import cn.sowell.copframe.xml.XmlNode;

@Service
public class WxPayServiceImpl implements WxPayService{

	@Resource
	WxConfigService configService;
	
	@Resource
	PrepayOrderFactory prepayOrderFactory;
	
	XMLConverter<UnifiedOrder> unifiedOrderConverter = new XMLConverter<UnifiedOrder>();
	XMLConverter<PrepayResult> prepayResultConverter = new XMLConverter<PrepayResult>();
	
	Logger logger = Logger.getLogger(WxPayService.class);
	
	@Override
	public PrepayResult sendPrepay(UnifiedOrder order, boolean replaceSignature) {
		if(order != null){
			try {
				XMLConvertConfig config = new XMLConvertConfig();
				config.addRequiredIgnored("sign");
				//将请求参数转换为xml对象
				XmlNode xml = unifiedOrderConverter.doConvert(order, config);
				//判断计算覆盖签名
				replaceSignature(replaceSignature, xml, order);
				//请求并返回结果对象
				XmlNode returnXML = HttpRequestUtils.postXMLAndReturnXML(SystemConstants.WXPAY_UNIFIED_ORDER_URL, xml);
				//将结果对象转换成Java对象
				if(returnXML != null){
					logger.info("微信支付预支付接口返回数据：" + returnXML);
					logger.info("返回数据签名验证结果：" + configService.checkSignature(null, returnXML));
					return prepayResultConverter.parse(returnXML, new PrepayResult());
				}
			} catch (XMLException e) {
				logger.error(e);
			}
		}
		return null;
	}

	@Override
	public PrepayResult sendPrepay(PrepayParameter parameter) {
		UnifiedOrder order = prepayOrderFactory.createOrder(parameter);
		return this.sendPrepay(order, true);
	}
	
	/**
	 * 判断计算覆盖签名
	 * @param replaceSignature 只有在值为true的时候，会覆盖签名值
	 * @param xml 要覆盖签名值的对象
	 * @param order 
	 */
	private void replaceSignature(boolean replaceSignature, XmlNode xml, UnifiedOrder order) {
		if(replaceSignature){
			XmlNode singNode = xml.getFirstElement("sign");
			if(singNode != null){
				singNode.empty();
			}else{
				singNode = xml.addNode("sign");
			}
			LinkedHashMap<String, String> parameters = xml.toTagTextMap();
			String signature = configService.getMd5Signature(configService.getWxPayKey(), parameters);
			singNode.addCDATA(signature);
			order.setSign(signature);
		}
	}
	
}
