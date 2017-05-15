package cn.sowell.ddxyz.model.drink.pojo.criteria;

import java.util.Date;

import cn.sowell.copframe.dto.format.OfDateFormat;

public class DeliveryCriteria {

	private Date startTime;
	
	private Date  endTime;
	
	private String timePoint;
	
	private String orderCode;
	
	private String locationName;
	
	private Integer hasPrinted;
	
	/**
	 * 时间范围字符串（用于从页面上传入，并且返回到页面中）
	 */
	private String timeRange;
	
	OfDateFormat dateformat = new OfDateFormat();
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public String getTimeRange() {
		if(timeRange == null){
			if(startTime != null || endTime != null){
				StringBuffer buffer = new StringBuffer();
				if(startTime != null){
					buffer.append(dateformat.formatDateTime(startTime));
				}
				if(endTime != null){
					buffer.append("~");
					buffer.append(dateformat.formatDateTime(endTime));
				}
				return buffer.toString();
			}
		}
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
		Date[] range = dateformat.splitDateRange(timeRange);
		this.setStartTime(range[0]);
		this.setEndTime(range[1]);
	}

	public Integer getHasPrinted() {
		return hasPrinted;
	}

	public void setHasPrinted(Integer hasPrinted) {
		this.hasPrinted = hasPrinted;
	}

}
