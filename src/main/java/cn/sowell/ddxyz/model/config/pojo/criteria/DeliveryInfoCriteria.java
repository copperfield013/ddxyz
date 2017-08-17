package cn.sowell.ddxyz.model.config.pojo.criteria;

import java.util.Date;

import cn.sowell.copframe.utils.date.CommonDateFormat;

public class DeliveryInfoCriteria {
	
	/**
	 * 从页面上接受的的时间字符串
	 */
	private String receiveTime;
	
	private Date deliveryTime;
	
	private String locationName;
	
	CommonDateFormat dateformat = new CommonDateFormat();
	
	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
		this.setDeliveryTime(dateformat.parse(receiveTime));
	}


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
