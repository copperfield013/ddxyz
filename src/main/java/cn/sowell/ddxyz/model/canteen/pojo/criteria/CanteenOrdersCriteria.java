package cn.sowell.ddxyz.model.canteen.pojo.criteria;

public class CanteenOrdersCriteria extends CanteenCriteria{
	private Long deliveryId;
	
	private boolean containsDefault = true;
	private boolean containsCompleted = true;
	private boolean containsClosed = false;
	private boolean containsCanceled = false;
	private boolean containsMiss = false;
	
	public boolean getContainsDefault() {
		return containsDefault;
	}

	public void setContainsDefault(boolean containsDefault) {
		this.containsDefault = containsDefault;
	}

	public boolean getContainsCompleted() {
		return containsCompleted;
	}

	public void setContainsCompleted(boolean containsCompleted) {
		this.containsCompleted = containsCompleted;
	}

	public boolean getContainsClosed() {
		return containsClosed;
	}

	public void setContainsClosed(boolean containsClosed) {
		this.containsClosed = containsClosed;
	}

	public boolean getContainsCanceled() {
		return containsCanceled;
	}

	public void setContainsCanceled(boolean containsCanceled) {
		this.containsCanceled = containsCanceled;
	}

	public boolean getContainsMiss() {
		return containsMiss;
	}

	public void setContainsMiss(boolean containsMiss) {
		this.containsMiss = containsMiss;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
}
