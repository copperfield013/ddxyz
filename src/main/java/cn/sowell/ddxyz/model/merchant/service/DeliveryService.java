package cn.sowell.ddxyz.model.merchant.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;

public interface DeliveryService {
	/**
	 * 根据店商品id获得当天的所有可用的配送对象，并将其结构化为时间点为key，该时间点下的所有配送的list为value的map
	 * 返回的map已经排序，按照时间点的早晚
	 * @param waresId 商品的id，如果为null，那么就将当天所有的配送对象返回
	 * @param usable 返回的配送是否需要都是当前时间可用的
	 * @return
	 * 返回的配送对象按照配送时间
	 */
	LinkedHashMap<DeliveryTimePoint, List<PlainDelivery>> getTodayDeliveries(Long waresId, boolean usable);

	/**
	 * 根据配送id获得当前配送的余量
	 * @param deliveryId
	 * @return
	 * 如果根据配送id没有找到配送对象，那么返回null
	 * 如果配送对象不限制配送量，那么返回{@link Integer#MAX_VALUE}
	 */
	Integer getDeliveryRemain(Long deliveryId);

	/**
	 * 根据商品id获得对应的所有可用配送时间点
	 * @param waresId
	 * @return
	 */
	List<DeliveryTimePoint> getTodayDeliveryTimePoints(long waresId);

	/**
	 * 根据门店id获得所有可用的配送点
	 * @param merchantId
	 * @return
	 */
	List<PlainLocation> getAllDeliveryLocation(long merchantId);
	
	/**
	 * 添加一条配送计划到系统中
	 * @param plan
	 */
	void addPlan(PlainDeliveryPlan plan);

	/**
	 * 根据订单id获得今天可用的配送，用于再次购买
	 * @param orderId
	 * @return
	 */
	Map<DeliveryTimePoint, List<PlainDelivery>> getUsableDeliveryMap(long orderId);
	
	List<PlainDeliveryPlan> getPlainDeliveryPlanPageList(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo);
	
	boolean changePlanDisabled(Long planId, Integer disabled);
	
}
