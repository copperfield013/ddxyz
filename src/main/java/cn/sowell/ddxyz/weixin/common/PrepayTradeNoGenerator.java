package cn.sowell.ddxyz.weixin.common;

import org.springframework.stereotype.Repository;

import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.weixin.pay.prepay.PrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.TradeNoGenerator;
import cn.sowell.copframe.weixin.pay.prepay.UnifiedOrder;

@Repository
public class PrepayTradeNoGenerator implements TradeNoGenerator{
	
	@Override
	public String generate(UnifiedOrder order, PrepayParameter parameter) {
		return TextUtils.uuid();
	}

}
