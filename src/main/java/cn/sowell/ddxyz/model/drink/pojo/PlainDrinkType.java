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
 * <p>Title: PlainDrink</p>
 * <p>Description: </p><p>
 * 饮料类型表
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月12日 下午2:09:47
 */
@Entity
@Table(name="t_drink_type")
public class PlainDrinkType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="c_name")
	private String name;
	
	@Column(name="c_view")
	private String view;
	
	@Column(name="c_base_price")
	private Integer basePrice;
	
	@Column(name="c_order")
	private Integer order;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
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
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public Integer getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
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
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
}
