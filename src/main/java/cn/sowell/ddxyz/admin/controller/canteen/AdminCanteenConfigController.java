package cn.sowell.ddxyz.admin.controller.canteen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

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
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public class AdminCanteenConfigController {
	
	@Resource
	CanteenDeliveryService canteenConfigService;
	
	@Resource
	FileUploadUtils fUploadUtils;
	
	@Resource
	FrameDateFormat dateFormat;
	
	Logger logger = Logger.getLogger(AdminCanteenConfigController.class);
	
	
	@RequestMapping("/week_delivery")
	public String weekDelivery(CanteenWeekDeliveryCriteria criteria, Model model) {
		PlainDelivery delivery = canteenConfigService.getCanteenDelivery(criteria);
		if(delivery != null){
			List<CanteenWeekDeliveryWaresItem> items = canteenConfigService.getCanteenDeliveryWaresItems(delivery.getId());
			model.addAttribute("deliveryWaresItems", items);
		}
		model.addAttribute("delivery", delivery);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_CANTEEN + "/config/canteen_week_delivery.jsp";
	}
	
	@RequestMapping("/batch_delivery")
	public String batchDelivery(){
		return AdminConstants.PATH_CANTEEN + "/config/canteen_batch_delivery.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_batch_delivery")
	public JsonResponse doBatchDelivery(){
		JsonResponse jRes = new JsonResponse();
		return jRes;
	}
	
	
	
	@RequestMapping("/generate_delivery")
	public String generateDelivery(Model model, @RequestParam String date){
		Date theDay = dateFormat.parse(date);
		if(theDay != null){
			Date Monday = dateFormat.getTheDayOfWeek(theDay, Calendar.MONDAY, 0, 0, 0, 0);
			CanteenWeekDeliveryCriteria criteria = new CanteenWeekDeliveryCriteria();
			criteria.setStartDate(Monday);
			criteria.setEndDate(dateFormat.incDay(Monday, 7));
			PlainDelivery delivery = canteenConfigService.getCanteenDelivery(criteria);
			if(delivery == null){
				model.addAttribute("Monday", Monday);
				model.addAttribute("Sunday", dateFormat.incDay(Monday, 6));
				List<PlainWares> canteenWaresList = canteenConfigService.getWaresList(DdxyzConstants.CANTEEN_MERCHANT_ID, false);
				List<PlainLocation> locations = canteenConfigService.getCanteenDeliveryLocations();
				model.addAttribute("waresList", canteenWaresList);
				model.addAttribute("locations", locations);
				model.addAttribute("criteria", criteria);
			}
		}
		return AdminConstants.PATH_CANTEEN + "/config/canteen_generate_delivery.jsp";
	}
	
	
	@ResponseBody
	@RequestMapping("/do_generate_delivery")
	public AjaxPageResponse doGenerateDelivery(
			@RequestParam String orderTimeRange, 
			@RequestParam String deliveryTimeRange,
			@RequestParam Long locationId,
			@RequestParam Integer waresCount,
			WebRequest request){
		String errMsg = null;
		
		List<PlainDeliveryWares> dWaresList = new ArrayList<PlainDeliveryWares>();
		for (int i = 0; i < waresCount; i++) {
			Long waresId = FormatUtils.toLong(request.getParameter("waresId-" + i));
			Integer maxCount = FormatUtils.toInteger(request.getParameter("maxCount-" + i));
			if(waresId != null){
				PlainDeliveryWares dWares = new PlainDeliveryWares();
				dWares.setWaresId(waresId);
				dWares.setMaxCount(maxCount);
				dWaresList.add(dWares);
			}
		}
		
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
				
				canteenConfigService.saveCanteenDelivery(delivery, dWaresList);
				
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
}
