package cn.sowell.ddxyz.model.message.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.ddxyz.model.message.service.CustomServerService;

@Service
public class CustomServerServiceImpl implements CustomServerService{

	Logger logger = Logger.getLogger(CustomServerServiceImpl.class);

	@Override
	public JSONObject addCustomServer(String accessToken, JSONObject jo) {
		String url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=" + accessToken;
		JSONObject json = HttpRequestUtils.postJsonAndReturnJson(url, jo);
		logger.info(json);
		return json;
	}

	@Override
	public JSONObject updateCustomServer(String accessToken, JSONObject jo) {
		String url = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=" + accessToken;
		JSONObject json = HttpRequestUtils.postJsonAndReturnJson(url, jo);
		return json;
	}

	@Override
	public JSONObject deleteCustomServer(String accessToken, String kf_account) {
		String url = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=" + accessToken + "&kf_account=" + kf_account;
		JSONObject json = HttpRequestUtils.postAndReturnJson(url, null);
		return json;
	}

	@Override
	public JSONObject getList(String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=" + accessToken;
		JSONObject json = HttpRequestUtils.postAndReturnJson(url, null);
		return json;
	}

	@Override
	public JSONObject inviteCustom(String accessToken, JSONObject jo) {
		String url = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker?access_token=" + accessToken;
		JSONObject json = HttpRequestUtils.postJsonAndReturnJson(url, jo);
		return json;
	}

}
