package cn.sowell.copframe.dto.ajax;

import com.alibaba.fastjson.JSONObject;

public class JsonResponse {
	private JSONObject jsonObject;

	public JsonResponse() {
		jsonObject = new JSONObject();
	}
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public JsonResponse setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
		return this;
	}
	
	
	@Override
	public String toString() {
		if(jsonObject == null){
			return null;
		}
		return jsonObject.toString();
	}
}
