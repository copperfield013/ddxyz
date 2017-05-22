package cn.sowell.ddxyz.model.config.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.config.dao.DeliveryInfoDao;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryInfoCriteria;
import cn.sowell.ddxyz.model.config.service.DeliveryInfoService;

@Service
public class DeliveryPlanInfoServiceImpl implements DeliveryInfoService {

	@Resource
	DeliveryInfoDao deliveryInfoDao;
	
	@Override
	public List<PlainDelivery> getPlainDeliveryPageList(DeliveryInfoCriteria criteria, CommonPageInfo pageInfo) {
		return deliveryInfoDao.getPlainDeilveryPageList(criteria, pageInfo);
		
	}

}
