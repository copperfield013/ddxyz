package cn.sowell.ddxyz.model.kanteen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;

@SuppressWarnings("serial")
public interface KanteenConstants {

	Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>(){
		{
			put(PlainKanteenOrder.STATUS_DEFAULT, "未支付");
			put(PlainKanteenOrder.STATUS_PAIED, "已支付");
			put(PlainKanteenOrder.STATUS_CONFIRMED, "已确认");
			put(PlainKanteenOrder.STATUS_COMPLETED, "已完成");
		}
	};
	Map<String, String> CANCELED_STATUS_MAP = new HashMap<String, String>(){
		{
			put(PlainKanteenOrder.CANSTATUS_CANCELED, "已取消");
			put(PlainKanteenOrder.CANSTATUS_CLOSED, "已关闭");
			put(PlainKanteenOrder.CANSTATUS_MISSED, "未领取");
			put(PlainKanteenOrder.CANSTATUS_PAYEXPIRED, "支付过期");
			put(PlainKanteenOrder.CANSTATUS_REFUNDED, "已退款");
		}
	};
	/**
	 * 用于映射配送的支持支付方式和订单的支付方式
	 * map的key为配送的可支持配送标识
	 * value为配送可支持的订单支付方式
	 */
	Map<Integer, Set<String>> PAYWAY_DELIVERY_ORDER_MAP = new HashMap<Integer, Set<String>>(){
		{
			put(PlainKanteenDelivery.PAYWAY_WXPAY, new HashSet<String>(){
				{
					add(PlainKanteenOrder.PAYWAY_WXPAY);
				}
			});
			put(PlainKanteenDelivery.PAYWAY_SPOT, new HashSet<String>(){
				{
					add(PlainKanteenOrder.PAYWAY_SPOT);
				}
			});
			put(PlainKanteenDelivery.PAYWAY_WXPAY_AND_SPOT, new LinkedHashSet<String>(){
				{
					add(PlainKanteenOrder.PAYWAY_WXPAY);
					add(PlainKanteenOrder.PAYWAY_SPOT);
				}
			});
		}
	};
	
	Map<String, String> PAYWAY_NAME_MAP = new HashMap<String, String>(){
		{
			put(PlainKanteenOrder.PAYWAY_WXPAY, "微信支付");
			put(PlainKanteenOrder.PAYWAY_SPOT, "现场支付");
		}
	};
	
	
}
