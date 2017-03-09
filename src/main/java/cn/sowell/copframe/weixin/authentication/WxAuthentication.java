package cn.sowell.copframe.weixin.authentication;

import java.util.Set;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
/**
 * 
 * <p>Title: WeiXinAuthentication</p>
 * <p>Description: </p><p>
 * 微信授权后的认证类
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月8日 下午4:18:22
 */
public class WxAuthentication extends AbstractAuthenticationToken{

	private static final long serialVersionUID = -8427350778217499484L;
	
	private final WxUserPrincipal principal;
	
	public WxAuthentication(WxUserPrincipal user) {
		super(getAuthoritiesFromUser(user));
		this.principal = user;
	}

	
	private static Set<GrantedAuthority> getAuthoritiesFromUser(WxUserPrincipal user){
		return user.getAuthorities();
	}
	

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

}
