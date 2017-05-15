package cn.sowell.ddxyz.model.common.core.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.sowell.copframe.utils.range.ComparableSingleRange;
import cn.sowell.copframe.utils.range.DateRange;
import cn.sowell.ddxyz.model.common.core.DispenseCode;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.core.OrderDispenseResource;
import cn.sowell.ddxyz.model.common.core.Product;
import cn.sowell.ddxyz.model.common.core.ProductDataHandler;
import cn.sowell.ddxyz.model.common.core.ProductItemParameter;
import cn.sowell.ddxyz.model.common.core.ProductManager;
import cn.sowell.ddxyz.model.common.core.ProductsParameter;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.core.result.CheckResult;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.common.service.DataPersistenceService;

@Repository
public class DefaultProductManager implements ProductManager, InitializingBean{

	@Resource
	DataPersistenceService dpService;
	
	
	Map<Serializable, Product> productMap = new LinkedHashMap<Serializable, Product>();
	Set<Serializable> invalidSet = new HashSet<Serializable>();
	Map<Product, Long> lastOperateMap = new LinkedHashMap<Product, Long>(); 
	
	
	Logger logger = Logger.getLogger(ProductManager.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO 初始化产品管理器
		
	}
	
	private synchronized Product getCacheProduct(Serializable key){
		Product product = productMap.get(key);
		lastOperateMap.put(product, System.currentTimeMillis());
		return product;
	}
	
	
	@Override
	public Product getProduct(Serializable productId) {
		Product product = getCacheProduct(productId);
		if(product == null){
			//只当无效数组中不包含这个标识时
			if(!invalidSet.contains(productId)){
				//从持久化层获得订单
				product = dpService.getProduct(productId);
				if(product != null){
					synchronized (productMap) {
						//再检查一遍map中是否包含key对应的订单对象
						Product temp = productMap.get(productId);
						if(temp != null){
							return temp;
						}else{
							//将订单放到map中
							productMap.put(productId, product);
						}
					}
				}else{
					//如果数据库中不存在这个key对应的订单，那么以后也找不到，此时把key放到数组当中
					invalidSet.add(productId);
				}
			}
		}
		lastOperateMap.put(product, System.currentTimeMillis());
		return product;
	}
	
	
	/**
	 * 调用工厂对象，根据产品请求构造产品对象
	 * @param itemParam
	 * @return
	 */
	private Product buildProduct(ProductItemParameter itemParam) {
		if(itemParam.getWaresId() != null){
			PlainProduct product = new PlainProduct();
			product.setWaresId(itemParam.getWaresId());
			product.setName(itemParam.getWaresName());
			product.setPrice(itemParam.getPrice());
			product.setStatus(Product.STATUS_DEFAULT);
			product.setCreateTime(new Date());
			product.setUpdateTime(product.getCreateTime());
			product.setThumbUri(itemParam.getThumbUri());
			ProductDataHandler handler = itemParam.getOptionPersistHandler();
			DefaultProduct dProduct = new DefaultProduct(product, dpService);
			dProduct.setOptionHandler(itemParam.getOptionPersistHandler());
			if(handler != null){
				Integer price = handler.calculateProductPrice(dProduct);
				if(price != null){
					dProduct.setPrice(price);
				}
			}
			return dProduct;
		}
		return null;
	}
	
	@Override
	public boolean appendProduct(ProductsParameter productParam, Order order) {
		//检查请求的合法性
		if(!checkParameterValid(productParam, order)){
			return false;
		}
		//获得所有产品请求
		List<ProductItemParameter> itemParamList = productParam.getProductItemParameterList();
		//构造产品的数组
		LinkedHashSet<Product> productSet = new LinkedHashSet<Product>();
		boolean allItemSet = true;
		if(itemParamList != null){
			//遍历产品请求
			for (ProductItemParameter itemParam : itemParamList) {
				//构造产品对象
				Product product = buildProduct(itemParam);
				if(product != null){
					productSet.add(product);
				}else{
					//不能构造产品对象
					//如果产品请求要求所有的产品都必须被构造，那么直接返回false
					if(productParam.isRequireBuildItemFull()){
						return false;
					}
					allItemSet = false;
				}
			}
		}
		//如果不要求所有的产品请求都要构造，或者是所有的产品请求都已经构造，此时可以将产品数组放置到订单中
		if(!productParam.isRequireBuildItemFull() || allItemSet){
			//把构造好的产品对象数组放到订单中
			order.setProductSet(productSet);
			return true;
		}
		return false;
		
	}

	/**
	 * 检查产品请求与订单对象的合法性
	 * @param productParam
	 * @param order
	 * @return
	 */
	private boolean checkParameterValid(ProductsParameter productParam, Order order) {
		return productParam != null && order != null;
	}
	
	@Override
	public void orderProducts(Order order, boolean persist) throws ProductException{
		normalSetOrderStatus(Product.STATUS_ORDERED, order, persist);
	}
	
