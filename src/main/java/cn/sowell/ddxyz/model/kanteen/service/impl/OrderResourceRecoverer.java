package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import cn.sowell.ddxyz.model.kanteen.service.KanteenOrderService;

public class OrderResourceRecoverer {
	
	final KanteenOrderService orderService;
	private ScheduledExecutorService pool;
	
	Logger logger = Logger.getLogger(OrderResourceRecoverer.class);
	
	public OrderResourceRecoverer(KanteenOrderService orderService) {
		super();
		this.orderService = orderService;
	}



	public void start() {
		if(pool != null){
			pool.shutdown();
		}
		pool = Executors.newScheduledThreadPool(1);
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				try {
					orderService.recoverUnpayResource();
				} catch (Exception e) {
					logger.error("回收资源时发生错误", e);
				}
			}
		};
		
		pool.scheduleWithFixedDelay(task, 0, 3, TimeUnit.SECONDS);

	}
}
