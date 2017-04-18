package cn.sowell.ddxyz.model.common.core;

import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;


public interface OrderPayParameter {
	Integer getActualPay();
	
	WeiXinUser getPayUser();
	
}
