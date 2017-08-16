package cn.sowell.ddxyz.admin.controller.canteen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenOrderUpdateItem;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenDeliveryService;
import cn.sowell.ddxyz.model.canteen.service.CanteenManageService;
import cn.sowell.ddxyz.model.canteen.service.CanteenService;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/canteen/manage")
public class AdminCanteenManageController {
	
	@Resource
	FrameDateFormat dateFormat;
	
	@Resource
	CanteenDeliveryService canteenConfigService;

	@Resource
	CanteenManageService canteenManageService;

	
	@Resource
	CanteenService canteenService;
	
	
	Logger logger;
	
	
	
	
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
		Integer totalAmount = canteenManageService.amountDelivery(delivery.getId());
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("delivery", delivery);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		return AdminConstants.PATH_CANTEEN + "/manage/canteen_week_orders.jsp";
		
	}
	
	@ResponseBody
	@RequestMapping("/export_orders/{deliveryId}")
	public ResponseEntity<byte[]> exportOrders(@PathVariable Long deliveryId){
		//根据id获得配送对象
		PlainDelivery delivery = canteenConfigService.getDelivery(deliveryId);
		byte[] byteArray = null;
		if(delivery != null){
			//根据配送对象
			List<CanteenDeliveryOrdersItem> items = canteenManageService.queryDeliveryOrderItems(delivery.getId(), null);
			//创建表格
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			//将数据写写到表格内
			canteenManageService.writeOrderToSheet(delivery, items, sheet);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				workbook.write(out);
				byteArray = out.toByteArray();
			} catch (IOException e) {
				logger.error("生成导出文件时发生错误", e);
			}
			
		}
		HttpHeaders header = new HttpHeaders();
		try {
			Date[] range = dateFormat.getTheWeekRange(delivery.getTimePoint(), null); 
			String fileName = "订单列表(" + dateFormat.formatDate(range[0]) + "~" + dateFormat.formatDate(range[1]) + ")";
			header.setContentDispositionFormData(fileName, new String((fileName + ".xlsx").getBytes("utf-8"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		header.add("content-type", "application/vnd.ms-excel;charset=utf-8");
		return new ResponseEntity<byte[]>(byteArray, header, HttpStatus.CREATED);
	}
	
	
	@RequestMapping("/print_orders/{deliveryId}")
	public String printOrdersPage(@PathVariable Long deliveryId, Model model){
		//根据id获得配送对象
		PlainDelivery delivery = canteenConfigService.getDelivery(deliveryId);
		
		if(delivery != null){
			//根据配送对象
			List<CanteenDeliveryOrdersItem> items = canteenManageService.queryDeliveryOrderItems(delivery.getId(), null);
			
			model.addAttribute("delivery", delivery);
			model.addAttribute("items", items);
		}
		Date[] range = dateFormat.getTheWeekRange(delivery.getTimePoint(), Calendar.MONDAY);
		model.addAttribute("Monday", range[0]);
		model.addAttribute("Sunday", range[1]);
		return AdminConstants.PATH_CANTEEN + "/manage/canteen_print_orders.jsp";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/order_tag")
	public JsonResponse orderTag(Long orderId) {
		PlainCanteenOrder order = canteenService.getCanteenOrder(orderId);
		List<CanteenOrderUpdateItem> orderItems = canteenService.getOrderItems(orderId);
		JsonResponse jRes = new JsonResponse();
		jRes.setJsonObject(canteenManageService.toOrderTagObject(order, orderItems));
		return jRes;
	}
	
	@ResponseBody
	@RequestMapping("/order_tag_all")
	public JsonResponse orderTagAll(@RequestParam Long deliveryId) {
		JsonResponse jRes = new JsonResponse();
		//根据id获得配送对象
		PlainDelivery delivery = canteenConfigService.getDelivery(deliveryId);
		if(delivery != null){
			//根据配送对象
			List<CanteenDeliveryOrdersItem> items = canteenManageService.queryDeliveryOrderItems(delivery.getId(), null);
			jRes.setJsonObject(canteenManageService.toOrderTagObject(items));
		}
		return jRes;
	}
	
}
