package cn.sowell.ddxyz.model.drink.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_drink_addition")
public class PlainDrinkAddition {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="drink_product_id")
	private Long drinkProductId;
	
	@Column(name="addition_type_id")
	private Long additionTypeId;
	
	@Column(name="c_addition_type_name")
	private String additionTypeName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAdditionTypeId() {
		return additionTypeId;
	}
	public void setAdditionTypeId(Long additionTypeId) {
		this.additionTypeId = additionTypeId;
	}
	public String getAdditionTypeName() {
		return additionTypeName;
	}
	public void setAdditionTypeName(String additionTypeName) {
		this.additionTypeName = additionTypeName;
	}
	public Long getDrinkProductId() {
		return drinkProductId;
	}
	public void setDrinkProductId(Long drinkProductId) {
		this.drinkProductId = drinkProductId;
	}
}
