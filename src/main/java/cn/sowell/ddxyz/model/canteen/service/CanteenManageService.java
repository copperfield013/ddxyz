package cn.sowell.ddxyz.model.canteen.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenOrderUpdateItem;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;

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
	
}
