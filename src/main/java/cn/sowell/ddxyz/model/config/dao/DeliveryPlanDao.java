package cn.sowell.ddxyz.model.config.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;

public interface DeliveryPlanDao {
	
	/**
	 * 持久化配送计划
	 * @param plan
	 */
	void savePlan(PlainDeliveryPlan plan);
	
	/**
	 * 根据查询条件获取配送计划的分页列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<PlainDeliveryPlan> getPlainDeliveryPlanPageList(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo);
	
	boolean changePlanDisabled(Long planId, Integer disabled);

}
