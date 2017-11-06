package cn.sowell.copframe.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

public class CpfHandlerExceptionResolver extends DefaultHandlerExceptionResolver{
	
	private Map<Integer, Map<String, String>> errorForwardMap;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		if(ex instanceof NoHandlerFoundException){
			String viewName = dispatch404(request);
			if(viewName != null){
				mv.setStatus(HttpStatus.NOT_FOUND);
				mv.setViewName(viewName);
				return mv;
			}
		}
		return null;
	}
	
	private String dispatch(Integer httpStatus, HttpServletRequest request){
		Map<String, String> pathMap = errorForwardMap.get(httpStatus);
		if(pathMap != null){
			String uri = request.getRequestURI().substring(request.getContextPath().length());
			
			StringBuffer result = new StringBuffer();
			pathMap.forEach((exp, viewName)->{
				AntPathMatcher matcher = new AntPathMatcher();
				if(matcher.match(exp, uri)){
					result.append(viewName);
					return;
				}
			});
			if(result.length() > 0){
				return result.toString();
			}
		}
		return null;
	}
	
	private String dispatch404(HttpServletRequest request) {
		return dispatch(404, request);
	}

	public String dispatch500(HttpServletRequest request) {
		return dispatch(500, request);
	}
	
	@Override
	public int getOrder() {
		return -1;
	}

	public Map<Integer, Map<String, String>> getErrorForwardMap() {
		return errorForwardMap;
	}

	public void setErrorForwardMap(Map<Integer, Map<String, String>> errorForwardMap) {
		this.errorForwardMap = errorForwardMap;
	}

}
