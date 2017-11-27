package cn.sowell.ddxyz.admin.controller.kanteen;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenLocation;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDeliveryCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDeliveryItem;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDeliveryService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenLocationService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/kanteen/delivery")
public class AdminKanteenDeliveryController {
	
	@Resource
	KanteenMerchantService merchantService;
	
	@Resource
	KanteenDeliveryService deliveryService;

	@Resource
	FrameDateFormat dateFormat;
	
	@Resource
	KanteenLocationService locationService;
	
	Logger logger = Logger.getLogger(AdminKanteenDeliveryController.class);

	
	@RequestMapping({"/", ""})
	public String main(KanteenDeliveryCriteria criteria, PageInfo pageInfo, Model model){
		List<KanteenDeliveryItem> items = deliveryService.queryDeliveryItems(criteria, pageInfo);
		model.addAttribute("items", items);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_KANTEEN_DELIVERY + "/delivery_list.jsp";
	}
	
	@RequestMapping("/add/{distributionId}")
	public String add(@PathVariable Long distributionId, Model model){
		model.addAttribute("distributionId", distributionId);
		return AdminConstants.PATH_KANTEEN_DELIVERY + "/delivery_add.jsp";
	}
	
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(@RequestParam Long distributionId, Long locationId, String timeRange, Integer payWay){
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		try {
			PlainKanteenDelivery delivery = new PlainKanteenDelivery();
			PlainKanteenLocation location = locationService.getLocation(locationId, merchant.getId());
			delivery.setLocationId(location.getId());
			delivery.setLocationName(location.getName());
			
			delivery.setUpdateUserId((Long) WxUtils.getCurrentUser(UserIdentifier.class).getId());
			delivery.setDistributionId(distributionId);
			delivery.setMerchantId(merchant.getId());
			
			Date[] range = dateFormat.splitDateRange(timeRange);
			Assert.isTrue(range[0].before(range[1]));
			delivery.setStartTime(range[0]);
			delivery.setEndTime(range[1]);
			
			delivery.setPayWay(payWay);
			
			deliveryService.saveDelivery(delivery);
			
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("创建成功", "delivery_list");
		} catch (Exception e) {
			logger.error("创建配送时发生错误");
			return AjaxPageResponse.FAILD("创建失败");
		}
	}
	
	
	
	
	
	
}
