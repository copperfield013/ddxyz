package cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup;

import java.util.Date;

import javax.persistence.Column;

public class KanteenWaresGroupItemForChoose {
	@Column(name="id")
	private Long waresGroupId;
	
	@Column(name="c_name")
	private String waresGroupName;
	
	@Column(name="c_desc")
	private String description;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="create_time")
	private Date createTime;
	
	
	private Integer waresCount;
	public Long getWaresGroupId() {
		return waresGroupId;
	}
	public void setWaresGroupId(Long waresGroupId) {
		this.waresGroupId = waresGroupId;
	}
	public String getWaresGroupName() {
		return waresGroupName;
	}
	public void setWaresGroupName(String waresGroupName) {
		this.waresGroupName = waresGroupName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getWaresCount() {
		return waresCount;
	}
	public void setWaresCount(Integer waresCount) {
		this.waresCount = waresCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
