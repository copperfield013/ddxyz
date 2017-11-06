package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenTrolleyWares;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenDistributionMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenOrderCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyWares;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenWaresOptionGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenCancelOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchantAnnounce;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenReceiver;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenSection;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;

public interface KanteenDao {
	/**
	 * 从数据库获得公告信息
	 * @param annouceKey
	 * @return
	 */
	List<PlainKanteenMerchantAnnounce> getOrderedAnnounces(String annouceKey);

	/**
	 * 找到商家的可预订时间在某个时间范围内的唯一的配销（如果有多个，则选择创建时间最靠前的那个）
	 * @param merchantId 商家id
	 * @param range 时间范围
	 * @return 
	 */
	PlainKanteenDistribution getDistribution(Long merchantId, Date[] range);

	/**
	 * 根据pojo的类型和id获得pojo对象
	 * @param <T>
	 * @param distributionId
	 * @param clazz
	 * @return
	 */
	<T> T get(Long id, Class<T> clazz);

	/**
	 * 根据菜单的id获得菜单下的所有商品组
	 * @param menuId
	 * @return
	 */
	List<PlainKanteenWaresGroup> getMenuWaresGroups(Long menuId);

	/**
	 * 根据配销id获得对应菜单的所有商品（要求在t_distribution_wares表中存在）
	 * @param distributionId
	 * @return
	 */
	List<KanteenDistributionMenuItem> getMenuItems(Long distributionId);

	/**
	 * 查找所有商品id中有选项的选项组
	 * @param waresIds
	 * @return
	 */
	Map<Long, List<KanteenWaresOptionGroup>> getMenuWaresOptionGroupsMap(Set<Long> waresIds);
	
	/**
	 * 根据用户id和配销的id获得购物车数据对象
	 * @param userId
	 * @param distributionId
	 * @return
	 */
	PlainKanteenTrolley getTrolley(Long userId, Long distributionId);
	
	
	/**
	 * 根据购物车id获得购物车内的所有商品数据
	 * @param trolleyId
	 * @return
	 */
	List<KanteenTrolleyWares> getTrolleyWares(Long trolleyId);
	
	/**
	 * 根据配销商品的id集合，获得对应的配销商品信息列表
	 * @param distributionWaresIds
	 * @return
	 */
	List<PlainKanteenDistributionWares> getDistributionWares(Set<Long> distributionWaresIds);

	/**
	 * 
	 * @param distributionId
	 * @param theTime 
	 * @return
	 */
	List<PlainKanteenDelivery> getEnabledDeliveries(Long distributionId, Date theTime);
	
	/**
	 * 在数据库内插入一行并返回
	 * @param class1
	 */
	void create(Class<?> pojoClass);

	/**
	 * 
	 * @param pojo
	 * @return 
	 */
	Long create(Object pojo);

	/**
	 * 根据购物车的id获得购物车内所有商品的映射，key为商品的distributionWaresId，value是商品数量
	 * @param trolleyId 购物车的id
	 * @return
	 */
	Map<Long, PlainKanteenTrolleyWares> getTrolleyWaresMap(long trolleyId);

	/**
	 * 移除购物车下的多个商品
	 * @param trolleyId 购物车id
	 * @param toRemove 包含多个distributionWaresId
	 */
	void removeTrolleyWares(Set<Long> toRemove);

	/**
	 * 更新多个购物车商品的数量
	 * @param trolleryId
	 * @param toUpdate
	 */
	void updateTrolleyWares(Map<Long, Integer> toUpdate);

	/**
	 * 获得用户最新的收件人信息
	 * @param userId
	 * @return
	 */
	PlainKanteenReceiver getLastReceiver(Long userId);

	/**
	 * 
	 * @param id
	 */
	void disableTrolley(Long id);

	/**
	 * 更新当前的量。当修改成功时，返回true
	 * @param distributionWaresId
	 * @param addition
	 * @return
	 */
	boolean updateDistributionWareCurrentCount(Long distributionWaresId,
			Integer addition);

	/**
	 * 更新订单数据
	 * @param pOrder
	 */
	void updateOrderWxPayFields(PlainKanteenOrder pOrder);

	/**
	 * 更新订单状态为已支付
	 * @param order
	 */
	void updateOrderAsPaied(PlainKanteenOrder order);

	/**
	 * 
	 * @param outTradeNo
	 * @return
	 */
	PlainKanteenOrder getOrderByOutTradeNo(String outTradeNo);

	/**
	 * 根据配销id获得对应的商家信息
	 * @param distributionId
	 * @return
	 */
	PlainKanteenMerchant getMerchantByDistributionId(Long distributionId);

	/**
	 * 根据条件分页查询订单
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<PlainKanteenOrder> query(KanteenOrderCriteria criteria,
			PageInfo pageInfo);

	/**
	 * 
	 * @param list
	 * @return
	 */
	List<PlainKanteenSection> getSections(Set<Long> orderIdSet);
	/**
	 * 根据配送id获得所有配送对象
	 * @param deliveryIds
	 * @return
	 */
	List<PlainKanteenDelivery> getDeliveries(Set<Long> deliveryIds);

	/**
	 * 修改订单的删除状态
	 * @param orderId
	 */
	void updateOrderAsDeleted(Long orderId, boolean toDel);

	/**
	 * 修改订单状态为已退款
	 * @param orderId
	 * @param refundFee
	 */
	void updateOrderAsRefunded(Long orderId, Integer refundFee);
	/**
	 * 
	 * @param orderId
	 */
	void cancelOrder(Long orderId);

	/**
	 * 根据订单找到对应的取消选项
	 * @param plainOrder
	 * @return
	 */
	PlainKanteenCancelOption getOrderCancelOption(PlainKanteenOrder plainOrder);

	

}
