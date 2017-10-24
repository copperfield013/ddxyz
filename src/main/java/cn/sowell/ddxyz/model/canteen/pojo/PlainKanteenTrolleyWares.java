package cn.sowell.ddxyz.model.canteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_trolley_wares")
public class PlainKanteenTrolleyWares {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="trolley_id")
	private Long trolleyId;
	
	@Column(name="distributionwares_id")
	private Long distributionWaresId;
	
	@Column(name="c_count")
	private Integer count;
	
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
	public Long getTrolleyId() {
		return trolleyId;
	}
	public void setTrolleyId(Long trolleyId) {
		this.trolleyId = trolleyId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getDistributionWaresId() {
		return distributionWaresId;
	}
	public void setDistributionWaresId(Long distributionWaresId) {
		this.distributionWaresId = distributionWaresId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
