package cn.sowell.ddxyz.model.drink.pojo.item;

import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;

public class ProductInfoItem {
	
	/**
	 * order的相关信息
	 */
	
	private String orderCode;
	
	private Date timePoint;
	
	private String receiverContact;
	
	private String locationName;
	
	private Date orderCreateTime;
	
	private Date payTime;
	
	/**
	 * DrinkProduct相关信息
	 */
	
	private Long drinkProductId;
	
	private String drinkName;
	
	private String teaAdditionName;
	
	private Integer sweetness;
	
	private Integer heat;
	
	private Integer cupSize;
	
	/**
	 * plainProduct价格
	 */
	private Integer price;
	
	/**
	 * DrinkAddition加料
	 */
	private List<PlainDrinkAddition> additions;
	
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

	public String getReceiverContact() {
		return receiverContact;
	}

	public void setReceiverContent(String receiverContact) {
		this.receiverContact = receiverContact;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	
	public Long getDrinkProductId() {
		return drinkProductId;
	}

	public void setDrinkProductId(Long drinkProductId) {
		this.drinkProductId = drinkProductId;
	}

	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public String getTeaAdditionName() {
		return teaAdditionName;
	}

	public void setTeaAdditionName(String teaAdditionName) {
		this.teaAdditionName = teaAdditionName;
	}

	public Integer getSweetness() {
		return sweetness;
	}

	public void setSweetness(Integer sweetness) {
		this.sweetness = sweetness;
	}

	public Integer getHeat() {
		return heat;
	}

	public void setHeat(Integer heat) {
		this.heat = heat;
	}

	public Integer getCupSize() {
		return cupSize;
	}

	public void setCupSize(Integer cupSize) {
		this.cupSize = cupSize;
	}

	public List<PlainDrinkAddition> getAdditions() {
		return additions;
	}

	public void setAdditions(List<PlainDrinkAddition> additions) {
		this.additions = additions;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
