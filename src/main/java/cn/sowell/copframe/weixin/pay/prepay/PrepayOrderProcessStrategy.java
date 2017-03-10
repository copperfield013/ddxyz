package cn.sowell.copframe.weixin.pay.prepay;

/**
 * 
 * <p>Title: PrepayOrderHandleStrategy</p>
 * <p>Description: </p><p>
 * 微信支付预订单对象的处理策略接口<br/>
 * 由{@link DefaultPrepayOrderFactory}调用，
 * 调用之前，factory已经先创建了一个统一下单信息{@link UnifiedOrder}的对象
 * 然后会调用{@link #support(PrepayParameter)}请求中的{@linkplain PrepayParameter}对象的当前接口实现对象<br/>
 * 先预处理{@link #preprocess(UnifiedOrder)}，将通用的参数设置到统一下单信息对象order中<br/>
 * 然后根据{@link PrepayParameter}对象，填充其他信息（每次提交交易都不同的信息）
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月10日 下午2:40:52
 * @param <T>
 */
public interface PrepayOrderProcessStrategy<T extends PrepayParameter> {
	/**
	 * 返回true时parameter支持当前策略对象
	 * @param parameter
	 * @return
	 */
	boolean support(T parameter);
	/**
	 * 预处理统一下单对象<br/>
	 * 一般用于把公众号id、商户号、随机数等信息放到下单对象order中
	 * @return
	 * 返回处理后的统一下单对象（如果在方法中手动创建了新的对象并返回，
	 * 那么工厂方法最终获得的是该新创建的对象而不是参数中的order对象)
	 * 如果返回null，那么最终工厂方法直接返回null，不会执行{@link #setParameter(UnifiedOrder, PrepayParameter)}方法
	 */
	UnifiedOrder preprocess(UnifiedOrder order);
	
	/**
	 * 处理统一下单对象<br/>
	 * 与预处理不同的是，这里需要有{@link PrepayParameter}对象参数
	 * 用于处理每个订单不相同的数据
	 * @param order
	 * @param parameter
	 * @return
	 */
	UnifiedOrder setParameter(UnifiedOrder order, T parameter);
}
