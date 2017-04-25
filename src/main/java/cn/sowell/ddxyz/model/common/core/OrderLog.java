package cn.sowell.ddxyz.model.common.core;

public interface OrderLog {
	final String TYPE_ORDER_CREATE = "创建订单";
	final String TYPE_ORDER_PAYED = "支付订单";
	final String TYPE_CANCELED = "取消订单";
	final String TYPE_COMPLETE = "确认收货"; 
}
