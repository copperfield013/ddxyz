package cn.sowell.ddxyz.model.common.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.sowell.ddxyz.model.common.core.OrderDetail;
@Entity
@Table(name="t_order_base")
public class PlainOrder implements OrderDetail{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="c_order_code")
	private String orderCode;
	
	@Column(name="location_id")
	private Long locationId;
	
	@Column(name="c_location_name")
	private String locationName;
	
	@Column(name="c_time_point")
	private Date timePoint;
	
	@Column(name="delivery_id")
	private Long deliveryId;
	
	@Column(name="order_user_id")
	private Long orderUserId;
	
	@Column(name="distribution_id")
	private Long distributionId;
	
	@Column(name="trolley_id")
	private Long trolleyId;
	
	@Column(name="c_dispense_resource_req")
	private String dispenseResourceRequest;
	
	@Column(name="c_status")
	private int orderStatus;
	
	@Column(name="c_canceled_status")
	private String canceledStatus;
	
	@Column(name="c_refund_fee")
	private Integer refundFee;
	
	@Column(name="c_prepay_id")
	private String prepayId;
	
	@Column(name="c_out_trade_no")
	private String outTradeNo;
	
	@Column(name="c_transaction_id")
	private String transactionId;
	
	@Column(name="c_total_price")
	private Integer totalPrice;
	
	@Column(name="c_actual_pay")
	private Integer actualPay;
	
	@Column(name="c_receiver_contact")
	private String receiverContact;
	
	@Column(name="c_receiver_address")
	private String receiverAddress;
	
	@Column(name="c_receiver_name")
	private String receiverName;
	
	@Column(name="c_comment")
	private String comment;
	
	@Column(name="c_pay_time")
	private Date payTime;
	
	@Column(name="c_pay_expire_time")
	private Date payExpireTime;
	
	@Column(name="c_print_time")
	private Date printTime;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
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
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getOrderUserId() {
		return orderUserId;
	}
	public void setOrderUserId(Long orderUserId) {
		this.orderUserId = orderUserId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public Integer getActualPay() {
		return actualPay;
	}
	public void setActualPay(Integer actualPay) {
		this.actualPay = actualPay;
	}
	public String getDispenseResourceRequest() {
		return dispenseResourceRequest;
	}
	public void setDispenseResourceRequest(String dispenseResourceRequest) {
		this.dispenseResourceRequest = dispenseResourceRequest;
	}
	public Integer getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}
	public String getCanceledStatus() {
		return canceledStatus;
	}
	public void setCanceledStatus(String canceledStatus) {
		this.canceledStatus = canceledStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getPayExpireTime() {
		return payExpireTime;
	}
	public void setPayExpireTime(Date payExpireTime) {
		this.payExpireTime = payExpireTime;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getPrintTime() {
		return printTime;
	}
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	public Long getDistributionId() {
		return distributionId;
	}
	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}
	public Long getTrolleyId() {
		return trolleyId;
	}
	public void setTrolleyId(Long trolleyId) {
		this.trolleyId = trolleyId;
	}
	
	
}
