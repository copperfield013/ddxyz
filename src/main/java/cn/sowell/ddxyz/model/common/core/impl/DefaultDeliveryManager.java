package cn.sowell.ddxyz.model.common.core.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.utils.range.ComparableSingleRange;
import cn.sowell.copframe.utils.range.DateRange;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryKey;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.DispenseResourceRequest;
import cn.sowell.ddxyz.model.common.core.OrderManager;
import cn.sowell.ddxyz.model.common.core.OrderParameter;
import cn.sowell.ddxyz.model.common.core.OrderToken;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.service.DataPersistenceService;
import cn.sowell.ddxyz.model.common.utils.DeliveryPeriodUtils;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;

@Repository("defaultDeliveryManager")
public class DefaultDeliveryManager implements DeliveryManager{
	@Resource
	private OrderManager odrManage;
	
	@Resource
	DataPersistenceService dpService;
	
	@Resource
	DeliveryService dService;
	
	@Resource
	WxConfigService configService;
	
	private Map<DeliveryKey, Delivery> deliveryMap = new LinkedHashMap<DeliveryKey, Delivery>();
	private Map<Delivery, Long> lastOperateMap = new LinkedHashMap<Delivery, Long>();
	
	
	Logger logger = Logger.getLogger(DeliveryManager.class);
	
	private synchronized Delivery getCachedDelivery(DeliveryKey key){
		Delivery delivery = deliveryMap.get(key);
		if(delivery != null){
			lastOperateMap.put(delivery, System.currentTimeMillis());
		}
		return delivery;
	}
	
	
	@Override
	public List<Delivery> loadTodayDeliveries(){
		List<PlainDeliveryPlan> plans = dpService.getTheDayUsablePlan();
		Map<DeliveryKey, PlainDelivery> map = generateDelivery(plans, Calendar.getInstance());
		Map<DeliveryKey, Delivery> dMap = dpService.mergeDeliveries(map);
		dMap.forEach((key, delivery) -> {
			if(!deliveryMap.containsKey(key)){
				deliveryMap.put(key, delivery);
				lastOperateMap.put(delivery, System.currentTimeMillis());
			}
		});
		return new ArrayList<Delivery>(dMap.values());
	}
	
	
	private Map<DeliveryKey, PlainDelivery> generateDelivery(List<PlainDeliveryPlan> plans, Calendar theDay) {
		Map<DeliveryKey, PlainDelivery> map = new LinkedHashMap<DeliveryKey, PlainDelivery>();
		for (PlainDeliveryPlan plan : plans) {
			try {
				List<Integer> hourList = DeliveryPeriodUtils.getHourList(plan.getPeriod(), theDay);
				if(!hourList.isEmpty()){
					for (Integer hour : hourList) {
						PlainDelivery pDelivery = new PlainDelivery();
						//配送地点的id
						pDelivery.setLocationId(plan.getLocationId());
						if(plan.getLocation() != null){
							//配送地点名称
							pDelivery.setLocationName(plan.getLocation().getName());
						}
						//配送最大数量
						pDelivery.setMaxCount(plan.getMaxCount());
						//绑定的商品id
						pDelivery.setWaresId(plan.getWaresId());
						//配送计划id
						pDelivery.setDeliveryPlanId(plan.getId());
						//生成配送时间
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.HOUR_OF_DAY, hour);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						pDelivery.setTimePoint(cal.getTime());
						//配送关闭时间
						cal.add(Calendar.MINUTE, -plan.getLeadMinutes());
						pDelivery.setCloseTime(cal.getTime());
						
						DeliveryKey key = new DeliveryKey(plan.getWaresId(), new DeliveryTimePoint(pDelivery.getTimePoint()), new DeliveryLocation(pDelivery.getLocationId()));
						map.put(key, pDelivery);
					}
				}
			} catch (ParseException e) {
				logger.error("根据配送计划[id=" + plan.getId() + ",period=" + plan.getPeriod() + "]生成配送时，规则转换错误", e);
			}
		}
		return map;
	}

	private Delivery getDelivery(DeliveryKey deliveryKey){
		synchronized (deliveryMap) {
			//从内存中获得配送对象
			Delivery result = getCachedDelivery(deliveryKey);
			//如果拿不到，那么就去数据库中获取
			if(result == null){
				result = dpService.getDelivery(deliveryKey);
				//如果从数据库中可以拿到，那么将对象放到内存中
				if(result != null){
					deliveryMap.put(deliveryKey, result);
					lastOperateMap.put(result, System.currentTimeMillis());
				}
			}
			return result;
		}
	}
	
	@Override
	public Delivery getDelivery(Long waresId, DeliveryTimePoint timePoint,
			DeliveryLocation location) {
		//配送的标识
		DeliveryKey deliveryKey = new DeliveryKey(waresId, timePoint, location);
		return getDelivery(deliveryKey);
	}
	
	@Override
	public Delivery getDelivery(Serializable deliveryId) {
		//配送的标识
		DeliveryKey deliveryKey = new DeliveryKey(deliveryId);
		return getDelivery(deliveryKey);
	}
	
	
	
	@Override
	public CheckResult checkOrderParameterAvailable(OrderParameter orderParameter,
			OrderToken orderToken){
		CheckResult result = new CheckResult(true, "检验成功");
		//判断请求对应的配送是否存在
		if(orderParameter.getDeliveryLocation() != null && orderParameter.getTimePoint() != null){
			Delivery delivery = getDelivery(orderParameter.getWaresId(), orderParameter.getTimePoint(), orderParameter.getDeliveryLocation());
			if(delivery != null){
				if(!delivery.isEnabled()){
					return result.setResult(false, "时间点[" + orderParameter.getTimePoint() + "]和地点[" + orderParameter.getDeliveryLocation() + "]对应的配送对象[" + delivery.getId() + "]当前被禁用");
				}
				
				DeliveryTimePoint timePoint = delivery.getTimePoint();
				//获得对应的配送对象
				if(timePoint.getClosed() && !configService.isDebug()){
					return result.setResult(false, "配送时间点的关闭时间为[" + timePoint.getCloseTime() + "]，已过期");
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
	
	@Override
	public synchronized void clearCache(DateRange range) {
		if(range != null){
			ComparableSingleRange<Long> timeRange = range.toLongRange();
			Set<Delivery> deliveries = new HashSet<Delivery>(lastOperateMap.keySet());
			for (Delivery delivery : deliveries) {
				Long time = lastOperateMap.get(delivery);
				if(timeRange.inRange(time)){
					lastOperateMap.remove(delivery);
					deliveryMap.remove(delivery.getId());
				}
			}
		}
	}

}
