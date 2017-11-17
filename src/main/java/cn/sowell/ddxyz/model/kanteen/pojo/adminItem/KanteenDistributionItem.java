package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;

import java.util.Date;

import javax.persistence.Column;

public class KanteenDistributionItem {
	@Column(name="d_id")
	private Long id;
	
	@Column(name="menu_name")
	private String menuName;
	
	@Column(name="c_start_time")
	private Date startTime;
	
	@Column(name="c_end_time")
	private Date endTime;
	
	@Column(name="delivery_count")
	private Integer deliveryCount;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getDeliveryCount() {
		return deliveryCount;
	}
	public void setDeliveryCount(Integer deliveryCount) {
		this.deliveryCount = deliveryCount;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
}
