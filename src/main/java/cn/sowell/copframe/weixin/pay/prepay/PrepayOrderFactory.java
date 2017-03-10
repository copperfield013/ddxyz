package cn.sowell.copframe.weixin.pay.prepay;
/**
 * 
 * <p>Title: PrepayOrderFactory</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月10日 下午12:27:05
 */
public interface PrepayOrderFactory {
	UnifiedOrder createOrder(PrepayParameter param);
}
