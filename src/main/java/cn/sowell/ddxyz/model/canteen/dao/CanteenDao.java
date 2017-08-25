package cn.sowell.ddxyz.model.canteen.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDeliveyWares;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenUserCacheInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenOrderInfoItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
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
	List<CanteenDeliveyWares> getCanteenDeliveryWares(Long deliveryId, Boolean disabled);

	PlainCanteenOrder getLastOrderOfUser(Long userId);
	
	/**
	 * 根据订单id获得所有产品
	 * @param orderId
	 * @return
	 */
	List<PlainProduct> getProducts(Long orderId);

	/**
	 * 获得持久化的canteen订单数据对象，不获取关联的基本订单对象
	 * @param orderId
	 * @return
	 */
	PlainCanteenOrder getCanteenOrder(Long orderId);

	/**
	 * 根据订单id获得基本订单对象
	 * @param orderId
	 * @return
	 */
	PlainOrder getOrder(Long orderId);

	/**
	 * 
	 * @param delList
	 */
	void deleteProducts(List<Long> delList);
	
	
	/**
	 * 添加商品配送的产品到指定的订单
	 * @param orderId
	 * @param dWaresId
	 * @param count
	 */
	void appendProduct(Long orderId, Long dWaresId, int count);

	/**
	 * 获得订单对应的所有的产品id，并将其按照其商品配送的id做整合
	 * @param orderId
	 * @return
	 */
	Map<Long, List<Long>> getDeliveryWaresProductIdsMap(Long orderId);
	
	/**
	 * 获得订单的客户信息
	 * @param orderId
	 * @return
	 */
	CanteenUserCacheInfo getOrderUserInfo(Long orderId);

	/**
	 * 更新订单基本数据，包括canteenOrder和orderBase
	 * @param cOrder
	 */
	void updateOrder(PlainCanteenOrder cOrder);
	/**
	 * 添加（或减少）商品配送当前的数量
	 * @param dWaresId
	 * @param count
	 */
	void addCurrentCount(long dWaresId, int addition);
	
	/**
	 * 设置订单的取消状态
	 * @param orderId 订单主键
	 * @param canStatusCanceled 不能为空的取消状态
	 */
	void setOrderCanceled(long orderId, String canStatusCanceled);

	/**
	 * 根据用户id和分页数据获得订单列表
	 * @param id
	 * @param pageInfo
	 * @return
	 */
	List<CanteenOrderInfoItem> getOrderPageList(long userId, CommonPageInfo pageInfo);
	
	/**
	 * 将所有产品设置状态为取消
	 * @param cancelProductIds
	 */
	void setProductCalceled(Set<Long> cancelProductIds);

}
