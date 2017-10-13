package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_distribution_wares")
public class PlainKanteenDistributionWares {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="distribution_id")
	private Long distributionId;
	
	@Column(name="menuwares_id")
	private Long menuWaresId;
	
	@Column(name="c_max_count")
	private Integer maxCount;
	
	@Column(name="c_current_count")
	private Integer currentCount;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	@Column(name="c_desc")
	private String description;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="create_user_id")
	private Date createUserId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDistributionId() {
		return distributionId;
	}
	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}
	public Long getMenuWaresId() {
		return menuWaresId;
	}
	public void setMenuWaresId(Long menuWaresId) {
		this.menuWaresId = menuWaresId;
	}
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public Integer getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
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
	public Date getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Date createUserId) {
		this.createUserId = createUserId;
	}
	
}
