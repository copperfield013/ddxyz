package cn.sowell.copframe.weixin.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
/**
 * 
 * <p>Title: WeiXinAuthenticationProvider</p>
 * <p>Description: </p><p>
 * 用于微信授权之后，处理本地构造的认证对象
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月8日 下午4:10:15
 */
public class WxAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return WxAuthentication.class.isAssignableFrom(authentication);
	}

}
