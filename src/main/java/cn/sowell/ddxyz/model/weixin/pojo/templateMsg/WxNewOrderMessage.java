package cn.sowell.ddxyz.model.weixin.pojo.templateMsg;

public class WxNewOrderMessage {
	private String prevMsg;
	private String orderCode;
	private String contact;
	private String orderContent;
	private String address;
	private String timePoint;
	private String postMsg;
	public String getPrevMsg() {
		return prevMsg;
	}
	public void setPrevMsg(String prevMsg) {
		this.prevMsg = prevMsg;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getOrderContent() {
		return orderContent;
	}
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
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
