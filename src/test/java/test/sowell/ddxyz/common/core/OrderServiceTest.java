package test.sowell.ddxyz.common.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sowell.copframe.weixin.pay.paied.WxPayStatus;
import cn.sowell.ddxyz.model.common.core.DefaultOrderPayParameter;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderParameter;
import cn.sowell.ddxyz.model.common.core.OrderToken;
import cn.sowell.ddxyz.model.common.core.ProductItemParameter;
import cn.sowell.ddxyz.model.common.core.ProductsParameter;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.impl.DefaultProductItemParameter;
import cn.sowell.ddxyz.model.common.core.impl.DrinkDataHandler;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.service.OrderService;
import cn.sowell.ddxyz.model.drink.term.OrderTerm;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.model.weixin.service.WeiXinUserService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
public class OrderServiceTest {
	@Resource
	DeliveryManager dManager;
	
	@Resource
	OrderService orderService;
	
	@Resource
	WeiXinUserService userService;
	
	@Resource
	DeliveryService dService;
	
	@Test
	public void applyOrderTest(){
		//创建订单参数
		OrderParameter orderParameter = new OrderParameter(1l);
		//设置下单的地址
		orderParameter.setDeliveryLocation(new DeliveryLocation(1l));
		//设置下单的时间点
		Calendar cal = Calendar.getInstance();
		cal.set(2017, 3, 5, 13, 0, 0);
		orderParameter.setTimePoint(new DeliveryTimePoint(cal.getTime()));
		//设置下单的总价
		orderParameter.setTotalPrice(1500);
		//构造产品参数
		ProductsParameter productParam = new ProductsParameter();
		List<ProductItemParameter> productItemParamList = new ArrayList<ProductItemParameter>();
		productParam.setProductItemParameterList(productItemParamList);
		
		//构造产品请求对象
		DefaultProductItemParameter itemReq = new DefaultProductItemParameter();
		itemReq.setWaresId(1l);
		itemReq.setPrice(10);
		itemReq.setWaresName("饮品");
		//设置产品参数
		DrinkDataHandler handler = new DrinkDataHandler();
		
		handler.getDrink().setHeat(1);
		handler.getDrink().setSweetness(1);
		handler.addAddition(1l, "红豆");
		handler.addAddition(2l, "波霸");
		handler.addAddition(3l, "珍珠");
		
		
		itemReq.setProductDataHandler(handler);
		productItemParamList.add(itemReq);
		
		//第二个产品请求对象
		DefaultProductItemParameter itemReq1 = new DefaultProductItemParameter();
		itemReq1.setWaresId(1l);
		itemReq1.setPrice(10);
		itemReq1.setWaresName("奶茶");
		
		DrinkDataHandler handler1 = new DrinkDataHandler();
		//第二个产品的参数
		handler1.getDrink().setHeat(1);
		handler1.getDrink().setSweetness(1);
		handler1.addAddition(6l, "燕麦");
		handler1.addAddition(5l, "波霸");
		handler1.addAddition(2l, "焦糖");
		
		itemReq1.setProductDataHandler(handler1);
		productItemParamList.add(itemReq1);
		
		//把产品请求参数设置到订单请求参数中
		orderParameter.setProductParameter(productParam);
		
		//设置用户信息
		WeiXinUser user = new WeiXinUser();
		user.setId(1l);
		user.setOpenid("ovZxms3dvkaZR2aFpmkWh2SxmCTY");
		
		OrderToken orderToken = new OrderToken() {
		};
		orderService.applyForOrder(orderParameter, user, orderToken);
	}
	
	@Test
	public void payOrder(){
		Order order = orderService.getOrder(26l);
		if(order != null){
			WeiXinUser payUser = userService.getWeiXinUserByOpenid("ovZxms3dvkaZR2aFpmkWh2SxmCTY");
			DefaultOrderPayParameter payParam = new DefaultOrderPayParameter(payUser);
			payParam.setActualPay(10000);
			try {
				order.pay(payParam);
			} catch (OrderException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void cancelOrder(){
		Order order = orderService.getOrder(24l);
		if(order != null){
			WeiXinUser payUser = userService.getWeiXinUserByOpenid("ovZxms3dvkaZR2aFpmkWh2SxmCTY");
			try {
				//取消订单
				order.cancel(payUser);
			} catch (OrderException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	@Test
	public void submitOrder(){
		JSONObject jo = JSON.parseObject("{\"deliveryLocationId\":1,\"timePoint\":{\"year\":2017,\"month\":3,\"hour\":13,\"date\":5},\"totalPrice\":5000,\"receiver\":{\"contact\":\"12345678\",\"name\":\"Copperfield\",\"address\":\"临时地址\"},\"items\":[{\"waresId\":1,\"waresName\":\"饮品\",\"drinkTypeId\":1,\"drinkTypeName\":\"奶茶\",\"cupCount\":5,\"sweetness\":1,\"heat\":1,\"cupSize\":2,\"perPrice\":1000,\"additions\":[{\"typeId\":1,\"name\":\"红豆\"},{\"typeId\":2,\"name\":\"珍珠\"}]},{\"waresId\":1,\"waresName\":\"饮品\",\"drinkTypeId\":2,\"drinkTypeName\":\"玛奇朵\",\"cupCount\":2,\"sweetness\":2,\"heat\":2,\"cupSize\":2,\"perPrice\":1200,\"additions\":[{\"typeId\":1,\"name\":\"波霸\"},{\"typeId\":2,\"name\":\"燕麦\"}]}]}");
		
		try {
			OrderTerm term = OrderTerm.fromJson(dManager, 1l, jo);
			WeiXinUser user = userService.getWeiXinUserByOpenid("ovZxms3dvkaZR2aFpmkWh2SxmCTY");
			OrderToken orderToken = new OrderToken() {
			};
			Order order = orderService.applyForOrder(term.createOrderParameter(), user, orderToken);
			
			DefaultOrderPayParameter payParam = new DefaultOrderPayParameter(user);
			payParam.setActualPay(1000);
			order.pay(payParam);
		} catch (OrderException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void md5Test(){
		DateFormat df = new SimpleDateFormat("yyMMdd");
		System.out.println(df.format(new Date()));
	}
	
	
	@Test
	public void addPlan(){
		PlainDeliveryPlan plan = new PlainDeliveryPlan();
		plan.setMaxCount(50);
		plan.setLeadMinutes(30);
		plan.setWaresId(1l);
		plan.setLocationId(1l);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		plan.setStartDate(cal.getTime());
		cal.set(Calendar.YEAR, 2018);
		plan.setEndDate(cal.getTime());
		
		plan.setPeriod("Y2017,2018。M3,4,5。W1,2,3。T13,16,18。");
		dService.addPlan(plan);
	}
	
	@Test
	public void getDeliveries(){
	}
	
	@Test
	public void checkOrderPayStatus(){
		Order order = orderService.getOrder(189l);
		WxPayStatus status = order.checkWxPayStatus();
		System.out.println(status);
	}
	
}
