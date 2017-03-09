package cn.sowell.copframe.weixin.authentication;

import java.util.Date;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
/**
 * 
 * <p>Title: WxUserPrincipal</p>
 * <p>Description: </p><p>
 * 微信接口能够提供的用户信息
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月9日 上午9:38:54
 */
public interface WxUserPrincipal {

	Set<GrantedAuthority> getAuthorities();
	
	public String getOpenid();

	public void setOpenid(String openid);

	public Integer getSubscribe();

	public void setSubscribe(Integer subscribe);

	public String getNickname();

	public void setNickname(String nickname);

	public String getSex();

	public void setSex(String sex);

	public String getLanguage();

	public void setLanguage(String language);

	public String getCity();

	public void setCity(String city);

	public String getProvince();

	public void setProvince(String province);

	public String getCountry();

	public void setCountry(String country);

	public String getHeadimgUrl();

	public void setHeadimgUrl(String headimgUrl);

	public Date getSubscribeTime();

	public void setSubscribeTime(Date subscribeTime);

	public String getUnionid();

	public void setUnionid(String unionid);

	public String getRemark();

	public void setRemark(String remark);

	public String getGroupId();

	public void setGroupId(String groupId);

}
