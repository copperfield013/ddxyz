package cn.sowell.copframe.dto.ajax;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class JsonRequestResolver implements HandlerMethodArgumentResolver{

	@Override
	public Object resolveArgument(MethodParameter parameter, 
			ModelAndViewContainer mavContainer, 
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		BufferedReader reader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String line;
		while((line = reader.readLine()) != null){
			buffer.append(line);
		}
		JSONObject json = JSON.parseObject(buffer.toString());
		JsonRequest jr = new JsonRequest();
		jr.setJsonObject(json);
		return jr;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> paramClass = parameter.getParameterType();
		if(JsonRequest.class.isAssignableFrom(paramClass)){
			return true;
		}
		return false;
	}

}
