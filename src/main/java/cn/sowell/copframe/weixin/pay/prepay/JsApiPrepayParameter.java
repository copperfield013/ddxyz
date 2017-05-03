package cn.sowell.copframe.weixin.pay.prepay;

import java.util.Date;

/**
 * 
 * <p>Title: JsApiPrepayParameter</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月10日 下午5:40:55
 * @see {@link UnifiedOrder}
 */
public class JsApiPrepayParameter implements PrepayParameter{
	
	/**
	 * 下订单的客户openid
	 */
	private String openId;
	/**
	 * 订单总价，单位：分
	 */
	private Integer totalFee;
	/**
	 * 商品简单描述，该字段请按照规范传递，具体请见参数规定(腾讯充值中心-QQ会员充值)
	 */
	private String body;
	/**
	 * 商品详情
	 */
	private OrderDetail orderDetail;
	
	/**
	 * 设置过期时间
	 */
	private Date expireTime;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

}
