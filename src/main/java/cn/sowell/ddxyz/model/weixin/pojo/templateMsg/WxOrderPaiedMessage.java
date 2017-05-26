package cn.sowell.ddxyz.model.weixin.pojo.templateMsg;

public class WxOrderPaiedMessage {
	private String prevMsg;
	private String detail;
	private String amount;
	private String address;
	private String timePoint;
	private String postMsg;
	public String getPrevMsg() {
		return prevMsg;
	}
	public void setPrevMsg(String prevMsg) {
		this.prevMsg = prevMsg;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getPostMsg() {
		return postMsg;
	}
	public void setPostMsg(String postMsg) {
		this.postMsg = postMsg;
	}
	
	
}
