package cn.sowell.ddxyz.model.common.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * <p>Title: PlainDeliveryPlan</p>
 * <p>Description: </p><p>
 * 配送计划
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月25日 上午10:25:59
 */
@Entity
@Table(name="t_delivery_plan")
public class PlainDeliveryPlan {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="location_id", updatable=false)
	private Long locationId;
	
	@ManyToOne
	@JoinColumn(name="location_id", insertable=false, updatable=false)
	private PlainLocation location;
	
	/**
	 * 周期字符串
	 */
	@Column(name="c_period")
	private String period;
	
	/**
	 * 配送最大数
	 */
	@Column(name="c_max_count")
	private Integer maxCount;
	
	/**
	 * 提前关闭时间
	 */
	@Column(name="c_lead_minutes")
	private Integer leadMinutes;
	
	/**
	 * 周期开始时间
	 */
	@Column(name="c_start_date")
	private Date startDate;
	
	/**
	 * 周期结束时间
	 */
	@Column(name="c_end_date")
	private Date endDate;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
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
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public Integer getLeadMinutes() {
		return leadMinutes;
	}
	public void setLeadMinutes(Integer leadMinutes) {
		this.leadMinutes = leadMinutes;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public PlainLocation getLocation() {
		return location;
	}
	public void setLocation(PlainLocation location) {
		this.location = location;
	}
}
