package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;

import java.util.Date;

import javax.persistence.Column;

public class KanteenMenuItem {
	@Column(name="id")
	private Long id;
	
	@Column(name="c_name")
	private String name;
	
	@Column(name="c_desc")
	private String description;
	
	private Integer groupCount;
	private Integer waresCount;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="c_disabled")
	private Integer disabled;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}
	public Integer getWaresCount() {
		return waresCount;
	}
	public void setWaresCount(Integer waresCount) {
		this.waresCount = waresCount;
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
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
}
