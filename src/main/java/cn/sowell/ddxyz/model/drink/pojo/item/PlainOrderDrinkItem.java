package cn.sowell.ddxyz.model.drink.pojo.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;

public class PlainOrderDrinkItem {
	@Column(name="drink_product_id")
	private Long drinkProductId;
	
	@Column(name="drink_type_id")
	private Long drinkTypeId;
	
	@Column(name="c_drink_type_name")
	private String drinkName;
	
	@Column(name="c_price")
	private Integer price;
	
	@Column(name="tea_addition_id")
	private Long teaAdditionId;
	
	@Column(name="c_tea_addition_name")
	private String teaAdditionName;
	
	@Column(name="c_sweetness")
	private Integer sweetness;
	
	@Column(name="c_heat")
	private Integer heat;
	
	@Column(name="c_cup_size")
	private Integer cupSize;
	
	private List<PlainDrinkAddition> additions = new ArrayList<PlainDrinkAddition>();
	
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
	public Long getDrinkTypeId() {
		return drinkTypeId;
	}
	public void setDrinkTypeId(Long drinkTypeId) {
		this.drinkTypeId = drinkTypeId;
	}
	public Long getTeaAdditionId() {
		return teaAdditionId;
	}
	public void setTeaAdditionId(Long teaAdditionId) {
		this.teaAdditionId = teaAdditionId;
	}
}
