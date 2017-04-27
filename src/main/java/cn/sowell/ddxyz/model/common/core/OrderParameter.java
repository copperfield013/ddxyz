package cn.sowell.ddxyz.model.common.core;


/**
 * 
 * <p>Title: OrderParam</p>
 * <p>Description: </p><p>
 * 订单请求
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月29日 上午10:10:21
 */
public class OrderParameter {
	private final long waresId;
	private DeliveryTimePoint timePoint;
	private DeliveryLocation deliveryLocation;

	private ReceiverInfo receiver;
	
	private Integer totalPrice;
	//private OrderDetail orderDetail;
	
	private DispenseResourceRequest dispenseResourceRequest;
	private ProductsParameter productParameter;
	
	private String comment;
	
	public OrderParameter(long waresId) {
		this.waresId = waresId;
	}
	
	/**
	 * 获得订单请求的时间点
	 * @return
	 */
	public DeliveryTimePoint getTimePoint() {
		return timePoint;
	}
	/**
	 * 设置订单请求中的时间点
	 * @param timePoint
	 */
	public void setTimePoint(DeliveryTimePoint timePoint) {
		this.timePoint = timePoint;
	}
	/**
	 * 获得订单请求的配送地址
	 * @return
	 */
	public DeliveryLocation getDeliveryLocation() {
		return deliveryLocation;
	}
	/**
	 * 设置订单请求的配送地点
	 * @param location
	 */
	public void setDeliveryLocation(DeliveryLocation deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}
	public DispenseResourceRequest getDispenseResourceRequest() {
		if(dispenseResourceRequest == null){
			if(productParameter != null && productParameter.getProductItemParameterList() != null){
				dispenseResourceRequest = new DispenseResourceRequest();
				dispenseResourceRequest.setDispenseCount(productParameter.getProductItemParameterList().size());
				dispenseResourceRequest.setResourceLocked(false);
			}
		}
		return dispenseResourceRequest;
	}
	public void setDispenseResourceRequest(
			DispenseResourceRequest dispenseResourceRequest) {
		this.dispenseResourceRequest = dispenseResourceRequest;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public ProductsParameter getProductParameter() {
		return productParameter;
	}
	public void setProductParameter(ProductsParameter productParameter) {
		this.productParameter = productParameter;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public ReceiverInfo getReceiver() {
		return receiver;
	}
	public void setReceiver(ReceiverInfo receiver) {
		this.receiver = receiver;
	}

	public long getWaresId() {
		return waresId;
	}
	
}
