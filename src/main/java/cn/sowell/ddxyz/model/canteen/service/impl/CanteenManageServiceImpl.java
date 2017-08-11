package cn.sowell.ddxyz.model.canteen.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.dao.CanteenManageDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenManageService;

@Service
public class CanteenManageServiceImpl implements CanteenManageService{

	@Resource
	CanteenManageDao manageDao;
	
	@Override
	public List<CanteenWeekTableItem> queryDeliveryTableItems(Long deliveryId,
			CanteenWeekTableCriteria criteria, CommonPageInfo pageInfo) {
		return manageDao.queryDeliveryTableItems(deliveryId, criteria, pageInfo);
	}
	
	@Override
	public List<CanteenDeliveryOrdersItem> queryDeliveryOrderItems(long deliveryId,
			CommonPageInfo pageInfo) {
		return manageDao.queryDeliveryOrderItems(deliveryId, pageInfo);
	}
	
}
