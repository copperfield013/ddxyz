package cn.sowell.ddxyz.model.drink.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <p>Title: PlainAddition</p>
 * <p>Description: </p><p>
 * 加料POJO
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月11日 下午6:48:02
 */
@Entity
@Table(name="t_drink_addition_type")
public class PlainDrinkAdditionType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="c_name")
	private String name;
	
	@Column(name="c_base_price")
	private String basePrice;
	
	@Column(name="drink_type_id")
	private Long drinkTypeId;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	@Column(name="c_order")
	private Integer order;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="c_update_time")
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public Long getDrinkTypeId() {
		return drinkTypeId;
	}
	public void setDrinkTypeId(Long drinkTypeId) {
		this.drinkTypeId = drinkTypeId;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
}
