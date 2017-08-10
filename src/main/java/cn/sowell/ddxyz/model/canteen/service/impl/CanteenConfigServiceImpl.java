package cn.sowell.ddxyz.model.canteen.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.CDATA;
import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.canteen.dao.CanteenConfigDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenDeliveryWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekDeliveryCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryWaresListItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenConfigService;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Repository
public class CanteenConfigServiceImpl implements CanteenConfigService{
	
	@Resource
	CanteenConfigDao configDao;
	
	
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
	public PlainDelivery getCanteenDeliveryOfTheWeek(CanteenWeekDeliveryCriteria criteria) {
		return configDao.getCanteenDeliveryOfTheWeek(criteria);
		
	}
	
	
}
