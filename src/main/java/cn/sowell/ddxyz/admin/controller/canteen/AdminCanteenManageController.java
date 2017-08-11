package cn.sowell.ddxyz.admin.controller.canteen;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenConfigService;
import cn.sowell.ddxyz.model.canteen.service.CanteenManageService;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/canteen/manage")
public class AdminCanteenManageController {
	
	@Resource
	FrameDateFormat dateFormat;
	
	@Resource
	CanteenConfigService canteenConfigService;

	@Resource
	CanteenManageService canteenManageService;
	
	
	
	
	@RequestMapping("/week_table")
	public String weekTable(CanteenWeekTableCriteria criteria, CommonPageInfo pageInfo, Model model){
		PlainDelivery delivery = canteenConfigService.getCanteenDelivery(criteria);
		if(delivery != null){
			List<CanteenWeekTableItem> items = canteenManageService.queryDeliveryTableItems(delivery.getId(), criteria, pageInfo);
			model.addAttribute("orderItems", items);
		}
		model.addAttribute("delivery", delivery);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		return AdminConstants.PATH_CANTEEN + "/manage/canteen_week_table.jsp";
	}
	
	
	@RequestMapping("/week_orders")
	public String weekOrders(CanteenCriteria criteria, CommonPageInfo pageInfo, Model model){
		PlainDelivery delivery = canteenConfigService.getCanteenDelivery(criteria);
		if(delivery != null){
			List<CanteenDeliveryOrdersItem> items = canteenManageService.queryDeliveryOrderItems(delivery.getId(), pageInfo);
			model.addAttribute("orderItems", items);
		}
		model.addAttribute("delivery", delivery);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		return AdminConstants.PATH_CANTEEN + "/manage/canteen_week_orders.jsp";
		
	}
	
	@ResponseBody
	@RequestMapping("/export_orders/{deliveryId}")
	public ResponseEntity<byte[]> exportOrders(@PathVariable Long deliveryId){
		File file = new File("d://a.xls");
		
		byte[] byteArray = null;
		try {
			byteArray = FileCopyUtils.copyToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpHeaders header = new HttpHeaders();
		try {
			header.setContentDispositionFormData("attachment", URLEncoder.encode("新建.xls", "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		header.add("content-type", "application/vnd.ms-excel;charset=utf-8");
		return new ResponseEntity<byte[]>(byteArray, header, HttpStatus.CREATED);
	}
	
}
