package cn.sowell.ddxyz.admin.controller.delivery;

import java.util.Calendar;
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
import cn.sowell.ddxyz.model.drink.pojo.criteria.DeliveryCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkDeliveryService;
import cn.sowell.ddxyz.model.drink.service.DrinkOrderService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/delivery")
public class AdminDeliveryController {
	
	@Resource
	FrameDateFormat fdFormat;
	
	@Resource
	DrinkDeliveryService drinkDeliveryService;
	
	@Resource
	OrderService orderService;
	
	@Resource
	DrinkOrderService drinkOrderService;
	
	
	@RequestMapping("/delivery_list")
	public String list(DeliveryCriteria criteria, CommonPageInfo pageInfo, Model model){
		if(criteria.getTimeRange() == null){
			criteria.setStartTime(fdFormat.getTheDayZero(new Date()));
			criteria.setEndTime(fdFormat.incDay(criteria.getStartTime(), 1));
		}
		if(criteria.getTimePoint() == null){
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			Integer timePoint = DdxyzConstants.TIMEPOINT_SET.higher(hour);
			if(timePoint != null){
				criteria.setTimePoint(String.valueOf(timePoint));
			}
		}
		List<PlainOrder> orderList = drinkDeliveryService.getOrderPageList(criteria, pageInfo);
		Map<PlainOrder,List<PlainOrderDrinkItem>> map = drinkDeliveryService.getOrderItems(orderList);
		Map<PlainOrder, Integer> orderMakedCountMap = drinkDeliveryService.mapOrderMakedCount(map);
//		
		Map<PlainOrderDrinkItem, List<PlainDrinkAddition>> additionMap = drinkDeliveryService.getDrinkItemAdditions(map);
		model.addAttribute("orderList", orderList);
		model.addAttribute("itemMap", map);
		model.addAttribute("additionMap", additionMap);
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("completedCountMap", orderMakedCountMap);
		return AdminConstants.PATH_DELIVERY + "/delivery_list.jsp";
	}
	
	@RequestMapping("/delivery-print")
	public String printOrder(@RequestParam Long orderId, Model model){
		PlainOrder plainOrder = orderService.getPlainOrder(orderId);
		if(plainOrder != null){
			List<PlainOrderDrinkItem> orderDrinkItemList = drinkOrderService.getOrderDrinkItemList(orderId);
			Map<PlainOrder, List<PlainOrderDrinkItem>> orderDrinkItemMap = new HashMap<PlainOrder, List<PlainOrderDrinkItem>>();
			orderDrinkItemMap.put(plainOrder, orderDrinkItemList);
			Map<PlainOrderDrinkItem, List<PlainDrinkAddition>> additionMap = drinkDeliveryService.getDrinkItemAdditions(orderDrinkItemMap);
			drinkDeliveryService.updateOrderPrinted(orderId);
			model.addAttribute("plainOrder", plainOrder);
			model.addAttribute("orderDrinkItemMap", orderDrinkItemMap);
			model.addAttribute("addtionMap", additionMap);
		}
		model.addAttribute("heatMap", DdxyzConstants.HEAT_MAP);
		model.addAttribute("sweetnessMap", DdxyzConstants.SWEETNESS_MAP);
		model.addAttribute("cupSizeMap", DdxyzConstants.CUP_SIZE_MAP);
		return AdminConstants.PATH_DELIVERY + "/delivery_print.jsp";
	}
	
}
