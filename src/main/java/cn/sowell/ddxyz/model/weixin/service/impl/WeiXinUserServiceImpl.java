package cn.sowell.ddxyz.model.weixin.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
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
	
	@Resource
	WxConfigService configService;
	
	Logger logger = Logger.getLogger(WeiXinUserServiceImpl.class);
	
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
		
		wxUser.setAppid(configService.getAppid());
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
	
	@Override
	public String[] getConfigedMessageOpenids(Long merchantId,
			String messageType) {
		return wxUserDao.getConfigedMessageOpenids(merchantId, messageType);
	}
	
}
