package test.sowell.copframe;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sowell.copframe.weixin.common.service.WxConfigService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
public class WxConfigServiceTest {
	
	@Resource
	WxConfigService configService;
	
	Logger logger = Logger.getLogger(WxConfigServiceTest.class);
	
	@Test
	public void testNotifyURL() throws Exception {
		logger.info(configService.getPayNotifyURL());
	}
}
