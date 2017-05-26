package test.sowell.ddxyz.common.core;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sowell.copframe.weixin.message.exception.WeiXinMessageException;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.weixin.service.WeiXinMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
public class WxMessageServiceTest {
	
	@Resource
	WeiXinMessageService messageService;
	
	@Resource
	OrderService oService;
	
	@Test
	public void sendMsg(){
		Order order = oService.getOrder(235l);
		try {
			messageService.sendNewOrderMessage(order, "ovZxms3dvkaZR2aFpmkWh2SxmCTY");
		} catch (WeiXinMessageException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sendPaiedMsg(){
		Order order = oService.getOrder(237L);
		try {
			messageService.sendOrderPaiedMessage(order);
		} catch (WeiXinMessageException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
