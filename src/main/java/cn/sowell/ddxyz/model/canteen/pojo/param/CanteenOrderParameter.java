package cn.sowell.ddxyz.model.canteen.pojo.param;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <p>Title: CanteenOrderParameter</p>
 * <p>Description: </p><p>
 * 餐厅订单的请求参数
 * </p>
 * @author Copperfield Zhang
 * @date 2017年8月2日 上午9:14:52
 */
public class CanteenOrderParameter {
	private Long deliveryId;
	private Long userId;
	private String receiverName;
	private String depart;
	private String contact;
	private List<CanteenOrderItem> orderItems;
	private String comment;
	
	private Integer totalPrice;
	
	
	public static CanteenOrderParameter fromJson(JSONObject jo){
		CanteenOrderParameter obj = JSON.toJavaObject(jo, CanteenOrderParameter.class);
		return obj;
		
	}
	
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public List<CanteenOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<CanteenOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

}
