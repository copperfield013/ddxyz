package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.copframe.weixin.pay.exception.WeiXinPayException;
import cn.sowell.copframe.weixin.pay.paied.WxPayStatus;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.copframe.weixin.pay.prepay.PrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.PrepayResult;
import cn.sowell.copframe.weixin.pay.refund.RefundRequest;
import cn.sowell.copframe.weixin.pay.refund.RefundResult;
import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenTrolleyWares;
import cn.sowell.ddxyz.model.common.core.OrderRefundParameter;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDao;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenDistributionMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenOrderCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyItem;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyWares;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenWaresOptionGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenCancelOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchantAnnounce;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenProduct;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenReceiver;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenSection;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.service.KanteenService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

@Service
public class KanteenServiceImpl implements KanteenService {

	@Resource
	KanteenDao kDao;
	
	@Resource
	FrameDateFormat dateFormat;
	@Resource
	WxPayService payService;
	
	@Override
	public PlainKanteenMerchant getMerchant(Long merchantId) {
		PlainKanteenMerchant merchant = kDao.get(merchantId, PlainKanteenMerchant.class);
		if(merchant != null){
			List<PlainKanteenMerchantAnnounce> announces = kDao.getOrderedAnnounces(merchant.getAnnounceKey());
			merchant.setAnnounces(CollectionUtils.toList(announces, anno->anno.getText()));
		}
		return merchant;
	}

	@Override
	public PlainKanteenDistribution getDistributionOfThisWeek(Long merchantId,
			Date date) {
		if(date != null){
			Date[] range = dateFormat.getTheWeekRange(date, Calendar.MONDAY);
			return kDao.getDistribution(merchantId, range);
		}
		return null;
	}

	@Override
	public KanteenMenu getKanteenMenu(Long distributionId) {
		PlainKanteenDistribution distribution = kDao.get(distributionId, PlainKanteenDistribution.class);
		if(distribution != null){
			KanteenMenu menu = new KanteenMenu();
			PlainKanteenMenu pmenu = kDao.get(distribution.getMenuId(), PlainKanteenMenu.class);
			menu.setPlainMenu(pmenu);
			//获得菜单内所有的商品组
			List<PlainKanteenWaresGroup> menuGroups = kDao.getMenuWaresGroups(pmenu.getId());
			//获得配销对应的所有商品
			List<KanteenDistributionMenuItem> menuItems = kDao.getMenuItems(distributionId);
			//获得group关于id的map
			Map<Long, PlainKanteenWaresGroup> menuGroupMap = CollectionUtils.toMap(menuGroups, group->group.getId());
			//整合商品组和商品
			Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap = CollectionUtils.toListMap(menuItems, item->menuGroupMap.get(item.getGroupId()));
			//根据条件筛掉菜单内的商品和商品组
			filter(menuItemMap);
			//对菜单进行排序
			menuItemMap = sort(menuItemMap);
			menu.setMenuItemMap(menuItemMap);
			
			Set<Long> waresIds = new HashSet<Long>();
			menuItemMap.values().forEach(itemList->itemList.forEach(item->waresIds.add(item.getWaresId())));
			Map<Long, List<KanteenWaresOptionGroup>> waresOptionGroupMap = kDao.getMenuWaresOptionGroupsMap(waresIds);
			menu.setWaresOptionGroupMap(waresOptionGroupMap);
			
			return menu;
		}
		return null;
	}

	static final Integer DISABLED = 1;
	
	/**
	 * 对menuItemMap内的所有元素进行筛选
	 * @param menuItemMap
	 */
	private void filter(
			Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap) {
		Iterator<Entry<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>>> itrEntry = menuItemMap.entrySet().iterator();
		while(itrEntry.hasNext()){
			Entry<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> entry = itrEntry.next();
			PlainKanteenWaresGroup group = entry.getKey();
			if(DISABLED.equals(group.getDisabled())){
				//禁用所有disabled的商品组
				itrEntry.remove();
			}else{
				Iterator<KanteenDistributionMenuItem> itrItems = entry.getValue().iterator();
				while(itrItems.hasNext()){
					KanteenDistributionMenuItem item = itrItems.next();
					if(DISABLED.equals(item.getDisabled())){
						//禁用所有disabled的商品
						itrItems.remove();
					}
				}
			}
		}
		
	}
	
