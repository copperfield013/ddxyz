package cn.sowell.ddxyz.model.common.core.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DispenseCode;
import cn.sowell.ddxyz.model.common.core.OrderDispenseResource;

public class DefaultDispenseResource implements OrderDispenseResource{
	private Set<DispenseCode> dispenseCodeSet = new LinkedHashSet<DispenseCode>();
	private boolean locked = false;
	private int expertCount = 0;
	
	public DefaultDispenseResource(int expertCount) {
		this.expertCount = expertCount;
	}

	@Override
	public synchronized Set<DispenseCode> getDispenseCode() {
		return dispenseCodeSet;
	}

	@Override
	public synchronized void addDispenseCode(DispenseCode dispenseCode) {
		dispenseCodeSet.add(dispenseCode);
	}

	@Override
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked){
		this.locked = locked;
	}
	

	@Override
	public boolean isExpect() {
		return dispenseCodeSet.size() == expertCount;
	}

	/**
	 * 设置期望获得的分配数
	 * @param expertCount
	 */
	public void setExpertCount(int expertCount){
		this.expertCount = expertCount;
	}
	
	@Override
	public void releaseResource() {
		for (DispenseCode dispenseCode : dispenseCodeSet) {
			Delivery delivery = dispenseCode.getBelong();
			if(delivery != null){
				delivery.removeDispenseCode(dispenseCode);
			}
		}
		
	}

}
