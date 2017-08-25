package cn.sowell.ddxyz.model.canteen.pojo.item;

import java.util.Date;
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
	
	@Column(name="c_comment")
	private String comment;
	
	@Column(name="c_canceled_status")
	private String cancelStatus;
	
	@Column(name="c_status")
	private Integer status;
	
	@Column(name="create_time")
	private Date createTime;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCancelStatus() {
		return cancelStatus;
	}
	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
