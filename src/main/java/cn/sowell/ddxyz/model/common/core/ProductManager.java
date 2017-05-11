package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import cn.sowell.copframe.utils.range.DateRange;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;

/**
 * 
 * <p>Title: PruductManager</p>
 * <p>Description: </p><p>
 * <h1>产品管理</h1>
 * 主要职能包括
 * <ul>
 * 	<li>根据订单请求构造产品对象</li>
 * 	<li>根据</li>
 * </ul>
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月30日 上午9:14:47
 */
public interface ProductManager {
	
	/**
	 * 根据产品id获得产品对象
	 * @param productId 产品id
	 * @return
	 */
	Product getProduct(Serializable productId);
	
	/**
	 * 根据订单请求，构造产品对象并将其放到订单对象中
	 * @param productParam 产品订单请求
	 * @param order 已经构造的订单
	 * @return 订单中的产品对象是否已经成功构造
	 */
	boolean appendProduct(ProductsParameter productParam, Order order);
	
	/**
	 * 将参数中的所有产品对象设置为下单状态（不是创建订单，是下单状态，即可以安排制作）
	 * 如果订单已经被取消，那么不能被下单
	 * @param productSet
	 * @param persist 是否需要同时对产品的状态进行持久化 
	 * @return 产品下单是否完成 
	 * @throws ProductException 
	 */
	void orderProducts(Order order, boolean persist) throws ProductException;
	
	/**
	 * 遗弃订单内的所有产品对象，要求所有产品对象的状态只能是默认状态
	 * @param order
	 * @param persist
	 */
	//void abandonOrderProducts(Order order, boolean persist) throws ProductException;
	
	/**
	 * 将所有产品从map中移除
	 * @param productSet
	 */
	void removeProductFromMap(Set<Product> productSet);

	/**
	 * 将产品全部缓存
	 * @param products 要缓存的所有产品的对象
	 * @return 
	 * @throws ProductException 
	 */
	boolean cacheProducts(Collection<Product> products);
	/**
	 * 将资源放到订单中产品中
	 * @param resource
	 * @param order
	 * @throws OrderException 
	 */
	void appendToProduct(OrderDispenseResource resource, Order order) throws OrderException;

	/**
	 * 清除最近操作时间为某个时间范围内的的所有产品
	 * @param range
	 */
	void clearCache(DateRange range);
	/**
	 * 设置打印后的产品状态
	 * @param productIds
	 * @throws ProductException 
	 */
	void setProductsPrinted(List<Long> productIds) throws ProductException;

	
	
}
