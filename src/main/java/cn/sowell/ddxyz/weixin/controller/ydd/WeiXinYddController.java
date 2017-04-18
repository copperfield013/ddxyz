package cn.sowell.ddxyz.weixin.controller.ydd;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkTeaAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkType;
import cn.sowell.ddxyz.model.drink.service.DrinkService;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/ydd")
public class WeiXinYddController {
	@Resource
	DeliveryService dService;
	
	@Resource
	DrinkService drinkService;
	
	@RequestMapping({"", "/"})
	public String index(){
		return WeiXinConstants.PATH_YDD + "/ydd_home.jsp";
	}
	
	@RequestMapping("/order")
	public String order(Model model){
		WxUserPrincipal user = WxUtils.getCurrentUser();
		long waresId = 1l;
		//获得当天的所有配送
		Map<DeliveryTimePoint, List<PlainDelivery>> deliveryMap = dService.getTodayDeliveries(waresId);
		//获得所有饮料种类
		List<PlainDrinkType> drinkTypes = drinkService.getAllDrinkTypes(waresId);
		//获得所有饮料的可用加茶
		Map<Long, List<PlainDrinkTeaAdditionType>> teaAdditionMap = drinkService.getTeaAdditionMap(waresId);
		//获得饮料的所有可用加料
		Map<Long, List<PlainDrinkAdditionType>> additionMap = drinkService.getAdditionMap(waresId);
		
		model.addAttribute("deliveryMap", deliveryMap);
		model.addAttribute("drinkTypes", drinkTypes);
		model.addAttribute("teaAdditionMap", teaAdditionMap);
		model.addAttribute("additionMap", additionMap);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		
		return WeiXinConstants.PATH_YDD + "/ydd_order.jsp";
	}
	
	@RequestMapping("/orderList")
	public String orderList(){
		return WeiXinConstants.PATH_YDD + "/ydd_order_list.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/getDeliveryRemain")
	public JsonResponse getDeliveryRemain(Long deliveryId){
		JsonResponse jRes = new JsonResponse();
		Integer remain = dService.getDeliveryRemain(deliveryId);
		if(remain == null){
			jRes.put("error", "no_found");
		}else if(remain == Integer.MAX_VALUE){
			jRes.put("remain", "unlimited");
		}else{
			jRes.put("remain", remain);
		}
		return jRes;
	}
	
}
