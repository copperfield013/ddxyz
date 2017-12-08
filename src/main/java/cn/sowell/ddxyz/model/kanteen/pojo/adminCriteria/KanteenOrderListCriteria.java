package cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria;

import java.util.Date;

public class KanteenOrderListCriteria {
	private Long merchantId;
	private Long distributionId;
	private Long deliveryId;
	private String orderCode;
	private Date orderRangeStart;
	private Date orderRangeEnd;
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
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Date getOrderRangeStart() {
		return orderRangeStart;
	}
	public void setOrderRangeStart(Date orderRangeStart) {
		this.orderRangeStart = orderRangeStart;
	}
	public Date getOrderRangeEnd() {
		return orderRangeEnd;
	}
	public void setOrderRangeEnd(Date orderRangeEnd) {
		this.orderRangeEnd = orderRangeEnd;
	}
	
}
