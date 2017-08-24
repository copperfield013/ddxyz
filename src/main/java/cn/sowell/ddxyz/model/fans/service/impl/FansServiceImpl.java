package cn.sowell.ddxyz.model.fans.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.ddxyz.model.fans.service.FansService;

@Service
public class FansServiceImpl implements FansService{

	@Override
	public JSONObject getFansList(String accessToken, String nextOpenId) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken + "&next_openid=";
		if(nextOpenId != null){
			url += nextOpenId;
		}
		JSONObject jo = HttpRequestUtils.postAndReturnJson(url, null);
		return jo;
	}

	@Override
	public JSONObject getFansInfoByOpenId(String accessToken, String openId) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN ";
		JSONObject jo = HttpRequestUtils.postAndReturnJson(url, null);
		return jo;
	}

	@Override
	public JSONObject getFansInfoByOpenIds(String accessToken, List<String> openIds) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + accessToken;
		JSONArray user_list = new JSONArray();
		for(String openId : openIds){
			JSONObject openIdJson = new JSONObject();
			openIdJson.put("openid", openId);
			openIdJson.put("lang", "zh_CN");
			user_list.add(openIdJson);
		}
		JSONObject jo = new JSONObject();
		jo.put("user_list", user_list);
		JSONObject result = HttpRequestUtils.postJsonAndReturnJson(url, jo);
		return result;
	}

	@Override
	public List<String> getFansPageList(JSONObject jo, CommonPageInfo pageInfo) {
		pageInfo.setCount(jo.getIntValue("count")); //设置总数
		JSONArray jsonArray = ((JSONObject) jo.get("data")).getJSONArray("openid");
		List<String> openIds = new ArrayList<String>();
		int beginCount = pageInfo.getFirstIndex();
		int endCount = pageInfo.getEndIndex();
		if(endCount > jo.getIntValue("count")){
			endCount = jo.getIntValue("count")-1;
		}
		for(int i = beginCount; i<= endCount; i++){
			openIds.add(jsonArray.getString(i));
		}
		return openIds;
	}

	@Override
	public JSONObject updateFansRemark(String accessToken, JSONObject jo) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=" + accessToken;
		JSONObject result = HttpRequestUtils.postJsonAndReturnJson(url, jo);
		return result;
	}

}
