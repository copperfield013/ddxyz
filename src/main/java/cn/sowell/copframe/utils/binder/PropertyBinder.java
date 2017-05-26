package cn.sowell.copframe.utils.binder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.util.Assert;

/**
 * 
 * <p>Title: HttpRequestBinder</p>
 * <p>Description: </p><p>
 * 用于将数据绑定到对象的属性当中
 * </p>
 * @author Copperfield Zhang
 * @date 2017年2月6日 下午4:09:28
 */
public class PropertyBinder {
	private Object target;
	
	@SuppressWarnings("rawtypes")
	private List<PropertyValueConverter> pvConverterList = new ArrayList<PropertyValueConverter>(); 
	
	static Logger logger = Logger.getLogger(HttpServletRequest.class);
	
	/**
	 * 构造一个绑定器
	 * @param target
	 */
	public PropertyBinder(Object target) {
		Assert.notNull(target);
		this.target = target;
		//默认添加一个属性反射转换器
		addPvConverter(ReflectSetValueConverter.getInstance());
	}
	
	/**
	 * 根据字段-值对象，通过反射获得字段的setter方法，并绑定对象的属性
	 * @param pvs 字段-值对象。类似于Map<String, Object>
	 * @throws BindException 当绑定失败时，会抛出异常
	 */
	public void bindIgnoreException(PropertyValues pvs){
		for (PropertyValue pv : pvs.getPropertyValues()) {
			PropertyValueConvertContext context = new DefaultPropertyValueConvertContext(this.target, pv);
			setPropertyValue(context);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setPropertyValue(PropertyValueConvertContext context){
		PropertyValue pv = context.getPropertyValue();
		if(pv != null){
			//遍历所有转换器，注意该序列添加转化器元素时时放到前面，因此越后面添加的转换器越先遍历到
			for (PropertyValueConverter pvConverter : this.pvConverterList) {
				if(pvConverter.check(context)){
					try {
						//转换值
						Object convertValue = pvConverter.convert(pv.getValue(), context);
						//设置属性
						pvConverter.setPropertyValue(this.target, convertValue, context);
					} catch (PropertyValueConvertException e) {
						//只记录除了反射set属性值的转换器抛出的异常
						if(!(pvConverter instanceof ReflectSetValueConverter)){
							logger.error(e);
						}
					}
					//只要check成功，就会停止遍历
					break;
				}
			}
		}
	}
	
	/**
	 * 添加属性值转换器。注意添加方式是插入到序列的头部，越后面添加的转换器优先级越高
	 * @param pvConverter
	 */
	@SuppressWarnings("rawtypes")
	public void addPvConverter(PropertyValueConverter pvConverter){
		this.pvConverterList.add(0, pvConverter);
	}
	/**
	 * 添加属性值转换器数组。注意添加方式是插入到序列的头部，越后面添加的转换器优先级越高
	 * @param propertyValueConverters
	 */
	@SuppressWarnings("rawtypes")
	public void addPvConverter(PropertyValueConverter[] propertyValueConverters) {
		for (PropertyValueConverter propertyValueConverter : propertyValueConverters) {
			addPvConverter(propertyValueConverter);
		}
		
	}
	
}
