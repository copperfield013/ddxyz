package cn.sowell.ddxyz.model.config.service;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryLocationCriteria;

public interface DeliveryLocationService {
	
	List<PlainLocation> getPlainLocationPageList(DeliveryLocationCriteria criteria, CommonPageInfo pageInfo);
	
	PlainLocation getPlainLocationById(Long locationId);
	
	void addPlainLocation(PlainLocation location);
	
	boolean checkLocationCode(String code);

}
