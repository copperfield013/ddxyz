package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;
import java.util.Set;

import org.springframework.util.Assert;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

/**
 * 
 * <p>Title: Order</p>
 * <p>Description: </p><p>
 * 订单接口
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月29日 上午10:10:56
 */
public interface Order {
	/**
	 * 获得订单在全局唯一的标识
	 * @return
	 */
	Serializable getKey();
	/**
	 * 设置订单在全局唯一的标识
	 */
	void setKey(Serializable orderKey);
	
	/**
	 * 获得订单号
	 * @return
	 */
	String getOrderCode();
	/**
	 * 设置订单号
	 * @param orderCode
	 */
	void setOrderCode(String orderCode);
	/**
	 * 获得配送地点
	 * @return
	 */
	DeliveryLocation getDeliveryLocation();
	
	/**
	 * 设置配送的地点
	 * @param location
	 */
	void setDeliveryLocation(DeliveryLocation location);
	
	/**
	 * 获得配送的时间点
	 * @return
	 */
	DeliveryTimePoint getDeliveryTimePoint();

	/**
	 * 设置配送点的时间点
	 * @param timePoint
	 */
	void setDeliveryTimePoint(DeliveryTimePoint timePoint);
	/**
	 * 配送标识
	 * @return
	 */
	Serializable getDeliveryId();
	/**
	 * 设置配送标识
	 * @param deliveryId
	 */
	void setDeliveryId(Serializable deliveryId);
	/**
	 * 设置下单的用户
	 * @param user
	 */
	void setOrderUser(WeiXinUser user);
	
	/**
	 * 获得下单的用户
	 * @return
	 */
	WeiXinUser getOrderUser();
	/**
	 * 获得当前订单的所有分发号的key
	 * @return
	 */
	//Set<Serializable> getDispenseCodeKeySet();
	
	/**
	 * 获得订单状态
	 * @return
	 * {@link #STATUS_DEFAULT}
	 * {@link #STATUS_SUBMITED} 
	 * {@link #STATUS_PAYED} 
	 * {@link #STATUS_COMPLETED}
	 * {@link #STATUS_CLOSED}
	 */
	int getOrderStatus();
	/**
	 * 获得订单的取消状态
	 * @return
	 * 
	 */
	String getCancelStatus();
	
	/**
	 * 设置订单状态
	 * 可取值
	 * {@link #STATUS_DEFAULT}
	 * {@link #STATUS_SUBMITED}
	 * {@link #STATUS_PAYED}
	 * {@link #STATUS_COMPLETED}
	 * {@link #STATUS_CLOSED}
	 */
	//void setOrderStatus(int orderStatus);
	/**
	 * 获得从支付接口返回的预付款订单的id
	 * @return
	 */
	String getPrepayId();
	/**
	 * 设置从支付接口返回的预付款订单的id
	 * @param prepayId
	 */
	void setPrepayId(String prepayId);
	
	/**
	 * 获得当前产品对象列表
	 * @return
	 */
	Set<Product> getProductSet();
	/**
	 * 设置产品对象列表
	 * @param productSet
	 */
	void setProductSet(Set<Product> productSet);
	
	/**
	 * 计算订单总价
	 * @return
	 */
	Integer getTotalPrice();
	/**
	 * 设置订单总价
	 */
	void setTotalPrice(Integer totalPrice);
	
	/**
	 * 设置支付订单时的实际支付金额（单位分）
	 * @param actualPay
	 */
	void setActualPay(Integer actualPay);
	/**
	 * 设置订单详情对象
	 * @param orderParam
	 */
	void setOrderDetail(OrderDetail orderDetail);
	
	/**
	 * 获得订单详情对象
	 * @return
	 */
	OrderDetail getOrderDetail();
	/**
	 * 从订单请求中获取数据并且设置当前订单的属性<br/>
	 * 当前方法默认不执行任何代码，执行的时机是订单构造后，并且在<br/>
	 * {@link #setDeliveryTimePoint(DeliveryTimePoint)}<br/>
	 * {@link #setDeliveryLocation(DeliveryLocation)}<br/>
	 * {@link #setOrderDetail(OrderDetail)}<br/>
	 * {@link #setOrderStatus(int)}
	 * 方法之后执行
	 * @param orderParam
	 */
	default void fromOrderParameter(OrderParameter orderParam){}
	
	/**
	 * 获得当前订单所拥有的资源
	 * @return
	 */
	OrderDispenseResource getOrderDispenseResource();
	/**
	 * 获得订单的描述
	 * @return
	 */
	String getDescription();
	
	DispenseResourceRequest getDispenseResourceRequest();
	
