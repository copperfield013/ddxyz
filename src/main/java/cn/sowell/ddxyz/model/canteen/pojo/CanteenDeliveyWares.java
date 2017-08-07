package cn.sowell.ddxyz.model.canteen.pojo;

import javax.persistence.Column;

public class CanteenDeliveyWares {
	@Column(name="dwaresid")
	private Long dWaresId;
	
	@Column(name="waresid")
	private Long waresId;
	
	@Column(name="waresname")
	private String waresName;
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="price_unit")
	private String priceUnit;
	
	@Column(name="maxcount")
	private Integer maxCount;
	
	@Column(name="currencount")
	private Integer currentCount;
	
	
	public Long getdWaresId() {
		return dWaresId;
	}
	public void setdWaresId(Long dWaresId) {
		this.dWaresId = dWaresId;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
}
