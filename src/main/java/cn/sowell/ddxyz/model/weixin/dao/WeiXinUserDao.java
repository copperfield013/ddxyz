package cn.sowell.ddxyz.model.weixin.dao;

import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

public interface WeiXinUserDao {
	WeiXinUser getWeiXinUserByOpenid(String openid);

	Long save(WeiXinUser wxUser);

	/**
	 * 根据微信用户的id从数据库获得用户信息
	 * @param orderUserId
	 * @return
	 */
	WeiXinUser getUser(long userId);
	
	/**
	 * 获得所有配置为可以接收messageType的消息的微信粉丝的openid
	 * @param messageType
	 * @return
	 */
	String[] getConfigedMessageOpenids(Long merchantId, String messageType);
}
