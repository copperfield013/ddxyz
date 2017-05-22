package cn.sowell.ddxyz.model.config.service;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryInfoCriteria;

public interface DeliveryInfoService {
	
	List<PlainDelivery> getPlainDeliveryPageList(DeliveryInfoCriteria criteria, CommonPageInfo pageInfo);

}
