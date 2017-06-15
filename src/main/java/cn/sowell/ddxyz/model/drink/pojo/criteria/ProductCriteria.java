package cn.sowell.ddxyz.model.drink.pojo.criteria;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.sowell.copframe.dto.format.OfDateFormat;

public class ProductCriteria {
	
	private String timePoint;
	
	private Long locationId;
	
	private String locationName;
	
	private Integer printStatus;
	
	private String orderCode;
	
	private String payTimeStr;
	

	public String getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
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

	public Integer getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(Integer printStatus) {
		this.printStatus = printStatus;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPayTimeStr() {
		return payTimeStr;
	}

	public void setPayTimeStr(String payTimeStr) throws ParseException {
		this.payTimeStr = payTimeStr;
	}

}