	/*@Override
	public void abandonOrderProducts(Order order, boolean persist)
			throws ProductException {
		Assert.notNull(order);
		synchronized (order) {
			//必须先对订单加锁，然后检测订单内所有产品后，再对产品进行修改
			CheckResult checkResult = checkOrderProductsToCancelStatus(order, Product.CAN_STATUS_ABANDON);
			if(checkResult.isSuc()){
				//如果需要持久化订单所有产品的状态
				if(persist){
					try {
						dpService.
						if(toStatus == Product.STATUS_ORDERED){
							dpService.setProductsOrdered(order.getProductSet());
						}else{
							dpService.setProductsStatus(order, toStatus);
						}
					} catch (Exception e) {
						throw new ProductException("持久化订单产品状态时发生错误", e);
					}
				}
				//遍历所有产品对象，更改对象的状态
				for (Product product : order.getProductSet()) {
					product.setStatusWithoutPersistence(toStatus);
				}
			}else{
				//检测不通过，直接抛出异常
				throw new ProductException(checkResult.getReason());
			}
		}
	}*/
	
	
	
	/**
	 * 检查订单内所有产品的状态是否都能转换成toStatus状态
	 * @param order
	 * @param statusOrdered
	 * @return
	 */
	private CheckResult checkOrderProductsToStatus(Order order, int toStatus) {
		CheckResult result = new CheckResult(true, "检测成功");
		Set<Product> products = order.getProductSet();
		for (Product product : products) {
			CheckResult productResult = Product.checkProductToStatus(product, toStatus); 
			if(!productResult.isSuc()){
				return result.setResult(false, "订单的产品状态更改时检测失败，订单内产品存在问题【" + productResult.getReason() + "】");
			}
		}
		return result;
	}
	/**
	 * 检查订单内所有产品的状态是否都能转换成toStatus状态
	 * @param order
	 * @param statusOrdered
	 * @return
	 */
	@SuppressWarnings("unused")
	private CheckResult checkOrderProductsToCancelStatus(Order order, String toCancelStatus) {
		CheckResult result = new CheckResult(true, "检测成功");
		Set<Product> products = order.getProductSet();
		for (Product product : products) {
			CheckResult productResult = Product.checkProductToCancelStatus(product, toCancelStatus); 
			if(!productResult.isSuc()){
				return result.setResult(false, "订单的产品状态更改时检测失败，订单内产品存在问题【" + productResult.getReason() + "】");
			}
		}
		return result;
	}


	@Override
	public void removeProductFromMap(Set<Product> productSet) {
		Assert.notNull(productSet);
		synchronized (productMap) {
			for (Product product : productSet) {
				productMap.remove(product.getId());
				lastOperateMap.remove(product);
			}
		}
	}
	
	@Override
	public boolean cacheProducts(Collection<Product> products) {
		if(checkProductId(products)){
			synchronized (productMap) {
				for (Product product : products) {
					productMap.put(product.getId(), product);
					lastOperateMap.put(product, System.currentTimeMillis());
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void appendToProduct(OrderDispenseResource resource, Order order) throws OrderException {
		if(resource != null && resource.isExpect()){
			Set<DispenseCode> codes = resource.getDispenseCode();
			Set<Product> products = order.getProductSet();
			if(codes.size() == products.size()){
				Iterator<DispenseCode> itrCode = codes.iterator();
				Iterator<Product> itrProduct = products.iterator();
				while(itrCode.hasNext()){
					itrProduct.next().setDispenseCode(itrCode.next());
				}
			}else{
				throw new OrderException("配送资源不足");
			}
		}else{
			throw new OrderException("配送资源不足");
		}
	}

	
	
	private boolean checkProductId(Collection<Product> products) {
		for (Product product : products) {
			if(product.getId() == null){
				return false;
			}
		}
		return true;
	}

	private void normalSetOrderStatus(int toStatus, Order order, boolean persist) throws ProductException{
		Assert.notNull(order);
		synchronized (order) {
			//必须先对订单加锁，然后检测订单内所有产品后，再对产品进行修改
			CheckResult checkResult = checkOrderProductsToStatus(order, toStatus);
			if(checkResult.isSuc()){
				//如果需要持久化订单所有产品的状态
				if(persist){
					try {
						if(toStatus == Product.STATUS_ORDERED){
							dpService.setProductsOrdered(order.getProductSet());
						}else{
							dpService.setProductsStatus(order, toStatus);
						}
					} catch (Exception e) {
						throw new ProductException("持久化订单产品状态时发生错误", e);
					}
				}
				//遍历所有产品对象，更改对象的状态
				for (Product product : order.getProductSet()) {
					product.setStatusWithoutPersistence(toStatus);
				}
			}else{
				//检测不通过，直接抛出异常
				throw new ProductException(checkResult.getReason());
			}
		}
	}

	@Override
	public synchronized void clearCache(DateRange range) {
		if(range != null){
			ComparableSingleRange<Long> timeRange = range.toLongRange();
			Set<Product> products = new LinkedHashSet<Product>(lastOperateMap.keySet());
			for (Product product : products) {
				Long time = lastOperateMap.get(product);
				if(timeRange.inRange(time)){
					lastOperateMap.remove(product);
					productMap.remove(product.getId());
				}
			}
		}
	}
	
	@Override
	public synchronized void setProductsPrinted(List<Long> productIds) throws ProductException {
		for (Long pId : productIds) {
			Product product = getProduct(pId);
			if(product.getStatus() == Product.STATUS_ORDERED && product.getCanceledStatus() == null){
				product.make();
			}
		}
	}

}
