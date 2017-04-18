package cn.sowell.ddxyz.model.common.core.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryKey;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.DispenseResourceRequest;
import cn.sowell.ddxyz.model.common.core.OrderDispenseResource;
import cn.sowell.ddxyz.model.common.core.OrderManager;
import cn.sowell.ddxyz.model.common.core.OrderParameter;
import cn.sowell.ddxyz.model.common.core.OrderToken;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.service.DataPersistenceService;

@Repository
public class DefaultDeliveryManager implements DeliveryManager, InitializingBean{
	@Resource
	private OrderManager odrManage;
	
	@Resource
	DataPersistenceService dpService;
	
	private Map<DeliveryKey, Delivery> deliveryMap = new LinkedHashMap<DeliveryKey, Delivery>();
	
	Logger logger = Logger.getLogger(DeliveryManager.class);
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("初始化配送管理器");
		//TODO: 从持久化数据中获取配送对象或者根据配送计划生成配送对象
		synchronized (deliveryMap) {
			
		}
		logger.info("配送管理器初始化完成");
	}
	
	private Delivery getDelivery(DeliveryKey deliveryKey){
		synchronized (deliveryMap) {
			//从内存中获得配送对象
			Delivery result = deliveryMap.get(deliveryKey);
			//如果拿不到，那么就去数据库中获取
			if(result == null){
				result = dpService.getDelivery(deliveryKey);
				//如果从数据库中可以拿到，那么将对象放到内存中
				if(result != null){
					deliveryMap.put(deliveryKey, result);
				}
			}
			return result;
		}
	}
	
	@Override
	public Delivery getDelivery(DeliveryTimePoint timePoint,
			DeliveryLocation location) {
		//配送的标识
		DeliveryKey deliveryKey = new DeliveryKey(timePoint, location);
		return getDelivery(deliveryKey);
	}
	
	@Override
	public Delivery getDelivery(Serializable deliveryId) {
		//配送的标识
		DeliveryKey deliveryKey = new DeliveryKey(deliveryId);
		return getDelivery(deliveryKey);
	}
	
	/**
	 * 构造分配资源对象
	 * @return
	 */
	OrderDispenseResource buildDispenseResource() {
		// TODO： 构造分配的资源对象
		return null;
	}

	
	
	
	@Override
	public CheckResult checkOrderParameterAvailable(OrderParameter orderParameter,
			OrderToken orderToken){
		CheckResult result = new CheckResult(true, "检验成功");
		//判断请求对应的配送是否存在
		if(orderParameter.getDeliveryLocation() != null && orderParameter.getTimePoint() != null){
			//获得对应的配送独享
			Delivery delivery = getDelivery(orderParameter.getTimePoint(), orderParameter.getDeliveryLocation());
			if(delivery != null){
				if(!delivery.isEnabled()){
					return result.setResult(false, "时间点[" + orderParameter.getTimePoint() + "]和地点[" + orderParameter.getDeliveryLocation() + "]对应的配送对象[" + delivery.getId() + "]当前被禁用");
				}
				//检查请求中的资源是否足够
				DispenseResourceRequest resourceReq = orderParameter.getDispenseResourceRequest();
				int dispenseCount = resourceReq.getDispenseCount();
				if(dispenseCount > 0){
					if(delivery.getMaxCount() != null){
						if(!delivery.checkAvailable(dispenseCount)){
							return result.setResult(false, "配送的资源不足，检验不通过");
						}else{
							return result.setResult(true, "配送资源充足");
						}
					}else{
						return result.setResult(true, "对应配送的资源没有限制数量，可以请求任意数量");
					}
				}else{
					//当需要的资源数量为0的时候，可以直接返回检查成功
					return result.setResult(true, "请求不需要资源，并且对应配送存在，因此可以创建");
				}
			}else{
				return result.setResult(false, "没有找到时间点[" + orderParameter.getTimePoint() + "]和地点[" + orderParameter.getDeliveryLocation() + "]对应的配送对象");
			}
		}
		return result;
	}
	
	@Override
	public DeliveryLocation getDeliveryLocation(long locationId) {
		PlainLocation location = dpService.getDeliveryLocation(locationId);
		return new DeliveryLocation(location);
	}
	
}
