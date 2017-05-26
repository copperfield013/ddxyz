package cn.sowell.ddxyz.weixin.controller.authenticate;


import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.sowell.copframe.SystemConstants;
import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.copframe.weixin.authentication.WxAuthorizationAccessToken;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.service.WxUserApiService;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;
import cn.sowell.ddxyz.model.weixin.service.WeiXinUserService;

@Controller
@RequestMapping("/weixin/authenticate")
public class WeiXinAuthenticateController {
	
	@Resource
	WeiXinUserService weiXinUserService;
	
	@Resource
	WxUserApiService userApiService;

	@Resource
	WxConfigService configService;
	
	Logger logger = Logger.getLogger(WeiXinAuthenticateController.class);
	
	@RequestMapping("/")
	public void authenticate(
			@RequestParam("code") String code, 
			@RequestParam("state") String state,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelAndView mv){
		HttpSession session = request.getSession();
		String sessionState = (String) session.getAttribute(SystemConstants.WXAUTHEN_STATE_KEY);
		if(state.equals(sessionState)){
			//移除该state，防止重复访问
			session.removeAttribute(SystemConstants.WXAUTHEN_STATE_KEY);
			//第一级验证，要求返回的state与发送之前的state相同
			if(!code.isEmpty()){
				WxAuthorizationAccessToken token = userApiService.getAuthorizationAccessToken(code);
				if(token != null){
					//根据accessToken和openid获得该用户的详细信息（获得部分信息）
					WxUserPrincipal wxUser = weiXinUserService.getWeiXinUserByOpenid(token.getOpenid());
					if(wxUser == null){
						//创建用户信息并保存到数据库
						logger.debug("数据库中不存在openid[" + token.getOpenid() + "]的用户，将进行创建");
						WeiXinUser user = new WeiXinUser();
						userApiService.loadUserFromServer(token, user);
						user.setAuthorityChain(PropertyPlaceholder.getProperty("wx_default_authen"));
						Date date = new Date();
						user.setAppid(configService.getAppid());
						user.setCreateTime(date);
						user.setUpdateTime(date);
						Long id = weiXinUserService.saveUser(user);
						wxUser = user;
						logger.debug("保存微信用户[" + user.getNickname() + "]到数据库中成功[id=" + id + "]");
					}
					//将用户信息保存到session中
					session.setAttribute(SystemConstants.WXUSER_KEY, wxUser);
					logger.debug("保存微信用户[" + wxUser.getNickname() + "]到session中[key=" + SystemConstants.WXUSER_KEY + "]");
					String redirectURL = (String) session.getAttribute(SystemConstants.WXREDIRECT_URL_KEY);
					try {
						response.sendRedirect(redirectURL);
						session.removeAttribute(SystemConstants.WXREDIRECT_URL_KEY);
						return;
					} catch (IOException e) {
					}
				}
				
			}
		}
		mv.setViewName("/weixin/common/denied.jsp");
	}
	
	
}
