package cn.sowell.ddxyz.model.canteen.pojo.param;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>Title: CanteenOrderUpdateParam</p>
 * <p>Description: </p><p>
 * 更新菜单订单的参数
 * </p>
 * @author Copperfield Zhang
 * @date 2017年8月4日 上午11:14:16
 */
public class CanteenOrderUpdateParam {
	/**
	 * 要更新的订单id
	 */
	private Long orderId;
	
	private String receiverName;
	private String receiverContact;
	private String depart;
	private String comment;
	
	List<CanteenOrderItem> items = new ArrayList<CanteenOrderItem>();
	
	
	
}
