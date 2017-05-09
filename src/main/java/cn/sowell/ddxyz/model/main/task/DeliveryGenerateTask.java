package cn.sowell.ddxyz.model.main.task;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.sowell.copframe.utils.range.DateRange;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.OrderManager;
import cn.sowell.ddxyz.model.common.core.ProductManager;

@Component
public class DeliveryGenerateTask {

	
	public DeliveryGenerateTask() {
		System.out.println(1111);
	}
	@Resource
	DeliveryManager dManager;
	
	@Resource
	OrderManager oManager;
	
	@Resource
	ProductManager pManager;
	
	
	/**
	 * 每天0点根据配送计划生成一次当天的配送
	 */
	@Scheduled(cron="0 30 0 * * ?")  
	public void generateTodayDeliveries() {
		dManager.loadTodayDeliveries();
	}
	
	/**
	 * 每天0点清理一次内存中当前之前的所有配送、订单和产品
	 */
	@Scheduled(cron="0 16/2 15 * * ?")
	public void clearCache(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, -12);
		DateRange range = new DateRange(null, cal.getTime());
		pManager.clearCache(range);
		oManager.clearCache(range);
		dManager.clearCache(range);
	}
	
	
	
}
