package cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class KanteenOrderStatCriteria {
	private Long distributionId;
	private Long deliveryId;
	private Set<String> status;
	private Set<String> canceledStatus;
	private boolean allCanceled;
	public Long getDistributionId() {
		return distributionId;
	}
	public KanteenOrderStatCriteria setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
		return this;
	}
	public Long getDeliveryId() {
		return deliveryId;
	}
	public KanteenOrderStatCriteria setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
		return this;
	}
	
	public KanteenOrderStatCriteria setStatus(String... status) {
		this.status = new HashSet<String>(Arrays.asList(status));
		return this;
	}
	public KanteenOrderStatCriteria setCanceledStatus(String... canceledStatus) {
		this.canceledStatus = new HashSet<String>(Arrays.asList(canceledStatus));
		return this;
	}
	public KanteenOrderStatCriteria setAllCanceled(boolean allCanceled) {
		this.allCanceled = allCanceled;
		return this;
	}
	public boolean getAllCanceled() {
		return allCanceled;
	}
	public Set<String> getStatus() {
		return status;
	}
	public Set<String> getCanceledStatus() {
		return canceledStatus;
	}
	
	
}
