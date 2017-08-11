package cn.sowell.ddxyz.model.canteen.pojo.item;

import javax.persistence.Column;

public class CanteenWeekDeliveryWaresItem {
	@Column(name="dwares_id")
	private Long deliveryWaresId;
	
	@Column(name="wares_id")
	private Long waresId;

	@Column(name="wares_name")
	private String waresName;
	
	@Column(name="c_base_price")
	private Integer unitPrice;
	
	@Column(name="c_price_unit")
	private String priceUnit;
	
	@Column(name="c_max_count")
	private Integer maxCount;
	
	@Column(name="c_current_count")
	private Integer currentCount;
	
	@Column(name="c_disabled")
	private Integer disabled;
	public Long getDeliveryWaresId() {
		return deliveryWaresId;
	}
	public void setDeliveryWaresId(Long deliveryWaresId) {
		this.deliveryWaresId = deliveryWaresId;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
	public String getWaresName() {
		return waresName;
	}
	public void setWaresName(String waresName) {
		this.waresName = waresName;
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
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	
}
