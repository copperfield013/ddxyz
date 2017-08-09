package cn.sowell.ddxyz.model.canteen.pojo;

public class CanteenOrderUpdateItem {
	private String waresName;
	private Integer count;
	private Integer unitPrice;
	private Long dWaresId;
	private String thumbUri;
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
	public Long getdWaresId() {
		return dWaresId;
	}
	public void setdWaresId(Long dWaresId) {
		this.dWaresId = dWaresId;
	}
	public String getThumbUri() {
		return thumbUri;
	}
	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}
}
