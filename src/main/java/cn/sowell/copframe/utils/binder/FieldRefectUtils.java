package cn.sowell.copframe.utils.binder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import cn.sowell.copframe.xml.XMLTag;

public class FieldRefectUtils<T> {
	
	private Class<T> clazz;
	
	Map<String, ClassPropertyComposite> propertyMap = null;

	private Function<ClassPropertyComposite, String> propertyNameGetter;
	
	
	public FieldRefectUtils(Class<T> clazz, Function<ClassPropertyComposite, String> propertyNameGetter){
		this.clazz = clazz;
		this.propertyNameGetter = propertyNameGetter;
	}
	
	/**
	 * 遍历所有字段
	 * @param consume
	 */
	public void iterateField(BiConsumer<? super String, ? super ClassPropertyComposite> consume){
		if(propertyMap == null){
			initPropertyMap();
		}
		propertyMap.forEach(consume);
	}
	
	/**
	 * 获得所有字段
	 * @return
	 */
	public Map<String, ClassPropertyComposite> getPropertyMap(){
		return new LinkedHashMap<String, ClassPropertyComposite>(propertyMap);
	}
	
	private void initPropertyMap(){
		propertyMap = new LinkedHashMap<String, ClassPropertyComposite>();
		Map<String, Field> map = getAllFieldMap(clazz);
		map.forEach((fieldName, field) -> {
			Method getter = getGetter(fieldName, clazz),
			setter = getSetter(field, clazz);
			ClassPropertyComposite composite = new ClassPropertyComposite(field, getter, setter);
			String propertyName = getPropertyName(composite);
			if(propertyName != null){
				propertyMap.put(propertyName, composite);
			}
		});
	}
	
	private String getPropertyName(ClassPropertyComposite composite) {
		return propertyNameGetter.apply(composite);
	}

	Map<String, Field> allFieldMap = null;
	/**
	 * 遍历类的所有字段，包括超类的字段
	 * 返回一个map。map的key是字段的tagName
	 * @param clazz
	 * @return
	 */
	Map<String, Field> getAllFieldMap(Class<T> clazz){
		if(allFieldMap == null){
			Map<String, Field> fieldNameMap = new LinkedHashMap<String, Field>();
			Map<String, Field> tagNameMap = new LinkedHashMap<String, Field>();
			Class<?> cClass = clazz;
			while(cClass != Object.class){
				Field[] fields = cClass.getDeclaredFields();
				for (Field field : fields) {
					String fieldName = field.getName();
					if(!fieldNameMap.containsKey(fieldName)){
						String key = fieldName;
						XMLTag tag = field.getDeclaredAnnotation(XMLTag.class);
						if(tag != null && !tag.tagName().isEmpty()){
							key = tag.tagName();
						}
						fieldNameMap.put(fieldName, field);
						tagNameMap.put(key, field);
					}
				}
				cClass = cClass.getSuperclass();
			}
			allFieldMap = tagNameMap;
		}
		return allFieldMap;
	}
	
	
	
	private Map<String, Method> setterMap = new HashMap<String, Method>();
	private Map<String, Method> getterMap = new HashMap<String, Method>();
	
	private Method getGetter(String fieldName, Class<T> baseClass){
		Method method = getterMap.get(fieldName);
		Class<?> cClass = baseClass;
		if(method == null){
			while(cClass != Object.class){
					try {
						method = cClass.getDeclaredMethod("get" + upcaseFirst(fieldName));
					} catch (NoSuchMethodException | SecurityException e) {}
					if(method != null){
						getterMap.put(fieldName, method);
					}else{
						try {
							method = cClass.getDeclaredMethod("is" + upcaseFirst(fieldName));
						} catch (NoSuchMethodException | SecurityException e) {}
					}
				cClass = cClass.getSuperclass();
			}
		}
		return method;
	}
	
	private Method getSetter(Field field, Class<T> baseClass){
		String fieldName = field.getName();
		Method method = setterMap.get(fieldName);
		Class<?> cClass = baseClass;
		if(method == null){
			while(cClass != Object.class){
				try {
					method = cClass.getDeclaredMethod("set" + upcaseFirst(fieldName), field.getType());
					if(method != null){
						setterMap.put(fieldName, method);
					}
				} catch (NoSuchMethodException | SecurityException e) {
				}
				cClass = cClass.getSuperclass();
			}
		}
		return method;
	}
	
	
	private String upcaseFirst(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
