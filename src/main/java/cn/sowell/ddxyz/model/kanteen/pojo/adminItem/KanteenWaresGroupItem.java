package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;

import java.util.Date;

import javax.persistence.Column;

public class KanteenWaresGroupItem {
	@Column(name="waresgroup_id")
	private Long id;
	
	@Column(name="waresgroup_name")
	private String name;
	
	@Column(name="waresgroup_desc")
	private String description;
	
	@Column(name="wares_count")
	private Integer waresCount;
	
	@Column(name="create_time")
	private Date createTime;
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public Integer getWaresCount() {
		return waresCount;
	}
	public void setWaresCount(Integer waresCount) {
		this.waresCount = waresCount;
	}
}
