package cn.sowell.copframe.weixin.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;

/**
 * 
 * <p>Title: WxUserUtils</p>
 * <p>Description: </p><p>
 * 微信模块的通用工具类
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月9日 上午9:38:13
 */
public class WxUtils {
	/**
	 * 获得当前登录的微信用户的信息
	 * 建议使用{@link #getCurrentUser(Class)}方法，参数用{@link WxUserPrincipal}来获取
	 * @return
	 */
	@Deprecated
	public static WxUserPrincipal getCurrentUser(){
		return getCurrentUser(WxUserPrincipal.class);
	}
	
	/**
	 * 
	 * @param <T>
	 * 当前支持类型
	 * 1.{@link WxUserPrincipal}
	 * 2.{@link UserIdentifier}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCurrentUser(Class<T> userClass){
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if(authen != null){
			Object user = authen.getPrincipal();
			return (T) user;
		}
		return null;
	}
	
}
