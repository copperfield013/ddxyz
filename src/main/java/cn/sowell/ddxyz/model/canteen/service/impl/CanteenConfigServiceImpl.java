package cn.sowell.ddxyz.model.canteen.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.CDATA;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.canteen.dao.CanteenConfigDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenDeliveryWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryWaresListItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekDeliveryWaresItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenConfigService;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Repository
public class CanteenConfigServiceImpl implements CanteenConfigService{
	
	@Resource
	CanteenConfigDao configDao;
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
	public void saveWares(PlainWares wares) {
		wares.setMerchantId(DdxyzConstants.CANTEEN_MERCHANT_ID);
		wares.setCreateTime(new Date());
		configDao.saveWares(wares);
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
	
}
