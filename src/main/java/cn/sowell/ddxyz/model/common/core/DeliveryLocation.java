package cn.sowell.ddxyz.model.common.core;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.ddxyz.model.common.pojo.PlainLocation;

/**
 * 
 * <p>Title: DeliveryLocation</p>
 * <p>Description: </p><p>
 * 配送点
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月27日 下午4:23:48
 */
public class DeliveryLocation {
	private PlainLocation location;
	
	public DeliveryLocation(PlainLocation location) {
		this.location = location;
	}
	
	public DeliveryLocation(Serializable locationId){
		this();
		this.setId(locationId);
	}
	
	/**
	 * 默认构造一个简单配送地点对象
	 */
	public DeliveryLocation() {
		this(new PlainLocation());
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DeliveryLocation){
			Serializable id = getId();
			if(id != null){
				return id.equals(((DeliveryLocation) obj).getId());
			}
		}
		return false;
	}


	public Serializable getId() {
		return location.getId();
	}


	public void setId(Serializable id) {
		location.setId((Long) id);
	}


	public String getLocationName() {
		return location.getName();
	}

	public void setLocationName(String locationName) {
		location.setName(locationName);
	}
	
	public void setCode(String code){
		location.setCode(code);
	}
	
	public String getCode(){
		return location.getCode();
	}
	
	@Override
	public String toString() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("name", getLocationName());
		return jo.toJSONString();
	}

}
