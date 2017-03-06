package cn.sowell.copframe.utils.converter;

public class ConverteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778084673142690493L;
	public ConverteException(Throwable e){
		super(e);
	}
	public ConverteException(String msg, Throwable e){
		super(msg, e);
	}
}
