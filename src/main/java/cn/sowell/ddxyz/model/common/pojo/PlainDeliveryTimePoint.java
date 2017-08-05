package cn.sowell.ddxyz.model.common.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <p>Title: PlainDeliveryTimePoint</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年6月12日 上午9:14:13
 */
@Entity
@Table(name="t_delivery_timepoint_count")
public class PlainDeliveryTimePoint {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="c_time_point")
	private Date timePoint;
	
	@Column(name="c_count_max")
	private Integer countMax;
	
	@Column(name="c_curr_count")
	private Integer currentCount;
	
	@Column(name="create_time")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWaresId() {
		return waresId;
	}

	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}

	public Date getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

	public Integer getCountMax() {
		return countMax;
	}

	public void setCountMax(Integer countMax) {
		this.countMax = countMax;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
