package cn.sowell.ddxyz.weixin.controller.main;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderRefundParameter;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.PATH_BASE + "/order")
public class WeiXinOrderController {
	
	@Resource
	OrderService oService;
	
	@Resource
	WxPayService payService;
	
	
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
	/**
	 * 微信支付状态返回
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/notify")
	public String paiedNotify(){
		return null;
	}
	
	
	
}
