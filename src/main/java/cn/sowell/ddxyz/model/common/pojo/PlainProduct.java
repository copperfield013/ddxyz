package cn.sowell.ddxyz.model.common.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_product_base")
public class PlainProduct{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="c_name")
	private String name;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="delivery_wares_id")
	private Long deliveryWaresId;
	
	@Column(name="c_price")
	private Integer price;
	
	@Column(name="c_price_unit")
	private String priceUnit;
	
	@Column(name="c_dispense_code")
	private String dispenseCode;
	
	@Column(name="c_dispense_key")
	private Integer dispenseKey;
	
	@Column(name="c_status")
	private int status;
	
	@Column(name="c_canceled_status")
	private String canceledStatus;
	
	@Column(name="c_refund_fee")
	private Integer refundFee;
	
	@Column(name="c_print_time")
	private Date printTime;
	
	@Column(name="c_thumb_uri")
	private String thumbUri;
	
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getDispenseCode() {
		return dispenseCode;
	}
	public void setDispenseCode(String dispenseCode) {
		this.dispenseCode = dispenseCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public Integer getDispenseKey() {
		return dispenseKey;
	}
	public void setDispenseKey(Integer dispenseKey) {
		this.dispenseKey = dispenseKey;
	}
	public Date getPrintTime() {
		return printTime;
	}
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	public String getThumbUri() {
		return thumbUri;
	}
	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}
	public Long getDeliveryWaresId() {
		return deliveryWaresId;
	}
	public void setDeliveryWaresId(Long deliveryWaresId) {
		this.deliveryWaresId = deliveryWaresId;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	
}
