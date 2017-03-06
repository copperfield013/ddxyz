package cn.sowell.ddxyz.common.weixin.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
/**
 * 
 * <p>Title: CommonRequestAttributeSetInterceptor</p>
 * <p>Description: </p><p>
 * spring拦截器，用于在请求中设置一些常用的变量
 * </p>
 * @author Copperfield Zhang
 * @date 2016年12月23日 下午4:06:01
 */
public class CommonRequestAttributeSetInterceptor implements WebRequestInterceptor{

	
	@Override
	public void preHandle(WebRequest request) throws Exception {
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
