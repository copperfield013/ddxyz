package cn.sowell.copframe.dto.ajax;

import com.alibaba.fastjson.JSONObject;

public class JsonResponse {
	private JSONObject jsonObject = new JSONObject();

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
	
	public JsonResponse put(String key, Object value){
		jsonObject.put(key, value);
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
