package cn.sowell.copframe.weixin.pay.prepay;


/**
 * 
 * <p>Title: TradeNoGenerator</p>
 * <p>Description: </p><p>
 * 订单号生成器接口
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月10日 下午2:19:23
 */
public interface TradeNoGenerator {
	/**
	 * 根据预付款参数生成订单号
	 * @param order
	 * @param parameter
	 * @return
	 */
	String generate(UnifiedOrder order, PrepayParameter parameter);
}
