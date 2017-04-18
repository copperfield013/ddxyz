package cn.sowell.ddxyz.model.common.core;

import cn.sowell.copframe.common.UserIdentifier;

/**
 * 
 * <p>Title: OrderRefundParameter</p>
 * <p>Description: </p><p>
 * 退款参数
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月10日 下午7:02:16
 */
public class OrderRefundParameter {
	//退款金额
	private Integer refundFee;
	//退款备注
	private String comment;
	/**
	 * 操作用户
	 */
	private UserIdentifier operateUser;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public UserIdentifier getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(UserIdentifier operateUser) {
		this.operateUser = operateUser;
	}
	public Integer getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}
}
