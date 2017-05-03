package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
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
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkOrderService;

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
	
	@Override
	public List<PlainOrder> getOrderList(Long userId) {
		return drinkOrderDao.getOrderList(userId);
	}
	
	public List<PlainOrder> getOrderPageList(Long userId, CommonPageInfo pageInfo){
		return drinkOrderDao.getOrderList(userId, pageInfo);
	}
	
	
	@Override
	public List<PlainDrinkOrder> getDrinkList(UserIdentifier user) {
		List<PlainOrder> orderList = getOrderList((Long)user.getId());
		List<PlainDrinkOrder> drinkList = getNewDrinkOrderList(orderList);
		return drinkList;
	}
	
	public List<PlainDrinkOrder> getDrinkPageList(UserIdentifier user, CommonPageInfo pageInfo){
		List<PlainOrder> orderList = getOrderPageList((Long)user.getId(), pageInfo);
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
	
}
