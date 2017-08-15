package cn.sowell.ddxyz.model.canteen.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class CanteenDelivery {
	private Long deliveryId;
	private String locationName;
	private Date timePointStart;
	private Date timePointEnd;
	
	private PlainDelivery plainDelivery;
	
	@JSONField(serialize=false)
	List<CanteenDeliveyWares> waresList;

	@JSONField(serialize=false)
	public String getJson() {
		JSONObject jo = (JSONObject) JSON.toJSON(this);
		Map<String, CanteenDeliveyWares> waresMap = CollectionUtils.toMap(waresList, wares->"id_" + wares.getdWaresId());
		jo.put("waresMap", waresMap);
		return jo.toJSONString();
	}
	
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getTimePointStart() {
		return timePointStart;
	}

	public void setTimePointStart(Date timePointStart) {
		this.timePointStart = timePointStart;
	}

	public Date getTimePointEnd() {
		return timePointEnd;
	}

	public void setTimePointEnd(Date timePointEnd) {
		this.timePointEnd = timePointEnd;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}


	public List<CanteenDeliveyWares> getWaresList() {
		return waresList;
	}

	public void setWaresList(List<CanteenDeliveyWares> waresList) {
		this.waresList = waresList;
	}


	public PlainDelivery getPlainDelivery() {
		return plainDelivery;
	}


	public void setPlainDelivery(PlainDelivery plainDelivery) {
		this.plainDelivery = plainDelivery;
	}


	
}
