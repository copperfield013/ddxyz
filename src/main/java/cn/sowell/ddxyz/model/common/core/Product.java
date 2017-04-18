package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;

import org.springframework.util.Assert;

import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;

/**
 * 
 * <p>Title: Product</p>
 * <p>Description: </p><p>
 * 产品接口
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月29日 上午11:14:19
 */
public interface Product {
	/**
	 * 获得产品的唯一标识
	 * @return
	 */
	Serializable getId();
	/**
	 * 获得产品的名称
	 * @return
	 */
	String getName();
	
	/**
	 * 获得产品详情
	 * @return
	 */
	PlainProduct getProductInfo();
	
	/**
	 * 设置产品详情
	 */
	void setProductDetail(PlainProduct productInfo);
	
	/**
	 * 获得产品价格（单位分）
	 * @return
	 */
	Integer getPrice();
	
	/**
	 * 
	 * @param price
	 */
	void setPrice(Integer price);
	
	/**
	 * 获得分发号
	 * @return
	 */
	DispenseCode getDispenseCode();
	/**
	 * 设置分发号
	 * @param dispenseCode
	 */
	void setDispenseCode(DispenseCode dispenseCode);
	
	
	
	
	/**
	 * 获得产品的状态
	 * @return
	 * @see #STATUS_ABANDON
	 */
	int getStatus();
	/**
	 * 获得当前产品的取消状态
	 * @return
	 * @see #CAN_STATUS_ABANDON
	 * @see #CAN_STATUS_ORDER_CANCEL
	 * @see #CAN_STATUS_ORDER_REFUND
	 */
	String getCanceledStatus();
	/**
	 * 设置产品的状态，但是不持久化该状态
	 * @param toStatus
	 * @throws ProductException 
	 */
	void setStatusWithoutPersistence(int toStatus) throws ProductException;
	
	/**
	 * 设置产品的取消状态，但是不持久化该状态
	 * 方法最终返回一个key，用于方法{@link #setCancelStatusNull(int)}回滚状态
	 * @param canStatusCanceled
	 * @return
	 * @throws ProductException
	 */
	int setCancelStatusWithoutPersistence(String canStatusCanceled) throws ProductException;
	/**
	 * 设置产品的退款金额，但是不持久化该退款金额。
	 * @param refundFee 退款金额。如果小于0，那么就退款金额就设置为产品价格
	 */
	void setRefundFeeWithoutPersistence(int refundFee);
	/**
	 * 不检查，直接设置产品的状态，也不持久化产品的状态
	 * @param toStatus
	 */
	//void setStatusWithoutCheck(int toStatus);
	/**
	 * 直接设置产品的状态为null，不检查也不持久化
	 * @param key 调用{@link #setStatusWithoutPersistence(int)}方法时返回的随机数
	 * 只有当随机数符合时，才可以把取消状态设置为null。
	 */
	boolean setCancelStatusNull(int key);
	
	void make() throws ProductException;
	/**
	 * 设置产品的状态为制作完成
	 * @throws ProductException
	 */
	void complete() throws ProductException;
	/**
	 * 设置产品的状态为装箱完成
	 * @throws ProductException
	 */
	void pack() throws ProductException;
	
	/**
	 * 设置产品的状态为配送中
	 * @throws ProductException
	 */
	void delivery() throws ProductException;
	
	/**
	 * 设置产品的状态为配送完成
	 * @throws ProductException
	 */
	void deliveried() throws ProductException;
	
	/**
	 * 获得产品详情的处理器
	 * @return
	 */
	ProductDataHandler getOptionHandler();
	
	boolean isClosed();
	/**
	 * 默认状态
	 */
	final int STATUS_DEFAULT = 0;
	/**
	 * 产品被下单状态
	 */
	final int STATUS_ORDERED = 1;
	/**
	 * 产品正在制作
	 */
	final int STATUS_MAKING = 2;
	/**
	 * 产品制作完成状态
	 */
	final int STATUS_COMPLETED = 3;
	/**
	 * 产品已装箱，准备配送
	 */
	final int STATUS_PACKED = 4;
	/**
	 * 产品正在派送中
	 */
	final int STATUS_DELIVERING = 5;
	/**
	 * 产品已送达状态
	 */
	final int STATUS_DELIVERIED = 6;
	/**
	 * 用户已经确认产品
	 */
	final int STATUS_CONFIRMED = 7;
	
	/**
	 * 遗弃订单产品对象，只能针对创建产品对象后不下单的产品状态
	 */
	final String CAN_STATUS_ABANDON = "abandon";
	/**
	 * 取消产品，产品下单并且在制作之前可以转换为这个状态。这个状态可能会包含退款
	 */
	final String CAN_STATUS_ORDER_CANCEL = "cancel";
	
