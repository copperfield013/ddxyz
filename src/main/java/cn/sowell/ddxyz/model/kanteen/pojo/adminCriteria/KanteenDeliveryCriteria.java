package cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria;

import java.util.Date;

public class KanteenDeliveryCriteria {
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
	
}
