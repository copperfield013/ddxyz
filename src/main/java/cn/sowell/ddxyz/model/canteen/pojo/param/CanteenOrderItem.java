package cn.sowell.ddxyz.model.canteen.pojo.param;

import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public class CanteenOrderItem {
	private Long deliveryWaresId;
	private Long waresId;
	private int count = 0;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Integer getPrice(PlainWares wares) {
		return wares.getBasePrice();
	}
	public Long getDeliveryWaresId() {
		return deliveryWaresId;
	}
	public void setDeliveryWaresId(Long deliveryWaresId) {
		this.deliveryWaresId = deliveryWaresId;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
}
