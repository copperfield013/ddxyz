package cn.sowell.ddxyz.model.drink.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

public class PlainDrinkOrder {
	
	private Long id;
	
	private String orderCode;
	
	private Date orderTime;
	
	private Integer totalPrice;
	
	private Integer cupCount;
	
	private int orderStatus;
	
	private String canceledStatus;
	
	private Date timePoint;
	
	private String locationName;
	
	private Date payExpireTime;
	
	private List<PlainOrderDrinkItem> orderDrinkItems = new ArrayList<PlainOrderDrinkItem>();
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getCupCount() {
		return cupCount;
	}

	public void setCupCount(Integer cupCount) {
		this.cupCount = cupCount;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCanceledStatus() {
		return canceledStatus;
	}

	public void setCanceledStatus(String canceledStatus) {
		this.canceledStatus = canceledStatus;
	}
	
	public List<PlainOrderDrinkItem> getOrderDrinkItems() {
		return orderDrinkItems;
	}

	public void setOrderDrinkItems(List<PlainOrderDrinkItem> orderDrinkItems) {
		this.orderDrinkItems = orderDrinkItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getPayExpireTime() {
		return payExpireTime;
	}

	public void setPayExpireTime(Date payExpireTime) {
		this.payExpireTime = payExpireTime;
	}


}
