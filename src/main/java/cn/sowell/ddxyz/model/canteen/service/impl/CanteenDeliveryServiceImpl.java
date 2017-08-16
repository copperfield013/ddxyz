package cn.sowell.ddxyz.model.canteen.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.canteen.dao.CanteenDeliveryDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenDeliveryWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryWaresListItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekDeliveryWaresItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenDeliveryService;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Repository
public class CanteenDeliveryServiceImpl implements CanteenDeliveryService{
	
	@Resource
	CanteenDeliveryDao configDao;
	@Resource
	FrameDateFormat dateFormat;
	
	
	@Override
	public List<PlainLocation> getCanteenDeliveryLocations() {
		return configDao.getDeliveryLocations("canteen", false);
	}
	
	@Override
	public List<CanteenDeliveryWaresListItem> getDeliveryWaresList(
			CanteenDeliveryWaresListCriteria criteria) {
		return configDao.queryDeliveryWaresList(criteria);
	}
	
	
	
	@Override
	public List<PlainWares> getWaresList(long canteenMerchantId, Boolean disabled) {
		return configDao.getWaresList(canteenMerchantId, disabled);
	}
	
	
	@Override
	public void saveCanteenDelivery(PlainDelivery delivery,
			List<PlainDeliveryWares> dWaresList) {
		Date createTime = new Date();
		delivery.setType("canteen");
		delivery.setCreateTime(createTime);
		
		
		PlainLocation location = configDao.getDeliveryLocation(delivery.getLocationId());
		if(location != null){
			Long deliveryId = configDao.saveDelivery(delivery);
			delivery.setLocationName(location.getName());
			dWaresList.forEach(dWares->{
				if(dWares.getWaresId() != null){
					dWares.setDeliveryId(deliveryId);
					dWares.setCurrentCount(0);
					configDao.saveDeliveryWares(dWares);
				}
			});
		}else{
			throw new RuntimeException("配送地址不存在");
		}
		
	}

	@Override
	public PlainDelivery getCanteenDelivery(Date startTime, Date endTime){
		return configDao.getCanteenDelivery(startTime, endTime);
	}
	
	@Override
	public List<CanteenWeekDeliveryWaresItem> getCanteenDeliveryWaresItems(
			Long deliveryId) {
		return configDao.getCanteenDeliveryWaresItems(deliveryId);
	}
	
	@Override
	public PlainDelivery getCanteenDelivery(CanteenCriteria criteria) {
		//根据日期参数构造日期范围条件
		if(criteria.getStartDate() == null && criteria.getEndDate() == null) {
			if(TextUtils.hasText(criteria.getDate())) {
				Date date = dateFormat.parse(criteria.getDate());
				if(date != null) {
					criteria.setStartDate(dateFormat.getTheDayOfWeek(date, Calendar.MONDAY, 0, 0, 0, 0));
					criteria.setEndDate(dateFormat.incDay(criteria.getStartDate(), 7));
				}
			}else {
				criteria.setDate(dateFormat.formatDate(new Date()));
				criteria.setStartDate(dateFormat.getTheDayOfWeek(Calendar.MONDAY, 0));
				criteria.setEndDate(dateFormat.incDay(criteria.getStartDate(), 7));
			}
		}
		PlainDelivery delivery = getCanteenDelivery(criteria.getStartDate(), criteria.getEndDate());
		return delivery;
	}

	@Override
	public PlainDelivery getDelivery(Long deliveryId) {
		return configDao.getDelivery(deliveryId);
	}
	
	
	@Override
	public void updateCanteenDelivery(PlainDelivery delivery,
			List<PlainDeliveryWares> dWaresList) {
		Assert.notNull(delivery);
		PlainDelivery originDelivery = getDelivery(delivery.getId());
		if(originDelivery != null){
			originDelivery.setOpenTime(delivery.getOpenTime());
			originDelivery.setCloseTime(delivery.getCloseTime());
			originDelivery.setTimePoint(delivery.getTimePoint());
			originDelivery.setClaimEndTime(delivery.getClaimEndTime());
			if(!originDelivery.getLocationId().equals(delivery.getLocationId())){
				PlainLocation location = configDao.getDeliveryLocation(delivery.getLocationId());
				if(location != null){
					originDelivery.setLocationId(delivery.getLocationId());
					originDelivery.setLocationName(location.getName());
				}
			}
			originDelivery.setUpdateTime(new Date());
			configDao.update(originDelivery);
		}else{
			throw new RuntimeException("id[" + delivery.getId() + "]对应的配送不存在");
		}
		
		
		//从数据库中获得配送当前的所有商品配送
		List<PlainDeliveryWares> originDWaresList = configDao.getDeliveryWaresList(delivery.getId());
		Map<Long, PlainDeliveryWares> originDWaresMap = CollectionUtils.toMap(originDWaresList, dWares->dWares.getId());
		//比对原有的数据和新的请求中的数据
		Set<Long> delIdSet = new HashSet<Long>(originDWaresMap.keySet());
		Set<PlainDeliveryWares> updateSet = new HashSet<PlainDeliveryWares>();
		Set<PlainDeliveryWares> addSet = new HashSet<PlainDeliveryWares>();
		for (PlainDeliveryWares dWares : dWaresList) {
			if(dWares.getId() != null){
				delIdSet.remove(dWares.getId());
				PlainDeliveryWares originDWares = originDWaresMap.get(dWares.getId());
				if(originDWares != null){
					originDWares.setMaxCount(dWares.getMaxCount());
					updateSet.add(originDWares);
				}else{
					throw new RuntimeException("存在原本订单中没有的商品配送，id为[" + dWares.getId() + "]");
				}
			}else{
				dWares.setCurrentCount(0);
				dWares.setDeliveryId(delivery.getId());
				addSet.add(dWares);
			}
		}
		//删除
		if (!delIdSet.isEmpty()) {
			configDao.removeDeliveryWares(delIdSet);
		}
		//添加
		addSet.forEach(dWares->configDao.create(dWares));
		//更新
		updateSet.forEach(dWares->configDao.update(dWares));
	}
	
	@Override
	public void disableDeliveryWares(Long deliveryWaresId, boolean disable) {
		configDao.disableDeliveryWares(deliveryWaresId, disable);
	}
	
}
