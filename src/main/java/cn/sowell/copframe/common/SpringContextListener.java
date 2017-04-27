package cn.sowell.copframe.common;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.sowell.ddxyz.model.common.core.DeliveryManager;

public class SpringContextListener implements ApplicationListener<ContextRefreshedEvent>{

	@Resource
	DeliveryManager dManager;
	private Logger logger = Logger.getLogger(SpringContextListener.class);
	
	//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。  
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			logger .info("初始化配送管理器");
			//从持久化数据中获取配送对象或者根据配送计划生成配送对象
			dManager.loadTodayDeliveries();
			logger.info("配送管理器初始化完成");
	    }  
	}

}
