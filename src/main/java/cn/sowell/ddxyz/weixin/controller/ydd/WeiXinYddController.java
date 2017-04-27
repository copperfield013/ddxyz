package cn.sowell.ddxyz.weixin.controller.ydd;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderOperateResult;
import cn.sowell.ddxyz.model.common.core.OrderToken;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderReceiver;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkOrder;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkTeaAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkType;
import cn.sowell.ddxyz.model.drink.service.DrinkOrderService;
import cn.sowell.ddxyz.model.drink.service.DrinkService;
import cn.sowell.ddxyz.model.drink.term.OrderTerm;
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
	DrinkOrderService drinkOrderService;
	
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
	public String index(Model model){
		long waresId = 1l;
		long merchantId = 1l;
		List<DeliveryTimePoint> timePointItems = dService.getTodayDeliveryTimePoints(waresId);
		List<PlainDrinkType> drinkTypes = drinkService.getAllDrinkTypes(waresId);
		List<PlainLocation> deliveryLocations = dService.getAllDeliveryLocation(merchantId);
		model.addAttribute("timePointItems", timePointItems);
		model.addAttribute("drinkTypes", drinkTypes);
		model.addAttribute("deliveryLocations", deliveryLocations);
		return WeiXinConstants.PATH_YDD + "/ydd_home.jsp";
	}
	
	@RequestMapping("/order")
	public String order(Integer deliveryHour, Model model){
		UserIdentifier user = WxUtils.getCurrentUser(UserIdentifier.class);
		long waresId = 1l;
		//获得当天的所有配送
		Map<DeliveryTimePoint, List<PlainDelivery>> deliveryMap = dService.getTodayDeliveries(waresId);
		//获得所有饮料种类
		List<PlainDrinkType> drinkTypes = drinkService.getAllDrinkTypes(waresId);
		//获得所有饮料的可用加茶
		Map<Long, List<PlainDrinkTeaAdditionType>> teaAdditionMap = drinkService.getTeaAdditionMap(waresId);
		//获得饮料的所有可用加料
		Map<Long, List<PlainDrinkAdditionType>> additionMap = drinkService.getAdditionMap(waresId);
		PlainOrderReceiver receiverInfo = oService.getLastReceiverInfo(user.getId());
		model.addAttribute("deliveryMap", deliveryMap);
		model.addAttribute("drinkTypes", drinkTypes);
		model.addAttribute("teaAdditionMap", teaAdditionMap);
		model.addAttribute("additionMap", additionMap);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("deliveryHour", deliveryHour);
		model.addAttribute("receiverInfo", receiverInfo);
		
		return WeiXinConstants.PATH_YDD + "/ydd_order.jsp";
	}
	
	@RequestMapping("/orderList")
	public String orderList(Model model){
		return WeiXinConstants.PATH_YDD + "/ydd_order_list.jsp";
	}
	
	@RequestMapping("/orderData")
	public String load(CommonPageInfo pageInfo, Model model){
		UserIdentifier user =  WxUtils.getCurrentUser(UserIdentifier.class);
		List<PlainDrinkOrder> drinkList = drinkOrderService.getDrinkPageList(user, pageInfo);
		Map<Long, Boolean> refundableMap = drinkOrderService.getRefundableMap(CollectionUtils.toList(drinkList, (order) -> order.getId()));
		model.addAttribute("refundableMap", refundableMap);
		model.addAttribute("orderDrinkList", drinkList);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		return WeiXinConstants.PATH_YDD + "/ydd_order_data.jsp";
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
		long waresId = 1l;
		try {
			OrderTerm term = OrderTerm.fromJson(dManager, waresId, jReq.getJsonObject());
			WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
			Order order = oService.applyForOrder(term.createOrderParameter(), user, OrderToken.getAnonymousToken());
			if(order != null){
				//订单创建成功
				//构造用于前台调用微信支付窗口的参数
				H5PayParameter payParam = payService.buildPayParameter(order.getPrepayId());
				jRes.put("payParam", payParam);
				jRes.put("orderId", order.getKey());
			}
		} catch (OrderException e) {
			logger.error("创建支付订单时失败", e);
		}
		return jRes;
	}
	
	
	@ResponseBody
	@RequestMapping("/order-paied")
	public JsonResponse orderPaid(Long orderId){
		JsonResponse jRes = new JsonResponse();
		try {
			WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
			oService.payOrder(orderId, user);
			jRes.put("status", "suc");
		} catch (Exception e) {
			logger.error("订单修改支付状态失败[orderId=" + orderId + "]");
			logger.error("更改订单状态为已支付时发生异常", e);
			jRes.put("status", "error");
		}
		return jRes;
	}
	
	@ResponseBody
	@RequestMapping("operateOrder")
	public JsonResponse operateOrder(Long orderId, String operateType){
		JsonResponse jRes = new JsonResponse();
		UserIdentifier operateUser = WxUtils.getCurrentUser(UserIdentifier.class);
		try {
			OrderOperateResult result = oService.operateOrder(orderId, operateType, operateUser);
			jRes.put("status", result.getStatus());
		} catch (Exception e) {
			logger.error("订单操作时出现异常", e);
		}
		return jRes;
	}
	
	
	
}
