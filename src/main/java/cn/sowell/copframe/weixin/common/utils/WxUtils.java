package cn.sowell.copframe.weixin.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
	 * @return
	 */
	public static WxUserPrincipal getCurrentUser(){
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if(authen != null){
			Object user = authen.getPrincipal();
			if(user instanceof WxUserPrincipal){
				return (WxUserPrincipal) user;
			}
		}
		return null;
	}
}
