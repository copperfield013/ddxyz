package cn.sowell.ddxyz.model.common.core.impl;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.ToLongFunction;

import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DispenseCode;

public class DefaultDispenseCode implements DispenseCode, Comparable<DispenseCode>{

	private Serializable key;
	private String code;
	private Delivery belong;
	
	@Override
	public Serializable getKey() {
		return key;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public Delivery getBelong() {
		return belong;
	}

	public void setKey(Serializable key) {
		this.key = key;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setBelong(Delivery belong) {
		this.belong = belong;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DispenseCode){
			if(code != null){
				return code.equals(((DispenseCode) obj).getCode());
			}else if(code == null && ((DispenseCode)obj).getCode() == null){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int compareTo(DispenseCode o) {
		if(o == null) return 1;
		Serializable thisKey = getKey(), otherKey = o.getKey();
		if(thisKey == null && otherKey == null) return 0;
		if(thisKey == null) return -1;
		if(otherKey == null) return 1;
		if(thisKey.getClass() == otherKey.getClass()){
			if(Comparable.class.isAssignableFrom(thisKey.getClass())){
				return ((Comparable)thisKey).compareTo(otherKey);
			}else{
				return Comparator.comparingLong(new ToLongFunction<DispenseCode>() {
					@Override
					public long applyAsLong(DispenseCode value) {
						return FormatUtils.toLong(value.getKey());
					}
				}).compare(this, o);
			}
		}else{
			throw new IllegalArgumentException("比较的dispenseCode的key类型不一致，对比的两个key的类型分别是[" + thisKey.getClass() + "]和[" + otherKey.getClass() + "]");
		}
	}
}
