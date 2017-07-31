package cn.sowell.ddxyz.admin.controller.production;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.format.OfDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.utils.qrcode.QrCodeUtils;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductCriteria;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;
import cn.sowell.ddxyz.model.drink.service.ProductService;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;

/**
 * 
 * <p>Title: ProductionController</p>
 * <p>Description: </p><p>
 * 生产管理
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月25日 上午8:20:58
 */
@Controller
@RequestMapping(AdminConstants.URI_BASE + "/production")
public class AdminProductionController {
	
	@Resource
	ProductService productService;
	
	@Resource
	OfDateFormat dateformat;
	
	@Resource
	FrameDateFormat fdFormat;
	
	@Resource
	DeliveryService deliveryService;
	
	
	Logger logger = Logger.getLogger(AdminProductionController.class);
	
	
	@RequestMapping("/main")
	public String main(Model model){
		List<DeliveryTimePoint> timePointList = deliveryService.getTodayDeliveryTimePoints(DdxyzConstants.WARES_ID);
		List<PlainLocation> locationList = deliveryService.getAllDeliveryLocation(DdxyzConstants.MERCHANT_ID);
		Map<DeliveryTimePoint, Integer> productPrintedCountMap = new HashMap<DeliveryTimePoint, Integer>();
		Map<DeliveryTimePoint, Integer> productNotPrintCountMap = new HashMap<DeliveryTimePoint, Integer>();
		for(DeliveryTimePoint timePoint : timePointList){
			int productPrintedCount = productService.getProductPrintedCountByStatus(1, timePoint.getDatetime());
			int productNotPrintCount = productService.getProductNotPrintCountByStatus(1, timePoint.getDatetime());
			productPrintedCountMap.put(timePoint, productPrintedCount);
			productNotPrintCountMap.put(timePoint, productNotPrintCount);
		}
		model.addAttribute("timePointList", timePointList);
		model.addAttribute("locationList", locationList);
		model.addAttribute("printedMap", productPrintedCountMap);
		model.addAttribute("notPrintMap", productNotPrintCountMap);
		return AdminConstants.PATH_PRODUCTION + "/production_main.jsp";
	}
	
	@RequestMapping("/query")
	public String query(@RequestParam(required=false, defaultValue="false") String withTimer, 
						ProductCriteria criteria, 
						CommonPageInfo pageInfo, 
						Model model){
		pageInfo.setPageSize(6);
		String locationName = criteria.getLocationName();
		if(!StringUtils.isEmpty(locationName)){
			try {
				locationName = URLDecoder.decode(locationName, "UTF-8");
				criteria.setLocationName(locationName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		List<ProductInfoItem> list = productService.getProductInfoItemPageList(criteria, pageInfo);
		model.addAttribute("productList", list);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("withTimer", withTimer);
		model.addAttribute("statusCnameMap", DdxyzConstants.PRODUCT_STATUS_CNAME);
		return AdminConstants.PATH_PRODUCTION + "/production_query.jsp";
	}
	
	@RequestMapping("/product-list")
	public String list(ProductionCriteria criteria,CommonPageInfo pageInfo, Model model){
		if(criteria.getTimeRange() == null){
			criteria.setStartTime(fdFormat.getTheDayZero(new Date()));
			criteria.setEndTime(fdFormat.incDay(criteria.getStartTime(), 1));
		}
		if(criteria.getProductStatus() == null){
			criteria.setProductStatus(Product.STATUS_ORDERED);
		}
		if(criteria.getTimePoint() == null){
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			Integer timePoint = DdxyzConstants.TIMEPOINT_SET.higher(hour);
			if(timePoint != null){
				criteria.setTimePoint(String.valueOf(timePoint));
			}
		}
		List<ProductInfoItem> list = productService.getProductInfoItemPageList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("statusCnameMap", DdxyzConstants.PRODUCT_STATUS_CNAME);
		return AdminConstants.PATH_PRODUCTION + "/production_list.jsp";
	}
	
	@RequestMapping("/product-print")
	public String productPrint(@RequestParam String productIds, Model model){
		List<Long> productIdList = CollectionUtils.toList(Arrays.asList(productIds.split(",")), pid->FormatUtils.toLong(pid));
		try {
			List<ProductInfoItem> list = productService.getProductInfoItemListByProductIds(productIdList);
			Collections.reverse(list);
			model.addAttribute("list", list);
		} catch (ProductException e) {
			logger.error("生产管理打印产品时，修改产品出现异常，将导致打印内容为空", e);
		}
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		return AdminConstants.PATH_PRODUCTION + "/production_print.jsp";
	}
	
	
	
	@RequestMapping("/print_tag")
	public String printTag(@RequestParam String productIds, Model model, HttpServletRequest request){
		List<Long> productIdList = CollectionUtils.toList(Arrays.asList(productIds.split(",")), pid->FormatUtils.toLong(pid));
		Map<Long, String> qrCodeMap = new HashMap<Long, String>();
		try {
			List<ProductInfoItem> list = productService.getProductInfoItemListByProductIds(productIdList);
			list.forEach(item->{
				String qrCode = productService.encodeQrCode(item.getDispenseCode());
				saveQrImage(qrCode, "png", request);
				qrCodeMap.put(item.getProductId(), qrCode);
			});
			Collections.reverse(list);
			model.addAttribute("list", list);
		} catch (ProductException e) {
			logger.error("生产管理打印产品时，修改产品出现异常，将导致打印内容为空", e);
		}
		model.addAttribute("qrCodeMap", qrCodeMap);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		return AdminConstants.PATH_PRODUCTION + "/production_tag.jsp";
	}
	
	
	private void saveQrImage(String qrCode, String suffix, HttpServletRequest request) {
		String folder = HttpRequestUtils.getRealPath(request, "/") + "/resources/qrcodes/";
		File fFolder = new File(folder);
		if(!fFolder.exists()){
			fFolder.mkdirs();
		}
		File file = new File(folder + qrCode + "." + suffix);
		try {
			if(file.createNewFile()){
				OutputStream outputStream = new FileOutputStream(file);
				QrCodeUtils.encodeQRCodeImage(qrCode, "utf-8", outputStream , suffix, 200, 200);
			}
		} catch (IOException e) {
		}
	}
	

	@RequestMapping("/print-specify-count-product")
	public String printSpecifyCountProduct(ProductionCriteria criteria, Model model){
		if(criteria.getTimeRange() == null){
			criteria.setStartTime(fdFormat.getTheDayZero(new Date()));
			criteria.setEndTime(fdFormat.incDay(criteria.getStartTime(), 1));
		}
		List<ProductInfoItem> list = productService.getProductInfoItemPageList(criteria, null);
		Collections.reverse(list);
		model.addAttribute("list", list);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_PRODUCTION + "/production_print.jsp";
	}
}
