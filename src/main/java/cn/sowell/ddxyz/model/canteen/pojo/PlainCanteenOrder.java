package cn.sowell.ddxyz.model.canteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.sowell.ddxyz.model.common.pojo.PlainOrder;

@Entity
@Table(name="t_canteen_order")
public class PlainCanteenOrder {
	@Transient
	private PlainOrder pOrder;
	@Id
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="c_depart")
	private String depart;
	
	@Column(name="c_delivery_end_time")
	private Date deliveryEndTime;
	
	@Column(name="c_order_open_time")
	private Date orderOpenTime;
	
	@Column(name="c_order_close_time")
	private Date orderCloseTime;
	
	public PlainOrder getpOrder() {
		return pOrder;
	}
	public void setpOrder(PlainOrder pOrder) {
		this.pOrder = pOrder;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getDeliveryEndTime() {
		return deliveryEndTime;
	}
	public void setDeliveryEndTime(Date deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}
	public Date getOrderOpenTime() {
		return orderOpenTime;
	}
	public void setOrderOpenTime(Date orderOpenTime) {
		this.orderOpenTime = orderOpenTime;
	}
	public Date getOrderCloseTime() {
		return orderCloseTime;
	}
	public void setOrderCloseTime(Date orderCloseTime) {
		this.orderCloseTime = orderCloseTime;
	}
	
}
