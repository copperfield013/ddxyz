package cn.sowell.copframe.weixin.authentication;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.sowell.copframe.SystemConstants;
import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.weixin.common.service.WxUserApiService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
/**
 * 
 * <p>Title: WeiXinUserOAuth2Filter</p>
 * <p>Description: </p><p>
 * 过滤微信端请求的过滤器，用于微信授权获得当前用户的信息
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月8日 下午4:11:43
 */
public class WxUserOAuth2Filter implements Filter {
	
	@Resource
	WxUserApiService wxUserApiService;
	
	Logger logger = Logger.getLogger(WxUserOAuth2Filter.class);
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.getContext();
		//只处理Http请求
		if(req instanceof HttpServletRequest && res instanceof HttpServletResponse){
			HttpServletRequest request = (HttpServletRequest) req;
			//获得当前会话的认证对象
			Authentication authentication = context.getAuthentication();
			logger.debug("过滤微信端请求:[" + HttpRequestUtils.getCompleteURL(request) + "],远程地址[" + request.getRemoteAddr() + "]");
			//如果当前认证对象为空，那么可能是用户对象还没有构造
			if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
				HttpSession session = request.getSession();
				//从session中获得认证对象
				WeiXinUser user = (WeiXinUser) session.getAttribute(SystemConstants.WXUSER_KEY);
				if(user != null){
					logger.debug("WeiXinUser[" + user.getNickname() + "]存在，将其构造成Authority对象并放到context中");
					//根据微信用户构造认证对象
					WxAuthentication wxAuthen = new WxAuthentication(user);
					//设置认证对象
					context.setAuthentication(wxAuthen);
					//移除微信用户对象
					session.removeAttribute(SystemConstants.WXUSER_KEY);
				}else{
					String projectURL = PropertyPlaceholder.getProperty("project_url") ;
					//获得当前请求的完整路径
					String reqURL = projectURL + PropertyPlaceholder.getProperty("wx_oauth_def_uri");
					if("get".equals(request.getMethod())){
						reqURL = HttpRequestUtils.getCompleteURL(request);
					}
					//保存当前的请求地址，用于本地认证后跳转
					session.setAttribute(SystemConstants.WXREDIRECT_URL_KEY, reqURL);
					//获得随机字符串，用于验证授权后的请求是不是当前执行的重定向引起的
					String state = TextUtils.randomStr(5, 62);
					//保存随机字符串
					session.setAttribute(SystemConstants.WXAUTHEN_STATE_KEY, state);
					//验证授权后，请求的地址，并不是当前拦截的地址
					String oAuthURL = wxUserApiService.getOauthRedirectURL(state);
					logger.debug("重定向向微信服务器请求验证[" + oAuthURL + "]");
					((HttpServletResponse) res).sendRedirect(oAuthURL);
					return;
				}
			}
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
