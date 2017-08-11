package cn.sowell.ddxyz.model.canteen.pojo.item;

import javax.persistence.Column;

import cn.sowell.ddxyz.model.canteen.dao.impl.CanteenManageDaoImpl;

/**
 * 
 * <p>Title: CanteenDeliveryOrderWaresItem</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年8月11日 下午2:57:41
 * @see {@link CanteenManageDaoImpl #getOrderWaresItemList}
 */
public class CanteenDeliveryOrderWaresItem {
	
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="c_name")
	private String waresName;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="delivery_wares_id")
	private Long deliveryWaresId;
	
	@Column(name="p_count")
	private Integer count;
	public String getWaresName() {
		return waresName;
	}
	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
	public Long getDeliveryWaresId() {
		return deliveryWaresId;
	}
	public void setDeliveryWaresId(Long deliveryWaresId) {
		this.deliveryWaresId = deliveryWaresId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
