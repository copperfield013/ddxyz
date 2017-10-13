package cn.sowell.ddxyz.weixin.controller.kanteen;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.service.KanteenService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/kanteen")
public class WeiXinKanteenController {
	
	@Resource
	KanteenService kanteenService;
	
	Logger logger = Logger.getLogger(WeiXinKanteenController.class);
	
	@RequestMapping({"", "/"})
	public String main(Long merchantId, Model model){
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
				
				model.addAttribute("deliveries", deliveries);
				model.addAttribute("menu", menu);
				model.addAttribute("trolley", trolley);
			}
			model.addAttribute("merchant", merchant);
		}
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
	
	
	@ResponseBody
	@RequestMapping("/commit_trolley")
	public JsonResponse commitTrolley(@RequestParam JsonRequest jReq){
		JsonResponse jRes = new JsonResponse();
		JSONObject json = jReq.getJsonObject();
		Long distributionId = json.getLong("distributionId");
		WeiXinUser user = WxUtils.getCurrentUser(WeiXinUser.class);
		KanteenTrolley trolley = kanteenService.getTrolley(user.getId(), distributionId);
		if(trolley != null){
			Map<Long, Integer> trolleyWares = kanteenService.extractTrolley(json.getJSONObject("trolleyData"));
			try {
				//调用方法更新购物车内的数据
				kanteenService.mergeTrolley(trolley.getId(), trolleyWares);
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
	
	
	
}
