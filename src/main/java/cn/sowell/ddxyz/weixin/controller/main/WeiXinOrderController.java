package cn.sowell.ddxyz.weixin.controller.main;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.xml.XMLRequest;
import cn.sowell.copframe.dto.xml.XMLResponse;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.copframe.weixin.pay.paied.WxPayStatus;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.copframe.xml.XMLConverter;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderRefundParameter;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.model.weixin.service.WeiXinUserService;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.PATH_BASE + "/order")
public class WeiXinOrderController {
	
	@Resource
	OrderService oService;
	
	@Resource
	WxPayService payService;
	
	@Resource
	WxConfigService configService;
	
	@Resource
	WeiXinUserService userService;
	
	Logger logger = Logger.getLogger(WeiXinOrderController.class);
	
	@ResponseBody
	@RequestMapping("/getPrepayCode")
	public JsonResponse getPrepayCode(Long orderId){
		JsonResponse response = new JsonResponse();
		Order order = oService.getOrder(orderId);
		if(order != null && order.getPrepayId() != null){
			H5PayParameter payParam = payService.buildPayParameter(order.getPrepayId());
			response.put("payParam", payParam);
		}
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping("/complete")
	public JsonResponse complete(Long orderId){
		JsonResponse response = new JsonResponse();
		Order order = oService.getOrder(orderId);
		if(order != null){
			try {
				UserIdentifier user = WxUtils.getCurrentUser(UserIdentifier.class);
				oService.completeOrder(order, user);
				response.put("status", "suc");
			} catch (OrderException e) {
				logger.error("订单完成时发生错误", e);
			}
		}
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping("/checkRefund")
	public JsonResponse checkRefund(Long orderId){
		JsonResponse jRes = new JsonResponse();
		Order order = oService.getOrder(orderId);
		if(order != null){
			OrderRefundParameter refundParam = new OrderRefundParameter();
			UserIdentifier operateUser = WxUtils.getCurrentUser(UserIdentifier.class);
			refundParam.setOperateUser(operateUser);
			refundParam.setRefundFee(order.getTotalPrice());
			CheckResult cResult = oService.checkOrderRefund(order, refundParam);
			if(cResult.isSuc()){
				jRes.put("canRefund", true);
			}else{
				jRes.put("errorReason", cResult.getReason());
			}
		}
		return jRes;
	}
	
	@ResponseBody
	@RequestMapping("/applyRefund")
	public JsonResponse doOrderRefund(Long orderId){
		JsonResponse jRes = new JsonResponse();
		Order order = oService.getOrder(orderId);
		if(order != null){
			OrderRefundParameter refundParam = new OrderRefundParameter();
			UserIdentifier operateUser = WxUtils.getCurrentUser(UserIdentifier.class);
			refundParam.setOperateUser(operateUser);
			refundParam.setRefundFee(order.getTotalPrice());
			try {
				oService.refundOrder(order, refundParam);
				jRes.setStatus("suc");
			} catch (OrderException e) {
				logger.error("", e);
				jRes.setStatus("error");
			}
		}
		
		return jRes;
	}
	//用于微信支付状态通知的请求转换 
	XMLConverter<WxPayStatus> converter = new XMLConverter<WxPayStatus>();
	/**
	 * 微信支付状态通知
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/notify")
	public XMLResponse paiedNotify(@RequestBody XMLRequest xReq){
		logger.info("接收微信支付状态通知请求");
		logger.info(xReq);
		
		//构造响应对象
		XMLResponse xRes = new XMLResponse();
		
		//检查请求的签名
		if(!configService.checkSignature(xReq.getElementValue("sign"), xReq.getRoot())){
			logger.error("签名不正确");
		}else{
			//将请求转换为对象
			WxPayStatus source = new WxPayStatus();
			WxPayStatus payStatus = converter.parse(xReq.getRoot(), source);
			//从请求中获得数据
			String payUserOpenid = payStatus.getOpenid(),
					outTradeNo = payStatus.getOutTradeNo();
			
			
			if(StringUtils.hasText(payUserOpenid) && StringUtils.hasText(outTradeNo)){
				Order order = oService.getOrderByOutTradeNo(outTradeNo);
				if(order != null){
					if(order.getCancelStatus() != null){
						xRes.putElement("return_code", "FAIL");
						xRes.putElement("return_msg", "当前订单已取消");
					}else if(order.getOrderStatus() >= Order.STATUS_PAYED){
						xRes.putElement("return_code", "FAIL");
						xRes.putElement("return_msg", "当前订单已经支付过");
					}else{
						WeiXinUser user = userService.getWeiXinUserByOpenid(payUserOpenid);
						if(user != null){
							try {
								oService.payOrder((Long) order.getKey(), user);
								xRes.putElement("return_code", "SUCCESS");
								xRes.putElement("return_msg", "OK");
							} catch (Exception e) {
								logger.error("更改订单状态为已支付时发生异常", e);
							}
						}else{
							logger.error("系统中没有找到openid[" + payUserOpenid + "]对应的微信用户");
						}
					}
					
				}else{
					logger.error("系统中没有找到outTradeNo[" + outTradeNo + "]对应的订单");
				}
			}else{
				logger.error("没有传入订单的outTrade或者支付者的openid[outTradeNo=" + outTradeNo + ", openid=" + payUserOpenid + "]");
			}
		}
		logger.info("微信状态通知请求，本地响应为");
		logger.info(xRes);
		return xRes;
	}
	
	
	
}
