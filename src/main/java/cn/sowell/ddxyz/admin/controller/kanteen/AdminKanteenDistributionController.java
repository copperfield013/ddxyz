package cn.sowell.ddxyz.admin.controller.kanteen;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDistributionChooseMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuOrderStat;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuOrderStatItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenMenuItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMenuService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenOrderService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/kanteen/distribution")
public class AdminKanteenDistributionController {
	
	@Resource
	KanteenMerchantService merchantService;
	
	@Resource
	KanteenDistributionService distributionService;
	
	@Resource
	KanteenMenuService menuService;
	
	@Resource
	FrameDateFormat dateFormat;
	
	@Resource
	KanteenOrderService orderService;
	
	Logger logger = Logger.getLogger(AdminKanteenDistributionController.class);

	
	@RequestMapping({"", "/"})
	public String main(PageInfo pageInfo, Model model){
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		List<KanteenDistributionItem> items = distributionService.queryDistributions(merchant.getId(), pageInfo);
		Map<Long, Integer> orderCountMap = orderService.getDistributionEffectiveOrderCountMap(CollectionUtils.toSet(items, item->item.getId()));
		model.addAttribute("distributionList", items);
		model.addAttribute("orderCountMap", orderCountMap);
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
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("保存成功", "distribution_list");
		} catch (Exception e) {
			logger.error("保存配销时发生错误", e);
			return AjaxPageResponse.FAILD("保存失败");
		}
	}
	
	@RequestMapping("/detail/{distributionId}")
	public String detail(@PathVariable Long distributionId, PageInfo pageInfo, Model model){
		PlainKanteenDistribution distribution = distributionService.getDistribution(distributionId);
		//配销菜单内所有产品的统计
		List<KanteenMenuOrderStatItem> orderStatItems = distributionService.queryMenuOrderStats(distributionId, pageInfo);
		//配销的订单状态统计
		KanteenMenuOrderStat stat = distributionService.getDistributionOrderStat(distributionId);
		model.addAttribute("distribution", distribution);
		model.addAttribute("orderStatItems", orderStatItems);
		model.addAttribute("stat", stat);
		return AdminConstants.PATH_KANTEEN_DISTRIBUTION + "/distribution_detail.jsp";
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
		return AdminConstants.PATH_CHOOSE_TABLE;
	}
	
	
	
	
	
}
