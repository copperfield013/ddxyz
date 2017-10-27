package cn.sowell.ddxyz.model.common.core.result;

public class CheckResult {
	private boolean isSuc = false;
	private String msg;
	private String reason;
	private Object data;
	public CheckResult(boolean defaultResult, String defaultMsg) {
		isSuc = defaultResult;
		msg = defaultMsg;
	}
	
	public boolean isSuc() {
		return isSuc;
	}
	public CheckResult isSuc(boolean isSuc) {
		this.isSuc = isSuc;
		return this;
	}
	public String getMsg() {
		return msg;
	}
	public CheckResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public CheckResult setResult(boolean isSuc, String reason){
		this.isSuc = isSuc;
		this.reason = reason;
		return this;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
