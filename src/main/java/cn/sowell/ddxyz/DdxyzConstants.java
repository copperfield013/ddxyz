package cn.sowell.ddxyz;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.Product;

@SuppressWarnings("serial")
public interface DdxyzConstants {
	final Integer VALUE_TRUE = 1;
	final String COMMON_SPLITER = ";";
	
	final long MERCHANT_ID = 1;
	final long WARES_ID = 1;
	
	
	final long CANTEEN_MERCHANT_ID = 2;
	
	final String ROLE_CANTEEN = "ROLE_CANTEEN";
	
	//新订单信息类型，用于在用户付款成功后，系统给商户客服发送微信消息
	final String MSGTYPE_NEWORDER = "new_order";
	
	final TreeSet<Integer> TIMEPOINT_SET = new TreeSet<Integer>(){
		{
			for(int i = 9; i <= 21; i++){
				add(i);
			}
		}
	};
	
	final Map<Integer, String> CUP_SIZE_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(2, "中杯");
			put(3, "大杯");
		}
	};
	
	final Map<Integer, String> SWEETNESS_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(3, "3分甜");
			put(5, "5分甜");
			put(7, "7分甜");
		}
	};
	
	final Map<Integer, String> HEAT_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(1, "冰");
			put(2, "常温");
			put(3, "热");
		}
	};
	/**
	 * 订单状态
	 */
	final Map<Integer, String> ORDER_STATUS_CNAME = new HashMap<Integer, String>(){
		{
			put(Order.STATUS_DEFAULT, "已创建");
			put(Order.STATUS_PAYED, "已支付");
			put(Order.STATUS_COMPLETED, "已完成");
			put(Order.STATUS_APPRAISED, "已评价");
		}
	};
	/**
	 * 订单取消状态
	 */
	final Map<String, String> ORDER_CAN_STATUS_CNAME = new HashMap<String, String>(){
		{
			put(Order.CAN_STATUS_CANCELED, "已取消");
			put(Order.CAN_STATUS_CLOSED, "已关闭");
			put(Order.CAN_STATUS_REFUNDED, "已退款");
		}
	};
	/**
	 * 产品状态
	 */
	final Map<Integer, String> PRODUCT_STATUS_CNAME = new HashMap<Integer, String>(){
		{
			put(Product.STATUS_DEFAULT, "默认");
			put(Product.STATUS_ORDERED, "已付款");
			put(Product.STATUS_MAKING, "正在制作");
			put(Product.STATUS_COMPLETED, "制作完成");
			put(Product.STATUS_PACKED, "已装箱");
			put(Product.STATUS_DELIVERING, "派送中");
			put(Product.STATUS_DELIVERIED, "已送达");
			put(Product.STATUS_CONFIRMED, "确认收货");
		}
	};
	/**
	 * 产品取消状态
	 */
	final Map<String, String> PRODUCT_CAN_STATUS_CNAME = new HashMap<String, String>(){
		{
			put(Product.CAN_STATUS_ABANDON, "已关闭");
			put(Product.CAN_STATUS_ORDER_CANCEL, "已取消");
			put(Product.CAN_STATUS_ORDER_REFUND, "已退款");
		}
	};
	final String ROLE_WXUSER = "ROLE_WXUSER";
	final String PREVIEW_WARES_KEY_PREFIX = "wares_preview_key";
	
	
}
