package cn.sowell.ddxyz.model.canteen.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;


public interface CanteenManageDao {

	/**
	 * 
	 * @param deliveryId
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<CanteenWeekTableItem> queryDeliveryTableItems(Long deliveryId,
			CanteenWeekTableCriteria criteria, CommonPageInfo pageInfo);

	/**
	 * 查询配送的所有订单
	 * @param deliveryId
	 * @param pageInfo
	 * @return
	 */
	List<CanteenDeliveryOrdersItem> queryDeliveryOrderItems(long deliveryId,
			CommonPageInfo pageInfo);

}
