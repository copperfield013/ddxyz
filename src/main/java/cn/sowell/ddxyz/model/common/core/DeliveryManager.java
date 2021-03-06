package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.sowell.copframe.utils.range.DateRange;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;


/**
 * 
 * <p>Title: DeliveryManager</p>
 * <p>Description: </p><p>
 * 配送管理<br/>
 * 配送管理的主要职责是用于管理当前系统中配送信息
 * 并且提供方法用于请求订单时，能够生成并且返回订单信息
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月29日 上午9:39:09
 */
public interface DeliveryManager {
	
	/**
	 * 根据时间点和配送地点获得对应的配送对象
	 * @param date
	 * @param location
	 * @return
	 */
	Delivery getDelivery(Long waresId, DeliveryTimePoint date, DeliveryLocation location);
	
	/**
	 * 根据标识获得配送信息对象
	 * @param id 标识
	 * @return 配送信息对象
	 */
	Delivery getDelivery(Serializable deliveryId);
	
	
	/**
	 * 检查订单请求是否可用
	 * @param request 订单请求对象
	 * @param orderToken 创建订单用的token
	 * @return 可用返回true，不可用返回false
	 */
	CheckResult checkOrderParameterAvailable(OrderParameter request, OrderToken orderToken);

	/**
	 * 根据配送对象的信息，从数据库重新拉取数据并更新对象的数据
	 * @param locationId
	 * @return 
	 */
	DeliveryLocation getDeliveryLocation(long locationId);
	/**
	 * 根据配送计划构造并持久化当天所有的配送
	 * @return
	 */
	List<Delivery> loadTodayDeliveries();

	/**
	 * 清除最近操作时间在某个时间范围捏的配送缓存
	 * @param range
	 */
	void clearCache(DateRange range);
	
	/**
	 * 获得某个时间点的余量
	 * @param waresId 商品id
	 * @param timepoint 时间点
	 * @return 为null时，表示该时间点没有限制，否则返回该时间点的余量
	 */
	Integer getTimepointRemain(Long waresId, Date timepoint);
	
}
