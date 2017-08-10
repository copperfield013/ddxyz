package cn.sowell.ddxyz.model.common.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_delivery_base")
public class PlainDelivery {
	//主键
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//配送地点主键
	@Column(name="location_id")
	private Long locationId;
	
	@Column(name="c_location_name")
	private String locationName;
	
	//配送时间点
	@Column(name="c_time_point")
	private Date timePoint;
	
	//预定开始时间
	@Column(name="c_open_time")
	private Date openTime;
	
	//预定关闭时间
	@Column(name="c_close_time")
	private Date closeTime;
	
	//领取结束时间
	@Column(name="c_clain_end_time")
	private Date claimEndTime;
	
	//最多配送量
	@Column(name="c_max_count")
	private Integer maxCount;
	
	//当前占用配送量
	@Column(name="c_current_count")
	private Integer currentCount = 0;
	
	//当前状态
	@Column(name="c_status")
	private int status = 0;
	
	@Column(name="c_type")
	private String type;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	//生成当前配送的配送计划的id
	@Column(name="delivery_plan_id")
	private Long deliveryPlanId;
	
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
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
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Long getDeliveryPlanId() {
		return deliveryPlanId;
	}
	public void setDeliveryPlanId(Long deliveryPlanId) {
		this.deliveryPlanId = deliveryPlanId;
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
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public Date getClaimEndTime() {
		return claimEndTime;
	}
	public void setClaimEndTime(Date claimEndTime) {
		this.claimEndTime = claimEndTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	
	
}
