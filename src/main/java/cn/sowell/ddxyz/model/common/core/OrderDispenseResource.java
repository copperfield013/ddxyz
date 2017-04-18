package cn.sowell.ddxyz.model.common.core;

import java.util.Set;

/**
 * 
 * <p>Title: OrderResource</p>
 * <p>Description: </p><p>
 * 系统请求订单时，分配的资源
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月30日 上午8:59:23
 */
public interface OrderDispenseResource {
	/**
	 * 获得分配号
	 * @return
	 */
	Set<DispenseCode> getDispenseCode();
	
	/**
	 * 添加分配号
	 * @param dispenseCode
	 */
	void addDispenseCode(DispenseCode dispenseCode);
	/**
	 * 获取的资源是否被锁定
	 * @return
	 */
	boolean isLocked();
	/**
	 * 该资源是否是请求所期望的
	 * @return
	 */
	boolean isExpect();
	/**
	 * 释放所有资源
	 */
	void releaseResource();
	
}
