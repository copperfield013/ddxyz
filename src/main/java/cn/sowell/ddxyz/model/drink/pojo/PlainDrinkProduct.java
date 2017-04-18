package cn.sowell.ddxyz.model.drink.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_drink_product")
public class PlainDrinkProduct {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="drink_type_id")
	private Long drinkTypeId;
	
	@Column(name="c_drink_type_name")
	private String drinkTypeName;
	/**
	 * 加茶主键
	 */
	@Column(name="tea_addition_id")
	private Long teaAdditionId;
	/**
	 * 加茶名称
	 */
	@Column(name="c_tea_addition_name")
	private String teaAdditionName;
	
	@Column(name="c_sweetness")
	private Integer sweetness;

	@Column(name="c_heat")
	private Integer heat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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
