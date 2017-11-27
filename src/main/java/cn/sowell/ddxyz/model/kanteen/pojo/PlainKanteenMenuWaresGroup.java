package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_menu_waresgroup")
public class PlainKanteenMenuWaresGroup {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="menu_id")
	private Long menuId;
	
	@Column(name="waresgroup_id")
	private Long waresgourpId;
	
	@Column(name="c_order")
	private Integer order;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_user_id")
	private Long updateUserId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getWaresgourpId() {
		return waresgourpId;
	}
	public void setWaresgourpId(Long waresgourpId) {
		this.waresgourpId = waresgourpId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	
}
