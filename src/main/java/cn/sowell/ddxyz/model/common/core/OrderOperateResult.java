package cn.sowell.ddxyz.model.common.core;

public class OrderOperateResult {
	private String status;
	private String message;
	/**
	 * 支付操作
	 */
	public static final String OPERATE_PAY = "pay";
	/**
	 * 确认收货操作
	 */
	public static final String OPERATE_CONFIRM = "confirm";
	/**
	 * 评价操作
	 */
	public static final String OPERATE_APPRAISE = "appraise";
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
