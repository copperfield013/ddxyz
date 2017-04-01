package cn.sowell.ddxyz.model.weixin.service;

import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

import com.alibaba.fastjson.JSONObject;

public interface WeiXinUserService {
	WeiXinUser getWeiXinUserByOpenid(String openid);

	Long saveUser(WeiXinUser wxUser);

	WxUserPrincipal buildFromJsonObject(JSONObject userJson);
}
