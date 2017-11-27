package cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup;

import java.util.Date;

import javax.persistence.Column;

public class KanteenMenuItemForChoose {
	@Column(name="menu_name")
	private String menuName;
	
	@Column(name="menu_id")
	private String menuId;
	
	@Column(name="c_desc")
	private String description;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	
}
