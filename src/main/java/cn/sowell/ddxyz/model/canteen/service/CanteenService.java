package cn.sowell.ddxyz.model.canteen.service;

import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.canteen.pojo.CanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenOrderUpdateItem;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenUserCacheInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderParameter;
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
	
	/**
	 * 
	 * @param uParam
	 * @return
	 * @throws OrderResourceApplyException 
	 */
	PlainCanteenOrder updateOrder(CanteenOrderParameter uParam) throws OrderResourceApplyException;

	/**
	 * 获得该礼拜的配送，如果该礼拜添加了多个配送，那么只取第一个
	 * @return
	 */
	CanteenDelivery getDeliveryOfThisWeek();

	CanteenUserCacheInfo getUserCacheInfo(Long userId);

	
	/**
	 * 获得该订单对应的明细表
	 * @param orderId
	 * @return
	 */
	List<CanteenOrderUpdateItem> getOrderItems(Long orderId);
	
	/**
	 * 根据订单id获得对应的canteen订单对象
	 * @param orderId
	 * @return
	 */
	PlainCanteenOrder getCanteenOrder(Long orderId);

	/**
	 * 根据配送id获得对应的canteen配送对象
	 * @param orderId
	 * @return
	 */
	CanteenDelivery getCanteenDelivery(Long deliveryId);
	
	/**
	 * 获得订单的用户信息
	 * @param orderId
	 * @return
	 */
	CanteenUserCacheInfo getOrderUserInfo(Long orderId);
}
