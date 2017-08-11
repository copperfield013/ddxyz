package cn.sowell.ddxyz.model.canteen.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenOrderUpdateItem;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenUserCacheInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenOrderInfoItem;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderParameter;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common2.core.OrderOperateException;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

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

	/**
	 * 取消订单<br/>
	 * 当当前订单已经被取消，或者执行操作的用户operateUser和创建订单的user不一致时，都会抛出异常
	 * @param operateUser 当前执行取消操作的用户
	 * @param orderId 取消的订单id
	 * @throws OrderOperateException 当取消失败时抛出异常
	 */
	void cancelOrder(WeiXinUser operateUser, Long orderId) throws OrderOperateException;
	
	
	/**
	 * 获取分页后的订单信息
	 * @param user
	 * @param pageInfo
	 * @return
	 */
	List<CanteenOrderInfoItem> getWaresPageList(UserIdentifier user, CommonPageInfo pageInfo);
	
	/**
	 * 获取订单中商品简略信息
	 * @param orderList
	 * @return
	 */
	Map<CanteenOrderInfoItem, List<CanteenOrderUpdateItem>> getCanteenOrderUpdateItemList(List<CanteenOrderInfoItem> orderList);
}
