package cn.sowell.ddxyz.model.drink.term;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.OrderParameter;
import cn.sowell.ddxyz.model.common.core.ProductItemParameter;
import cn.sowell.ddxyz.model.common.core.ProductsParameter;
import cn.sowell.ddxyz.model.common.core.ReceiverInfo;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.impl.DefaultProductItemParameter;
import cn.sowell.ddxyz.model.common.core.impl.DrinkDataHandler;

public class OrderTerm {
	DeliveryLocation deliveryLocation;
	Integer year;
	Integer month;
	Integer date;
	Integer hour;
	Integer totalPrice;
	ReceiverInfo receiver;
	int dispenseCount = 0;
	
	ArrayList<OrderItem> items = new ArrayList<OrderItem>();  
	
	public static OrderTerm fromJson(DeliveryManager dManager, JSONObject jo) throws OrderException{
		Assert.notNull(dManager);
		Assert.notNull(jo);
		Long locationId = jo.getLong("deliveryLocationId");
		if(locationId != null){
			DeliveryLocation location = dManager.getDeliveryLocation(locationId);
			if(location != null){
				OrderTerm ot = new OrderTerm();
				ot.deliveryLocation = location;
				
				JSONObject timePoint = jo.getJSONObject("timePoint");
				if(timePoint != null){
					ot.year = timePoint.getInteger("year");
					ot.month = timePoint.getInteger("month");
					ot.date = timePoint.getInteger("date");
					ot.hour = timePoint.getInteger("hour");
					
					if(ot.year == null || ot.month == null || ot.date == null || ot.hour == null){
						throw new OrderException("配送时间点错误");
					}
					
					ot.totalPrice = jo.getInteger("totalPrice");
					if(ot.totalPrice == null){
						throw new OrderException("没有传入订单总价格参数");
					}
					
					JSONObject recJo = jo.getJSONObject("receiver");
					if(recJo != null){
						ReceiverInfo receiver = new ReceiverInfo();
						receiver.setContact(recJo.getString("contact"));
						receiver.setName(recJo.getString("name"));
						receiver.setAddress(recJo.getString("address"));
					}else{
						throw new OrderException("没有传入收货人信息参数");
					}
					
					JSONArray items = jo.getJSONArray("items");
					for (Object ele : items) {
						if(ele instanceof JSONObject){
							JSONObject jitem = (JSONObject) ele;
							OrderItem item = new OrderItem();
							item.setWaresId(jitem.getLong("waresId"));
							item.setWaresName(jitem.getString("waresName"));
							item.setDrinkTypeId(jitem.getLong("drinkTypeId"));
							item.setDrinkTypeName(jitem.getString("drinkTypeName"));
							item.setTeaAdditionId(jitem.getLong("teaAdditionId"));
							item.setTeaAdditionName(jitem.getString("teaAdditionName"));
							item.setCupCount(jitem.getIntValue("cupCount"));
							item.setSweetness(jitem.getInteger("sweetness"));
							item.setHeat(jitem.getInteger("heat"));
							item.setCupSize(jitem.getInteger("cupSize"));
							item.setPerPrice(jitem.getInteger("perPrice"));
							JSONArray additions = jitem.getJSONArray("additions");
							if(additions != null){
								for (Object obj : additions) {
									if(obj instanceof JSONObject){
										JSONObject jAddition = (JSONObject) obj;
										AdditionItem aItem = new AdditionItem();
										aItem.setTypeId(jAddition.getLong("typeId"));
										aItem.setName(jAddition.getString("name"));
										item.addAddition(aItem);
									}
								}
							}
							ot.items.add(item);
						}
					}
					return ot;
				}else{
					throw new OrderException("没有找到配送时间点参数timePoint");
				}
			}else{
				throw new OrderException("没有找到配送地点id[" + locationId + "]对应的配送地点");
			}
		}else{
			throw new OrderException("配送点的id没有传入");
		}
		
	}
	
	public OrderParameter createOrderParameter(){
		//创建订单参数
		OrderParameter oParameter = new OrderParameter();
		//设置配送点
		oParameter.setDeliveryLocation(deliveryLocation);
		//设置配送时间点
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, hour, 0, 0);
		oParameter.setTimePoint(new DeliveryTimePoint(cal.getTime()));
		//设置订单总价
		oParameter.setTotalPrice(totalPrice);
		//设置收货人信息
		oParameter.setReceiver(receiver);
		//构造产品参数
		ProductsParameter pParam = new ProductsParameter();
		oParameter.setProductParameter(pParam);
		List<ProductItemParameter> pItemParamList = new ArrayList<ProductItemParameter>();
		pParam.setProductItemParameterList(pItemParamList);
		
		for (OrderItem oItem : items) {
			for (int i = 0; i < oItem.getCupCount(); i++) {
				DefaultProductItemParameter itemParam = new DefaultProductItemParameter();
				itemParam.setWaresId(oItem.getWaresId());
				itemParam.setPrice(oItem.getPerPrice());
				itemParam.setWaresName(oItem.getWaresName());
				itemParam.setPrice(oItem.getPerPrice());
				DrinkDataHandler productPersistHandler = new DrinkDataHandler();
				productPersistHandler.getDrink().setDrinkTypeId(oItem.getDrinkTypeId());
				productPersistHandler.getDrink().setDrinkTypeName(oItem.getDrinkTypeName());
				productPersistHandler.getDrink().setHeat(oItem.getHeat());
				productPersistHandler.getDrink().setCupSize(oItem.getCupSize());
				productPersistHandler.getDrink().setSweetness(oItem.getSweetness());
				productPersistHandler.getDrink().setTeaAdditionId(oItem.getTeaAdditionId());
				productPersistHandler.getDrink().setTeaAdditionName(oItem.getTeaAdditionName());
				for (AdditionItem addition : oItem.getAdditions()) {
					productPersistHandler.addAddition(addition.getTypeId(), addition.getName());
				}
				itemParam.setProductDataHandler(productPersistHandler);
				pItemParamList.add(itemParam);
			}
		}
		return oParameter;
	}
}
