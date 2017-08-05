package cn.sowell.ddxyz.model.common2.core;

/**
 * 
 * <p>Title: C2OrderRequest</p>
 * <p>Description: </p><p>
 * 订单请求接口
 * </p>
 * @author Copperfield Zhang
 * @date 2017年8月2日 上午8:50:42
 */
public interface C2OrderRequest {
	/**
	 * 从订单资源管理器获得资源
	 * @param orderResourceManager
	 * @return
	 */
	C2OrderResource requestResource(C2OrderResourceManager orderResourceManager);
}
