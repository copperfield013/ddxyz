package cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria;

public class KanteenWaresCriteria {
	private Long merchantId;
	private String waresName;

	public String getWaresName() {
		return waresName;
	}

	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
}
