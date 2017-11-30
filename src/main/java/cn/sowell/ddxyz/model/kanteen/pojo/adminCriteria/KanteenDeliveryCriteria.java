package cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria;

import java.util.Date;

public class KanteenDeliveryCriteria {
	private Long distributionId;
	private Date startTime;
	private Long merchantId;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}
	
}
