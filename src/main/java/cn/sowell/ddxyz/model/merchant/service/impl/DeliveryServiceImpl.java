package cn.sowell.ddxyz.model.merchant.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.merchant.dao.DeliveryDao;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;

@Service("deliveryServiceImpl")
public class DeliveryServiceImpl implements DeliveryService{

	@Resource
	DeliveryDao deliveryDao;
	
	@Resource
	DeliveryManager dManager;
	
	@Resource
	OrderService oService;
	
	@Override
	public LinkedHashMap<DeliveryTimePoint, List<PlainDelivery>> getTodayDeliveries(
			Long waresId, boolean usable) {
		List<PlainDelivery> deliveris = deliveryDao.getAllDelivery(waresId, new Date());
		Date current = new Date();
		return (LinkedHashMap<DeliveryTimePoint, List<PlainDelivery>>) CollectionUtils.toListMap(deliveris, delivery -> {
			if(!usable || current.before(delivery.getCloseTime())){
				return new DeliveryTimePoint(delivery.getTimePoint(), delivery.getCloseTime());
			}else{
				return null;
			}
		});
	}
	
	@Override
	public Integer getDeliveryRemain(Long deliveryId) {
		Delivery delivery = dManager.getDelivery(deliveryId);
		if(delivery != null){
			Integer maxCount = delivery.getMaxCount();
			if(maxCount == null){
				return Integer.MAX_VALUE;
			}else{
				return maxCount - delivery.getCurrentCount();
			}
		}else{
			return null;
		}
	}
	
	@Override
	public List<DeliveryTimePoint> getTodayDeliveryTimePoints(long waresId) {
		List<PlainDelivery> deliveris = deliveryDao.getAllDelivery(waresId, new Date());
		Set<DeliveryTimePoint> result = new LinkedHashSet<DeliveryTimePoint>();
		deliveris.forEach(delivery -> result.add(new DeliveryTimePoint(delivery.getTimePoint(), delivery.getCloseTime())));
		return new ArrayList<DeliveryTimePoint>(result);
	}
	
	@Override
	public List<PlainLocation> getAllDeliveryLocation(long merchantId) {
		Set<PlainLocation> locations = deliveryDao.getAllLocation(merchantId);
		return new ArrayList<PlainLocation>(locations);
	}
	
	
	@Override
	public void addPlan(PlainDeliveryPlan plan) {
		plan.setCreateTime(new Date());
		deliveryDao.savePlan(plan);
	}
	
	
	@Override
	public Map<DeliveryTimePoint, List<PlainDelivery>> getUsableDeliveryMap(
			long orderId) {
		Map<DeliveryTimePoint, List<PlainDelivery>> map = new LinkedHashMap<DeliveryTimePoint, List<PlainDelivery>>();
		Order order = oService.getOrder(orderId);
		if(order != null){
			Serializable deliveryId = order.getDeliveryId();
			PlainDelivery pDelivery = deliveryDao.getPlainDelivery((long) deliveryId);
			Long waresId = pDelivery.getWaresId();
			LinkedHashMap<DeliveryTimePoint, List<PlainDelivery>> allDeliveryMap = getTodayDeliveries(waresId, false);
			Date current = new Date();
			allDeliveryMap.forEach((timePoint, delivery) -> {
				if(current.compareTo(timePoint.getCloseTime()) < 0){
					map.put(timePoint, delivery);
				}
			});
		}
		return map;
	}
	public List<PlainDeliveryPlan> getPlainDeliveryPlanPageList(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo){
		return deliveryDao.getPlainDeliveryPlanPageList(criteria, pageInfo);
	}

	@Override
	public boolean changePlanDisabled(Long planId, Integer disabled) {
		if(disabled == DdxyzConstants.VALUE_TRUE){
			return deliveryDao.changePlanDisabled(planId, disabled);
		}else if(disabled == null){
			return deliveryDao.changePlanDisabled(planId, null);
		}
		return false;
	}
	
}
