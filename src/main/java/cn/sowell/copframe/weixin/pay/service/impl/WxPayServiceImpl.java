package cn.sowell.copframe.weixin.pay.service.impl;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.sowell.copframe.SystemConstants;
import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.exception.XMLException;
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.pay.exception.WeiXinPayException;
import cn.sowell.copframe.weixin.pay.paied.WxPayStatus;
import cn.sowell.copframe.weixin.pay.paied.WxPayStatusParam;
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
import cn.sowell.copframe.xml.Dom4jNode;
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
	
	@Resource
	FrameDateFormat dateFormat;
	
	XMLConverter<UnifiedOrder> unifiedOrderConverter = new XMLConverter<UnifiedOrder>();
	XMLConverter<PrepayResult> prepayResultConverter = new XMLConverter<PrepayResult>();
	XMLConverter<RefundRequest> refundReqConverter = new XMLConverter<RefundRequest>();
	XMLConverter<RefundResult> refundResultConverter = new XMLConverter<RefundResult>();
	XMLConverter<WxPayStatusParam> wxPayStatusParamConverter = new XMLConverter<WxPayStatusParam>();
	XMLConverter<WxPayStatus> wxPayStatusConverter = new XMLConverter<WxPayStatus>();
	
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
					PrepayResult result = prepayResultConverter.parse(returnXML, new PrepayResult());
					result.setSubmitUOrder(order);
					return result;
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
				
				parameter.setExpireTime(order.getPayExpireTime());
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
			XmlNode returnXML = null;
			try {
				returnXML = doRefund(xml);
			} catch (Exception e) {
				logger.error("", e);
			}
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
			request.setOutTradeNo(order.getOutTradeNo());
			request.setOutRefundNo(generateOutRefundNo());
			request.setTotalFee(order.getTotalPrice());
			UserIdentifier operateUser = refundParam.getOperateUser();
			if(operateUser != null){
				request.setOperateUserId(String.valueOf(operateUser.getId()));
			}
			request.setRefundFee(refundParam.getRefundFee());
			return request;
		}
		return null;
	}

	private String generateOutRefundNo() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateFormat.format(new Date(), "yyMMddHHmmss"));
		buffer.append(TextUtils.randomStr(5, 10));
		return buffer.toString();
	}
	
	
	public XmlNode doRefund(XmlNode xmlNode) throws Exception {
		/**
		 * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
		 */

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		ClassPathResource resFile = new ClassPathResource(configService.getMerchantPKCS12FilePath());
		InputStream instream = resFile.getInputStream();
		String merchantId = configService.getMerchantId();
		try {
			keyStore.load(instream, merchantId.toCharArray());// 这里写密码..默认是你的MCHID
		} finally {
			instream.close();
		}

		
		SSLContext sslcontext = 
				SSLContexts.custom()
					.loadKeyMaterial(keyStore, merchantId.toCharArray())// 这里也是写密码的
					.build();
		@SuppressWarnings("deprecation")
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(SystemConstants.WXPAY_REFUND_URL); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(xmlNode.asXML(), "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();

				String jsonStr = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				EntityUtils.consume(entity);
				System.out.println(jsonStr);
				return new Dom4jNode(jsonStr);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
	
	@Override
	public WxPayStatus checkPayStatus(String outTradeNo){
		Assert.hasText(outTradeNo);
		WxPayStatus status = new WxPayStatus();
		XmlNode paramXML;
		try {
			paramXML = buildCheckPayStatusParam(outTradeNo);
		} catch (XMLException e) {
			logger.error("微信支付订单查询参数构造时出现异常", e);
			return null;
		}
		XmlNode returnXML = HttpRequestUtils.postXMLAndReturnXML(SystemConstants.WXPAY_ORDER_QUERY, paramXML);
		if(returnXML != null){
			return wxPayStatusConverter.parse(returnXML, status);
		}else{
			logger.error("微信支付订单查询返回数据有误");
			return null;
		}
	}

	private XmlNode buildCheckPayStatusParam(String outTradeNo) throws XMLException {
		WxPayStatusParam param = new WxPayStatusParam();
		param.setAppid(configService.getAppid());
		param.setMerchantId(configService.getMerchantId());
		param.setOutTradeNo(outTradeNo);
		param.setNonceStr(TextUtils.uuid());
		XMLConvertConfig config = new XMLConvertConfig();
		config.addRequiredIgnored("sign");
		//将请求参数转换为xml对象
		XmlNode xml = wxPayStatusParamConverter.doConvert(param, config);
		//判断计算覆盖签名
		replaceSignature(true, xml, param);
		return xml;
	}

	
}
