package cn.sowell.ddxyz.model.fans.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.page.CommonPageInfo;

public interface FansService {
	
	JSONObject getFansList(String accessToken, String nextOpenId);
	
	JSONObject getFansInfoByOpenId(String accessToken, String openId);
	
	JSONObject getFansInfoByOpenIds(String accessToken, List<String> openIds);
	
	List<String> getFansPageList(JSONObject jo, CommonPageInfo pageInfo);
	
	JSONObject updateFansRemark(String accessToken, JSONObject jo);

}
