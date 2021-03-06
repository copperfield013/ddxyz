package cn.sowell.ddxyz.weixin.controller.ydd;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.Order;
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
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkOrderService;
import cn.sowell.ddxyz.model.drink.service.DrinkService;
import cn.sowell.ddxyz.model.drink.service.OrderItemService;
import cn.sowell.ddxyz.model.drink.term.OrderTerm;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;
import cn.sowell.ddxyz.model.merchant.service.MerchantDisabledRuleService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

import com.alibaba.fastjson.JSONObject;

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
	
	@Resource
	MerchantDisabledRuleService ruleService;
	
	Logger logger = Logger.getLogger(WeiXinYddController.class);

	@Resource
	OrderItemService oiService;
	
	@RequestMapping({"", "/"})
	public String index(Model model, HttpServletRequest request){
		System.out.println(HttpRequestUtils.getIpAddress(request));
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
	public String order(Integer deliveryHour, Long deliveryId, Long orderId, Model model){
		UserIdentifier user = WxUtils.getCurrentUser(UserIdentifier.class);
		long waresId = 1l;
		//获得当天的所有配送
		Map<DeliveryTimePoint, List<PlainDelivery>> deliveryMap = dService.getTodayDeliveries(waresId, true);
		//获得所有饮料种类
		List<PlainDrinkType> drinkTypes = drinkService.getAllDrinkTypes(waresId);
		//获得所有饮料的可用加茶
		Map<Long, List<PlainDrinkTeaAdditionType>> teaAdditionMap = drinkService.getTeaAdditionMap(waresId);
		//获得饮料的所有可用加料
		Map<Long, List<PlainDrinkAdditionType>> additionMap = drinkService.getAdditionMap(waresId);
		PlainOrderReceiver receiverInfo = oService.getLastReceiverInfo(user.getId());
		//如果传入订单主键，那么找到这个订单
		
		//判断当前时间是否可以下单
		boolean  isDisabled = ruleService.getIsInRules(DdxyzConstants.MERCHANT_ID);
		
		model.addAttribute("isDebug", configService.isDebug());
		model.addAttribute("deliveryMap", deliveryMap);
		model.addAttribute("drinkTypes", drinkTypes);
		model.addAttribute("teaAdditionMap", teaAdditionMap);
		model.addAttribute("additionMap", additionMap);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("deliveryHour", deliveryHour);
		model.addAttribute("receiverInfo", receiverInfo);
		
		model.addAttribute("deliveryId", deliveryId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("isDisabled", isDisabled);
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
		model.addAttribute("currentTime", new Date());
		return WeiXinConstants.PATH_YDD + "/ydd_order_data.jsp";
	}
	
	@RequestMapping("/loadOrderItem")
	public String loadOrderItem(Long orderId, Model model){
		PlainDrinkOrder dOrder = drinkOrderService.getOrderItem(orderId);
		Map<Long, Boolean> refundableMap = drinkOrderService.getRefundableMap(Arrays.asList(orderId));
		model.addAttribute("refundableMap", refundableMap);
		model.addAttribute("orderDrinkList", Arrays.asList(dOrder));
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
	@RequestMapping("/canOrder")
	public JsonResponse canOrder(){
		JsonResponse jRes = new JsonResponse();
		boolean  isDisabled = ruleService.getIsInRules(DdxyzConstants.MERCHANT_ID);
		if(isDisabled){
			jRes.put("status", false);
		}else{
			jRes.put("status", true);
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
			Order order = oService.applyForOrder(term.createOrderParameter(oiService), user, OrderToken.getAnonymousToken());
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
		Order order = oService.getOrder(orderId);
		
		if(order.getCancelStatus() != null){
			jRes.put("status", "fail");
			jRes.put("msg", "订单已取消");
		}else if(order.getOrderStatus() >= Order.STATUS_PAYED){
			jRes.put("status", "suc");
			jRes.put("paied", true);
			jRes.put("msg", "订单已支付，当前状态为[" + DdxyzConstants.ORDER_STATUS_CNAME.get(order.getOrderStatus()) + "]");
		}else{
			try {
				WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
				oService.payOrder(orderId, user);
				jRes.put("status", "suc");
			} catch (Exception e) {
				logger.error("订单修改支付状态失败[orderId=" + orderId + "]");
				logger.error("更改订单状态为已支付时发生异常", e);
				jRes.put("status", "error");
			}
		}
		return jRes;
	}
	
	@RequestMapping("/deliverySelection")
	public String deliverySelection(long orderId, Model model){
		Map<DeliveryTimePoint, List<PlainDelivery>> deliveryMap = dService.getUsableDeliveryMap(orderId);
		model.addAttribute("deliveryMap", deliveryMap);
		return WeiXinConstants.PATH_YDD + "/delivery_selection.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/loadOrderForInit")
	public JsonResponse loadOrderForInit(long deliveryId, long orderId, Model model){
		JsonResponse res = new JsonResponse();
		UserIdentifier user =  WxUtils.getCurrentUser(UserIdentifier.class);
		Delivery delivery = dManager.getDelivery(deliveryId);
		if(delivery != null){
			List<PlainOrderDrinkItem> dOrderList = drinkOrderService.getOrderDrinkItemList(orderId);
			Order order = oService.getOrder(orderId);
			UserIdentifier orderUser = oService.getOrderUser(order);
			if(user.getId().equals(orderUser.getId())){
				JSONObject initOrder = drinkOrderService.converteInitOrder(delivery, dOrderList);
				res.put("initOrder", initOrder);
			}
		}
		return res;
	}
	
}
