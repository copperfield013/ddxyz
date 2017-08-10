package cn.sowell.ddxyz.model.canteen.pojo.item;

import java.util.Date;

import javax.persistence.Column;

public class CanteenDeliveryWaresListItem {
	
	@Column(name="wares_name")
	private String name;
	
	@Column(name="c_base_price")
	private Integer unitPrice;
	
	@Column(name="c_price_unit")
	private String priceUnit;
	
	
	@Column(name="c_open_time")
	private Date orderOpenTime;
	
	@Column(name="c_close_time")
	private Date orderCloseTime;
	
	@Column(name="c_time_point")
	private Date claimStartTime;
	
	@Column(name="c_clain_end_time")
	private Date claimEndTime;
	
	@Column(name="delivery_id")
	private Long deliveryId;
	
	@Column(name="dwares_id")
	private Long deliveryWaresId;
	
	@Column(name="c_location_name")
	private String locationName;
	
	@Column(name="c_max_count")
	private Integer maxCount;
	
	@Column(name="c_current_count")
	private Integer currentCount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getClaimStartTime() {
		return claimStartTime;
	}
	public void setClaimStartTime(Date claimStartTime) {
		this.claimStartTime = claimStartTime;
	}
	public Date getClaimEndTime() {
		return claimEndTime;
	}
	public void setClaimEndTime(Date claimEndTime) {
		this.claimEndTime = claimEndTime;
	}
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public Long getDeliveryWaresId() {
		return deliveryWaresId;
	}
	public void setDeliveryWaresId(Long deliveryWaresId) {
		this.deliveryWaresId = deliveryWaresId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public Integer getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}
	public Date getOrderOpenTime() {
		return orderOpenTime;
	}
	public void setOrderOpenTime(Date orderOpenTime) {
		this.orderOpenTime = orderOpenTime;
	}
	public Date getOrderCloseTime() {
		return orderCloseTime;
	}
	public void setOrderCloseTime(Date orderCloseTime) {
		this.orderCloseTime = orderCloseTime;
	}
	
	
	
}