	/**
	 * 对menuItemMap进行排序，并且返回排序后的对象
	 * @param menuItemMap
	 * @return
	 */
	private Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> sort(
			Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap) {
		TreeMap<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> map = new TreeMap<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>>(Orderable.COMPARATOR);
		map.putAll(menuItemMap);
		map.forEach((group, itemList) ->{
			itemList.sort(Orderable.COMPARATOR);
		});
		return map;
	}


	@Override
	public KanteenTrolley getTrolley(Long userId, Long distributionId) {
		PlainKanteenDistribution distribution = kDao.get(distributionId, PlainKanteenDistribution.class);
		if(distribution != null){
			PlainKanteenTrolley pTrolley = kDao.getTrolley(userId, distributionId);
			//构造购物车对象
			KanteenTrolley trolley = new KanteenTrolley();
			if(pTrolley == null){
				pTrolley = new PlainKanteenTrolley();
				pTrolley.setCreateTime(new Date());
				pTrolley.setUserId(userId);
				pTrolley.setDistributionId(distributionId);
				pTrolley.setMerchantId(distribution.getMerchantId());
				Long id = kDao.create(pTrolley);
				pTrolley.setId(id);
			}else{
				//获得购物车内的所有商品
				List<KanteenTrolleyWares> trolleyWares = kDao.getTrolleyWares(pTrolley.getId()),
						validWares = new ArrayList<KanteenTrolleyWares>(),
						invalidWares = new ArrayList<KanteenTrolleyWares>();
				if(trolleyWares != null && trolleyWares.size() > 0){
					//获得配销对应的所有商品
					List<PlainKanteenDistributionWares> distributionWares = kDao.getDistributionWares(new HashSet<Long>(CollectionUtils.toList(trolleyWares, wares->wares.getDistributionWaresId())));
					Map<Long, PlainKanteenDistributionWares> distributionWaresMap = CollectionUtils.toMap(distributionWares, wares->wares.getId());
					//将根据当前配销的商品余量限制，筛选购物车内的商品
					trolleyWares.forEach(tWares->{
						PlainKanteenDistributionWares dWares = distributionWaresMap.get(tWares.getDistributionWaresId());
						if(dWares != null &&
								(dWares.getMaxCount() == null || tWares.getCount() + dWares.getCurrentCount() <= dWares.getMaxCount())){
							validWares.add(tWares);
						}else{
							invalidWares.add(tWares);
						}
					});
					trolley.setValidWares(validWares);
					trolley.setInvalidWares(invalidWares);
				}
			}
			trolley.setPlainTrolley(pTrolley);
			return trolley;
		}
		return null;
	}

	@Override
	public List<PlainKanteenDelivery> getEnabledDeliveries(Long distributionId) {
		return kDao.getEnabledDeliveries(distributionId);
	}
	

	@Override
	public List<KanteenTrolleyItem> extractTrolleyItems(JSONObject trolleyData) {
		Pattern pattern = Pattern.compile("^id_(\\d+)$");
		Pattern tempPattern = Pattern.compile("^id_(temp_\\d+)$");
		List<KanteenTrolleyItem> list = new ArrayList<KanteenTrolleyItem>();
		trolleyData.forEach((key, value)->{
			JSONObject jTrolleyItem = (JSONObject) value;
			KanteenTrolleyItem item = new KanteenTrolleyItem();
			Matcher matcher = tempPattern.matcher(key);
			if(matcher.matches()) {
				String tempId = matcher.group(1);
				item.setTempId(tempId);
			}else {
				matcher = pattern.matcher(key);
				if(matcher.matches()) {
					Long trolleyWaresId = Long.valueOf(matcher.group(1));
					item.setTrolleyWaresId(trolleyWaresId);
				}
			}
			item.setDistributionWaresId(jTrolleyItem.getLong("dwId"));
			item.setCount(jTrolleyItem.getInteger("count"));
			String optionsStr = jTrolleyItem.getString("options");
			if(optionsStr != null) {
				item.setWaresOptionIds(CollectionUtils.toList(Arrays.asList(TextUtils.splitToArray(optionsStr, ",")), option->Long.valueOf(option)));
			}
			list.add(item);
			
		});
		return list;
	}
	
