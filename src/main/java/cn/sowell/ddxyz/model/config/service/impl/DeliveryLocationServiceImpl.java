package cn.sowell.ddxyz.model.config.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.config.dao.DeliveryLocationDao;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryLocationCriteria;
import cn.sowell.ddxyz.model.config.service.DeliveryLocationService;

@Service
public class DeliveryLocationServiceImpl implements DeliveryLocationService {
	
	@Resource
	DeliveryLocationDao deliveryLocationDao;

	@Override
	public List<PlainLocation> getPlainLocationPageList(DeliveryLocationCriteria criteria, CommonPageInfo pageInfo) {
		List<PlainLocation> list = deliveryLocationDao.getPlainLocationPageList(criteria, pageInfo);
		return list;
	}

	@Override
	public PlainLocation getPlainLocationById(Long locationId) {
		PlainLocation location = deliveryLocationDao.getPlainLocaitionById(locationId);
		return location;
	}

	@Override
	public void addPlainLocation(PlainLocation location) {
		location.setCreateTime(new Date());
		deliveryLocationDao.savePlainLocation(location);
	}

	@Override
	public boolean checkLocationCode(String code) {
		return deliveryLocationDao.checkLocationCode(code);
	}

	@Override
	public void deletePlainLocation(Long locationId) {
		deliveryLocationDao.deletePlainLocation(locationId);
	}

}
