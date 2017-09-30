package cn.sowell.ddxyz.weixin.controller.kanteen;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sowell.ddxyz.model.canteen.pojo.KanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.service.KanteenService;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

@Controller
@RequestMapping(WeiXinConstants.URI_BASE + "/kanteen")
public class WeiXinKanteenController {
	
	@Resource
	KanteenService kanteenService;
	
	@RequestMapping({"", "/"})
	public String main(Long merchantId, Model model){
		KanteenMerchant merchant = kanteenService.getMerchant(merchantId);
		if(merchant != null){
			KanteenMenu menu = kanteenService.getMenuOfThisWeek(merchantId);
			KanteenDelivery delivery = kanteenService.getDelieryOfThisWeek(merchantId);
			model.addAttribute("merchant", merchant);
			model.addAttribute("menu", menu);
			model.addAttribute("delivery", delivery);
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
	
	
	
}
