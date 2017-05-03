package cn.sowell.ddxyz.admin.controller.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.format.OfDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.drink.pojo.criteira.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;
import cn.sowell.ddxyz.model.drink.service.ProductService;
import cn.sowell.ddxyz.model.main.AdminMainConstants;

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
public class ProductionController {
	
	@Resource
	ProductService productService;
	
	@Resource
	OfDateFormat dateformat;
	
	@RequestMapping("/product-list")
	public String list(ProductionCriteria criteria,CommonPageInfo pageInfo, Model model){
		if(criteria.getTimeRange() == null){
			criteria.setStartTime(dateformat.splitDateRange(criteria.getTimeRange())[0]);
			criteria.setEndTime(dateformat.splitDateRange(criteria.getTimeRange())[1]);
		}
		List<ProductInfoItem> list = productService.getProductInfoItemPageList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		return AdminConstants.PATH_PRODUCTION + "/production_list.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/product-data")
	public String getData(ProductionCriteria criteria, Model model){
		List<ProductInfoItem> list = productService.getProductInfoItemList(criteria);
		model.addAttribute("list", list);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_PRODUCTION + "/production_data.jsp";
	}
	
	@RequestMapping("/product-print")
	public String productPrint(@RequestParam String productIds, Model model){
		List<String> productIdList = Arrays.asList(productIds.split(","));
		List<ProductInfoItem> list = productService.getProductInfoItemListByProductIds(productIdList);
		model.addAttribute("list", list);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		return AdminConstants.PATH_PRODUCTION + "/production_print.jsp";
	}
}
