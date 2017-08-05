package cn.sowell.ddxyz.model.common2.core;

import java.util.Set;

/**
 * 
 * <p>Title: C2OrderResource</p>
 * <p>Description: </p><p>
 * 请求后的订单资源
 * </p>
 * @author Copperfield Zhang
 * @date 2017年8月2日 上午8:52:34
 */
public interface C2OrderResource {
	/**
	 * 获得分配资源的总数
	 * @return
	 */
	int getResourceCount();
	
	/**
	 * 获得分配的资源的集合
	 * @return 
	 */
	Set<C2DispenseResource> getDispenseResources();
}
