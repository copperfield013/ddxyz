package cn.sowell.ddxyz.model.canteen.pojo.item;

import java.util.Date;

import javax.persistence.Column;
/**
 * 
 * <p>Title: CanteenOrderInfoItem</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年8月17日 上午11:06:17
 * @see
 */
public class CanteenOrderInfoItem {
	
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="c_depart")
	private String depart;
	
	@Column(name="c_delivery_end_time")
	private Date deliveryEndTime;
	
	@Column(name="c_order_code")
	private String orderCode;
	
	@Column(name="c_location_name")
	private String locationName;
	
	@Column(name="c_time_point")
	private Date timePoint;
	
	@Column(name="c_status")
	private int orderStatus;
	
	@Column(name="c_canceled_status")
	private String canceledStatus;
	
	@Column(name="c_total_price")
	private Integer totalPrice;
	
	@Column(name="c_receiver_contact")
	private String receiverContact;
	
	@Column(name="c_receiver_address")
	private String receiverAddress;
	
	@Column(name="c_receiver_name")
	private String receiverName;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="c_comment")
	private String comment;

	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getDepart() {
		return depart;
	}
	
	public void setDepart(String depart) {
		this.depart = depart;
	}
	
	public Date getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(Date deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}

	public String getOrderCode() {
		return orderCode;
	}
	
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public Date getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

	public int getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getCanceledStatus() {
		return canceledStatus;
	}
	
	public void setCanceledStatus(String canceledStatus) {
		this.canceledStatus = canceledStatus;
	}
	
	public Integer getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getReceiverContact() {
		return receiverContact;
	}
	
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}
	
	public String getReceiverAddress() {
		return receiverAddress;
	}
	
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	
	public String getReceiverName() {
		return receiverName;
	}
	
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
