package cn.sowell.ddxyz.model.canteen.service;

import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderParameter;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderUpdateParam;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public interface CanteenService {
	
	
	void saveWares(PlainWares wares);
	
	/**
	 * 保存配送计划
	 * @param plan
	 * @param planWares
	 */
	void savePlan(PlainDeliveryPlan plan, List<PlainDeliveryPlanWares> planWares);
	
	/**
	 * 生成某天的所有配送
	 * @param date
	 */
	void generateTheDayDeliveries(Date date);
	
	/**
	 * 根据订单参数构造并且持久化订单对象
	 * 构造对象需要向对应的配送有足够余量资源可以构造订单
	 * @return
	 * @throws OrderResourceApplyException 如果没有
	 */
	PlainCanteenOrder createOrder(CanteenOrderParameter coParam) throws OrderResourceApplyException;
	
	
	PlainCanteenOrder updateOrder(CanteenOrderUpdateParam uParam);
	
	
}