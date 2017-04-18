package cn.sowell.copframe.weixin.pay.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.sowell.copframe.SystemConstants;
import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.exception.XMLException;
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.pay.exception.WeiXinPayException;
import cn.sowell.copframe.weixin.pay.prepay.GoodsDetail;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.copframe.weixin.pay.prepay.JsApiPrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.OrderDetail;
import cn.sowell.copframe.weixin.pay.prepay.PrepayOrderFactory;
import cn.sowell.copframe.weixin.pay.prepay.PrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.PrepayResult;
import cn.sowell.copframe.weixin.pay.prepay.SignSetable;
import cn.sowell.copframe.weixin.pay.prepay.UnifiedOrder;
import cn.sowell.copframe.weixin.pay.refund.RefundRequest;
import cn.sowell.copframe.weixin.pay.refund.RefundResult;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.copframe.xml.XMLConvertConfig;
import cn.sowell.copframe.xml.XMLConverter;
import cn.sowell.copframe.xml.XmlNode;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderRefundParameter;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

@Service
public class WxPayServiceImpl implements WxPayService{

	@Resource
	WxConfigService configService;
	
	@Resource
	PrepayOrderFactory prepayOrderFactory;
	
	XMLConverter<UnifiedOrder> unifiedOrderConverter = new XMLConverter<UnifiedOrder>();
	XMLConverter<PrepayResult> prepayResultConverter = new XMLConverter<PrepayResult>();
	XMLConverter<RefundRequest> refundReqConverter = new XMLConverter<RefundRequest>();
	private XMLConverter<RefundResult> refundResultConverter = new XMLConverter<RefundResult>();
	
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
		//订单参数转换为微信统一下单对象
		UnifiedOrder order = prepayOrderFactory.createOrder(parameter);
		return this.sendPrepay(order, true);
	}
	
	/**
	 * 判断计算覆盖签名
	 * @param replaceSignature 只有在值为true的时候，会覆盖签名值
	 * @param xml 要覆盖签名值的对象
	 * @param signSetable 
	 */
	private void replaceSignature(boolean replaceSignature, XmlNode xml, SignSetable signSetable) {
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
			signSetable.setSign(signature);
		}
	}
	
	
	
	@Override
	public PrepayParameter buildPrepayParameter(Order order) throws WeiXinPayException {
		Assert.notNull(order);
		JsApiPrepayParameter parameter = new JsApiPrepayParameter();
		WeiXinUser user = order.getOrderUser();
		if(user != null){
			String openid = user.getOpenid();
			if(StringUtils.hasText(openid)){
				//设置购买的用户的openid
				parameter.setOpenId(openid);
				if(order.getTotalPrice() == null){
					throw new WeiXinPayException("构造微信支付的参数需要传入商品的总价");
				}
				//设置总价
				parameter.setTotalFee(order.getTotalPrice());
				//付款时的标题
				parameter.setBody("点点新意-一点点奶茶");
				//构造订单详情对象
				OrderDetail orderDetail = new OrderDetail();
				//遍历订单里的所有产品，并将所有产品转换为商品
				Set<Product> productSet = order.getProductSet();
				List<GoodsDetail> goodsDetails = new ArrayList<GoodsDetail>();
				for (Product product : productSet) {
					GoodsDetail gd = new GoodsDetail();
					gd.setGoodsId(FormatUtils.toString(product.getId()));
					gd.setGoodsName(product.getName());
					gd.setQuantity(1);
					gd.setPrice(product.getPrice());
					goodsDetails.add(gd);
				}
				orderDetail.setGoodsDetail(goodsDetails);
				parameter.setOrderDetail(orderDetail);
				return parameter;
			}else{
				throw new WeiXinPayException("订单对象中OrderUser的open为空");
			}
		}else{
			throw new WeiXinPayException("订单对象中的OrderUser为null");
		}
	}
	
	@Override
	public H5PayParameter buildPayParameter(String prepayId) {
		if(prepayId != null){
			H5PayParameter result = new H5PayParameter();
			result.setAppId(configService.getAppid());
			result.setTimeStamp(String.valueOf(configService.getCurrentTimestamp()));
			result.setNonceStr(TextUtils.uuid());
			result.setPackageStr("prepay_id=" + prepayId);
			String sign = configService.getMd5Signature(configService.getWxPayKey(), result.toMap());
			result.setPaySign(sign);
			return result;
		}
		return null;
	}
	
	
	@Override
	public RefundResult sendRefund(RefundRequest refundReq, boolean replaceSignature) {
		Assert.notNull(refundReq);
		try {
			XMLConvertConfig config = new XMLConvertConfig();
			config.addRequiredIgnored("sign");
			//将请求参数转换为xml对象
			XmlNode xml = refundReqConverter.doConvert(refundReq, config);
			//判断计算覆盖签名
			replaceSignature(replaceSignature, xml, refundReq);
			//请求并返回结果对象
			XmlNode returnXML = HttpRequestUtils.postXMLAndReturnXML(SystemConstants.WXPAY_REFUND_URL, xml);
			//将结果对象转换成Java对象
			if(returnXML != null){
				logger.info("微信退款接口返回数据：" + returnXML);
				logger.info("返回数据签名验证结果：" + configService.checkSignature(null, returnXML));
				return refundResultConverter.parse(returnXML, new RefundResult());
			}
		} catch (XMLException e) {
			logger.error(e);
		}
		return null;
	}
	
	@Override
	public RefundRequest buildRefundRequest(OrderRefundParameter refundParam, Order order) {
		if(refundParam != null){
			RefundRequest request = new RefundRequest();
			request.setAppid(configService.getAppid());
			request.setMerchantId(configService.getMerchantId());
			request.setNonceStr(TextUtils.uuid());
			request.setOutTradeNo(order.getOrderCode());
			UserIdentifier operateUser = refundParam.getOperateUser();
			if(operateUser != null){
				request.setOperateUserId(String.valueOf(operateUser.getId()));
			}
			request.setRefundFee(refundParam.getRefundFee());
			return request;
		}
		return null;
	}
	
	
}
