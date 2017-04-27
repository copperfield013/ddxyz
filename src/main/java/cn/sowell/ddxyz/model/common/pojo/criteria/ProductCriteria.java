package cn.sowell.ddxyz.model.common.pojo.criteria;

import java.util.Set;

public class ProductCriteria {
	private Integer productStatusRangeMin;
	private Integer productStatusRangeMax;
	private Set<Long> orderIdRange;
	private String canceledStatus;
	private boolean canceled = false;
	public Integer getProductStatusRangeMin() {
		return productStatusRangeMin;
	}
	public void setProductStatusRangeMin(Integer productStatusRangeMin) {
		this.productStatusRangeMin = productStatusRangeMin;
	}
	public Integer getProductStatusRangeMax() {
		return productStatusRangeMax;
	}
	public void setProductStatusRangeMax(Integer productStatusRangeMax) {
		this.productStatusRangeMax = productStatusRangeMax;
	}
	public Set<Long> getOrderIdRange() {
		return orderIdRange;
	}
	public void setOrderIdRange(Set<Long> orderIdRange) {
		this.orderIdRange = orderIdRange;
	}
	public boolean getCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	public String getCanceledStatus() {
		return canceledStatus;
	}
	public void setCanceledStatus(String canceledStatus) {
		this.canceledStatus = canceledStatus;
	}
}
