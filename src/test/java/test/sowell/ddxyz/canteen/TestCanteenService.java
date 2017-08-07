package test.sowell.ddxyz.canteen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.ddxyz.model.canteen.pojo.CanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.PlainCanteenOrder;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderItem;
import cn.sowell.ddxyz.model.canteen.pojo.param.CanteenOrderParameter;
import cn.sowell.ddxyz.model.canteen.service.CanteenService;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlanWares;
import cn.sowell.ddxyz.model.common2.core.OrderResourceApplyException;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
public class TestCanteenService {
	
	@Resource
	CanteenService canteenService;
	
	@Resource
	FrameDateFormat dateFormat;
	
	public void saveWares(){
		PlainWares wares = new PlainWares();
		wares.setBasePrice(20);
		canteenService.saveWares(wares);
	}
	@Test
	public void savePlan(){
		
		PlainDeliveryPlan plan = new PlainDeliveryPlan();
		List<PlainDeliveryPlanWares> planWaresList = new ArrayList<PlainDeliveryPlanWares>();
		plan.setLocationId(35l);
		plan.setPeriod("Y2017。M5,6,7,8,9。W5。T16。");
		plan.setStartDate(dateFormat.parse("2017-1-1"));
		plan.setEndDate(dateFormat.parse("2017-10-1"));
		plan.setLeadMinutes(1440);
		plan.setCreateTime(new Date());
		
		PlainDeliveryPlanWares planWares = new PlainDeliveryPlanWares();
		planWares.setMaxCount(100);
		planWares.setWaresId(2l);
		planWaresList.add(planWares);
		
		PlainDeliveryPlanWares planWares1 = new PlainDeliveryPlanWares();
		planWares1.setMaxCount(100);
		planWares1.setWaresId(3l);
		planWaresList.add(planWares1);
		
		
		canteenService.savePlan(plan, planWaresList);
	}
	
	@Test
	public void testGenerateDelivery(){
		canteenService.generateTheDayDeliveries(dateFormat.incDay(new Date(), 4));
	}
	
	
	@Test
	public void testCreate() throws OrderResourceApplyException {
		CanteenOrderParameter coParam = new CanteenOrderParameter();
		coParam.setDeliveryId(561l);
		
		coParam.setContact("15657198552");
		coParam.setReceiverName("张荣波");
		coParam.setDepart("研发部");
		coParam.setTotalPrice(9000);
		coParam.setUserId(14l);
		coParam.setComment("无");
		
		List<CanteenOrderItem> orderItems = new ArrayList<CanteenOrderItem>();
		CanteenOrderItem item = new CanteenOrderItem();
		item.setDeliveryWaresId(5l);
		item.setWaresId(2l);
		item.setCount(2);
		orderItems.add(item);
		
		CanteenOrderItem item1 = new CanteenOrderItem();
		item1.setDeliveryWaresId(6l);
		item1.setWaresId(3l);
		item1.setCount(2);
		orderItems.add(item1);
		
		coParam.setOrderItems(orderItems);
		
		PlainCanteenOrder order = canteenService.createOrder(coParam);
		System.out.println(order);
	}
	
	
	
	
	@Test
	public void testDeliveryWares() {
		CanteenDelivery delivery = canteenService.getDeliveryOfThisWeek();
		System.out.println(delivery);
	}
	
	
	
	
	
	
	
	
	
	
}
