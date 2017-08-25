package cn.sowell.ddxyz.model.canteen.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenOrderUpdateItem;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenOrdersCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common2.core.OrderOperateException;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;

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
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<CanteenDeliveryOrdersItem> queryDeliveryOrderItems(CanteenOrdersCriteria criteria,
			CommonPageInfo pageInfo);
	/**
	 * 将配送订单的数据写到工作表内
	 * @param delivery
	 * @param items
	 * @param sheet
	 */
	void writeOrderToSheet(PlainDelivery delivery, List<CanteenDeliveryOrdersItem> items, Sheet sheet);

	/**
	 * 将订单信息封装成标签所需要的数据对象
	 * @param order
	 * @param orderItems
	 * @return
	 */
	JSONObject toOrderTagObject(PlainCanteenOrder order, List<CanteenOrderUpdateItem> orderItems);
	/**
	 * 将订单数据封装成标签所需要的数据对象
	 * @param items
	 * @return
	 */
	JSONObject toOrderTagObject(List<CanteenDeliveryOrdersItem> items);

	/**
	 * 计算该配送下所有未取消的订单的总金额
	 * @param id
	 * @return
	 */
	Integer amountDelivery(long id);

	/**
	 * 将订单中所有没有被取消的订单设置为完成
	 * @param orderIds
	 */
	void completeOrders(List<Long> orderIds);

	
	/**
	 * 关闭对应的订单，同时回收订单的所有资源
	 * @param orderId
	 * @throws OrderResourceApplyException 
	 * @throws OrderOperateException 
	 */
	void closeOrder(Long orderId) throws OrderOperateException, OrderResourceApplyException;
	
	/**
	 * 取消订单的完成状态，将订单的状态修改为默认状态。同时修改订单下所有产品的状态
	 * @param orderId
	 */
	void cancelComplete(Long orderId);

	/**
	 * 移除订单的取消状态。在必要的情况下，还需要重新占用资源
	 * @param orderId
	 * @throws OrderOperateException 
	 * @throws OrderResourceApplyException 
	 */
	void removeCanceled(Long orderId) throws OrderOperateException, OrderResourceApplyException;

	/**
	 * 设置订单的取消状态为未领取
	 * @param orderId
	 */
	void setOrderMiss(Long orderId);
}
