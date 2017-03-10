package cn.sowell.copframe.weixin.pay.prepay;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * <p>Title: DefaultPrepayOrderFactory</p>
 * <p>Description: </p><p>
 * 创建预付款订单的工厂对象
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月10日 下午1:44:50
 */
@Repository
@SuppressWarnings("rawtypes")
public class DefaultPrepayOrderFactory implements PrepayOrderFactory{

	private List<PrepayOrderProcessStrategy> strategies;
	
	@SuppressWarnings("unchecked")
	@Override
	public UnifiedOrder createOrder(PrepayParameter param) {
		//构造统一下单对象
		UnifiedOrder order = new UnifiedOrder();
		for (PrepayOrderProcessStrategy strategy : strategies) {
			if(strategy.support(param)){
				order = strategy.preprocess(order);
				if(order != null){
					order = strategy.setParameter(order, param);
					break;
				}
			}
		}
		return order;
	}

	public List<PrepayOrderProcessStrategy> getStrategies() {
		return strategies;
	}

	public void setStrategies(List<PrepayOrderProcessStrategy> strategies) {
		this.strategies = strategies;
	}

	
	
	

}
