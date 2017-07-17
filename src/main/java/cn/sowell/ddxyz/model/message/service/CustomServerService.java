package cn.sowell.ddxyz.model.message.service;

import com.alibaba.fastjson.JSONObject;

public interface CustomServerService {
	
	/**
	 * 添加客服
	 * @param accessToken
	 * @param jo
	 * @return
	 */
	JSONObject addCustomServer(String accessToken, JSONObject jo);
	
	/**
	 * 修改客服信息
	 * @param accessToken
	 * @param jo
	 * @return
	 */
	JSONObject updateCustomServer(String accessToken, JSONObject jo);
	
	/**
	 * 删除客服信息
	 * @param accessToken
	 * @param jo
	 * @return
	 */
	JSONObject deleteCustomServer(String accessToken, String kf_account);
	
	/**
	 * 获取所有的客服人员列表
	 * @param accessToken
	 * @return
	 */
	JSONObject getList(String accessToken);
	
	/**
	 * 邀请绑定客服微信号
	 * @param accessToken
	 * @param jo
	 * @return
	 */
	JSONObject inviteCustom(String accessToken, JSONObject jo);
}
