package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;

import javax.persistence.Column;

public class KanteenMenuWaresGroupItem {
	@Column(name="group_id")
	private Long groupId;
	
	@Column(name="menu_group_id")
	private Long menuGroupId;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="c_order")
	private Integer order;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getMenuGroupId() {
		return menuGroupId;
	}
	public void setMenuGroupId(Long menuGroupId) {
		this.menuGroupId = menuGroupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
