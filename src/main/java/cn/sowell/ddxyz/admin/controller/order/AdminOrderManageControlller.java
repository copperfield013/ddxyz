package cn.sowell.ddxyz.admin.controller.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.criteria.OrderCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.OrderStatisticsListItem;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkDeliveryService;
import cn.sowell.ddxyz.model.drink.service.DrinkOrderService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/order-manage")
public class AdminOrderManageControlller {
	
	@Resource
	FrameDateFormat fdFormat;
	
	@Resource
	DrinkOrderService drinkOrderService;
	
	@Resource
	DrinkDeliveryService drinkDeliveryService;
	
	@Resource
	OrderService orderService;
	
	@RequestMapping("/order-list")
	public String list(OrderCriteria criteria, CommonPageInfo pageInfo, Model model){
		if(criteria.getTimeRange() == null){
			criteria.setStartTime(fdFormat.getTheDayZero(new Date()));
			criteria.setEndTime(fdFormat.incDay(criteria.getStartTime(), 1));
		}
		List<PlainOrder> orderList = drinkOrderService.getOrderPageList(criteria,pageInfo);
		Map<Long, Integer> cupCountMap = drinkOrderService.getOrderCupCount(orderList);
		model.addAttribute("orderList", orderList);
		model.addAttribute("cupCountMap", cupCountMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_ORDER + "/order_list.jsp";
	}
	
	@RequestMapping("/order-detail")
	public String deltail(@RequestParam Long id, Model model){
		PlainOrder plainOrder = orderService.getPlainOrder(id);
		List<PlainOrderDrinkItem> orderDrinkItemList = drinkOrderService.getOrderDrinkItemList(id);
		//订单条目
		Map<PlainOrder, List<PlainOrderDrinkItem>> orderDrinkItemMap = new HashMap<PlainOrder, List<PlainOrderDrinkItem>>();
		orderDrinkItemMap.put(plainOrder, orderDrinkItemList);
		//加料
		Map<PlainOrderDrinkItem, List<PlainDrinkAddition>> additionMap = drinkDeliveryService.getDrinkItemAdditions(orderDrinkItemMap);
		List<PlainOrder> orderList = new ArrayList<PlainOrder>();
		orderList.add(plainOrder);
		Map<Long, Integer> cupCountMap = drinkOrderService.getOrderCupCount(orderList); //杯数
		model.addAttribute("plainOrder", plainOrder);
		model.addAttribute("orderDrinkItemMap", orderDrinkItemMap);
		model.addAttribute("addtionMap", additionMap);
		model.addAttribute("cupCountMap", cupCountMap);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		return AdminConstants.PATH_ORDER + "/order_detail.jsp";
	}
	
	@RequestMapping("/order-statistics")
	public String orderStatistics(OrderCriteria criteria, CommonPageInfo pageInfo, Model model){
		List<OrderStatisticsListItem> statisticsList = drinkOrderService.statisticOrder(criteria, pageInfo);
		model.addAttribute("statisticsList", statisticsList);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		return AdminConstants.PATH_ORDER + "/order_statistics.jsp";
	}

}
