package cn.sowell.ddxyz.admin.controller.production;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.format.OfDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;
import cn.sowell.ddxyz.model.drink.service.ProductService;

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
	
	Logger logger = Logger.getLogger(AdminProductionController.class);
	
	
	@RequestMapping("/product-list")
	public String list(ProductionCriteria criteria,CommonPageInfo pageInfo, Model model){
		if(criteria.getTimeRange() == null){
			criteria.setStartTime(fdFormat.getTheDayZero(new Date()));
			criteria.setEndTime(fdFormat.incDay(criteria.getStartTime(), 1));
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
		;
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
