package cn.sowell.ddxyz.model.kanteen.service.impl;

public class OrderPayException extends Exception {
	private static final long serialVersionUID = 2411689838637276369L;
	private boolean paied = false;
	public OrderPayException(String msg) {
		super(msg);
	}
	public OrderPayException(String msg, boolean paied) {
		super(msg);
		this.paied = paied;
	}
	public boolean isPaied() {
		return paied;
	}
	public void setPaied(boolean paied) {
		this.paied = paied;
	}
	

}
