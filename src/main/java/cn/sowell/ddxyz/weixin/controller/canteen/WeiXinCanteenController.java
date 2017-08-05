package cn.sowell.ddxyz.weixin.controller.canteen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/canteen")
public class WeiXinCanteenController {
	
	@RequestMapping("/home")
	public String home(){
		
		return WeiXinConstants.PATH_CANTEEN + "/canteen_home.jsp";
	}
	
	@RequestMapping("/order")
	public String order(){
		return WeiXinConstants.PATH_CANTEEN + "/canteen_order.jsp";
	}
	
	@RequestMapping("/order_list")
	public String orderList(){
		return WeiXinConstants.PATH_CANTEEN + "/canteen_order_list.jsp";
	}
}
