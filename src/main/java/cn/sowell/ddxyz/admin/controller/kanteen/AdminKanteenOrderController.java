package cn.sowell.ddxyz.admin.controller.kanteen;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.kanteen.KanteenConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenOrderItem;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenOrderService;

@Controller
@RequestMapping(AdminConstants.URI_KANTEEN_BASE + "/order")
public class AdminKanteenOrderController {
	
	@Resource
	KanteenMerchantService merchantService;
	
	@Resource
	KanteenOrderService orderService;
	
	@RequestMapping("/order_list/distribution/{distributionId}")
	public String distributionOrderList(@PathVariable Long distributionId, KanteenOrderListCriteria criteria, PageInfo pageInfo, Model model){
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		criteria.setMerchantId(merchant.getId());
		List<KanteenOrderItem> orderList = orderService.queryOrderList(criteria, pageInfo);
		model.addAttribute("orderList", orderList);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("orderStatusMap", KanteenConstants.ORDER_STATUS_MAP);
		model.addAttribute("canceledStatusMap", KanteenConstants.CANCELED_STATUS_MAP);
		return AdminConstants.PATH_KANTEEN_ORDER + "/order_list.jsp";
	}
	
	
}
