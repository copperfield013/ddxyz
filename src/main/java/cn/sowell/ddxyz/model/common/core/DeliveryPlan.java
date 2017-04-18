package cn.sowell.ddxyz.model.common.core;

import java.util.Date;
import java.util.List;

/**
 * 
 * <p>Title: DeliveryPlan</p>
 * <p>Description: </p><p>
 * 配送计划
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月27日 下午4:09:14
 */
public interface DeliveryPlan {
	
	/**
	 * 获得配送地点
	 * @return
	 */
	DeliveryLocation getLocation();
	
	/**
	 * 获得最大配送量
	 * @return
	 */
	Integer getMaxDeliveryCount();
	
	/**
	 * 获得配送周期字符串
	 * @return
	 */
	String getPeriod();
	
	/**
	 * 获得计划生效时间（返回0点，即该天的0点生效）
	 * @return
	 */
	Date getEffectiveDate();
	
	/**
	 * 获得计划过期时间（返回0点，即该天的0点过期）
	 * @return
	 */
	Date getTimeoutDate();
	
	/**
	 * 生成某一天的配送<br/>
	 * 每个配送计划每次可以生成若干个配送对象
	 * @return
	 */
	public List<Delivery> generateDelivery(Date theDay);
	
}
