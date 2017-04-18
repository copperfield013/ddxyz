package cn.sowell.ddxyz.model.common.core;

public interface OrderToken {
	
	/**
	 * 
	 * @return
	 */
	static OrderToken getAnonymousToken(){
		return new OrderToken(){};
	}
}
