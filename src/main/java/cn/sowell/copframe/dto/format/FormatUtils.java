package cn.sowell.copframe.dto.format;

import java.math.BigDecimal;
/**
 * 
 * <p>Title: FormatUtils</p>
 * <p>Description: </p><p>
 * 格式化工具类
 * </p>
 * @author Copperfield Zhang
 * @date 2016年3月13日 下午1:49:08
 */
public class FormatUtils {
	/**
	 * 将对象转换成Integer<br/>
	 * 机制是将o.toString转换成Double，再获取Double对象的intValue()
	 * 如果转换失败，就返回null
	 * @param o
	 * @return
	 */
	public static Integer toInteger(Object o){
		try {
			return Double.valueOf(o.toString()).intValue();
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 将对象转换成Long</br>
	 * 机制是将o.toString()转换成Double，在将Doule转换成BigDecimal，最后获得BigDecimal的longValue
	 * @param o
	 * @return 如果转换有误，返回null
	 */
	public static Long toLong(Object o){
		try {
			return BigDecimal.valueOf(toDouble(o)).longValue();
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 直接获得o的toString
	 * @param o
	 * @return 如果转换有误，返回null
	 */
	public static String toString(Object o){
		try {
			return o.toString();
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 获得整数的BigDecimal
	 * @param o
	 * @return 如果转换有误，返回null
	 */
	public static BigDecimal toBigDecimal(Object o){
		try {
			return BigDecimal.valueOf(toLong(o));
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 将对象转换成Double
	 * @param o
	 * @return 如果转换有误，返回null
	 */
	public static Double toDouble(Object o){
		try {
			return Double.valueOf(o.toString());
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 将对象转换成布尔型
	 * 如果对象是字符串类型，就根据Boolean.value()方法转换，
	 * 只有传入"true"的时候才为真,
	 * 如果传入其它类型的对象，则仅当对象不为空时返回真
	 * @param o
	 * @return 如果转换有误，返回false
	 */
	public static boolean toBoolean(Object o){
		try {
			if(o != null){
				if(o instanceof String){
					return Boolean.valueOf(o.toString());
				}else{
					return true;
				}
			}
		} catch (Exception e) {}
		return false;
	}
}
