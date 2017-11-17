package cn.sowell.ddxyz.admin.controller.kanteen;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/kanteen/distribution")
public class AdminKanteenDistributionController {
	
	@Resource
	KanteenMerchantService merchantService;
	
	@Resource
	KanteenDistributionService distributionService;
	
	
	@RequestMapping({"", "/"})
	public String main(PageInfo pageInfo, Model model){
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		List<KanteenDistributionItem> items = distributionService.queryDistributions(merchant.getId(), pageInfo);
		model.addAttribute("distributionList", items);
		return AdminConstants.PATH_KANTEEN_DISTRIBUTION + "/distribution_list.jsp";
	}
	
	
	
	
	
	
}
