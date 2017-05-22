package cn.sowell.ddxyz.model.config.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;
import cn.sowell.ddxyz.model.config.dao.DeliveryPlanDao;
import cn.sowell.ddxyz.model.config.service.DeliveryPlanService;

@Service("deliveryPlanServiceImpl")
public class DeliveryPlanServiceImpl implements DeliveryPlanService {
	
	@Resource
	DeliveryPlanDao deliveryPlanDao;

	@Override
	public void addPlan(PlainDeliveryPlan plan) {
		plan.setCreateTime(new Date());
		deliveryPlanDao.savePlan(plan);

	}

	@Override
	public List<PlainDeliveryPlan> getPlainDeliveryPlanPageList(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo) {
		return deliveryPlanDao.getPlainDeliveryPlanPageList(criteria, pageInfo);
	}

	@Override
	public boolean changePlanDisabled(Long planId, Integer disabled) {
		if(disabled == DdxyzConstants.VALUE_TRUE){
			return deliveryPlanDao.changePlanDisabled(planId, disabled);
		}else if(disabled == null){
			return deliveryPlanDao.changePlanDisabled(planId, null);
		}
		return false;
	}

}
