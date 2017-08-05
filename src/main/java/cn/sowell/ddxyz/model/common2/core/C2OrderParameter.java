package cn.sowell.ddxyz.model.common2.core;

import java.util.List;

public class C2OrderParameter {
	private C2Delivery delivery;
	private C2OrderReceiver receiver;
	private List<C2OrderItemParameter> orderItems;
	private String comment;
	
	
	public List<C2OrderItemParameter> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<C2OrderItemParameter> orderItems) {
		this.orderItems = orderItems;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public C2Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(C2Delivery delivery) {
		this.delivery = delivery;
	}
	public C2OrderReceiver getReceiver() {
		return receiver;
	}
	public void setReceiver(C2OrderReceiver receiver) {
		this.receiver = receiver;
	}
}
