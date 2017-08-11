package cn.sowell.ddxyz.model.canteen.pojo.item;

import java.util.Date;

import javax.persistence.Column;

public class CanteenWeekTableItem {
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="c_order_code")
	private String orderCode;
	
	@Column(name="delivery_wares_id")
	private Long deliveryWaresId;
	
	@Column(name="c_receiver_name")
	private String receiverName;
	
	@Column(name="c_receiver_contact")
	private String receiverContact;
	
	@Column(name="c_depart")
	private String receiverDepart;
	
	@Column(name="wares_name")
	private String waresName;
	
	@Column(name="p_count")
	private Integer count;
	
	@Column(name="c_base_price")
	private Integer unitPrice;
	
	@Column(name="c_price_unit")
	private String priceUnit;
	
	@Column(name="order_time")
	private Date orderTime;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getWaresName() {
		return waresName;
	}
	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getDeliveryWaresId() {
		return deliveryWaresId;
	}
	public void setDeliveryWaresId(Long deliveryWaresId) {
		this.deliveryWaresId = deliveryWaresId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverContact() {
		return receiverContact;
	}
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}
	public String getReceiverDepart() {
		return receiverDepart;
	}
	public void setReceiverDepart(String receiverDepart) {
		this.receiverDepart = receiverDepart;
	}
	
}
