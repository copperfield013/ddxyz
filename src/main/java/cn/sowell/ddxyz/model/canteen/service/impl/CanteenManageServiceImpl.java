package cn.sowell.ddxyz.model.canteen.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.admin.controller.canteen.PlainCanteenOrderStat;
import cn.sowell.ddxyz.model.canteen.dao.CanteenManageDao;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenOrderUpdateItem;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenOrdersCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;
import cn.sowell.ddxyz.model.canteen.service.CanteenManageService;
import cn.sowell.ddxyz.model.canteen.service.CanteenService;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common2.core.OrderOperateException;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class CanteenManageServiceImpl implements CanteenManageService{

	@Resource
	CanteenManageDao manageDao;
	
	@Resource
	CanteenService canteenService;
	
	@Override
	public List<CanteenWeekTableItem> queryDeliveryTableItems(Long deliveryId,
			CanteenWeekTableCriteria criteria, CommonPageInfo pageInfo) {
		return manageDao.queryDeliveryTableItems(deliveryId, criteria, pageInfo);
	}
	
	@Override
	public List<CanteenDeliveryOrdersItem> queryDeliveryOrderItems(CanteenOrdersCriteria criteria,
			CommonPageInfo pageInfo) {
		return manageDao.queryDeliveryOrderItems(criteria, pageInfo);
	}
	
	@Override
	public void writeOrderToSheet(PlainDelivery delivery, List<CanteenDeliveryOrdersItem> items, Sheet sheet) {
		int rowNum = 0;
		writeOrderHeader(sheet.createRow(rowNum++));
		for (CanteenDeliveryOrdersItem item : items) {
			writeOrderRow(delivery, item, sheet.createRow(rowNum++));
		}
	}



	private void writeOrderHeader(Row row) {
		row.createCell(0).setCellValue("序号");
		row.createCell(1).setCellValue("订单号");
		row.createCell(2).setCellValue("领取人");
		row.createCell(3).setCellValue("部门");
		row.createCell(4).setCellValue("手机号");
		row.createCell(5).setCellValue("订单明细");
		row.createCell(6).setCellValue("应收款");
		row.createCell(7).setCellValue("备注");
	}
	
	private void writeOrderRow(PlainDelivery delivery, CanteenDeliveryOrdersItem item, Row row) {
		row.createCell(0).setCellValue(row.getRowNum());
		row.createCell(1).setCellValue(item.getOrderCode());
		row.createCell(2).setCellValue(item.getReceiverName());
		row.createCell(3).setCellValue(item.getDepart());
		row.createCell(4).setCellValue(item.getReceiverContact());
		ArrayList<String> waresItems = CollectionUtils.toList(item.getWaresItemsList(), wItem->wItem.getWaresName() + "×" + wItem.getCount());
		String detail = CollectionUtils.toChain(waresItems, "\r\n");
		row.createCell(5, Cell.CELL_TYPE_STRING).setCellValue(detail);
		row.createCell(6).setCellValue(TextUtils.formatFloat(item.getTotalPrice() / 100, "0.00"));
		row.createCell(7).setCellValue(item.getComment());
	}
	
	
	
	public static void main(String[] args) throws IOException {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("序号");
		File file = new File("d://aa.xlsx");
		file.createNewFile();
		OutputStream stream = new FileOutputStream(file);
		wb.write(stream);
		stream.close();
	}
	
	@Override
	public JSONObject toOrderTagObject(PlainCanteenOrder cOrder, List<CanteenOrderUpdateItem> orderItems) {
		JSONObject jo = new JSONObject();
		PlainOrder order = cOrder.getpOrder();
		jo.put("orderCode", order.getOrderCode());
		jo.put("receiverName", order.getReceiverName());
		jo.put("depart", cOrder.getDepart());
		jo.put("receiverContact", order.getReceiverContact());
		jo.put("totalPrice", TextUtils.formatFloat(order.getTotalPrice() / 100, "0.00"));
		JSONArray jArray = new JSONArray();
		orderItems.forEach(item->{
			JSONObject jItem = new JSONObject();
			jItem.put("waresName", item.getWaresName());
			jItem.put("count", item.getCount());
			jArray.add(jItem);
		});
		jo.put("orderItems", jArray);
		return jo;
	}

	@Override
	public JSONObject toOrderTagObject(List<CanteenDeliveryOrdersItem> items) {
		JSONObject jo = new JSONObject();
		JSONArray jArr = new JSONArray();
		items.forEach(item->{
			JSONObject order = new JSONObject();
			order.put("orderCode", item.getOrderCode());
			order.put("receiverName", item.getReceiverName());
			order.put("depart", item.getDepart());
			order.put("receiverContact", item.getReceiverContact());
			order.put("totalPrice", TextUtils.formatFloat(item.getTotalPrice() / 100, "0.00"));
			JSONArray orderItems = new JSONArray();
			item.getWaresItemsList().forEach(wItem->{
				JSONObject waresItem = new JSONObject();
				waresItem.put("waresName", wItem.getWaresName());
				waresItem.put("count", wItem.getCount());
				orderItems.add(waresItem);
			});
			order.put("orderItems", orderItems);
			jArr.add(order);
		});
		jo.put("orders", jArr);
		return jo;
	}
	
	@Override
	public Integer amountDelivery(long deliveryId) {
		return manageDao.amountDelivery(deliveryId);
	}
	
	@Override
	public void completeOrders(List<Long> orderIds) {
		manageDao.setOrderStatus(orderIds, Order.STATUS_COMPLETED);
		manageDao.setOrderProductsStatus(orderIds, Product.STATUS_COMPLETED);
	}
	
	@Override
	public void closeOrder(Long orderId) throws OrderOperateException, OrderResourceApplyException {
		canteenService.cancelOrder(orderId, Order.CAN_STATUS_CLOSED);
	}
	
	@Override
	public void setOrderMiss(Long orderId) {
		manageDao.setOrderCancelStatus(orderId, Order.CAN_STATUS_MISS);
		manageDao.setOrderProductsCancelStatus(orderId, Product.CAN_STATUS_ORDER_MISS);
	}
	
	
	@Override
	public void cancelComplete(Long orderId) {
		manageDao.setOrderStatus(Arrays.asList(orderId), Order.STATUS_DEFAULT);
		manageDao.setOrderProductsStatus(Arrays.asList(orderId), Product.STATUS_DEFAULT);
	}
	
	
	@Override
	public void removeCanceled(Long orderId) throws OrderOperateException, OrderResourceApplyException {
		PlainCanteenOrder cOrder = canteenService.getCanteenOrder(orderId);
		if(cOrder != null){
			PlainOrder pOrder = cOrder.getpOrder();
			String cancelStatus = pOrder.getCanceledStatus();
			if(cancelStatus != null){
				switch (cancelStatus) {
				case Order.CAN_STATUS_CLOSED:
					//如果原来的状态是关闭的，那么移除关闭状态，还需要重新占用资源
					canteenService.reapplyOrderResource(cOrder);
					manageDao.setOrderCancelStatus(orderId, null);
					manageDao.setOrderProductsCancelStatus(orderId, null);
					break;
				case Order.CAN_STATUS_MISS:
					//如果原来的状态是未领取的，那么只需要移除订单和产品的取消状态，不需要重新占用资源
					manageDao.setOrderCancelStatus(orderId, null);
					manageDao.setOrderProductsCancelStatus(orderId, null);
					break;
				default:
					throw new OrderOperateException("不支持移除的取消状态[" + cancelStatus + "]");
				}
				
			}else{
				throw new OrderOperateException("订单[" + orderId + "]无法移除取消状态，因为其不是取消状态");
			}
		}
	}

	@Override
	public PlainCanteenOrderStat statDelivery(Long deliveryId) {
		PlainCanteenOrderStat stat = new PlainCanteenOrderStat();
		stat.setTotalCount(manageDao.getTotalOrderCount(deliveryId));
		stat.setEffective(manageDao.getEffectiveOrderCount(deliveryId));
		stat.setCompleted(manageDao.getCompletedOrderCount(deliveryId));
		stat.setMissed(manageDao.getMissedOrderCount(deliveryId));
		stat.setCanceled(manageDao.getCanceledOrderCount(deliveryId));
		stat.setClosed(manageDao.getClosedOrderCount(deliveryId));
		return stat;
	}
	
	
	@Override
	public int completeOrders(Long deliveryId) {
		return manageDao.completeOrders(deliveryId);
	}
	
}
