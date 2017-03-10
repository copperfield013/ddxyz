package cn.sowell.copframe.utils.binder;

/**
 * 
 * <p>Title: PropertyValueConverter</p>
 * <p>Description: </p><p>
 * 用于设置属性时的值转换
 * </p>
 * @author Copperfield Zhang
 * @date 2017年2月8日 上午8:59:44
 * @param <T>设置属性的目标对象类型
 * @param <V>转换后的值类型
 */
public interface PropertyValueConverter<T, V> {
	/**
	 * 检查转换的上下文，返回true时可以转换，返回false时不能转换<br/>
	 * 注意如果有多个转换器对其能够check成功，那么只取优先级高的进行转换和set<br/>
	 * @param context
	 * @return
	 */
	boolean check(PropertyValueConvertContext context);
	
	/**
	 * 转换属性值
	 * @param source 属性值的原对象
	 * @param context 转换上下文
	 * @return 转换后的值
	 * @throws PropertyValueConvertException 如果check成功但是转换时抛出异常，不会切换到下一个转换器，会立即停止转换
	 */
	V convert(Object source, PropertyValueConvertContext context) throws PropertyValueConvertException;
	
	/**
	 * 将转换后的值value设置到target对象内
	 * @param target 要设置属性的对象
	 * @param value 已经转换后的要设置的属性的值
	 * @param context 转换上下文
	 * @throws PropertyValueConvertException 如果check成功但是set值时抛出异常，不会切换到下一个转换器，会立即停止转换
	 */
	void setPropertyValue(T target, V value, PropertyValueConvertContext context) throws PropertyValueConvertException;
	
	
}
