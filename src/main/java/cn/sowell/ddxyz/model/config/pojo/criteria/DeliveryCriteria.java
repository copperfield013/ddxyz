package cn.sowell.ddxyz.model.config.pojo.criteria;

import java.util.Date;

public class DeliveryCriteria {
	
	private Date deliveryTime;
	
	private String locationName;

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
