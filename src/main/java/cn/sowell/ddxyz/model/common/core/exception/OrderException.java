package cn.sowell.ddxyz.model.common.core.exception;


public class OrderException extends Exception {

	public OrderException(String msg) {
		super(msg);
	}

	public OrderException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -241877007600521946L;

}
