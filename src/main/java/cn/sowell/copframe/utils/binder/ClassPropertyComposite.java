package cn.sowell.copframe.utils.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.springframework.util.Assert;

import cn.sowell.copframe.dto.format.FormatUtils;

public class ClassPropertyComposite {
	private Field field;
	private Method getter;
	private Method setter;
	
	public ClassPropertyComposite(Field field, Method getter, Method setter) {
		super();
		Assert.notNull(field);
		Assert.notNull(getter);
		Assert.notNull(setter);
		this.field = field;
		this.getter = getter;
		this.setter = setter;
	}
	
	public String getFieldName(){
		return this.field.getName();
	}
	
	public Object getValue(Object target) throws PropertyMethodException{
		try {
			return getter.invoke(target);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new PropertyMethodException(e);
		}
	}
	
	public ClassPropertyComposite setValue(Object target, Object value) throws PropertyMethodException{
		try {
			Parameter[] parameter = setter.getParameters();
			if(parameter.length == 1){
				Class<?> paramType = parameter[0].getType();
				value = FormatUtils.toClass(paramType, value);
			}
			setter.invoke(target, value);
		} catch (IllegalAccessException
				| InvocationTargetException e) {
			throw new PropertyMethodException(e);
		}
		return this;
	}
	
	public <T extends Annotation> T getFieldAnno(Class<T> annotationClass){
		return field.getDeclaredAnnotation(annotationClass);
	}
	
	public <T extends Annotation> boolean hasFieldAnno(Class<T> annotationClass){
		return getFieldAnno(annotationClass) != null;
	}
	
	public <T extends Annotation> T getGetterAnno(Class<T> annotationClass){
		return getter.getDeclaredAnnotation(annotationClass);
	}
	
	public <T extends Annotation> boolean hasGetterAnno(Class<T> annotationClass){
		return getGetterAnno(annotationClass) != null;
	}
	
	public <T extends Annotation> T getSetterAnno(Class<T> annotationClass){
		return setter.getDeclaredAnnotation(annotationClass);
	}
	
	public <T extends Annotation> boolean hasSetterAnno(Class<T> annotationClass){
		return getSetterAnno(annotationClass) != null;
	} 
	
	
}
