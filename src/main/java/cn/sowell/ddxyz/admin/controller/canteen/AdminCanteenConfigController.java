package cn.sowell.ddxyz.admin.controller.canteen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import cn.sowell.copframe.common.file.FileUploadUtils;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.ajax.PageAction;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekDeliveryCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekDeliveryWaresItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenConfigService;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/canteen/config")
public class AdminCanteenConfigController {
	
	@Resource
	CanteenConfigService canteenConfigService;
	
	@Resource
	FileUploadUtils fUploadUtils;
	
	@Resource
	FrameDateFormat dateFormat;
	
	Logger logger = Logger.getLogger(AdminCanteenConfigController.class);
	
	
	@RequestMapping("/week_delivery")
	public String weekDelivery(CanteenWeekDeliveryCriteria criteria, Model model) {
		//根据日期参数构造日期范围条件
		if(criteria.getStartDate() == null && criteria.getEndDate() == null) {
			if(TextUtils.hasText(criteria.getDate())) {
				Date date = dateFormat.parse(criteria.getDate());
				if(date != null) {
					criteria.setStartDate(dateFormat.getTheDayOfWeek(date, Calendar.MONDAY, 0, 0, 0, 0));
					criteria.setEndDate(dateFormat.incDay(criteria.getStartDate(), 7));
				}
			}else {
				criteria.setDate(dateFormat.formatDate(new Date()));
				criteria.setStartDate(dateFormat.getTheDayOfWeek(Calendar.MONDAY, 0));
				criteria.setEndDate(dateFormat.incDay(criteria.getStartDate(), 7));
			}
		}
		PlainDelivery delivery = canteenConfigService.getCanteenDelivery(criteria);
		if(delivery != null){
			List<CanteenWeekDeliveryWaresItem> items = canteenConfigService.getCanteenDeliveryWaresItems(delivery.getId());
			model.addAttribute("deliveryWaresItems", items);
		}
		model.addAttribute("delivery", delivery);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_CANTEEN + "/canteen_week_delivery.jsp";
	}
	
	
	/*@RequestMapping("/wares_list")
	public String waresList(Model model, CanteenDeliveryWaresListCriteria criteria){
		if(criteria.getStartDate() == null && criteria.getEndDate() == null) {
			if(TextUtils.hasText(criteria.getDate())) {
				Date date = dateFormat.parse(criteria.getDate());
				if(date != null) {
					criteria.setStartDate(dateFormat.getTheDayOfWeek(date, Calendar.MONDAY, 0, 0, 0, 0));
					criteria.setEndDate(dateFormat.incDay(criteria.getStartDate(), 7));
				}
			}else {
				criteria.setStartDate(dateFormat.getTheDayOfWeek(Calendar.MONDAY, 0));
				criteria.setEndDate(dateFormat.incDay(criteria.getStartDate(), 7));
			}
		}
		
		List<CanteenDeliveryWaresListItem> deliveryWaresList = canteenConfigService.getDeliveryWaresList(criteria);
		model.addAttribute("deliveryWaresList", deliveryWaresList);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_CANTEEN + "/canteen_ware_list.jsp";
	}*/
	
	
	@RequestMapping("/batch_delivery")
	public String batchDelivery(){
		return AdminConstants.PATH_CANTEEN + "/canteen_batch_delivery.jsp";
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
			}
		}
		return AdminConstants.PATH_CANTEEN + "/canteen_generate_delivery.jsp";
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
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("创建成功", "wares-public");
		}
		
	}
	
	@RequestMapping("/create_wares")
	public String createWares(){
		return AdminConstants.PATH_CANTEEN + "/canteen_create_wares.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_create_wares")
	public AjaxPageResponse doCreateWares(
			@RequestParam("thumb") MultipartFile file,
			@RequestParam("waresName") String waresName, 
			@RequestParam("unitPrice") Float unitPrice, 
			@RequestParam("priceUnit") String priceUnit
			){
		AjaxPageResponse response = new AjaxPageResponse();
		PlainWares wares = new PlainWares();
		wares.setBasePrice((int)(unitPrice * 100));
		wares.setPriceUnit(priceUnit);
		wares.setName(waresName);
		
		String[] nameSplit = file.getOriginalFilename().split("\\.");
		String suffix = nameSplit[nameSplit.length - 1];
		String fileName = "f_" + TextUtils.uuid(10, 36) + "." + suffix;
		try {
			wares.setThumbUri(fUploadUtils.saveFile(fileName, file.getInputStream()));
			canteenConfigService.saveWares(wares);
			response.setLocalPageAction(PageAction.CLOSE);
			response.setNotice("创建成功");
			response.setNoticeType(NoticeType.SUC);
			response.setTargetPageId("canteen-batch-delivery");
			response.setTargetPageAction(PageAction.REFRESH);
		} catch (IOException e) {
			logger.error("上传文件失败", e);
			response.setNotice("创建失败");
			response.setNoticeType(NoticeType.ERROR);
		}
		return response;
	}
	
	
}
