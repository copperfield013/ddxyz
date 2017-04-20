package cn.sowell.ddxyz.model.drink.pojo.item;

import java.util.List;

import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;

public class PlainOrderDrinkItem {
	
	private Long drinkProductId;
	
	private String drinkName;
	
	private Integer price;
	
	private String teaAdditionName;
	
	private Integer sweetness;
	
	private Integer heat;
	
	private Integer cupSize;
	
	private List<PlainDrinkAddition> additions;
	
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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
}
