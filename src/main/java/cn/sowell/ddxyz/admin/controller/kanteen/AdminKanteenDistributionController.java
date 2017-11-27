package cn.sowell.ddxyz.admin.controller.kanteen;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.choose.ChooseTablePage;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDistributionChooseMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenMenuItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/kanteen/distribution")
public class AdminKanteenDistributionController {
	
	@Resource
	KanteenMerchantService merchantService;
	
	@Resource
	KanteenDistributionService distributionService;
	
	@Resource
	FrameDateFormat dateFormat;
	
	Logger logger = Logger.getLogger(AdminKanteenDistributionController.class);
	
	@RequestMapping({"", "/"})
	public String main(PageInfo pageInfo, Model model){
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		List<KanteenDistributionItem> items = distributionService.queryDistributions(merchant.getId(), pageInfo);
		model.addAttribute("distributionList", items);
		return AdminConstants.PATH_KANTEEN_DISTRIBUTION + "/distribution_list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(){
		return AdminConstants.PATH_KANTEEN_DISTRIBUTION + "/distribution_add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(@RequestParam Long menuId, @RequestParam String dateRange, Integer saveProduct){
		PlainKanteenDistribution distribution = new PlainKanteenDistribution();
		distribution.setMenuId(menuId);
		Date[] range = dateFormat.splitDateRange(dateRange);
		distribution.setStartTime(range[0]);
		distribution.setEndTime(range[1]);
		distribution.setMerchantId(merchantService.getCurrentMerchant().getId());
		distribution.setSaveProduct(saveProduct == null? null: 1);
		try {
			distributionService.saveDistribution(distribution);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("保存成功", "distribution_id");
		} catch (Exception e) {
			logger.error("保存配销时发生错误", e);
			return AjaxPageResponse.FAILD("保存失败");
		}
	}
	
	
	@RequestMapping("/update/{distributionId}")
	public String upate(@PathVariable Long distributionId, Model model){
		PlainKanteenDistribution distribution = distributionService.getDistribution(distributionId);
		model.addAttribute("distribution", distribution);
		return AdminConstants.PATH_KANTEEN_DISTRIBUTION + "/distribution_update.jsp";
	}
	
	
	
	@RequestMapping("/choose_menu")
	public String chooseMenu(KanteenDistributionChooseMenuCriteria criteria, PageInfo pageInfo, Model model){
		criteria.setMerchantId(merchantService.getCurrentMerchant().getId());
		
		List<KanteenMenuItemForChoose> list = distributionService.queryMenuListForChoose(criteria, pageInfo);
		
		ChooseTablePage<KanteenMenuItemForChoose> tpage = new ChooseTablePage<KanteenMenuItemForChoose>("delivery-choose-menu", "menu_");
		tpage
			.setPageInfo(pageInfo)
			.setAction("admin/kanteen/distribution/choose_menu")
			.addHidden("merchantId", criteria.getMenuId())
			.setIsMulti(false)
			.setTableData(list, handler->{
				handler
					.setDataKeyGetter(data->data.getMenuId())
					.addColumn("菜单名", (cell, data)->cell.setText(data.getMenuName()))
					.addColumn("描述", (cell, data)->cell.setText(data.getDescription()))
					.addColumn("创建时间", (cell, data)->cell.setText(dateFormat.formatDateTime(data.getCreateTime())))
					;
			})
			;
		
		model.addAttribute("tpage", tpage);
		return AdminConstants.PATH_BASE + "/common/choose_table.jsp";
	}
	
	
	
	
	
}
