package cn.sowell.ddxyz.model.menu.service;

import com.alibaba.fastjson.JSONObject;

public interface MenuService {
	
	public JSONObject createMenu(String accessToken, JSONObject jo);
	
	public JSONObject getMenu(String accessToken);

}
