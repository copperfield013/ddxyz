package cn.sowell.ddxyz.weixin.controller.canteen;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenUserCacheInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderParameter;
import cn.sowell.ddxyz.model.canteen.service.CanteenService;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

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
			PlainCanteenOrder order = canteenService.createOrder(parameter);
			System.out.println(order);
		} catch (OrderResourceApplyException e) {
			logger.error("创建订单时发生错误", e);
		}
		return jRes;
	}
	
	@RequestMapping("/order_list")
	public String orderList(){
		return WeiXinConstants.PATH_CANTEEN + "/canteen_order_list.jsp";
	}
}
