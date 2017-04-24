package cn.sowell.ddxyz.model.merchant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.merchant.dao.DeliveryDao;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService{

	@Resource
	DeliveryDao deliveryDao;
	
	@Resource
	DeliveryManager dManager;
	
	@Override
	public LinkedHashMap<DeliveryTimePoint, List<PlainDelivery>> getTodayDeliveries(
			Long waresId) {
		List<PlainDelivery> deliveris = deliveryDao.getAllDelivery(waresId, new Date());
		return (LinkedHashMap<DeliveryTimePoint, List<PlainDelivery>>) CollectionUtils.toListMap(deliveris, delivery -> {
			return new DeliveryTimePoint(delivery.getTimePoint());
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

}
