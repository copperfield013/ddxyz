package cn.sowell.copframe;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import cn.sowell.copframe.common.CpfHandlerExceptionResolver;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
/**
 * <p>Title: ControllerExceptionHandler</p>
 * <p>Description: </p><p>
 * 通用的Controller错误处理机制，将错误显示到控制台后将错误信息返回到页面
 * </p>
 * @author Copperfield Zhang
 * @date 2016年12月20日 上午11:27:28
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	@Resource
    WxConfigService configService;
	
	@Resource
	CpfHandlerExceptionResolver exceptionResolver;

	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleIOException(Exception ex, HttpServletRequest request) {
        ex.printStackTrace();
        if(configService.isDebug()){
    		return new ModelAndView(new View() {
				
				@Override
				public void render(Map<String, ?> model, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					response.getWriter().append(ClassUtils.getShortName(ex.getClass()) + ex.getMessage());
				}
				
				@Override
				public String getContentType() {
					return "text/plain;charset=utf-8";
				}
			});
        }else{
        	return new ModelAndView(exceptionResolver.dispatch500(request));
        }
    }
    
    
    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleIOException(Exception ex, Writer writer, ModelAndView mv) {
        ex.printStackTrace();
        if(configService.isDebug()){
        	try {
				writer.write(ClassUtils.getShortName(ex.getClass()) + ex.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }else{
        	mv.setViewName("/weixin/common/500.jsp");
        }
    }*/
}
