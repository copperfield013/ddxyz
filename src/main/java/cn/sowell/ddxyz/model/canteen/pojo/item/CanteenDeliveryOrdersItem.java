package cn.sowell.ddxyz.model.canteen.pojo.item;

import java.util.List;

import javax.persistence.Column;

public class CanteenDeliveryOrdersItem {
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="c_order_code")
	private String orderCode;
	
	@Column(name="c_receiver_name")
	private String receiverName;
	
	@Column(name="c_depart")
	private String depart;
	
	@Column(name="c_receiver_contact")
	private String receiverContact;
	
	@Column(name="c_total_price")
	private Integer totalPrice;
	
	private List<CanteenDeliveryOrderWaresItem> waresItemsList;
	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getReceiverContact() {
		return receiverContact;
	}
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}
	public List<CanteenDeliveryOrderWaresItem> getWaresItemsList() {
		return waresItemsList;
	}
	public void setWaresItemsList(List<CanteenDeliveryOrderWaresItem> waresItemsList) {
		this.waresItemsList = waresItemsList;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
