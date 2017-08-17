package cn.sowell.ddxyz.model.weixin.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.copframe.utils.text.TextHandler;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.message.exception.WeiXinMessageException;
import cn.sowell.copframe.weixin.message.service.WxTemplateMessageService;
import cn.sowell.copframe.weixin.message.template.TemplateMessageRequestParameter;
import cn.sowell.copframe.weixin.message.template.config.WxMessageTemplateConfig;
import cn.sowell.copframe.weixin.message.template.config.WxMessageTemplateHandler;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;
import cn.sowell.ddxyz.model.drink.service.DrinkOrderService;
import cn.sowell.ddxyz.model.weixin.pojo.templateMsg.WxNewOrderMessage;
import cn.sowell.ddxyz.model.weixin.pojo.templateMsg.WxOrderPaiedMessage;
import cn.sowell.ddxyz.model.weixin.service.WeiXinMessageService;

@Service
public class WeiXinMessageServiceImpl implements WeiXinMessageService{

	@Resource
	WxTemplateMessageService tMessageService;
	
	@Resource
	FrameDateFormat dateFormat;
	
	@Resource
	WxMessageTemplateConfig mtConfig;
	
	@Resource
	WxConfigService configService;
	
	@Override
	public void sendNewOrderMessage(Order order, String... openids) throws WeiXinMessageException {
		if(openids.length != 0){
			PlainOrder pOrder = (PlainOrder) order.getOrderDetail();
			WxMessageTemplateHandler handler = mtConfig.getHandler("newOrder", configService.getAppKey());
			TemplateMessageRequestParameter parameter = 
					handler.build(
							null, WxNewOrderMessage.class, tempInfo->{
								tempInfo.setPrevMsg("收到一个新的订单");
								tempInfo.setOrderCode(pOrder.getOrderCode());
								tempInfo.setContact(pOrder.getReceiverContact());
								tempInfo.setOrderContent(getOrderContent(order));
								tempInfo.setAddress(pOrder.getLocationName());
								tempInfo.setTimePoint(dateFormat.formatDateTime(pOrder.getTimePoint()));
								tempInfo.setPostMsg("请及时处理");
							}
							);
			for (String openid : openids) {
				parameter.setToUserOpenId(openid);
				tMessageService.sendMessage(parameter);
			}
		}
	}
	
	@Override
	public void sendOrderPaiedMessage(Order order) throws WeiXinMessageException{
		Assert.notNull(order);
		if(order.getOrderStatus() >= Order.STATUS_PAYED){
			PlainOrder pOrder = (PlainOrder) order.getOrderDetail();
			String openid = order.getOrderUser().getOpenid();
			WxMessageTemplateHandler handler = mtConfig.getHandler("orderPaied", configService.getAppKey());
			DecimalFormat df = new DecimalFormat("0.00");
			tMessageService.sendMessage(handler.build(openid, WxOrderPaiedMessage.class, msg->{
				msg.setPrevMsg("订单已成功支付");
				msg.setDetail("商品数量：" + order.getProductSet().size() + "，总价：" + df.format(FormatUtils.toDouble(pOrder.getActualPay()) / 100) + "元");
				msg.setAddress(pOrder.getLocationName());
				msg.setTimePoint(dateFormat.formatDateTime(pOrder.getTimePoint()));
				msg.setAmount(String.valueOf(pOrder.getTotalPrice()));
				msg.setPostMsg("请在取餐时间二十分钟内到取餐地址领取商品，过时不候。感谢您对点点新意的支持");
			}));
		}else{
			throw new WeiXinMessageException("订单还没有支付，不能发送付款通知");
		}
	}
	

	@Resource
	DrinkOrderService drinkService;
	private String getOrderContent(Order order) {
		List<PlainOrderDrinkItem> items = drinkService.getOrderDrinkItemList((Long) order.getKey());
		
		TextHandler content = new TextHandler("订单内包含#{cupCount}杯饮料，");
		items.forEach(p->{
			content.appendText(p.getDrinkName() + "，");
		});
		content.trim("，");
		content.setParameter("cupCount", items.size());
		return content.getText();
	}


}
