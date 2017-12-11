package cn.sowell.ddxyz.model.merchant.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;

public interface DeliveryDao {

	/**
	 * 获得商户的在该天的所有配送
	 * @param merchantId
	 * @return
	 */
	List<PlainDelivery> getAllDelivery(long merchantId, Date date);

	/**
	 * 查询所有与商户关联的配送点
	 * @param merchantId
	 * @return
	 */
	Set<PlainLocation> getAllLocation(long merchantId);
	
	/**
	 * 持久化配送计划
	 * @param plan
	 */
	void savePlan(PlainDeliveryPlan plan);
	
	/**
	 * 根据配送id获得配送对象
	 * @param deliveryId
	 * @return
	 */
	PlainDelivery getPlainDelivery(long deliveryId);
	
	/**
	 * 根据查询条件获取配送计划的分页列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<PlainDeliveryPlan> getPlainDeliveryPlanPageList(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo);
	
	boolean changePlanDisabled(Long planId, Integer disabled);

	/**
	 * 
	 * @param set
	 * @return
	 */
	Map<Long, PlainKanteenDelivery> getDeliveryMap(Set<Long> deliveryIds);
	

}
