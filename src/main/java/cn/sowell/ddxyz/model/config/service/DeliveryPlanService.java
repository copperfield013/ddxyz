package cn.sowell.ddxyz.model.config.service;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;

public interface DeliveryPlanService {
	
	/**
	 * 添加一条配送计划到系统中
	 * @param plan
	 */
	void addPlan(PlainDeliveryPlan plan);
	
	List<PlainDeliveryPlan> getPlainDeliveryPlanPageList(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo);
	
	boolean changePlanDisabled(Long planId, Integer disabled);
}
