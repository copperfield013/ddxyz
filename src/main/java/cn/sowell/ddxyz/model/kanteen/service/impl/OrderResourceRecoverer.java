package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.sowell.ddxyz.model.kanteen.service.KanteenOrderService;

public class OrderResourceRecoverer {
	
	final KanteenOrderService orderService;
	private ScheduledExecutorService pool;
	
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
				orderService.recoverUnpayResource();
			}
		};
		
		pool.scheduleWithFixedDelay(task, 0, 3, TimeUnit.SECONDS);

	}
}
