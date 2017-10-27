package cn.sowell.copframe.weixin.pay.service.impl;

import java.util.Date;
import java.util.List;

import cn.sowell.copframe.weixin.pay.prepay.GoodsDetail;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

/**
 * 
 * <p>Title: WxPayOrder</p>
 * <p>Description: </p><p>
 * 用于创建微信预支付订单的对象
 * </p>
 * @author Copperfield Zhang
 * @date 2017年10月23日 上午9:39:54
 */
public interface WxPayOrder {
	/**
	 * 下单的微信用户
	 * @return
	 */
	WeiXinUser getOrderUser();

	/**
	 * 订单总价
	 * @return
	 */
	Integer getTotalPrice();

	/**
	 * 可支付过期时间
	 * @return
	 */
	Date getPayExpireTime();

	/**
	 * 订单内产品列表
	 * @return
	 */
	List<GoodsDetail> getGoodsDetails();
	
	/**
	 * 调用微信支付接口时，需要的标题
	 * @return
	 */
	String getPayTitle();

	String getWxOutTradeNo();

}
