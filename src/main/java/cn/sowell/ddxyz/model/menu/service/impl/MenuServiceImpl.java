package cn.sowell.ddxyz.model.menu.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.ddxyz.model.menu.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	

	@Override
	public JSONObject createMenu(String accessToken, JSONObject jo) {
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
		JSONObject json = HttpRequestUtils.postJsonAndReturnJson(url, jo);
		return json;
	}

	@Override
	public JSONObject getMenu(String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + accessToken;
		JSONObject json = HttpRequestUtils.postAndReturnJson(url, null);
		return json;
	}

}
