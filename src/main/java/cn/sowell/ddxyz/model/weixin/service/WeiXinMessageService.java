package cn.sowell.ddxyz.model.weixin.service;

import cn.sowell.copframe.weixin.message.exception.WeiXinMessageException;
import cn.sowell.ddxyz.model.common.core.Order;
/**
 * 
 * <p>Title: WeiXinMessageService</p>
 * <p>Description: </p><p>
 * 微信消息服务
 * </p>
 * @author Copperfield Zhang
 * @date 2017年5月25日 下午4:18:33
 */
public interface WeiXinMessageService {
	/**
	 * 向某个管理员发送新订单的通知
	 * @param openid
	 * @param order
	 * @throws WeiXinMessageException
	 */
	void sendNewOrderMessage(Order order, String... openids)
			throws WeiXinMessageException;
	/**
	 * 向订单的支付用户发送已支付的消息
	 * @param order
	 * @throws WeiXinMessageException
	 */
	void sendOrderPaiedMessage(Order order) throws WeiXinMessageException;
	
}
