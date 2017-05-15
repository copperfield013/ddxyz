package cn.sowell.ddxyz.model.common.core;

/**
 * 
 * <p>Title: ProductItemParameter</p>
 * <p>Description: </p><p>
 * 订单中，单独一个商品的请求
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月5日 下午2:18:29
 */
public interface ProductItemParameter {
	/**
	 * 商品的id
	 * @return
	 */
	Long getWaresId();
	/**
	 * 商品名称
	 * @return
	 */
	String getWaresName();
	/**
	 * 商品的价格
	 * @return
	 */
	Integer getPrice();
	
	/**
	 * 缩略图
	 * @return
	 */
	String getThumbUri();
	/**
	 * 获得用于持久化产品的参数信息的处理器
	 * @return
	 */
	ProductDataHandler getOptionPersistHandler();
	
}
