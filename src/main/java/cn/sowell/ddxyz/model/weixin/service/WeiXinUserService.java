package cn.sowell.ddxyz.model.weixin.service;

import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

import com.alibaba.fastjson.JSONObject;

public interface WeiXinUserService {
	WeiXinUser getWeiXinUserByOpenid(String openid);

	Long saveUser(WeiXinUser wxUser);

	WxUserPrincipal buildFromJsonObject(JSONObject userJson);

	/**
	 * 根据id获得微信用户
	 * @param userId
	 * @return
	 */
	WeiXinUser getWeiXinUserById(Long userId);
	
	/**
	 * 获得所有配置为可以接收messageType的消息的微信粉丝的openid
	 * @param messageType
	 * @return
	 */
	String[] getConfigedMessageOpenids(Long merchantId, String messageType);
	
}
