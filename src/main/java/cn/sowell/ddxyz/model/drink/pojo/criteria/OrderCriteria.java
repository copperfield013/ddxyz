package cn.sowell.ddxyz.model.drink.pojo.criteria;

import java.util.Date;

import cn.sowell.copframe.utils.date.CommonDateFormat;

public class OrderCriteria {

	private Date startTime;
	
	private Date endTime;
	
	private String timePoint;
	
	private Long userId;
	
	private String orderCode;
	
	private String pointTimel;
	
	private String locationName;
	
	/**
	 * 时间范围字符串（用于从页面上传入，并且返回到页面中）
	 */
	private String timeRange;
	
	CommonDateFormat dateformat = new CommonDateFormat();

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPointTimel() {
		return pointTimel;
	}

	public void setPointTimel(String pointTimel) {
		this.pointTimel = pointTimel;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
