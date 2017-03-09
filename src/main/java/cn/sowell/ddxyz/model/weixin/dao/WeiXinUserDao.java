package cn.sowell.ddxyz.model.weixin.dao;

import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

public interface WeiXinUserDao {
	WeiXinUser getWeiXinUserByOpenid(String openid);

	Long save(WeiXinUser wxUser);
}
