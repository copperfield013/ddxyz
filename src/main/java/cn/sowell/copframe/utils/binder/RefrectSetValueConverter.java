package cn.sowell.copframe.utils.binder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

import cn.sowell.copframe.dto.format.FormatUtils;
/**
 * 
 * <p>Title: RefrectSetValueConverter</p>
 * <p>Description: </p><p>
 * 属性反射转换器。
 * 通过反射获得属性的setter，然后将值设置到对象的属性内
 * </p>
 * @author Copperfield Zhang
 * @date 2017年2月8日 上午9:48:04
 */
public class RefrectSetValueConverter implements PropertyValueConverter<Object, Object>{

	private RefrectSetValueConverter(){
		
	}
	
	@Override
	public boolean check(PropertyValueConvertContext context) {
		return true;
	}

	@Override
	public Object convert(Object source, PropertyValueConvertContext context) {
		return source;
	}

	@Override
	public void setPropertyValue(Object target, Object value, PropertyValueConvertContext context) throws PropertyValueConvertException {
		//获得属性名
		String propertyName = context.getPropertyValue().getName();
		//获得要设置属性的对象的类型
		Class<?> targetClass = target.getClass();
		//获得属性描述对象
		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(targetClass, propertyName);
		if(pd != null){
			Method setter = pd.getWriteMethod();
			//获得了要求属性要有setter方法，才能设置值
			if(setter != null){
				Class<?>[] paramTypes = setter.getParameterTypes();
				if(paramTypes.length == 1){
					//setter方法只能有一个参数
					Class<?> paramType = paramTypes[0];
					try {
						//转换属性值的类型并调用setter方法来设置值
						setter.invoke(target, formatToClass(paramType, value));
					} catch (Exception e) {
						throw new PropertyValueConvertException(e);
					}
				}
			}
		}
	}

	
	private <T> T formatToClass(Class<T> paramType, Object value) {
		return FormatUtils.toClass(paramType, value);
	}


	private static RefrectSetValueConverter instance;
	/**
	 * 获得反射转换器的单例。因为该转换器是状态无关的对象，因此不需要重复构造
	 * @return
	 */
	public static RefrectSetValueConverter getInstance(){
		if(instance == null){
			instance = new RefrectSetValueConverter();
		}
		return instance;
	}
	
}
