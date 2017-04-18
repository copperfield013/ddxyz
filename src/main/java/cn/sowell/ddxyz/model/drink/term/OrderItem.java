package cn.sowell.ddxyz.model.drink.term;

import java.util.LinkedHashSet;
import java.util.Set;

public class OrderItem {
	private Long waresId;
	private String waresName;
	private Long drinkTypeId;
	private String drinkTypeName;
	private Long teaAdditionId;
	private String teaAdditionName;
	private int cupCount = 0;
	private Integer sweetness;
	private Integer heat;
	private Integer cupSize;
	private Set<AdditionItem> additions = new LinkedHashSet<AdditionItem>();
	private Integer perPrice;
	public Long getDrinkTypeId() {
		return drinkTypeId;
	}
	public void setDrinkTypeId(Long drinkTypeId) {
		this.drinkTypeId = drinkTypeId;
	}
	public String getDrinkTypeName() {
		return drinkTypeName;
	}
	public void setDrinkTypeName(String drinkTypeName) {
		this.drinkTypeName = drinkTypeName;
	}
	public int getCupCount() {
		return cupCount;
	}
	public void setCupCount(int cupCount) {
		this.cupCount = cupCount;
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
	public Set<AdditionItem> getAdditions() {
		return additions;
	}
	public void setAdditions(Set<AdditionItem> additions) {
		this.additions = additions;
	}
	public Integer getPerPrice() {
		return perPrice;
	}
	public void setPerPrice(Integer perPrice) {
		this.perPrice = perPrice;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
	public String getWaresName() {
		return waresName;
	}
	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}
	public void addAddition(AdditionItem aItem) {
		additions.add(aItem);
	}
	public Long getTeaAdditionId() {
		return teaAdditionId;
	}
	public void setTeaAdditionId(Long teaAdditionId) {
		this.teaAdditionId = teaAdditionId;
	}
	public String getTeaAdditionName() {
		return teaAdditionName;
	}
	public void setTeaAdditionName(String teaAdditionName) {
		this.teaAdditionName = teaAdditionName;
	}
}
