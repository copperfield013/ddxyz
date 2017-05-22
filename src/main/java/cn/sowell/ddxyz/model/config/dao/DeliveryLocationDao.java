package cn.sowell.ddxyz.model.config.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryLocationCriteria;

public interface DeliveryLocationDao {
	
	List<PlainLocation> getPlainLocationPageList(DeliveryLocationCriteria criteria, CommonPageInfo pageInfo);
	
	PlainLocation getPlainLocaitionById(Long locationId);
	
	void savePlainLocation(PlainLocation location);
	
	boolean checkLocationCode(String code);

}
