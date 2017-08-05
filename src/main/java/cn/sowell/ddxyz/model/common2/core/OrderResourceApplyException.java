package cn.sowell.ddxyz.model.common2.core;

public class OrderResourceApplyException extends Exception {

	public OrderResourceApplyException(String msg) {
		super(msg);
	}

	public OrderResourceApplyException(String msg, int remain, int count) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2733498220579544665L;

}
