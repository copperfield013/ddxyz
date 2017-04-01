package cn.sowell.copframe.dto.ajax;

import com.alibaba.fastjson.JSONObject;

public class JsonRequest {
	private JSONObject jsonObject;

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	@Override
	public String toString() {
		if(jsonObject == null){
			return null;
		}else{
			return jsonObject.toJSONString();
		}
	}
	
}
