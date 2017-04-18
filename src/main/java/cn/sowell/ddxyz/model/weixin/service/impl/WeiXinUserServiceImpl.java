package cn.sowell.ddxyz.model.weixin.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.weixin.dao.WeiXinUserDao;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.model.weixin.service.WeiXinUserService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class WeiXinUserServiceImpl implements WeiXinUserService{
	@Resource
	WeiXinUserDao wxUserDao;
	
	@Override
	public WeiXinUser getWeiXinUserByOpenid(String openid) {
		return wxUserDao.getWeiXinUserByOpenid(openid);
	}

	@Override
	public Long saveUser(WeiXinUser wxUser) {
		return wxUserDao.save(wxUser);
	}
	
	
	@Override
	public WxUserPrincipal buildFromJsonObject(JSONObject userJson) {
		WeiXinUser wxUser = new WeiXinUser();
		/*wxUser.setSubscribe(userJson.getInteger("subscribe"));
		wxUser.setSubscribeTime(new Date(userJson.getLong("subscribe_time") * 1000));
		wxUser.setGroupId(userJson.getString("groupid"));;
		wxUser.setLanguage(userJson.getString("language"));
		wxUser.setRemark(userJson.getString("remark"));*/
		
		wxUser.setOpenid(userJson.getString("openid"));
		wxUser.setNickname(userJson.getString("nickname"));
		int intSex = userJson.getIntValue("sex");
		wxUser.setSex(intSex == 1? "男": intSex == 2? "女": "未知");
		wxUser.setCity(userJson.getString("city"));
		wxUser.setProvince(userJson.getString("province"));
		wxUser.setCountry(userJson.getString("country"));
		wxUser.setHeadimgUrl(userJson.getString("headimgurl"));
		wxUser.setUnionid(userJson.getString("unionid"));
		StringBuffer buffer = new StringBuffer();
		JSONArray array = userJson.getJSONArray("privilege");
		for (Object item : array) {
			buffer.append(item + DdxyzConstants.COMMON_SPLITER);
		}
		wxUser.setPrivilege(buffer.toString());
		
		wxUser.setAuthorityChain(PropertyPlaceholder.getProperty("wx_default_authen"));
		
		Date date = new Date();
		wxUser.setCreateTime(date);
		wxUser.setUpdateTime(date);
		return wxUser;
	}

	
	@Override
	public WeiXinUser getWeiXinUserById(Long userId) {
		return wxUserDao.getUser(userId);
	}
}
