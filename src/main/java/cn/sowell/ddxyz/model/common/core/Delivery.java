package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;

/**
 * 
 * <p>Title: Delivery</p>
 * <p>Description: </p><p>
 * 配送
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月27日 下午5:35:03
 */
public interface Delivery {
	/**
	 * 配送的标识
	 * @return
	 */
	Serializable getId();
	/**
	 * 获得配送点
	 * @return
	 */
	DeliveryLocation getLocation();
	/**
	 * 获得配送时间点
	 * @return
	 */
	DeliveryTimePoint getTimePoint();
	
	/**
	 * 获得当前配送最多的配送量
	 * 如果为null，说明不限制配送量
	 * @return
	 */
	Integer getMaxCount();
	
	/**
	 * 获得当前配送已经被申请的数量
	 * @return
	 */
	int getCurrentCount();
	
	/**
	 * 获得状态
	 * @return
	 */
	int getStatus();
	
	/**
	 * 判断当前配送的资源是否足够
	 * @param reqCount 请求的资源数
	 * @return
	 */
	boolean checkAvailable(int reqCount);
	
	/**
	 * 申请分发号数组
	 * @param dispenseCount 申请的数量
	 * @param countStrict 是否数量强约束。<br/>
	 * 	如果传入true，说明只有在能够申请到dispenseCount个分发号的情况下返回对应数量长度的数组，
	 *  此时如果申请不到这么多个分发号，那么就返回null<br/>
	 *  如果传入false，说明如果能够申请到dispenseCount个分发号的话，就返回这么多，
	 *  如果申请不到这么多分发号，能申请多少就返回多少 
	 * @return 最终返回申请成功的分发号数组，因为资源有限等问题，数组长度不一定与参数dispenseCount值相同
	 */
	//DispenseCode[] applyForDispenseCode(int dispenseCount, boolean countStrict);
	
	/**
	 * 根据分发号的键获得分发号对象
	 * @param key
	 * @return
	 */
	DispenseCode getDispenseCode(Serializable key);
	/**
	 * 从缓存中移除分发号对象
	 * @param dispenseCode
	 */
	void removeDispenseCode(DispenseCode dispenseCode);
	
	
	/**
	 * 申请分配资源
	 * @param dispenseResourceRequest 资源请求
	 * @param resource 最终设置资源的对象
	 * @return
	 */
	OrderDispenseResource applyForDispenseResource(DispenseResourceRequest dispenseResourceRequest);
	
	/**
	 * 当前配送对象是否启用
	 * @return
	 */
	boolean isEnabled();
	
	/**
	 * 配送默认状态
	 */
	int STATUS_DEFAULT = 0;
	/**
	 * 配送启用状态
	 */
	int STATUS_ENABLED = 1;
	/**
	 * 配送禁用状态
	 */
	int STATUS_DISABLED = 2;
	
}
