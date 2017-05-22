package cn.sowell.ddxyz.model.config.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryInfoCriteria;

public interface DeliveryInfoDao {
	
	List<PlainDelivery> getPlainDeilveryPageList(DeliveryInfoCriteria criteria, CommonPageInfo pageInfo);

}
