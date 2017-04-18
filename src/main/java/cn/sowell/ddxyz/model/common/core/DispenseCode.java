package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;

/**
 * 
 * <p>Title: DispenseCode</p>
 * <p>Description: </p><p>
 * 分发号接口
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月29日 上午9:22:18
 */
public interface DispenseCode {
	/**
	 * 获得分发号的键
	 * @return
	 */
	Serializable getKey();
	
	/**
	 * 获得分发号字符串
	 * @return
	 */
	String getCode();
	/**
	 * 获得其所属的配送
	 * @return
	 */
	Delivery getBelong();
	
}
