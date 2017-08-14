package cn.sowell.ddxyz.model.canteen.service;

import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenDeliveryWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryWaresListItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekDeliveryWaresItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public interface CanteenDeliveryService {

	/**
	 * 获得餐厅的所有配送地址
	 * @return
	 */
	List<PlainLocation> getCanteenDeliveryLocations();

	
	/**
	 * 
	 * @param criteria
	 * @return
	 */
	List<CanteenDeliveryWaresListItem> getDeliveryWaresList(
			CanteenDeliveryWaresListCriteria criteria);


	/**
	 * 获得门店的所有商品
	 * @param canteenMerchantId
	 * @param disabled
	 * @return
	 */
	List<PlainWares> getWaresList(long canteenMerchantId, Boolean object);

	/**
	 * 
	 * @param delivery
	 * @param dWaresList
	 */
	void saveCanteenDelivery(PlainDelivery delivery,
			List<PlainDeliveryWares> dWaresList);

	
	/**
	 * 根据配送id获得该配送的所有商品配送信息
	 * @param waresId
	 * @return
	 */
	List<CanteenWeekDeliveryWaresItem> getCanteenDeliveryWaresItems(
			Long deliveryId);

	/**
	 * 根据时间范围获得唯一的配送对象
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	PlainDelivery getCanteenDelivery(Date startTime, Date endTime);
	
	/**
	 * 根据条件对象获得该时间范围内的第一条配送
	 * @param criteria
	 * @return
	 */
	PlainDelivery getCanteenDelivery(CanteenCriteria criteria);

	/**
	 * 根据配送id获得配送对象
	 * @param deliveryId
	 * @return
	 */
	PlainDelivery getDelivery(Long deliveryId);


	/**
	 * 修改并持久化一个配送
	 * @param delivery
	 * @param dWaresList
	 */
	void updateCanteenDelivery(PlainDelivery delivery,
			List<PlainDeliveryWares> dWaresList);

	/**
	 * 禁用（启用）某个商品配送
	 * @param deliveryWaresId
	 * @param disable
	 */
	void disableDeliveryWares(Long deliveryWaresId, boolean disable);

}
