package cn.sowell.copframe.common;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.merchant.service.MerchantDisabledRuleService;
import cn.sowell.ddxyz.model.message.service.MessageConfigService;

public class SpringContextRefreshListener implements ApplicationListener<ContextRefreshedEvent>{

	@Resource
	DeliveryManager dManager;
	
	@Resource
	MerchantDisabledRuleService ruleService;
	
	@Resource
	MessageConfigService messaageConfigService;
	
	private Logger logger = Logger.getLogger(SpringContextRefreshListener.class);
	
	//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。  
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("初始化配送管理器");
		//从持久化数据中获取配送对象或者根据配送计划生成配送对象
		dManager.loadTodayDeliveries();
		logger.info("配送管理器初始化完成");
		
		logger.info("初始化门店规则");
		ruleService.getRulesList();
		logger.info("门店规则初始化完成");
		
		logger.info("初始化自动回复规则");
		messaageConfigService.getList();
		logger.info("自动回复规则初始化完成");
	}

}
