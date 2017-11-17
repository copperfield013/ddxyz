package cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria;

import java.util.List;

public class KanteenChooseWaresListCriteria {
	private List<Long> except;
	private String mode;
	private Long merchantId;
	private String wareName;
	
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public List<Long> getExcept() {
		return except;
	}
	public void setExcept(List<Long> except) {
		this.except = except;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
}
