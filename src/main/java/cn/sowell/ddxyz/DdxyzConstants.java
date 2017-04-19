package cn.sowell.ddxyz;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.sowell.ddxyz.model.common.core.Order;

@SuppressWarnings("serial")
public interface DdxyzConstants {
	Integer VALUE_TRUE = 1;
	String COMMON_SPLITER = ";";
	Map<Integer, String> CUP_SIZE_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(2, "中杯");
			put(3, "大杯");
		}
	};
	
	Map<Integer, String> SWEETNESS_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(3, "3分甜");
			put(5, "5分甜");
			put(7, "7分甜");
		}
	};
	
	Map<Integer, String> HEAT_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(1, "冰");
			put(2, "常温");
			put(3, "热");
		}
	};
	
	Map<Integer, String> ORDER_STATUS_CNAME = new HashMap<Integer, String>(){
		{
			put(Order.STATUS_DEFAULT, "已创建");
			put(Order.STATUS_PAYED, "已支付");
			put(Order.STATUS_COMPLETED, "已完成");
			put(Order.STATUS_APPRAISED, "已评价");
		}
	};
	
	Map<String, String> ORDER_CAN_STATUS_CNAME = new HashMap<String, String>(){
		{
			put(Order.CAN_STATUS_CANCELED, "已取消");
			put(Order.CAN_STATUS_CLOSED, "已关闭");
			put(Order.CAN_STATUS_REFUNDED, "已退款");
		}
	};
	
}
