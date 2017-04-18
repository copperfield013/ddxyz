package cn.sowell.ddxyz.model.common.core;

import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;


public class DefaultOrderPayParameter implements OrderPayParameter{
	private Integer actualPay;
	private WeiXinUser payUser;
	
	public DefaultOrderPayParameter(WeiXinUser payUser) {
		this.payUser = payUser;
	}
	
	@Override
	public Integer getActualPay() {
		return actualPay;
	}

	public void setActualPay(Integer actualPay) {
		this.actualPay = actualPay;
	}

	@Override
	public WeiXinUser getPayUser() {
		return payUser;
	}

}
