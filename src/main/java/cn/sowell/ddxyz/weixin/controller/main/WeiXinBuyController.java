package cn.sowell.ddxyz.weixin.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;

@Controller
@RequestMapping("/weixinBuy")
public class WeiXinBuyController {
	
	@RequestMapping("/index")
	public String index(){
		return "/weixin/buy_index.jsp";
	}
	
	@RequestMapping("/toBuy")
	public String toBuy(){
		return "/weixin/buy.jsp";
	}
	
	@RequestMapping("/order")
	public String viewMyOrder(){
		return "/weixin/order.jsp";
	}
	
	@ResponseBody
	@RequestMapping(value="/testJson")
	public Object testJson(@RequestBody JsonRequest jReq, JsonResponse jRes){
		System.out.println(jReq);
		return jRes.setJsonObject(jReq.getJsonObject());
	}

}
