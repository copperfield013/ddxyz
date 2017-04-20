package cn.sowell.ddxyz.weixin.controller.ydd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderToken;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkTeaAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkType;
import cn.sowell.ddxyz.model.drink.pojo.PlainOrderDrink;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkService;
import cn.sowell.ddxyz.model.drink.service.OrderDrinkService;
import cn.sowell.ddxyz.model.drink.service.PlainDrinkAdditionService;
import cn.sowell.ddxyz.model.drink.service.PlainOrderService;
import cn.sowell.ddxyz.model.drink.term.OrderTerm;
import cn.sowell.ddxyz.model.main.AdminMainConstants;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/ydd")
public class WeiXinYddController {
	@Resource
	DeliveryService dService;
	
	@Resource
	DrinkService drinkService;
	
	@Resource
	PlainOrderService plainOrderService;
	
	@Resource
	OrderDrinkService orderDrinkService;
	
	@Resource
	PlainDrinkAdditionService drinkAdditionService;
	
	@Resource
	OrderService oService;

	@Resource
	DeliveryManager dManager;
	
	@Resource
	WxConfigService configService;
	
	@Resource
	WxPayService payService;
	
	Logger logger = Logger.getLogger(WeiXinYddController.class);
	
	@RequestMapping({"", "/"})
	public String index(){
		return WeiXinConstants.PATH_YDD + "/ydd_home.jsp";
	}
	
	@RequestMapping("/order")
	public String order(Model model){
		WxUserPrincipal user = WxUtils.getCurrentUser(WxUserPrincipal.class);
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
	public String orderList(Model model){
		UserIdentifier user =  WxUtils.getCurrentUser(UserIdentifier.class);
		List<PlainOrder> orderList = plainOrderService.getOrderList((Long)user.getId());
		List<PlainOrderDrink> orderDrinkList = new ArrayList<PlainOrderDrink>();
		if(orderList != null && orderList.size() > 0){
			for(PlainOrder plainOrder : orderList){
				List<PlainOrderDrinkItem> orderDrinkItemList = orderDrinkService.getOrderDrinkItemList(plainOrder.getId());
				PlainOrderDrink plainOrderDrink = new PlainOrderDrink();
				plainOrderDrink.setOrderCode(plainOrder.getOrderCode());
				plainOrderDrink.setCanceledStatus(plainOrder.getCanceledStatus());
				plainOrderDrink.setOrderStatus(plainOrder.getOrderStatus());
				plainOrderDrink.setTotalPrice(plainOrder.getTotalPrice());
				plainOrderDrink.setOrderTime(plainOrder.getUpdateTime());
				plainOrderDrink.setOrderDrinkItems(orderDrinkItemList);
				plainOrderDrink.setCupCount(0);
				if(orderDrinkItemList != null && orderDrinkItemList.size() > 0){
					for(PlainOrderDrinkItem item : orderDrinkItemList){
						List<PlainDrinkAddition> additions = drinkAdditionService.getDrinkAdditionList(item.getDrinkProductId());
						item.setAdditions(additions);
					}
					plainOrderDrink.setCupCount(orderDrinkItemList.size());
				}
				orderDrinkList.add(plainOrderDrink);
			}
		}
		model.addAttribute("orderDrinkList", orderDrinkList);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
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
	
	@ResponseBody
	@RequestMapping("/submitOrder")
	public JsonResponse submitOrder(@RequestBody JsonRequest jReq){
		JsonResponse jRes = new JsonResponse();
		try {
			OrderTerm term = OrderTerm.fromJson(dManager, jReq.getJsonObject());
			WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
			Order order = oService.applyForOrder(term.createOrderParameter(), user, OrderToken.getAnonymousToken());
			if(order != null){
				//订单创建成功
				//构造用于前台调用微信支付窗口的参数
				H5PayParameter payParam = payService.buildPayParameter(order.getPrepayId());
				jRes.put("payParam", payParam);
			}
		} catch (OrderException e) {
			logger.error("创建支付订单时失败", e);
		}
		return jRes;
	}
	
	
	@RequestMapping("/pay-result")
	public String payResult(Long orderId, String status, Model model){
		model.addAttribute("orderId", orderId);
		model.addAttribute("status", status);
		return WeiXinConstants.PATH_YDD + "/ydd_pay_result.jsp";
	}
	
	
	
}