	/**
	 * 订单默认状态
	 */
	final int STATUS_DEFAULT = 0;
	/**
	 * 订单已支付，待完成或取消
	 */
	final int STATUS_PAYED = 1;
	/**
	 * 订单已完成
	 */
	final int STATUS_COMPLETED = 2;
	/**
	 * 订单已评价
	 */
	final int STATUS_APPRAISED = 3;
	/**
	 * 订单已取消，该动作由用户主动发起，
	 */
	final String CAN_STATUS_CANCELED = "canceled";
	final String CAN_STATUS_CLOSED  = "closed";
	final String CAN_STATUS_REFUNDED = "refunded";
	
	/**
	 * 支付订单
	 * @param payParam
	 * @throws OrderException
	 */
	void pay(OrderPayParameter payParam) throws OrderException;
	
	/**
	 * 订单完成
	 * @throws OrderException
	 */
	void complete() throws OrderException;
	
	/**
	 * 取消订单，用户主动取消
	 * @param payUser 主动取消的用户，一般就是下单的用户
	 * @throws OrderException 
	 */
	void cancel(UserIdentifier cancelUser) throws OrderException;
	

	/**
	 * 关闭订单，系统自动关闭
	 * @throws OrderException 
	 */
	void close() throws OrderException;

	/**
	 * 订单退款
	 * @throws OrderException
	 */
	void refund(OrderRefundParameter refundParam) throws OrderException;
	/**
	 * 订单全额退款
	 * @param user
	 * @throws OrderException 
	 */
	void refundTotal(UserIdentifier user) throws OrderException;
	/**
	 * 检测订单是否能够修改为toStatus状态
	 * @param order
	 * @param toStatus
	 * @return
	 */
	public static CheckResult checkOrderToStatus(Order order, int toStatus) {
		Assert.notNull(order);
		CheckResult result = new CheckResult(true, "检测成功");
		int cStatus = order.getOrderStatus();
		switch (toStatus) {
			case Order.STATUS_DEFAULT:
				return result.setResult(false, "不能将订单状态修改为默认状态");
			case Order.STATUS_PAYED:
				if(cStatus != Order.STATUS_DEFAULT){
					return result.setResult(false, "不能将支付状态为" + cStatus + "的订单");
				}
				break;
			case Order.STATUS_COMPLETED:
				if(cStatus != Order.STATUS_PAYED){
					return result.setResult(false, "不能将不是支付状态的订单设置为完成状态（当前状态为" + cStatus + "）");
				}
				break;
			default:
				return result.setResult(false, "未知订单状态" + toStatus);
		}
		return result;
	}
	/**
	 * 判断订单order能否转变成状态canStatusCanceled
	 * @param order
	 * @param canStatusCanceled
	 * @return
	 */
	public static CheckResult checkOrderToCancelStatus(Order order,
			String cancelStatus){
		Assert.notNull(order);
		CheckResult result = new CheckResult(true, "检测通过");
		if(cancelStatus == null){
			return result.setResult(false, "订单取消状态不能设置为null");
		}
		String curCancelStatus = order.getCancelStatus();
		int orderStatus = order.getOrderStatus();
		
		if(curCancelStatus != null){
			return result.setResult(false, "订单原本的取消状态非空，为[" + curCancelStatus + "]，不能设置为[" + cancelStatus + "]");
		}
		
		if(CAN_STATUS_CANCELED.equals(cancelStatus)){
			//转换成取消状态
			//只有状态是默认状态才能取消订单
			if(STATUS_DEFAULT != orderStatus){
				return result.setResult(false, "只能是默认状态的订单能取消，当前订单状态为[" + orderStatus + "],不能取消");
			}
		}else if(CAN_STATUS_CLOSED.equals(cancelStatus)){
			//转换成关闭状态
			if(STATUS_DEFAULT != orderStatus){
				return result.setResult(false, "只能是默认状态的订单能取消，当前订单状态为[" + orderStatus + "],不能取消");
			}
		}else if(CAN_STATUS_REFUNDED.equals(cancelStatus)){
			//退款状态
			//只有支付完成，并且订单下的所有产品都没有开始生产之前，才能对订单进行退款
			if(STATUS_PAYED != orderStatus){
				return result.setResult(false, "只能对状态为已支付的订单退款，当前订单的状态为[" + orderStatus + "]");
			}
			for (Product product : order.getProductSet()) {
				if(product.getStatus() >= Product.STATUS_MAKING){
					return result.setResult(false, "只能产品开始制作之前对产品进行退款，订单内存在产品状态为[" + product.getStatus() + "]");
				}
			}
		}else{
			result.setResult(false, "未知订单取消状态[" + cancelStatus + "]");
		}
		return result;
	}
	
	
}
