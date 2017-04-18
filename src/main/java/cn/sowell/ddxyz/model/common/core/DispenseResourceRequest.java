package cn.sowell.ddxyz.model.common.core;


/**
 * 
 * <p>Title: DispenseResourceRequest</p>
 * <p>Description: </p><p>
 * 分配资源的请求对象
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月30日 上午9:48:00
 */
public class DispenseResourceRequest {
	private int dispenseCount = 0;
	private boolean resourceLocked;
	
	
	
	public int getDispenseCount() {
		return dispenseCount;
	}
	public void setDispenseCount(int dispenseCount) {
		this.dispenseCount = dispenseCount;
	}
	public boolean isResourceLocked() {
		return resourceLocked;
	}
	public void setResourceLocked(boolean resourceLocked) {
		this.resourceLocked = resourceLocked;
	}
	
}
