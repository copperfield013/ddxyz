package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.dao.CommonProductDao;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.common.pojo.criteria.ProductCriteria;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.drink.dao.DrinkAdditionDao;
import cn.sowell.ddxyz.model.drink.dao.DrinkOrderDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkOrder;
import cn.sowell.ddxyz.model.drink.pojo.criteria.OrderCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.OrderStatisticsListItem;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkOrderService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class DrinkOrderServiceImpl implements DrinkOrderService{

	@Resource
	DrinkOrderDao drinkOrderDao;
	
	@Resource
	DrinkAdditionDao drinkAdditionDao;
	
	@Resource
	CommonProductDao productDao;
	
	@Resource
	OrderService oService;
	
	@Override
	public List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId) {
		return drinkOrderDao.getOrderDrinkItemList(orderId);
	}
	
	
	@Override
	public List<PlainDrinkAddition> getDrinkAdditionList(Long productId) {
		return drinkAdditionDao.getDrinkAdditionList(productId);
	}
	
	public List<PlainOrder> getOrderPageList(OrderCriteria criteria, CommonPageInfo pageInfo) {
		return drinkOrderDao.getOrderList(criteria, pageInfo);
	}
	
	
	public List<PlainDrinkOrder> getDrinkPageList(UserIdentifier user, CommonPageInfo pageInfo){
		List<PlainOrder> orderList = drinkOrderDao.getOrderPageList((long) user.getId(), pageInfo);
		List<PlainDrinkOrder> drinkList = getNewDrinkOrderList(orderList);
		return drinkList;
	}


	private List<PlainDrinkOrder> getNewDrinkOrderList(List<PlainOrder> orderList){
		List<PlainDrinkOrder> orderItemList = new ArrayList<PlainDrinkOrder>();
		if(orderList != null && orderList.size() > 0){
			for(PlainOrder plainOrder : orderList){
				orderItemList.add(toDrinkOrderItem(plainOrder));
			}
		}
		return orderItemList;
	}
	
	private PlainDrinkOrder toDrinkOrderItem(PlainOrder pOrder) {
		List<PlainOrderDrinkItem> orderDrinkItemList = getOrderDrinkItemList(pOrder.getId());
		PlainDrinkOrder plainOrderDrink = new PlainDrinkOrder();
		plainOrderDrink.setId(pOrder.getId());
		plainOrderDrink.setOrderCode(pOrder.getOrderCode());
		plainOrderDrink.setCanceledStatus(pOrder.getCanceledStatus());
		plainOrderDrink.setOrderStatus(pOrder.getOrderStatus());
		plainOrderDrink.setTotalPrice(pOrder.getTotalPrice());
		plainOrderDrink.setOrderTime(pOrder.getCreateTime());
		plainOrderDrink.setOrderDrinkItems(orderDrinkItemList);
		plainOrderDrink.setCupCount(0);
		plainOrderDrink.setTimePoint(pOrder.getTimePoint());
		plainOrderDrink.setLocationName(pOrder.getLocationName());
		plainOrderDrink.setPayExpireTime(pOrder.getPayExpireTime());
		if(orderDrinkItemList != null && orderDrinkItemList.size() > 0){
			for(PlainOrderDrinkItem item : orderDrinkItemList){
				List<PlainDrinkAddition> additions = getDrinkAdditionList(item.getDrinkProductId());
				item.setAdditions(additions);
			}
			plainOrderDrink.setCupCount(orderDrinkItemList.size());
		}
		return plainOrderDrink;
	}


	@Override
	public Map<Long, Boolean> getRefundableMap(List<Long> orderIdList) {
		HashMap<Long, Boolean> result = new HashMap<Long, Boolean>();
		ProductCriteria criteria = new ProductCriteria();
		criteria.setOrderIdRange(new HashSet<Long>(orderIdList));
		//设置产品状态范围条件
		criteria.setProductStatusRangeMin(Product.STATUS_MAKING);
		criteria.setProductStatusRangeMax(Product.STATUS_DEFAULT);
		List<PlainProduct> unRefundableProducts = productDao.getProducts(criteria);
		Set<Long> unrefundableOrderIds= new HashSet<Long>();
		CollectionUtils.appendTo(unRefundableProducts, unrefundableOrderIds, product->product.getOrderId());
		orderIdList.forEach(orderId -> {
			if(!unrefundableOrderIds.contains(orderId)){
				result.put(orderId, true);
			}
		});
		return result;
	}
	
	@Override
	public PlainDrinkOrder getOrderItem(Long orderId) {
		PlainOrder pOrder = oService.getPlainOrder(orderId);
		return toDrinkOrderItem(pOrder);
		
	}
	
	
	@Override
	public JSONObject converteInitOrder(Delivery delivery, List<PlainOrderDrinkItem> orderItems) {
		Assert.notNull(delivery);
		Assert.notEmpty(orderItems);
		JSONObject result = new JSONObject();
		result.put("deliveryTimePoint", delivery.getTimePoint().getHour());
		result.put("deliveryLocationId", delivery.getLocation().getId());
		JSONArray items = new JSONArray();
		result.put("items", items);
		for (PlainOrderDrinkItem orderItem : orderItems) {
			JSONObject item = new JSONObject();
			item.put("drinkTypeId", orderItem.getDrinkTypeId());
			item.put("teaAdditionTypeId", orderItem.getTeaAdditionId());
			item.put("cupSizeKey", orderItem.getCupSize());
			item.put("sweetnessKey", orderItem.getSweetness());
			item.put("heatKey", orderItem.getHeat());
			JSONArray additionIds = new JSONArray();
			item.put("additionIds", additionIds);
			List<PlainDrinkAddition> additions = orderItem.getAdditions();
			if(additions != null){
				for (PlainDrinkAddition plainDrinkAddition : additions) {
					additionIds.add(plainDrinkAddition.getAdditionTypeId());
				}
			}
			items.add(item);
		}
		return result;
	}


	@Override
	public Map<Long, Integer> getOrderCupCount(List<PlainOrder> list) {
		List<Long> orderIdList = new ArrayList<Long>();
		if(list != null && list.size() >0){
			for(PlainOrder order : list){
				orderIdList.add(order.getId());
			}
		}
		Map<Long, Integer> map = drinkOrderDao.getOrderCupCount(orderIdList);
		return map;
	}


	@Override
	public List<OrderStatisticsListItem> statisticOrder(OrderCriteria criteria, CommonPageInfo pageInfo) {
		return drinkOrderDao.statisticsOrder(criteria, pageInfo);
	}
}