	@Override
	public void mergeTrolleyWares(Long trolleyId, List<KanteenTrolleyItem> existsItems) {
		Map<Long, Integer> originTrolleyWaresMap = kDao.getTrolleyWaresMap(trolleyId);
		Set<Long> toRemove = new HashSet<Long>(originTrolleyWaresMap.keySet());
		Map<Long, Integer> toCreate = new HashMap<Long, Integer>();
		Map<Long, Integer> toUpdate = new HashMap<Long, Integer>();
		
		
		
		
		
		trolleyWaresMap.forEach((distributionWaresId, count)->{
			if(!Integer.valueOf(0).equals(count)){
				if(originTrolleyWaresMap.containsKey(distributionWaresId)){
					toRemove.remove(distributionWaresId);
					Integer originCount = originTrolleyWaresMap.get(distributionWaresId);
					if(!originCount.equals(count)){
						toUpdate.put(distributionWaresId, count);
					}
				}else{
					toCreate.put(distributionWaresId, count);
				}
			}
		});
		
	}

	@Override
	public void mergeTrolleyWares(Long trolleyId, Map<Long, Integer> trolleyWaresMap) {
		Map<Long, Integer> originTrolleyWaresMap = kDao.getTrolleyWaresMap(trolleyId);
		Set<Long> toRemove = new HashSet<Long>(originTrolleyWaresMap.keySet());
		Map<Long, Integer> toCreate = new HashMap<Long, Integer>();
		Map<Long, Integer> toUpdate = new HashMap<Long, Integer>();
		trolleyWaresMap.forEach((distributionWaresId, count)->{
			if(!Integer.valueOf(0).equals(count)){
				if(originTrolleyWaresMap.containsKey(distributionWaresId)){
					toRemove.remove(distributionWaresId);
					Integer originCount = originTrolleyWaresMap.get(distributionWaresId);
					if(!originCount.equals(count)){
						toUpdate.put(distributionWaresId, count);
					}
				}else{
					toCreate.put(distributionWaresId, count);
				}
			}
		});
		kDao.removeTrolleyWares(trolleyId, toRemove);
		kDao.updateTrolleyWares(trolleyId, toUpdate);
		toCreate.forEach((distributionWaresId, count)->{
			PlainKanteenTrolleyWares trolleyWares = new PlainKanteenTrolleyWares();
			trolleyWares.setDistributionWaresId(distributionWaresId);
			trolleyWares.setCount(count);
			trolleyWares.setTrolleyId(trolleyId);
			trolleyWares.setCreateTime(new Date());
			trolleyWares.setUpdateTime(trolleyWares.getCreateTime());
			kDao.create(trolleyWares);
		});
	}
	
