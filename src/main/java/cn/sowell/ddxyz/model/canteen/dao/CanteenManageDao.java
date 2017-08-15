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

	/**
	 * 计算该配送下所有没有取消的订单的总金额
	 * @param deliveryId
	 * @return 返回计算后的总金额，单位为分
	 */
	Integer amountDelivery(long deliveryId);

}
