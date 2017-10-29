package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class KanteenTrolleyItem {
	private Long trolleyWaresId;
	private Long distributionWaresId;
	private List<Long> waresOptionIds = new ArrayList<Long>();
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
	public List<Long> getWaresOptionIds() {
		return waresOptionIds;
	}
	public void setWaresOptionIds(List<Long> waresOptionIds) {
		this.waresOptionIds = waresOptionIds;
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
	
	public JSONObject getResponseJson() {
		
	}
	
}