	@Override
	public PlainKanteenReceiver getLastReceiver(Long userId) {
		return kDao.getLastReceiver(userId);
	}
	
	
	@Override
	public void packOrder(KanteenOrder order, KanteenTrolley trolley) {
		PlainKanteenTrolley pTrolley = trolley.getPlainTrolley();
		PlainKanteenOrder pOrder = order.getPlainOrder();
		pOrder.setOrderUserId(pTrolley.getUserId());
		pOrder.setDistributionId(trolley.getPlainTrolley().getDistributionId());
		pOrder.setTrolleyId(trolley.getId());
		pOrder.setStatus(PlainKanteenOrder.STATUS_DEFAULT);
		Calendar calendar = Calendar.getInstance();
		pOrder.setCreateTime(calendar.getTime());
		calendar.add(Calendar.MINUTE, 15);
		pOrder.setPayExpiredTime(calendar.getTime());
		pOrder.setOrderCode(generateOrderCode(pOrder.getCreateTime()));
		List<KanteenTrolleyWares> validWareses = trolley.getValidWares();
		pOrder.setTotalPrice(trolley.getTotalValidPrice());
		
		
		PlainKanteenMerchant merchant = kDao.getMerchantByDistributionId(pOrder.getDistributionId());
		if(merchant != null){
			pOrder.setMerchantId(merchant.getId());
			pOrder.setMerchantName(merchant.getName());
			order.setPayTitle(merchant.getName());
		}else{
			throw new RuntimeException("配销[id=" + pOrder.getDistributionId() + "]找不到对应的商家");
		}
		
		
		//默认顺序
		int sectionOrder = 0;
		for (KanteenTrolleyWares validWares : validWareses) {
			PlainKanteenDistributionWares dWares = kDao.get(validWares.getDistributionWaresId(), PlainKanteenDistributionWares.class);
			if(dWares != null){
				PlainKanteenWares wares = kDao.get(dWares.getWaresId(), PlainKanteenWares.class);
				
				if(wares != null){
					PlainKanteenSection section = new PlainKanteenSection();
					section.setDistributionWaresId(validWares.getDistributionWaresId());
					section.setMenuWaresId(dWares.getMenuWaresId());
					section.setBasePrice(wares.getBasePrice());
					section.setCount(validWares.getCount());
					section.setCreateTime(pOrder.getCreateTime());
					section.setOrder(++sectionOrder);
					section.setPriceUnit(wares.getPriceUnit());
					section.setStatus(PlainKanteenSection.STATUS_DEFAULT);
					section.setThumbUri(wares.getThumbUri());
					section.setTotalPrice(wares.getBasePrice() * validWares.getCount());
					section.setUpdateTime(section.getCreateTime());
					section.setWaresId(wares.getId());
					section.setWaresName(wares.getName());
					order.getSectionList().add(section);
				}else{
					throw new RuntimeException("商品不存在[waresId=" + dWares.getId()+ "]");
				}
			}else{
				throw new RuntimeException("配销商品[distributionWaresId=" + validWares.getDistributionWaresId() + "]不存在");
			}
		}
	}
	
