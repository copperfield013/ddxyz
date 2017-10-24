package cn.sowell.ddxyz.weixin.controller.kanteen;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.dto.xml.XMLRequest;
import cn.sowell.copframe.dto.xml.XMLResponse;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.xml.XMLConverter;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.copframe.weixin.pay.paied.WxPayStatus;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.KanteenConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenOrderCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenReceiver;
import cn.sowell.ddxyz.model.kanteen.service.KanteenService;
import cn.sowell.ddxyz.model.kanteen.service.impl.OrderPayException;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.model.weixin.service.WeiXinUserService;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/kanteen")
public class WeiXinKanteenController {
	

	@Resource
	KanteenService kanteenService;
	
	@Resource
	WxPayService payService;
	
	@Resource
	WxConfigService configService;
	
	@Resource
	WeiXinUserService userService;
	
	
	Logger logger = Logger.getLogger(WeiXinKanteenController.class);
	
	@RequestMapping({"", "/"})
	public String main(Long merchantId, Model model){
		if(merchantId == null){
			merchantId = FormatUtils.toLong(PropertyPlaceholder.getProperty("wx_default_merchant_id"));
		}
		PlainKanteenMerchant merchant = kanteenService.getMerchant(merchantId);
		if(merchant != null){
			WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
			//获得本周的配销
			PlainKanteenDistribution distribution = kanteenService.getDistributionOfThisWeek(merchantId, new Date());
			if(distribution != null){
				//获得配销的所有配送信息
				List<PlainKanteenDelivery> deliveries = kanteenService.getEnabledDeliveries(distribution.getId());
				//如果配销存在，那么就拿到配销对应的菜单信息
				KanteenMenu menu = kanteenService.getKanteenMenu(distribution.getId());
				//获得配销对应的购物车，如果不存在，则创建一个
				KanteenTrolley trolley = kanteenService.getTrolley(user.getId(), distribution.getId());
				
				model.addAttribute("distribution", distribution);
				model.addAttribute("deliveries", deliveries);
				model.addAttribute("menu", menu);
				model.addAttribute("trolley", trolley);
				model.addAttribute("validWares", JSONObject.toJSON(trolley.getValidWares()));
			}
			model.addAttribute("merchant", merchant);
		}
		return WeiXinConstants.PATH_KANTEEN + "/kanteen_index.jsp";
	}
	
	@RequestMapping("/order/{distributionId}")
	public String order(@PathVariable Long distributionId, Model model){
		WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
		KanteenTrolley trolley = kanteenService.getTrolley(user.getId(), distributionId);
		//获得当前用户最近的一次领取人信息
		PlainKanteenReceiver receiver = kanteenService.getLastReceiver(user.getId());
		
		//获得配销的所有配送信息
		List<PlainKanteenDelivery> deliveries = kanteenService.getEnabledDeliveries(distributionId);
		
		model.addAttribute("trolley", trolley);
		model.addAttribute("receiver", receiver);
		model.addAttribute("deliveries", deliveries);
		model.addAttribute("distributionId", distributionId);
		model.addAttribute("deliveriesJson", JSON.toJSON(deliveries));
		
		return WeiXinConstants.PATH_KANTEEN + "/kanteen_order.jsp";
	}
	
