package cn.sowell.ddxyz.model.canteen.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.canteen.dao.CanteenDao;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDeliveyWares;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenOrderUpdateItem;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenUserCacheInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderItem;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderParameter;
import cn.sowell.ddxyz.model.canteen.service.CanteenService;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.common.utils.DeliveryPeriodUtils;
import cn.sowell.ddxyz.model.common2.core.C2OrderResource;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Service
public class CanteenServiceImpl implements CanteenService {

	@Resource
	CanteenDao cDao;
	
	
	Logger logger = Logger.getLogger(CanteenService.class);
	
	
	@Override
	public void saveWares(PlainWares wares) {
		cDao.saveWares(wares);
	}
	
	@Override
	public void savePlan(PlainDeliveryPlan plan,
			List<PlainDeliveryPlanWares> planWares) {
		cDao.savePlan(plan, planWares);
	}
	
	@Override
	public void generateTheDayDeliveries(Date date){
		List<PlainDeliveryPlan> plans = cDao.getThedayNullWaresIdPlans(date);
		Map<PlainDeliveryPlan, List<PlainDeliveryPlanWares>> planWaresMap = cDao.getPlanWaresMap(plans);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Map<PlainDelivery, List<PlainDeliveryWares>> dWaresMap = generateDelivery(planWaresMap, cal);
		cDao.saveDelivery(dWaresMap);
	}
	
	
	private Map<PlainDelivery, List<PlainDeliveryWares>> generateDelivery(Map<PlainDeliveryPlan, List<PlainDeliveryPlanWares>> planWaresMap, Calendar theDay) {
		Map<PlainDelivery, List<PlainDeliveryWares>> map = new LinkedHashMap<PlainDelivery, List<PlainDeliveryWares>>();
		planWaresMap.forEach((plan, pWares)->{
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
						pDelivery.setType(plan.getType());
						//生成配送时间
						Calendar cal = (Calendar) theDay.clone();
						cal.set(Calendar.HOUR_OF_DAY, hour);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						pDelivery.setTimePoint(cal.getTime());
						//配送预定关闭时间
						Calendar orderCloseTime = (Calendar) cal.clone();
						orderCloseTime.add(Calendar.MINUTE, -plan.getLeadMinutes());
						pDelivery.setCloseTime(orderCloseTime.getTime());
						if(plan.getClaimMinutes() != null){
							//领取结束时间
							Calendar claimEndTime = (Calendar) cal.clone();
							claimEndTime.add(Calendar.MINUTE, plan.getClaimMinutes());
							pDelivery.setClaimEndTime(claimEndTime.getTime());
						}
						
						List<PlainDeliveryWares> dWaresList = new ArrayList<PlainDeliveryWares>();
						map.put(pDelivery, dWaresList);
						for (PlainDeliveryPlanWares pw : pWares) {
							PlainDeliveryWares dWares = new PlainDeliveryWares();
							dWares.setWaresId(pw.getWaresId());
							dWares.setMaxCount(pw.getMaxCount());
							dWares.setCurrentCount(0);
							dWaresList.add(dWares);
						}
					}
				}
			} catch (ParseException e) {
				logger.error("根据配送计划[id=" + plan.getId() + ",period=" + plan.getPeriod() + "]生成配送时，规则转换错误", e);
			}
		});
		return map;
	}
	
	
	
	@Override
	public synchronized PlainCanteenOrder createOrder(CanteenOrderParameter coParam) throws OrderResourceApplyException {
		//申请订单资源
		applyForOrderResource(coParam);
		//根据得到的订单资源创建订单
		PlainOrder pOrder = new PlainOrder();
		List<PlainProduct> products = initOrder(pOrder, coParam);
		
		PlainCanteenOrder cOrder = new PlainCanteenOrder();
		cOrder.setDepart(coParam.getDepart());
		cOrder.setpOrder(pOrder);
		//订单持久化
		cDao.createOrder(cOrder, products);
		//返回订单
		return cOrder;
	}
	
	private List<PlainProduct> initOrder(PlainOrder pOrder, CanteenOrderParameter coParam) {
		pOrder.setComment(coParam.getComment());
		pOrder.setCreateTime(new Date());
		pOrder.setDeliveryId(coParam.getDeliveryId());
		
		PlainDelivery delivery = cDao.getDelivery(coParam.getDeliveryId());
		
		pOrder.setLocationId(delivery.getLocationId());
		pOrder.setLocationName(delivery.getLocationName());
		pOrder.setTimePoint(delivery.getTimePoint());
		
		pOrder.setOrderCode(generateOrderCode());
		
		pOrder.setOrderStatus(Order.STATUS_DEFAULT);
		pOrder.setOrderUserId(coParam.getUserId());
		pOrder.setReceiverName(coParam.getReceiverName());
		pOrder.setReceiverContact(coParam.getContact());
		
		pOrder.setTotalPrice(coParam.getTotalPrice());
		
		List<PlainProduct> products = new ArrayList<PlainProduct>();
		for (CanteenOrderItem item : coParam.getOrderItems()) {
			products.addAll(createProducts(item));
		}
		return products;
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	private List<PlainProduct> createProducts(CanteenOrderItem item){
		PlainWares wares = cDao.getWares(item.getWaresId());
		Date createTime = new Date();
		List<PlainProduct> products = new ArrayList<PlainProduct>();
		for (int i = 0; i < item.getCount(); i++) {
			PlainProduct product = new PlainProduct();
			product.setDeliveryWaresId(item.getDeliveryWaresId());
			product.setWaresId(wares.getId());
			product.setName(wares.getName());
			product.setPrice(item.getPrice(wares));
			product.setThumbUri(wares.getThumbUri());
			product.setCreateTime(createTime);
			products.add(product);
		}
		return products;
	}
	

	private String generateOrderCode() {
		StringBuffer buffer = new StringBuffer();
		buffer.append((new SimpleDateFormat("yyMMddHHmmss")).format(new Date()));
		buffer.append(TextUtils.uuid(5, 10));
		return buffer.toString();
	}

	private synchronized C2OrderResource applyForOrderResource(CanteenOrderParameter coParam) throws OrderResourceApplyException{
		PlainDelivery delivery = cDao.getDelivery(coParam.getDeliveryId());
		if(delivery == null){
			throw new OrderResourceApplyException("不存在id为[" + coParam.getDeliveryId() + "]的delivery");
		}
		Date now = new Date();
		if((delivery.getCloseTime() != null && now.after(delivery.getCloseTime())) || now.after(delivery.getTimePoint())){
			throw new OrderResourceApplyException("当前时间不在下单范围内[" + delivery.getTimePoint() + "~" + delivery.getCloseTime() +"]");
		}
		
		
		
		List<PlainDeliveryWares> deliveryWaresList = cDao.getDeliveryWaresList(coParam.getDeliveryId()); 
		Map<Long, PlainDeliveryWares> deliveryWaresMap = CollectionUtils.toMap(deliveryWaresList, deliveryWares->deliveryWares.getId());
		CanteenOrderResource resource = new CanteenOrderResource();
		
		//将请求参数中的所有订单条目整合到资源对象中
		for (CanteenOrderItem item : coParam.getOrderItems()) {
			PlainDeliveryWares deliveryWares = deliveryWaresMap.get(item.getDeliveryWaresId());
			if(deliveryWares == null){
				throw new OrderResourceApplyException("配送[id=" + coParam.getDeliveryId() + "]不包含商品配送[deliveryWaresId=" + item.getDeliveryWaresId() + "]");
			}else{
				resource.putCart(deliveryWares, item.getCount());
			}
		}
		//遍历所有资源请求，检测资源的可用情况，只要一个资源请求不足，便抛出异常
		for (Entry<PlainDeliveryWares, Integer> entry : resource.getDeliveryCountMap().entrySet()) {
			PlainDeliveryWares dWares = entry.getKey();
			int count = entry.getValue();
			if(dWares.getMaxCount() != null){
				int remain = dWares.getMaxCount() - dWares.getCurrentCount();
				if(remain < count){
					throw new OrderResourceApplyException("商品[" + dWares.getWaresId() + "]的剩余资源不足", remain, count);
				}
			}
		}
		//占据资源
		occupy(resource);
		return resource;
	}

	private synchronized void occupy(CanteenOrderResource resource) {
		resource.getDeliveryCountMap().forEach((dWares, count)->{
			int updateCount = dWares.getCurrentCount() + count;
			cDao.updateCurrentCount(dWares.getId(), updateCount);
		});
	}
	
	@Override
	public CanteenDelivery getDeliveryOfThisWeek() {
		PlainDelivery pDelivery = cDao.getDeliveryOfThisWeek(new Date());
		CanteenDelivery cDelivery = new CanteenDelivery();
		cDelivery.setDeliveryId(pDelivery.getId());
		cDelivery.setLocationName(pDelivery.getLocationName());
		cDelivery.setTimePointStart(pDelivery.getTimePoint());
		cDelivery.setTimePointEnd(pDelivery.getClaimEndTime());
		
		List<CanteenDeliveyWares> waresList = cDao.getCanteenDeliveryWares(pDelivery.getId());
		cDelivery.setWaresList(waresList);
		return cDelivery;
	}
	
	@Override
	public CanteenUserCacheInfo getUserCacheInfo(Long userId) {
		PlainCanteenOrder order = cDao.getLastOrderOfUser(userId);
		if(order != null){
			CanteenUserCacheInfo userInfo = new CanteenUserCacheInfo();
			PlainOrder pOrde = order.getpOrder();
			userInfo.setName(pOrde.getReceiverName());
			userInfo.setContact(pOrde.getReceiverContact());
			userInfo.setUserId(userId);
			userInfo.setDepart(order.getDepart());
			return userInfo;
		}
		return null;
	}
	
	@Override
	public List<CanteenOrderUpdateItem> getOrderItems(Long orderId) {
		List<PlainProduct> products = cDao.getProducts(orderId);
		Map<Long, CanteenOrderUpdateItem> itemMap = new LinkedHashMap<Long, CanteenOrderUpdateItem>();
		products.forEach(product->{
			CanteenOrderUpdateItem item = itemMap.get(product.getDeliveryWaresId());
			if(item == null){
				item = new CanteenOrderUpdateItem();
				item.setCount(1);
				item.setWaresName(product.getName());
				item.setdWaresId(product.getDeliveryWaresId());
				item.setUnitPrice(product.getPrice());
				item.setThumbUri(product.getThumbUri());
				itemMap.put(product.getDeliveryWaresId(), item);
			}else{
				item.setCount(item.getCount() + 1);
			}
		});
		return new ArrayList<CanteenOrderUpdateItem>(itemMap.values());
		
	}
	
	@Override
	public PlainCanteenOrder getCanteenOrder(Long orderId) {
		PlainCanteenOrder cOrder = cDao.getCanteenOrder(orderId);
		if(cOrder != null){
			PlainOrder order = cDao.getOrder(orderId);
			cOrder.setpOrder(order);
		}
		return cOrder;
	}
	
	@Override
	public CanteenDelivery getCanteenDelivery(Long deliveryId) {
		PlainDelivery delivery = cDao.getDelivery(deliveryId);
		if(delivery != null){
			CanteenDelivery result = new CanteenDelivery();
			result.setDeliveryId(deliveryId);
			result.setLocationName(delivery.getLocationName());
			result.setTimePointStart(delivery.getTimePoint());
			result.setTimePointEnd(delivery.getClaimEndTime());
			List<CanteenDeliveyWares> waresList = cDao.getCanteenDeliveryWares(deliveryId);
			result.setWaresList(waresList);
			return result;
		}
		return null;
	}
	
	@Override
	public PlainCanteenOrder updateOrder(CanteenOrderParameter param) throws OrderResourceApplyException {
		//获得原本的订单对象
		Long orderId = param.getOriginOrderId();
		//获得原始的canteen订单对象
		PlainCanteenOrder cOrder = cDao.getCanteenOrder(orderId);
		if(cOrder != null){
			PlainOrder pOrder = cDao.getOrder(orderId);
			cOrder.setpOrder(pOrder);
			replaceOrder(param, cOrder);
			cDao.updateOrder(cOrder);
			
			
			//获得原始的订单明细
			List<CanteenOrderUpdateItem> originOrderItems = getOrderItems(orderId);
			
			Map<Long, CanteenOrderUpdateItem> originDWaresItemMap = CollectionUtils.toMap(originOrderItems, item->item.getdWaresId());
			
			
			//需要添加的节点列表
			Map<Long, Integer> addItems = new LinkedHashMap<Long, Integer>();
			
			//获得参数中需要更新的所有节点
			Map<Long, CanteenOrderItem> updateWaresItemMap = new LinkedHashMap<Long, CanteenOrderItem>();
			for (CanteenOrderItem orderItem : param.getOrderItems()) {
				CanteenOrderItem exist = updateWaresItemMap.get(orderItem.getDeliveryWaresId());
				if(exist == null){
					exist = new CanteenOrderItem();
					exist.setDeliveryWaresId(orderItem.getDeliveryWaresId());
					exist.setWaresId(orderItem.getWaresId());
					exist.setCount(orderItem.getCount());
					updateWaresItemMap.put(exist.getDeliveryWaresId(), exist);
				}else{
					exist.setCount(exist.getCount() + 1);
				}
			}
			
			Set<Long> originDWaresIdSet = originDWaresItemMap.keySet();
			//遍历更新的节点，与原始节点相比，count大于原始的话，说明要增加
			updateWaresItemMap.forEach((deliveryWaresId, item)->{
				CanteenOrderUpdateItem originDWaresItem = originDWaresItemMap.get(deliveryWaresId);
				if(originDWaresItem != null){
					originDWaresIdSet.remove(deliveryWaresId);
					addItems.put(deliveryWaresId, item.getCount() - originDWaresItem.getCount());
				}else{
					addItems.put(deliveryWaresId, item.getCount());
				}
			});
			//origin中与update的差集，即被删除的节点
			originDWaresIdSet.forEach(originDwaresId->{
				addItems.put(originDwaresId, -originDWaresItemMap.get(originDwaresId).getCount());
			});
			
			//更新该订单的所有节点
			updateProduct(orderId, addItems);
			return null;
			
		}
		return null;
	}
	
	/**
	 * 覆盖订单的字段
	 * @param param
	 * @param cOrder
	 */
	private void replaceOrder(CanteenOrderParameter param,
			PlainCanteenOrder cOrder) {
		cOrder.setDepart(param.getDepart());
		PlainOrder pOrder = cOrder.getpOrder();
		
		pOrder.setComment(param.getComment());
		pOrder.setReceiverContact(param.getContact());
		pOrder.setReceiverName(param.getReceiverName());
		pOrder.setTotalPrice(param.getTotalPrice());
		
	}

	private void updateProduct(Long orderId, Map<Long, Integer> addItems) throws OrderResourceApplyException {
		//根据订单id获得系统内订单的所有产品的idmap，key是配送产品的id
		Map<Long, List<Long>> dWaresProductIdMap = cDao.getDeliveryWaresProductIdsMap(orderId);
		
		for (Entry<Long, Integer> entry : addItems.entrySet()) {
			Long dWaresId = entry.getKey();
			Integer count = entry.getValue();
			if(count > 0){
				//添加
				//申请资源
				applyResource(dWaresId, count);
				//添加订单的产品
				cDao.appendProduct(orderId, dWaresId, count);
			}else if(count < 0){
				//删除
				List<Long> productIds = dWaresProductIdMap.get(dWaresId);
				List<Long> delList = productIds.subList(0, -count);
				//删除前几个产品
				cDao.deleteProducts(delList);
				//释放资源
				applyResource(dWaresId, count);
			}
		}
		
	}

	/**
	 * 配送商品的资源申请。如果余量不足，则抛出异常
	 * @param dWaresId 商品配送的id
	 * @param count 需要申请（释放）的资源数
	 * @throws OrderResourceApplyException 
	 */
	private synchronized void applyResource(long dWaresId, int count) throws OrderResourceApplyException {
		//获得余量
		Integer remain = cDao.getDeliveryWaresRemain(dWaresId);
		
		if(remain != null){
			if(remain > count){
				cDao.addCurrentCount(dWaresId, count);
			}else{
				throw new OrderResourceApplyException("资源不足", remain, count);
			}
		}
	}
	
	@Override
	public CanteenUserCacheInfo getOrderUserInfo(Long orderId) {
		return cDao.getOrderUserInfo(orderId);
			
	}
	
	
	public List<PlainOrder> getWaresPageList(UserIdentifier user, CommonPageInfo pageInfo){
		List<PlainOrder> orderList = cDao.getOrderPageList((long) user.getId(), pageInfo);
		return orderList;
	}
	
	public Map<PlainOrder, List<CanteenOrderUpdateItem>> getCanteenOrderUpdateItemList(List<PlainOrder> orderList){
		Map<PlainOrder, List<CanteenOrderUpdateItem>> result = new HashMap<PlainOrder, List<CanteenOrderUpdateItem>>();
		if(orderList != null && orderList.size() > 0){
			for(PlainOrder plainOrder : orderList){
				result.put(plainOrder, getOrderItems(plainOrder.getId()));
			}
		}
		return result;
	}

	@Override
	public Map<PlainOrder, PlainCanteenOrder> getPlainCanteenOrderMap(List<PlainOrder> orderList) {
		Map<PlainOrder, PlainCanteenOrder> result = new HashMap<>();
		if(orderList != null && orderList.size() > 0){
			for(PlainOrder plainOrder : orderList){
				result.put(plainOrder, getCanteenOrder(plainOrder.getId()));
			}
		}
		return result;
	}
	
}
