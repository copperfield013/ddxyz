package cn.sowell.ddxyz.model.canteen.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenDeliveryWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekDeliveryCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryWaresListItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekDeliveryWaresItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public interface CanteenDeliveryDao {
	/**
	 * 获得所有配送地址
	 * @param string
	 * @return
	 */
	List<PlainLocation> getDeliveryLocations(String type, Boolean disabled);

	
	/**
	 * 查询配送商品的列表
	 * @param criteria
	 * @return
	 */
	List<CanteenDeliveryWaresListItem> queryDeliveryWaresList(
			CanteenDeliveryWaresListCriteria criteria);

	/**
	 * 获得该店铺的所有商品
	 * @param canteenMerchantId
	 * @param disabled 为true时，获得所有禁用的商品，为false时，获得所有可用的商品。传入null返回所有商品
	 * @return
	 */
	List<PlainWares> getWaresList(long canteenMerchantId, Boolean disabled);


	/**
	 * 持久化配送对象
	 * @param delivery
	 * @return 
	 */
	Long saveDelivery(PlainDelivery delivery);


	/**
	 * 持久化商品配送对象
	 * @param dWares
	 * @return 
	 */
	Long saveDeliveryWares(PlainDeliveryWares dWares);

	/**
	 * 根据配送地址的id获得配送地址
	 * @param locationId
	 * @return
	 */
	PlainLocation getDeliveryLocation(Long locationId);


	/**
	 * 
	 * @param criteria
	 * @return
	 */
	PlainDelivery getCanteenDelivery(CanteenWeekDeliveryCriteria criteria);


	List<CanteenWeekDeliveryWaresItem> getCanteenDeliveryWaresItems(
			Long deliveryId);

	/**
	 * 根据时间范围获得唯一的canteen配送对象
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	PlainDelivery getCanteenDelivery(Date startTime, Date endTime);

	
	PlainDelivery getDelivery(Long deliveryId);

	/**
	 * 更新
	 * @param originDelivery
	 */
	void update(Object obj);


	/**
	 * 获得配送的所有商品配送
	 * @param id
	 * @return
	 */
	List<PlainDeliveryWares> getDeliveryWaresList(Long delivery);


	/**
	 * 从数据库中移除对应配送
	 * @param delIdSet
	 */
	void removeDeliveryWares(Set<Long> delIdSet);


	void create(Object dWares);


	void disableDeliveryWares(Long deliveryWaresId, boolean disable);


	


}
