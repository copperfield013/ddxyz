package cn.sowell.ddxyz.model.canteen.service;

import java.util.List;

import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenDeliveryWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryWaresListItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public interface CanteenConfigService {

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
	 * 创建商品
	 * @param wares
	 */
	void saveWares(PlainWares wares);

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

}
