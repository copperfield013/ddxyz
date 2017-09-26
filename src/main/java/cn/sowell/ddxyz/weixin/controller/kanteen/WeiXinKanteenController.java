package cn.sowell.ddxyz.weixin.controller.kanteen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/kanteen")
public class WeiXinKanteenController {
	
	@RequestMapping({"", "/"})
	public String main(){
		return WeiXinConstants.PATH_KANTEEN + "/kanteen_index.jsp";
	}
	
	@RequestMapping("/order")
	public String order(){
		return WeiXinConstants.PATH_KANTEEN + "/kanteen_order.jsp";
	}
	
	@RequestMapping("/order_list")
	public String orderList(){
		return WeiXinConstants.PATH_KANTEEN + "/kanteen_order_list.jsp";
	}
	
	
	
}
