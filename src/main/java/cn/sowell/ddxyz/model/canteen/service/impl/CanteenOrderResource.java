package cn.sowell.ddxyz.model.canteen.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common2.core.C2DispenseResource;
import cn.sowell.ddxyz.model.common2.core.C2OrderResource;

public class CanteenOrderResource implements C2OrderResource{

	private Map<PlainDeliveryWares, Integer> deliveryCountMap = new HashMap<PlainDeliveryWares, Integer>();
	
	@Override
	public int getResourceCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<C2DispenseResource> getDispenseResources() {
		return null;
	}

	/**
	 * 将配送的id
	 * @param deliveryId
	 * @param count
	 */
	public synchronized void putCart(PlainDeliveryWares delivery, int count) {
		Integer originCount = deliveryCountMap.get(delivery);
		if(originCount != null){
			deliveryCountMap.put(delivery, originCount + count);
		}else{
			deliveryCountMap.put(delivery, count);
		}
	}
	
	public int getCountFromCart(PlainDelivery deliveryId){
		Integer originCount = deliveryCountMap.get(deliveryId);
		if(originCount != null){
			return originCount;
		}else{
			return 0;
		}
	}
	public Map<PlainDeliveryWares, Integer> getDeliveryCountMap(){
		return deliveryCountMap;
	}
	

}
