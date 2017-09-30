package cn.sowell.ddxyz.admin.controller.canteen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.common.file.FileUploadUtils;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekDeliveryCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekDeliveryWaresItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenDeliveryService;
import cn.sowell.ddxyz.model.canteen.service.CanteenManageService;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/canteen/delivery")
public class AdminCanteenDeliveryController {
	
	@Resource
	CanteenDeliveryService deliveryService;
	
	@Resource
	FileUploadUtils fUploadUtils;
	
	@Resource
	FrameDateFormat dateFormat;

	@Resource
	CanteenManageService canteenManageService;
	
	@RequestMapping("/week_delivery")
	public String weekDelivery(CanteenWeekDeliveryCriteria criteria, Model model) {
		PlainDelivery delivery = deliveryService.getCanteenDelivery(criteria);
		if(delivery != null){
			List<CanteenWeekDeliveryWaresItem> items = deliveryService.getCanteenDeliveryWaresItems(delivery.getId());
			model.addAttribute("deliveryWaresItems", items);
			Integer totalAmount = canteenManageService.amountDelivery(delivery.getId());
			model.addAttribute("totalAmount", totalAmount);
			PlainCanteenOrderStat stat = canteenManageService.statDelivery(delivery.getId());
			model.addAttribute("stat", stat);
		}
		model.addAttribute("delivery", delivery);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_CANTEEN + "/delivery/canteen_week_delivery.jsp";
	}
	
	
	@RequestMapping("/add")
	public String generateDelivery(Model model, @RequestParam String date){
		Date theDay = dateFormat.parse(date);
		if(theDay != null){
			Date Monday = dateFormat.getTheDayOfWeek(theDay, Calendar.MONDAY, 0, 0, 0, 0);
			CanteenWeekDeliveryCriteria criteria = new CanteenWeekDeliveryCriteria();
			criteria.setStartDate(Monday);
			criteria.setEndDate(dateFormat.incDay(Monday, 7));
			PlainDelivery delivery = deliveryService.getCanteenDelivery(criteria);
			if(delivery == null){
				model.addAttribute("Monday", Monday);
				model.addAttribute("Sunday", dateFormat.incDay(Monday, 6));
				List<PlainWares> canteenWaresList = deliveryService.getWaresList(DdxyzConstants.CANTEEN_MERCHANT_ID, false);
				List<PlainLocation> locations = deliveryService.getCanteenDeliveryLocations();
				model.addAttribute("waresList", canteenWaresList);
				model.addAttribute("locations", locations);
				model.addAttribute("criteria", criteria);
			}
		}
		return AdminConstants.PATH_CANTEEN + "/delivery/canteen_delivery_add.jsp";
	}
	
	
	private List<PlainDeliveryWares> getDWaresListFromRequest(WebRequest request, int waresCount){
		List<PlainDeliveryWares> dWaresList = new ArrayList<PlainDeliveryWares>();
		for (int i = 0; i < waresCount; i++) {
			Long waresId = FormatUtils.toLong(request.getParameter("waresId-" + i));
			Integer maxCount = FormatUtils.toInteger(request.getParameter("maxCount-" + i));
			Long deliveryWaresId = FormatUtils.toLong(request.getParameter("deliveryWaresId-" + i));
			if(waresId != null || deliveryWaresId != null){
				PlainDeliveryWares dWares = new PlainDeliveryWares();
				dWares.setId(deliveryWaresId);
				dWares.setWaresId(waresId);
				dWares.setMaxCount(maxCount);
				dWaresList.add(dWares);
			}
		}
		return dWaresList;
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doGenerateDelivery(
			@RequestParam String orderTimeRange, 
			@RequestParam String deliveryTimeRange,
			@RequestParam Long locationId,
			@RequestParam Integer waresCount,
			WebRequest request){
		String errMsg = null;
		
		List<PlainDeliveryWares> dWaresList = getDWaresListFromRequest(request, waresCount);
		
		PlainDelivery delivery = new PlainDelivery();
		//设定领取地点
		delivery.setLocationId(locationId);
		Date[] orderTimes = dateFormat.splitDateRange(orderTimeRange),
				deliveryTimes = dateFormat.splitDateRange(deliveryTimeRange);
		if(orderTimes[0] != null && orderTimes[1] != null){
			delivery.setOpenTime(orderTimes[0]);
			delivery.setCloseTime(orderTimes[1]);
			if(deliveryTimes[0] != null && deliveryTimes[1] != null){
				delivery.setTimePoint(deliveryTimes[0]);
				delivery.setClaimEndTime(deliveryTimes[1]);
				
				deliveryService.saveCanteenDelivery(delivery, dWaresList);
				
			}else{
				errMsg = "领取时间范围有误";
			}
		}else{
			errMsg = "预定时间范围有误";
		}
		
		if(errMsg != null){
			return AjaxPageResponse.FAILD(errMsg);
		}else{
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("创建成功", "canteen-week-delivery");
		}
	}
	
	@RequestMapping("/update/{deliveryId}")
	public String updateDelivery(@PathVariable Long deliveryId, Model model){
		PlainDelivery delivery = deliveryService.getDelivery(deliveryId);
		if(delivery != null){
			Date theDay = delivery.getTimePoint();
			if(theDay != null){
				Date[] range = dateFormat.getTheWeekRange(theDay, Calendar.MONDAY);
				CanteenWeekDeliveryCriteria criteria = new CanteenWeekDeliveryCriteria();
				criteria.setStartDate(range[0]);
				criteria.setEndDate(range[1]);
				List<PlainWares> canteenWaresList = deliveryService.getWaresList(DdxyzConstants.CANTEEN_MERCHANT_ID, false);
				List<PlainLocation> locations = deliveryService.getCanteenDeliveryLocations();
				List<CanteenWeekDeliveryWaresItem> items = deliveryService.getCanteenDeliveryWaresItems(delivery.getId());
				model.addAttribute("deliveryWaresItemsJson", JSON.toJSONString(items));
				model.addAttribute("waresList", canteenWaresList);
				model.addAttribute("locations", locations);
				model.addAttribute("criteria", criteria);
				model.addAttribute("delivery", delivery);
			}
		}
		return AdminConstants.PATH_CANTEEN + "/delivery/canteen_delivery_update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_update/{deliveryId}")
	public AjaxPageResponse doUpdateDelivery(
			@PathVariable Long deliveryId, 
			@RequestParam String orderTimeRange, 
			@RequestParam String deliveryTimeRange,
			@RequestParam Long locationId,
			@RequestParam Integer waresCount,
			WebRequest request){
		PlainDelivery delivery = deliveryService.getDelivery(deliveryId);
		
		String errMsg = null;
		
		
		if(delivery != null){
			List<PlainDeliveryWares> dWaresList = getDWaresListFromRequest(request, waresCount);
			
			//设定领取地点
			delivery.setLocationId(locationId);
			Date[] orderTimes = dateFormat.splitDateRange(orderTimeRange),
					deliveryTimes = dateFormat.splitDateRange(deliveryTimeRange);
			if(orderTimes[0] != null && orderTimes[1] != null){
				delivery.setOpenTime(orderTimes[0]);
				delivery.setCloseTime(orderTimes[1]);
				if(deliveryTimes[0] != null && deliveryTimes[1] != null){
					delivery.setTimePoint(deliveryTimes[0]);
					delivery.setClaimEndTime(deliveryTimes[1]);
					
					deliveryService.updateCanteenDelivery(delivery, dWaresList);
					
				}else{
					errMsg = "领取时间范围有误";
				}
			}else{
				errMsg = "预定时间范围有误";
			}
		}else{
			errMsg = "找不到对应的分发事件";
		}
		
		if(errMsg != null){
			return AjaxPageResponse.FAILD(errMsg);
		}else{
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "canteen-week-delivery");
		}
	}
	
	@ResponseBody
	@RequestMapping("/disable_wares/{disable}/{deliveryWaresId}")
	public AjaxPageResponse disableWares(@PathVariable Long deliveryWaresId, @PathVariable String disable){
		try {
			deliveryService.disableDeliveryWares(deliveryWaresId, "disable".equals(disable));
			return AjaxPageResponse.REFRESH_LOCAL("操作成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	@RequestMapping("/group_manage")
	public String waresGroupManage(Long deliveryId){
		return AdminConstants.PATH_CANTEEN + "/canteen_delivery_wares_group_manage.jsp";
	}
	
	
	@ResponseBody
	@RequestMapping("/move_group")
	public JsonResponse moveGroup(Long groupId, Boolean isUp){
		JsonResponse jRes = new JsonResponse();
		
		return jRes;
	}
	
	
}
