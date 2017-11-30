package cn.sowell.ddxyz.model.config.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryLocationCriteria;

public interface DeliveryLocationDao {
	
	List<PlainLocation> getPlainLocationPageList(DeliveryLocationCriteria criteria, PageInfo pageInfo);
	
	PlainLocation getPlainLocaitionById(Long locationId);
	
	void savePlainLocation(PlainLocation location);
	
	boolean checkLocationCode(String code);
	
	void deletePlainLocation(Long locationId);

}
