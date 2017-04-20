package cn.sowell.ddxyz.model.drink.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

public class PlainOrderDrink {
	
	private String orderCode;
	
	private Date orderTime;
	
	private Integer totalPrice;
	
	private Integer cupCount;
	
	private int orderStatus;
	
	private String canceledStatus;
	
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

}