	private String generateOrderCode(Date date) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateFormat.format(date, "yyyyMMddHHmmss"));
		//TODO:生成订单号的规则
		return buffer.toString();
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public H5PayParameter saveOrder(KanteenOrder order) throws WeiXinPayException {
		//锁定订单内的商品
		lockResource(order);
		//保存订单对象到数据库
		PlainKanteenOrder pOrder = order.getPlainOrder();
		Long orderId = kDao.create(pOrder);
		List<PlainKanteenSection> sectionList = order.getSectionList();
		sectionList.forEach(section->{
			section.setOrderId(orderId);
			section.setCreateTime(pOrder.getCreateTime());
			Long sectionId = kDao.create(section);
			if(order.isCreateProduct()){
				for(int i = 0; i < section.getCount(); i++){
					PlainKanteenProduct product = new PlainKanteenProduct();
					product.setSectionId(sectionId);
					product.setName(section.getWaresName());
					product.setPrice(section.getBasePrice());
					product.setPriceUnit(section.getPriceUnit());
					product.setThumbUri(section.getThumbUri());
					product.setCreateTime(section.getCreateTime());
					kDao.create(product);
				}
			}
		});
		
		H5PayParameter payParameter = null;
		//如果是微信支付的话，那么调用微信接口创建微信预支付订单
		if(PlainKanteenOrder.PAYWAY_WXPAY.equals(order.getPlainOrder().getPayway())){
			//创建调用微信预支付订单的请求参数对象
			PrepayParameter prepayParameter = payService.buildPrepayParameter(order);
			//调用接口发送请求参数
			PrepayResult prepayResult = payService.sendPrepay(prepayParameter);
			if(prepayResult != null && prepayResult.returnSuccess()){
				pOrder.setWxOutTradeNo(prepayResult.getSubmitUOrder().getOutTradeNo());
				pOrder.setWxPrepayId(prepayResult.getPrepayId());
				//将微信接口返回的预支付订单数据更新到订单中
				kDao.updateOrderWxPayFields(pOrder);
			}
			//将接口返回的预付款订单id转换成前台调用微信支付接口的可用参数
			payParameter = payService.buildPayParameter(prepayResult.getPrepayId());
			
		}
		//清空购物车
		clearTrolley(order.getPlainOrder().getTrolleyId());
		return payParameter;
	}
	
	private void lockResource(KanteenOrder order) {
		List<PlainKanteenSection> sectionList = order.getSectionList();
		for (PlainKanteenSection section : sectionList) {
			Long distributionWaresId = section.getDistributionWaresId();
			if(!kDao.updateDistributionWareCurrentCount(distributionWaresId, section.getCount())){
				throw new RuntimeException("锁定[distributionWaresId=" + distributionWaresId + "]失败，添加量" + section.getCount());
			}
			
		}
	}

	@Override
	public void clearTrolley(Long trolleyId) {
		kDao.disableTrolley(trolleyId);
	}
	
	@Override
	public KanteenOrder extractOrder(JSONObject json) {
		KanteenOrder order = new KanteenOrder();
		PlainKanteenOrder pOrder = order.getPlainOrder();
		pOrder.setReceiverName(json.getString("receiverName"));
		pOrder.setReceiverContact(json.getString("receiverContact"));
		pOrder.setReceiverDepart(json.getString("receiverDepart"));
		pOrder.setDeliveryId(json.getLong("deliveryId"));
		pOrder.setLocationName(json.getString("locationName"));
		pOrder.setLocationId(json.getLong("locationId"));
		pOrder.setPayway(json.getString("payway"));
		//pOrder.setTotalPrice(json.getInteger("totalPrice"));
		pOrder.setRemark(json.getString("remark"));
		return order;
	}
	
	@Override
	public PlainKanteenOrder getOrder(Long orderId) {
		return kDao.get(orderId, PlainKanteenOrder.class);
	}
	
	
	@Override
	public void payOrder(PlainKanteenOrder order, WeiXinUser user) throws OrderPayException{
		//检测订单是否可以被支付
		if(order == null){
			throw new OrderPayException("订单不存在");
		}else if(!PlainKanteenOrder.STATUS_DEFAULT.equals(order.getStatus())){
			throw new OrderPayException("订单已支付", true);
		}else if(order.getCanceledStatus() != null){
			throw new OrderPayException("订单已取消");
		}else if(order.getPayExpiredTime().before(new Date())){
			throw new OrderPayException("已超过订单可支付时间");
		}else{
			//调用微信接口查询订单的支付状态
			WxPayStatus checkResult = payService.checkPayStatus(order.getWxOutTradeNo());
			//支付成功，修改订单状态
			if(WxPayStatus.TRADESTATUE_SUC.equals(checkResult.getTradeState())){
				order.setActualPay(checkResult.getTotalFee());
				order.setStatus(PlainKanteenOrder.STATUS_PAIED);
				kDao.updateOrderAsPaied(order);
			}else{
				throw new OrderPayException("通过微信接口查看到订单还没有成功支付，无法修改状态");
			}
		}
		
	}
	
	@Override
	public PlainKanteenOrder getOrderByOutTradeNo(String outTradeNo) {
		return kDao.getOrderByOutTradeNo(outTradeNo);
	}
	
	@Override
	public List<KanteenOrder> queryOrder(KanteenOrderCriteria criteria,
			PageInfo pageInfo) {
		List<PlainKanteenOrder> plainOrderList = kDao.query(criteria, pageInfo);
		List<KanteenOrder> orderList = new ArrayList<KanteenOrder>();
		List<PlainKanteenSection> sections = kDao.getSections(new HashSet<Long>(CollectionUtils.toList(plainOrderList, order->order.getId())));
		Map<Long, List<PlainKanteenSection>> sectionsMap = CollectionUtils.toListMap(sections, section->section.getOrderId());
		plainOrderList.forEach(pOrder->{
			KanteenOrder order = new KanteenOrder();
			order.setPlainOrder(pOrder);
			order.setSectionList(sectionsMap.get(order.getOrderId()));
			orderList.add(order);
		});
		return orderList;
	}
	
	@Override
	public Map<Long, PlainKanteenDelivery> getDeliveryMap(
			Set<Long> deliveryIds) {
		if(deliveryIds != null && deliveryIds.size() > 0){
			List<PlainKanteenDelivery> deliveries = kDao.getDeliveries(deliveryIds);
			return CollectionUtils.toMap(deliveries, delivery->delivery.getId());
		}else{
			return new HashMap<Long, PlainKanteenDelivery>();
		}
	}
	
	
	@Override
	public void hideOrder(WeiXinUser user, Long orderId) {
		checkAuthority(user, orderId, true);
		kDao.updateOrderAsDeleted(orderId, true);
	}
	
	
	
	@Override
	public void refundOrder(WeiXinUser user, Long orderId) {
		CheckResult checkResult = checkAuthority(user, orderId, true);
		PlainKanteenOrder plainOrder = (PlainKanteenOrder) checkResult.getData();
		if(PlainKanteenOrder.STATUS_DEFAULT.equals(plainOrder.getStatus())){
			throw new RuntimeException("订单状态未支付，无法退款");
		}
		OrderRefundParameter refundParam = new OrderRefundParameter();
		refundParam.setOperateUser(user);
		refundParam.setRefundFee(plainOrder.getActualPay());
		KanteenOrder order = getKanteenOrder(plainOrder);
		RefundRequest refundReq = payService.buildRefundRequest(refundParam, order);
		RefundResult result = payService.sendRefund(refundReq, true);
		if(result != null){
			if("SUCCESS".equals(result.getResultCode())){
				//退款请求成功
				kDao.updateOrderAsRefunded(orderId, result.getRefundFee());
			}else if("FAIL".equals(result.getResultCode())){
				throw new RuntimeException("调用微信退款接口时返回失败，原因是[" + result.getErrorCodeDesc() + "]");
			}else{
				throw new RuntimeException("微信退款接口返回未知状态码【" + result.getResultCode() + "】");
			}
		}else{
			//退款请求失败
			throw new RuntimeException("调用微信退款接口时发生异常，退款失败");
		}
	}
	
	
	@Override
	public void cancelOrder(WeiXinUser user, Long orderId) {
		//检查操作的权限 
		CheckResult checkResult = checkAuthority(user, orderId, true);
		PlainKanteenOrder plainOrder = (PlainKanteenOrder) checkResult.getData();
		//检查订单是否已经被取消，如果已经被取消，那么不能再次取消
		if(plainOrder.getCanceledStatus() != null){
			throw new RuntimeException("订单已被取消，不能再次取消");
		}
		
		//取消订单只能在订单已支付但是未完成的情况下执行
		if(!PlainKanteenOrder.STATUS_PAIED.equals(plainOrder.getStatus())){
			throw new RuntimeException("取消订单只能在订单已支付但是未完成的情况下执行");
		}
		//根据商家的配置检查订单的可取消性
		checkOrderCancelOption(plainOrder);
		kDao.cancelOrder(orderId);
	}
	
	
	private void checkOrderCancelOption(PlainKanteenOrder plainOrder) {
		PlainKanteenCancelOption option = kDao.getOrderCancelOption(plainOrder);
		if(option != null){
			
		}else{
			
		}
		
		
		
	}

	private KanteenOrder getKanteenOrder(PlainKanteenOrder plainOrder) {
		List<PlainKanteenSection> sections = kDao.getSections(new HashSet<Long>(Arrays.asList(plainOrder.getId())));
		KanteenOrder order = new KanteenOrder();
		order.setPlainOrder(plainOrder);
		order.setSectionList(sections);
		return order;
	}

	private CheckResult checkAuthority(WeiXinUser user, Long orderId, boolean throwException){
		CheckResult result = new CheckResult(false, "");
		if(user == null || user.getId() == null){
			result.setMsg("user不存在");
		}
		PlainKanteenOrder order = getOrder(orderId);
		if(order != null){
			result.setData(order);
			if(user.getId().equals(order.getOrderUserId())){
				result.setResult(true, "验证通过");
			}else{
				result.setMsg("用户没有权限");
			}
		}else{
			result.setMsg("订单[id=" + orderId + "]不存在");
		}
		if(!result.isSuc() && throwException){
			throw new RuntimeException(result.getMsg());
		}
		return result;
	}
	
	
	
	
}