	/**
	 * 产品退款，产品在制作完成之后都可以应用这个状态。
	 */
	final String CAN_STATUS_ORDER_REFUND = "refund";
	
	/**
	 * 检查产品能否更改为状态toStatus
	 * @param product
	 * @param toStatus 如果为默认状态或者未知状态，返回false
	 * @return
	 */
	public static CheckResult checkProductToStatus(Product product, int toStatus){
		CheckResult result = new CheckResult(true, "检测成功");
		int cStatus = product.getStatus();
		switch (toStatus) {
			case Product.STATUS_DEFAULT:
				return result.setResult(false, "产品不能更改为默认状态");
			case Product.STATUS_ORDERED:
				if(cStatus != Product.STATUS_DEFAULT){
					return result.setResult(false, "产品只能从默认状态才能转为已下单状态，当前状态为[" + cStatus + "]");
				}
				break;
			case Product.STATUS_MAKING:
				if(cStatus != Product.STATUS_ORDERED){
					return result.setResult(false, "产品只能从已下单状态才能转为正在制作状态，当前状态为[" + cStatus + "]");
				}
				break;
			case Product.STATUS_COMPLETED:
				if(cStatus != Product.STATUS_MAKING){
					return result.setResult(false, "产品只能从正在制作状态才能转为制作完成状态，当前状态为[" + cStatus + "]");
				}
				break;
			case Product.STATUS_PACKED:
				if(cStatus != Product.STATUS_COMPLETED){
					return result.setResult(false, "产品只能从制作完成状态才能转为已装箱状态，当前状态为[" + cStatus + "]");
				}
				break;
			case Product.STATUS_DELIVERING:
				if(cStatus != Product.STATUS_PACKED){
					return result.setResult(false, "产品只能从已装箱状态才能转为正在配送状态，当前状态为[" + cStatus + "]");
				}
			case Product.STATUS_DELIVERIED:
				if(cStatus != Product.STATUS_DELIVERING){
					return result.setResult(false, "产品只能从正在配送状态才能转为配送完成状态，当前状态为[" + cStatus + "]");
				}
				break;
			case Product.STATUS_CONFIRMED:
				if(cStatus != Product.STATUS_DELIVERIED){
					return result.setResult(false, "产品只能从配送完成状态才能转为用户确认状态，当前状态为[" + cStatus + "]");
				}
				break;
			/*case Product.STATUS_ABANDON:
				if(cStatus != Product.STATUS_DEFAULT){
					return result.setResult(false, "产品只能从默认状态才能转为遗弃状态，当前状态为[" + cStatus + "]");
				}
				break;
			case Product.STATUS_ORDER_CANCELED:
				if(cStatus == Product.STATUS_COMPLETED){
					return result.setResult(false, "产品不能从完成状态更改为取消状态");
				}
				break;*/
			default: 
				return result.setResult(false, "未知产品状态：" + cStatus);
		}
		return result;
	}
	public static CheckResult checkProductToCancelStatus(Product product,
			String cancelStatus){
		Assert.notNull(product);
		CheckResult result = new CheckResult(true, "检验通过");
		if(cancelStatus == null){
			return result.setResult(false, "产品状态不能设置为null");
		}
		boolean closed = product.isClosed();
		if(closed){
			return result.setResult(false, "产品原本已经被关闭，状态为[" + product.getCanceledStatus() + "], 不能再转换为[" + cancelStatus + "]");
		}
		int pStatus = product.getStatus();
		if(CAN_STATUS_ABANDON.equals(cancelStatus)){
			//要遗弃当前产品，只能在产品未付款之前执行
			if(pStatus != Product.STATUS_DEFAULT){
				return result.setResult(false, "产品只能在默认状态下遗弃，当前状态为[" + pStatus + "]");
			}
		}else if(CAN_STATUS_ORDER_CANCEL.equals(cancelStatus)){
			//取消产品的下单状态，只能在产品付款后，制作前修改
			if(pStatus != Product.STATUS_ORDERED){
				return result.setResult(false, "取消产品的下单状态只能在产品付款后，并且在制作之前。当前产品状态为[" + pStatus + "]");
			}
		}else if(CAN_STATUS_ORDER_REFUND.equals(cancelStatus)){
			//产品退款，在产品制作之后可以修改
			if(pStatus >= STATUS_MAKING){
				return result.setResult(false, "产品在制作之前不能申请退款，如果是未下单产品，可以遗弃；如果是未制作产品，可以取消。当前产品状态是[" + pStatus + "]");
			}
		}
		return result;
	}
	
	
	
}
