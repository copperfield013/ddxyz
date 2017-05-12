package cn.sowell.ddxyz.model.drink.pojo.item;

import java.util.Date;

import javax.persistence.Column;

public class OrderStatisticsListItem {
	
	@Column(name="order_date")
	private Date orderDate;
	
	@Column(name="income")
	private Integer income;
	
	@Column(name="order_count")
	private Integer orderCount;
	
	@Column(name="cup_count")
	private Integer cupCount;

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public Integer getCupCount() {
		return cupCount;
	}

	public void setCupCount(Integer cupCount) {
		this.cupCount = cupCount;
	}

}
