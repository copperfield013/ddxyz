package cn.sowell.ddxyz.model.canteen.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.sowell.ddxyz.model.canteen.pojo.CanteenDeliveyWares;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public interface CanteenDao {

	void updateCurrentCount(Long deliveryWaresId, int updateCount);

	PlainDeliveryWares getDeliveryWare(Long deliveryWaresId);
	
	List<PlainDeliveryWares> getDeliveryWaresList(Long deliveryId);

	PlainDelivery getDelivery(Long deliveryId);

	PlainWares getWares(Long waresId);

	void createOrder(PlainCanteenOrder cOrder, List<PlainProduct> products);

	void savePlan(PlainDeliveryPlan plan, List<PlainDeliveryPlanWares> planWares);
	
	/**
	 * 保存配送对象，以及配送所属的商品信息（配送与商品的关联将在方法内构建）
	 * @param dWaresMap
	 */
	void saveDelivery(Map<PlainDelivery, List<PlainDeliveryWares>> dWaresMap);
	/**
	 * 获得当天的所有配送计划
	 * @return
	 */
	List<PlainDeliveryPlan> getThedayNullWaresIdPlans(Date theDay);
	/**
	 * 根据配送计划获得对应的所有配送计划商品
	 * @param plans
	 * @return
	 */
	Map<PlainDeliveryPlan, List<PlainDeliveryPlanWares>> getPlanWaresMap(
			List<PlainDeliveryPlan> plans);

	void saveWares(PlainWares wares);

	/**
	 * 获得日期当周的配送对象
	 * @param date
	 * @return
	 */
	PlainDelivery getDeliveryOfThisWeek(Date date);
	/**
	 * 根据配送对象获得其所属的所有配送商品信息
	 * @param deliveryId
	 * @return
	 */
	List<CanteenDeliveyWares> getCanteenDeliveryWares(Long deliveryId);

	PlainCanteenOrder getLastOrderOfUser(Long userId);


}
