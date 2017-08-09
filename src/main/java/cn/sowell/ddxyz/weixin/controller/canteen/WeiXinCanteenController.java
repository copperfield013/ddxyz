package cn.sowell.ddxyz.weixin.controller.canteen;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenOrderUpdateItem;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenUserCacheInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderParameter;
import cn.sowell.ddxyz.model.canteen.service.CanteenService;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/canteen")
public class WeiXinCanteenController {
	
	@Resource
	CanteenService canteenService;
	
	
	Logger logger = Logger.getLogger(WeiXinCanteenController.class);
	
	@RequestMapping("/home")
	public String home(){
		
		return WeiXinConstants.PATH_CANTEEN + "/canteen_home.jsp";
	}
	
	@RequestMapping("/order")
	public String order(Model model){
		WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
		CanteenDelivery delivery = canteenService.getDeliveryOfThisWeek();
		CanteenUserCacheInfo userInfo = canteenService.getUserCacheInfo(user.getId());
		model.addAttribute("delivery", delivery);
		model.addAttribute("user", user);
		model.addAttribute("userInfo", userInfo);
		return WeiXinConstants.PATH_CANTEEN + "/canteen_order.jsp";
	}
	
	
	@ResponseBody
	@RequestMapping("/doOrder")
	public JsonResponse doOrder(@RequestBody JsonRequest jReq){
		JsonResponse jRes = new JsonResponse();
		CanteenOrderParameter parameter = CanteenOrderParameter.fromJson(jReq.getJsonObject());
		try {
			canteenService.createOrder(parameter);
			jRes.setStatus("suc");
		} catch (OrderResourceApplyException e) {
			jRes.setStatus("error");
			logger.error("创建订单时发生错误", e);
		}
		return jRes;
	}
	
	@RequestMapping("/order_list")
	public String orderList(){
		return WeiXinConstants.PATH_CANTEEN + "/canteen_order_list.jsp";
	}
	
	
	@RequestMapping("/update_order/{orderId}")
	public String updateOrder(@PathVariable Long orderId, Model model){
		//根据orderId获得订单对象
		PlainCanteenOrder cOrder = canteenService.getCanteenOrder(orderId);
		//根据orderId获得对应的配送信息对象
		CanteenDelivery delivery = canteenService.getCanteenDelivery(cOrder.getpOrder().getDeliveryId());
		//根据orderId获得其所有的商品列表
		List<CanteenOrderUpdateItem> orderItems = canteenService.getOrderItems(orderId);
		CanteenUserCacheInfo user = canteenService.getOrderUserInfo(orderId);
		model.addAttribute("delivery", delivery);
		model.addAttribute("order", cOrder.getpOrder());
		model.addAttribute("cOrder", cOrder);
		model.addAttribute("orderItemsJson", JSON.toJSONString(orderItems));
		model.addAttribute("orderItems", orderItems);
		model.addAttribute("user", user);
		
		return WeiXinConstants.PATH_CANTEEN + "/canteen_update_order.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doUpdateOrder")
	public JsonResponse doUpdateOrder(@RequestBody JsonRequest jReq){
		CanteenOrderParameter orderParameter = CanteenOrderParameter.fromJson(jReq.getJsonObject());
		JsonResponse jRes = new JsonResponse();
		try {
			canteenService.updateOrder(orderParameter);
			jRes.setStatus("suc");
		} catch (OrderResourceApplyException e) {
			jRes.setStatus("error");
			logger.error("修改订单时发生错误", e);
		}
		return jRes;
	}
	
	
}