	@RequestMapping("/order_list")
	public String orderList(Model model){
		WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
		KanteenOrderCriteria criteria = new KanteenOrderCriteria();
		criteria.setUserId(user.getId());
		List<KanteenOrder> orderList = kanteenService.queryOrder(criteria , null);
		Map<Long, PlainKanteenDelivery> deliveryMap = kanteenService.getDeliveryMap(new HashSet<Long>(CollectionUtils.toList(orderList, order->order.getPlainOrder().getDeliveryId())));
		model.addAttribute("orderList", orderList);
		model.addAttribute("orderStatusMap", KanteenConstants.ORDER_STATUS_MAP);
		model.addAttribute("deliveryMap", deliveryMap);
		return WeiXinConstants.PATH_KANTEEN + "/kanteen_order_list.jsp";
	}
	
	
	@ResponseBody
	@RequestMapping("/commit_trolley")
	public JsonResponse commitTrolley(@RequestBody JsonRequest jReq){
		JsonResponse jRes = new JsonResponse();
		JSONObject json = jReq.getJsonObject();
		Long distributionId = json.getLong("distributionId");
		WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
		KanteenTrolley trolley = kanteenService.getTrolley(user.getId(), distributionId);
		if(trolley != null){
			Map<Long, Integer> trolleyWares = kanteenService.extractTrolley(json.getJSONObject("trolleyData"));
			try {
				//调用方法更新购物车内的数据
				kanteenService.mergeTrolleyWares(trolley.getId(), trolleyWares);
				jRes.put("trolleyId", trolley.getId());
				jRes.setStatus("suc");
			} catch (Exception e) {
				logger.error("更新购物车时发生错误", e);
				jRes.setStatus("error");
			}
			return jRes;
		}else{
			jRes.setStatus("error");
			logger.error("无法获得或创建购物车对象");
		}
		return jRes;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/confirm_order/{distributionId}")
	public JsonResponse confirmOrder(@PathVariable Long distributionId, @RequestBody JsonRequest jReq){
		JsonResponse jRes = new JsonResponse();
		WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
		//拉取用户在该商家的购物车数据
		KanteenTrolley trolley = kanteenService.getTrolley(user.getId(), distributionId);
		if(trolley != null){
			KanteenOrder order = kanteenService.extractOrder(jReq.getJsonObject());
			order.setOrderUser(user);
			try {
				//将购物车内的商品包装成订单
				kanteenService.packOrder(order, trolley);
				//创建并保存订单
				H5PayParameter payParameter = kanteenService.saveOrder(order);
				jRes.put("orderId", order.getOrderId());
				jRes.put("payParameter", JSON.toJSON(payParameter));
				jRes.setStatusSuccees();
			} catch (Exception e) {
				logger.error("创建订单失败", e);
				jRes.setStatus("error");
			}
		}
		return jRes;
	}
	
	@ResponseBody
	@RequestMapping("/order_paied")
	public JsonResponse orderPaied(@RequestParam Long orderId){
		JsonResponse jRes = new JsonResponse();
		
		PlainKanteenOrder order = kanteenService.getOrder(orderId);
		if(order != null){
			try {
				WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
				kanteenService.payOrder(order, user);
				jRes.setStatusSuccees();
			}catch(OrderPayException e){
				if(!e.isPaied()){
					logger.error(e.getMessage(), e);
					jRes.setStatus("error");
					jRes.getJsonObject().put("msg", e.getMessage());
				}else{
					jRes.setStatusSuccees();
				}
			} catch (Exception e) {
				logger.error("订单修改支付状态失败[orderId=" + orderId + "]");
				logger.error("更改订单状态为已支付时发生异常", e);
				jRes.setStatus("error");
			}
		}else{
			jRes.setStatus("error");
			jRes.getJsonObject().put("msg", "没有找到订单");
		}
		return jRes;
	}
	
	
	//用于微信支付状态通知的请求转换 
	XMLConverter<WxPayStatus> converter = new XMLConverter<WxPayStatus>();
	
	/**
	 * 微信支付状态通知
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/notify")
	public XMLResponse paiedNotify(@RequestBody XMLRequest xReq){
		logger.info("接收微信支付状态通知请求");
		logger.info(xReq);
		
		//构造响应对象
		XMLResponse xRes = new XMLResponse();
		
		//检查请求的签名
		if(!configService.checkSignature(xReq.getElementValue("sign"), xReq.getRoot())){
			logger.error("签名不正确");
		}else{
			//将请求转换为对象
			WxPayStatus source = new WxPayStatus();
			WxPayStatus payStatus = converter.parse(xReq.getRoot(), source);
			//从请求中获得数据
			String payUserOpenid = payStatus.getOpenid(),
					outTradeNo = payStatus.getOutTradeNo();
			
			
			if(StringUtils.hasText(payUserOpenid) && StringUtils.hasText(outTradeNo)){
				PlainKanteenOrder order = kanteenService.getOrderByOutTradeNo(outTradeNo);
				
				
				//Order order = oService.getOrderByOutTradeNo(outTradeNo);
				if(order != null){
					if(order.getCanceledStatus() != null){
						xRes.putElement("return_code", "FAIL");
						xRes.putElement("return_msg", "当前订单已取消");
					}else if(!PlainKanteenOrder.STATUS_DEFAULT.equals(order.getStatus())){
						xRes.putElement("return_code", "FAIL");
						xRes.putElement("return_msg", "当前订单已经支付过");
					}else{
						WeiXinUser user = userService.getWeiXinUserByOpenid(payUserOpenid);
						if(user != null){
							try {
								kanteenService.payOrder(order, user);
								xRes.putElement("return_code", "SUCCESS");
								xRes.putElement("return_msg", "OK");
							} catch (Exception e) {
								if(e instanceof OrderPayException && ((OrderPayException) e).isPaied()){
									xRes.putElement("return_code", "SUCCESS");
									xRes.putElement("return_msg", "OK");
								}else{
									logger.error("更改订单状态为已支付时发生异常", e);
								}
							}
						}else{
							logger.error("系统中没有找到openid[" + payUserOpenid + "]对应的微信用户");
						}
					}
					
				}else{
					logger.error("系统中没有找到outTradeNo[" + outTradeNo + "]对应的订单");
				}
			}else{
				logger.error("没有传入订单的outTrade或者支付者的openid[outTradeNo=" + outTradeNo + ", openid=" + payUserOpenid + "]");
			}
		}
		logger.info("微信状态通知请求，本地响应为");
		logger.info(xRes);
		return xRes;
	}
	
	
	@RequestMapping("/order_list_data")
	public String orderListData(String range, PageInfo pageInfo, Model model){
		WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
		pageInfo.setPageSize(5);
		KanteenOrderCriteria criteria = new KanteenOrderCriteria();
		criteria.setUserId(user.getId());
		List<KanteenOrder> orderList = kanteenService.queryOrder(criteria , pageInfo);
		Map<Long, PlainKanteenDelivery> deliveryMap = kanteenService.getDeliveryMap(new HashSet<Long>(CollectionUtils.toList(orderList, order->order.getPlainOrder().getDeliveryId())));
		model.addAttribute("orderList", orderList);
		model.addAttribute("orderStatusMap", KanteenConstants.ORDER_STATUS_MAP);
		model.addAttribute("deliveryMap", deliveryMap);
		
		return WeiXinConstants.PATH_KANTEEN + "/kanteen_order_list_data.jsp"; 
	}
	
	
	
}
