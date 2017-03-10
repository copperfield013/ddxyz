package cn.sowell.copframe.utils.binder;

import org.springframework.beans.PropertyValue;

public interface PropertyValueConvertContext {
	Object getTarget();
	String getPropertyName();
	PropertyValue getPropertyValue();
}
