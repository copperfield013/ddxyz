package cn.sowell.ddxyz.model.canteen.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenOrdersCriteria;
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
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<CanteenDeliveryOrdersItem> queryDeliveryOrderItems(CanteenOrdersCriteria criteria,
			CommonPageInfo pageInfo);

	/**
	 * 计算该配送下所有没有取消的订单的总金额
	 * @param deliveryId
	 * @return 返回计算后的总金额，单位为分
	 */
	Integer amountDelivery(long deliveryId);

	/**
	 * 将所有没有被取消的订单的状态更改为已完成
	 * @param orderIds
	 */
	void setOrderStatus(List<Long> orderIds, int orderStatus);

	/**
	 * 将所有没有被取消的产品的状态修改为已完成
	 * @param orderIds
	 */
	void setOrderProductsStatus(List<Long> orderIds, int productStatus);

	/**
	 * 移除订单的取消状态
	 * @param orderId
	 * @param cancelStatus 
	 */
	void setOrderCancelStatus(Long orderId, String cancelStatus);

	/**
	 * 移除订单的所有产品的取消状态
	 * @param orderId
	 * @param cancelStatus 
	 */
	void setOrderProductsCancelStatus(Long orderId, String cancelStatus);

	
}
