package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;

import javax.persistence.Column;

public class KanteenLocationItemForChoose {
	@Column(name="id")
	private Long locationId;
	
	@Column(name="c_name")
	private String locationName;
	
	@Column(name="c_address")
	private String address;
	
	@Column(name="c_code")
	private String code;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
