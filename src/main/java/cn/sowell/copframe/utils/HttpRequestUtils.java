package cn.sowell.copframe.utils;

import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class HttpRequestUtils {
	
	/**
	 * 用于获取当前请求的完整url，包括get方式提交的参数
	 * @param request
	 * @param withoutPort 是否要去去除端口
	 * @return
	 */
	public static String getCompleteURL(HttpServletRequest request){
		return getCompleteURL(request, true);
	}
	
	/**
	 * 用于获取当前请求的完整url，包括get方式提交的参数
	 * @param request
	 * @param withoutPort 是否要去去除端口
	 * @return
	 */
	public static String getCompleteURL(HttpServletRequest request, boolean withoutPort){
		Assert.notNull(request);
		String requestURL = request.getRequestURL().toString();
		if(withoutPort){
			Pattern pattern =  Pattern.compile("^(\\w+://)?[^/]+:(\\d+)/?(.*)$");
			Matcher matcher = pattern.matcher(requestURL);
			if(matcher.matches()){
				String port = matcher.group(2);
				requestURL = requestURL.replaceFirst(":" + port, "");
			}
		}
		String requestQuery = request.getQueryString(),
			url = requestURL + (StringUtils.hasText(requestQuery) ? ("?" + requestQuery) : "")
			;
		return url;
	}
	/**
	 * 将request中parameter数据转到attribute中
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static void restoreParametersToAttribute(HttpServletRequest request){
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			String[] parameters = request.getParameterValues(name);
			if(parameters != null){
				if(parameters.length >= 1){
					request.setAttribute(name, parameters[0]);
				}
			}
		}
	}
}
