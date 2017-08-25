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
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common2.core.C2OrderResource;
import cn.sowell.ddxyz.model.common2.core.OrderOperateException;
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

	/**
	 * 取消订单<br/>
	 * 当当前订单已经被取消，或者执行操作的用户operateUser和创建订单的user不一致时，都会抛出异常
	 * @param operateUser 当前执行取消操作的用户
	 * @param orderId 取消的订单id
	 * @param toCancelStatus 更改后的取消狀態
	 * @throws OrderOperateException 当取消失败时抛出异常
	 * @throws OrderResourceApplyException 
	 */
	void cancelOrder(Long orderId, String toCancelStatus) throws OrderOperateException, OrderResourceApplyException;
	
	
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
	/**
	 * 判断该配送是否已经超过预定时间
	 * @param delivery
	 * @return
	 */
	boolean checkDeliveryOrderOvertime(PlainDelivery delivery, Date theTime);

	/**
	 * 根据已经存在的订单，重新检测订单内容恢复的资源是否足够。该方法不会修改订单及对应产品的状态，只会检测并修改资源的数量
	 * @param cOrder
	 * @return
	 * @throws OrderResourceApplyException 资源不足的情况下，抛出异常
	 */
	C2OrderResource reapplyOrderResource(PlainCanteenOrder cOrder)
			throws OrderResourceApplyException;

	/**
	 * 用于微信用户取消自己的订单
	 * @param orderId
	 * @param canStatusCanceled
	 * @throws OrderResourceApplyException 
	 * @throws OrderOperateException 
	 */
	void cancelUserOrder(Long orderId, String canStatusCanceled) throws OrderOperateException, OrderResourceApplyException;

}
