package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;

/**
 * 
 * <p>Title: DeliveryKey</p>
 * <p>Description: </p><p>
 * 配送的key，只可内部使用
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月30日 上午9:55:50
 */
public class DeliveryKey{
	Serializable deliveryId;
	DeliveryTimePoint timePoint;
	DeliveryLocation location;
	Long waresId;
	public DeliveryKey(Long waresId, DeliveryTimePoint timePoint,
			DeliveryLocation location) {
		this.waresId = waresId;
		this.timePoint = timePoint;
		this.location = location;
	}
	public DeliveryKey(Serializable deliveryId){
		this.deliveryId = deliveryId;
	}
	
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	/**
	 * 判断两个key对象是否相同
	 * 如果两个key对象的 {@link #deliveryId}都存在，那么判断两个id是否相同，相同的话直接返回true
	 * 否则判断两个key对象的 {@link #timePoint}和 {@link #location}属性是否相同
	 * 如果都相同，那么返回true
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DeliveryKey){
			DeliveryKey key = (DeliveryKey) obj;
			//判断deliveryId是否相同
			if(deliveryId != null){
				if(deliveryId.equals(key.deliveryId)){
					return true;
				}
			}
			//判断时间点和配送地点是否相同
			if(timePoint != null){
				if(location != null){
					if(waresId != null){
						return timePoint.equals(key.timePoint)
								&& location.equals(key.location) 
								&& waresId.equals(key.waresId)
								;
					}
				}
			}
		}
		return false;
	}
	public Serializable getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Serializable deliveryId) {
		this.deliveryId = deliveryId;
	}
	public DeliveryTimePoint getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(DeliveryTimePoint timePoint) {
		this.timePoint = timePoint;
	}
	public DeliveryLocation getLocation() {
		return location;
	}
	public void setLocation(DeliveryLocation location) {
		this.location = location;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
}