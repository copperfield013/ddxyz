package cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria;

import java.util.List;

public class KanteenChooseWaresGroupListCriteria {
	private List<Long> exceptGroupIds;
	private String waresGroupName;
	private Long merchantId;
	public String getWaresGroupName() {
		return waresGroupName;
	}
	public void setWaresGroupName(String waresGroupName) {
		this.waresGroupName = waresGroupName;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public List<Long> getExceptGroupIds() {
		return exceptGroupIds;
	}
	public void setExceptGroupIds(List<Long> exceptGroupIds) {
		this.exceptGroupIds = exceptGroupIds;
	}
	
}
