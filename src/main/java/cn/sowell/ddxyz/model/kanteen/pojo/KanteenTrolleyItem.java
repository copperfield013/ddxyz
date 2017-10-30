package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.LinkedHashSet;
import java.util.Set;

public class KanteenTrolleyItem {
	private Long trolleyWaresId;
	private Long distributionWaresId;
	private Set<Long> waresOptionIds = new LinkedHashSet<Long>();
	private Integer count;
	private String tempId;
	public Long getDistributionWaresId() {
		return distributionWaresId;
	}
	public void setDistributionWaresId(Long distributionWaresId) {
		this.distributionWaresId = distributionWaresId;
	}
	public Long getTrolleyWaresId() {
		return trolleyWaresId;
	}
	public void setTrolleyWaresId(Long trolleyWaresId) {
		this.trolleyWaresId = trolleyWaresId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getTempId() {
		return tempId;
	}
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	public Set<Long> getWaresOptionIds() {
		return waresOptionIds;
	}
	public void setWaresOptionIds(Set<Long> waresOptionIds) {
		this.waresOptionIds = waresOptionIds;
	}
	
}
