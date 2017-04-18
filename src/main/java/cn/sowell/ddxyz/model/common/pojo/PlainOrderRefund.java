package cn.sowell.ddxyz.model.common.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_order_refund")
public class PlainOrderRefund {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//订单主键
	@Column(name="order_id")
	private Long orderId;
	
	
	//退款金额，单位分
	@Column(name="c_refund_fee")
	private Integer refundFee;
	
	//退款单号
	@Column(name="c_refund_no")
	private String refundNo;
	//备注
	@Column(name="c_comment")
	private String comment;
	
	@Column(name="create_time")
	private Date createTime = new Date();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
