package cn.sowell.copframe.utils.binder;

import org.springframework.beans.PropertyValue;

public class DefaultPropertyValueConvertContext implements PropertyValueConvertContext {
	
	private Object target;
	private PropertyValue pv;
	
	public DefaultPropertyValueConvertContext(Object target, PropertyValue pv) {
		this.target = target;
		this.pv = pv;
	}

	@Override
	public Object getTarget() {
		return target;
	}

	@Override
	public String getPropertyName() {
		return getPropertyValue().getName();
	}

	@Override
	public PropertyValue getPropertyValue() {
		return this.pv;
	}

}
