package cn.sowell.ddxyz.weixin.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.service.WxCredentialService;

import com.alibaba.fastjson.JSON;
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

	@Resource
	WxConfigService configService;
	
	@Resource
	WxCredentialService credentialService;
	
	@Override
	public void preHandle(WebRequest request) throws Exception {
		if(request instanceof NativeWebRequest){
			HttpServletRequest req = ((NativeWebRequest) request).getNativeRequest(HttpServletRequest.class);
			String url = HttpRequestUtils.getCompleteURL(req);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			AntPathMatcher matcher = new AntPathMatcher("/");
			String path = HttpRequestUtils.getURI(req);
			
			String basePath = req.getScheme()+"://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath()+"/";
			if(matcher.match("/weixin", path) || matcher.match("/weixin/**", path)){
				basePath = configService.getProjectURL();
				//匹配微信端的请求
				String appid = configService.getAppid(),
						nonce = TextUtils.uuid();
				long timeStamp = configService.getCurrentTimestamp();
				String jsApiTicket = credentialService.getJsApiTicket();
				Map<String, String> signParam = new HashMap<String, String>();
				signParam.put("noncestr", nonce);
				signParam.put("jsapi_ticket", jsApiTicket);
				signParam.put("timestamp", String.valueOf(timeStamp));
				signParam.put("url", url);
				String signature = configService.getSha1Signature(signParam);
				
				paramMap.put("appid", appid);
				paramMap.put("timestamp", timeStamp);
				paramMap.put("nonceStr", nonce);
				paramMap.put("signature", signature);
				
				
				request.setAttribute("$paramMap", paramMap, WebRequest.SCOPE_REQUEST);
				request.setAttribute("$paramMapJson", JSON.toJSON(paramMap), WebRequest.SCOPE_REQUEST);
			}
			req.setAttribute("basePath", basePath);
		}
		
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
