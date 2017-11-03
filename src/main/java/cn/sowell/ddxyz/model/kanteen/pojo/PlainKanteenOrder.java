package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_order_kanteen")
public class PlainKanteenOrder {
	
	/**
	 * 订单已创建
	 */
	public static final String STATUS_DEFAULT = "default";

	/**
	 * 订单已支付
	 */
	public static final String STATUS_PAIED = "paied";
	/**
	 * 订单已确认
	 */
	public static final String STATUS_CONFIRMED = "confirmed";
	/**
	 * 微信支付
	 */
	public static final String PAYWAY_WXPAY = "wxpay";
	/**
	 * 现场支付
	 */
	public static final String PAYWAY_SPOT = "spot";

	

	/**
	 * 订单已退款
	 */
	public static final String CANSTATUS_REFUNDED = "refunded";

	public static final String CANSTATUS_CANCELED = "canceled";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="merchant_id")
	private Long merchantId;
	
	@Column(name="c_merchant_name")
	private String merchantName;
	
	@Column(name="c_order_code")
	private String orderCode;
	
	@Column(name="order_user_id")
	private Long orderUserId;
	
	@Column(name="location_id")
	private Long locationId;
	
	@Column(name="c_location_name")
	private String locationName;

	@Column(name="c_payway")
	private String payway;
	
	@Column(name="c_pay_time")
	private Date payTime;
	
	@Column(name="c_pay_expired_time")
	private Date payExpiredTime;
	
	@Column(name="delivery_id")
	private Long deliveryId;
	
	@Column(name="distribution_id")
	private Long distributionId;
	
	@Column(name="trolley_id")
	private Long trolleyId;
	
	@Column(name="c_status")
	private String status;
	
	@Column(name="c_canceled_status")
	private String canceledStatus;
	
	@Column(name="c_refund_fee")
	private Integer refundFee;
	
	@Column(name="c_wx_prepay_id")
	private String wxPrepayId;
	
	@Column(name="c_wx_out_trade_no")
	private String wxOutTradeNo;
	
	@Column(name="c_wx_transaction_id")
	private String wxTransactionId;
	
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
	
	@Column(name="c_receiver_depart")
	private String receiverDepart;
	
	@Column(name="c_remark")
	private String remark;
	
	@Column(name="c_print_time")
	private Date printTime;
	
	@Column(name="c_deleted")
	private Integer deleted;
	
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getOrderUserId() {
		return orderUserId;
	}

	public void setOrderUserId(Long orderUserId) {
		this.orderUserId = orderUserId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getPayExpiredTime() {
		return payExpiredTime;
	}

	public void setPayExpiredTime(Date payExpiredTime) {
		this.payExpiredTime = payExpiredTime;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCanceledStatus() {
		return canceledStatus;
	}

	public void setCanceledStatus(String canceledStatus) {
		this.canceledStatus = canceledStatus;
	}

	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	public String getWxPrepayId() {
		return wxPrepayId;
	}

	public void setWxPrepayId(String wxPrepayId) {
		this.wxPrepayId = wxPrepayId;
	}

	public String getWxOutTradeNo() {
		return wxOutTradeNo;
	}

	public void setWxOutTradeNo(String wxOutTradeNo) {
		this.wxOutTradeNo = wxOutTradeNo;
	}

	public String getWxTransactionId() {
		return wxTransactionId;
	}

	public void setWxTransactionId(String wxTransactionId) {
		this.wxTransactionId = wxTransactionId;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getActualPay() {
		return actualPay;
	}

	public void setActualPay(Integer actualPay) {
		this.actualPay = actualPay;
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


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
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

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getReceiverDepart() {
		return receiverDepart;
	}

	public void setReceiverDepart(String receiverDepart) {
		this.receiverDepart = receiverDepart;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	
}
