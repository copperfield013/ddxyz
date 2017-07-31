package cn.sowell.ddxyz.weixin.controller.ydd;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.ddxyz.model.drink.service.ProductService;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/delivery")
public class WeiXinDeliveryController {
	
	@Resource
	ProductService productService;

	@RequestMapping("/main")
	public String main(){
		return WeiXinConstants.PATH_DELIVERY + "/ydd_delivery_main.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxResolveQrCode")
	public JsonResponse ajaxResolveQrCode(String code){
		JsonResponse jRes = new JsonResponse();
		jRes.put("origin", productService.getProductByQrCode(code));
		return jRes;
	}
	
	
}
