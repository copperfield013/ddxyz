package cn.sowell.ddxyz.model.canteen.service;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;

public interface CanteenManageService {
	
	/**
	 * 根据条件查找某个配送下的订单周表数据
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<CanteenWeekTableItem> queryDeliveryTableItems(
			Long deliveryId, 
			CanteenWeekTableCriteria criteria, CommonPageInfo pageInfo);

	/**
	 * 查询订单列表
	 * @param deliveryId
	 * @param pageInfo
	 * @return
	 */
	List<CanteenDeliveryOrdersItem> queryDeliveryOrderItems(long id,
			CommonPageInfo pageInfo);
	
}
