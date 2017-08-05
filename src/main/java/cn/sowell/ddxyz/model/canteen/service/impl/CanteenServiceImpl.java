package cn.sowell.ddxyz.model.canteen.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.canteen.dao.CanteenDao;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderItem;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderParameter;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderUpdateParam;
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
						//生成配送时间
						Calendar cal = (Calendar) theDay.clone();
						cal.set(Calendar.HOUR_OF_DAY, hour);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						pDelivery.setTimePoint(cal.getTime());
						//配送关闭时间
						cal.add(Calendar.MINUTE, -plan.getLeadMinutes());
						pDelivery.setCloseTime(cal.getTime());
						
						
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
		pOrder.setCommment(coParam.getComment());
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
			PlainWares wares = cDao.getWares(item.getWaresId());
			for (int i = 0; i < item.getCount(); i++) {
				PlainProduct product = new PlainProduct();
				product.setDeliveryWaresId(item.getDeliveryWaresId());
				product.setWaresId(wares.getId());
				product.setName(wares.getName());
				product.setPrice(item.getPrice(wares));
				product.setThumbUri(wares.getThumbUri());
				product.setCreateTime(pOrder.getCreateTime());
				products.add(product);
			}
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
	public PlainCanteenOrder updateOrder(CanteenOrderUpdateParam uParam) {
		return null;
	}

}
