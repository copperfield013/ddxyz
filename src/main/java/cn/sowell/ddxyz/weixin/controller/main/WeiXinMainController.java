package cn.sowell.ddxyz.weixin.controller.main;

import java.io.BufferedReader;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.weixin.pay.service.WxPayService;
import cn.sowell.ddxyz.weixin.WeiXinConstants;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/weixin")
public class WeiXinMainController {
	
	@Resource
	WxPayService payService;
	
	@RequestMapping({"/", ""})
	public String index(){
		return "/weixin/index.jsp";
	}
	
	
	@RequestMapping("/home")
	public String home(){
		return WeiXinConstants.PATH_BASE + "/home";
	}
	
	
	@RequestMapping("/404")
	public String go404(){
		return WeiXinConstants.PATH_BASE + "/common/404.jsp";
	}
	
	@RequestMapping("/500")
	public String go500(){
		return WeiXinConstants.PATH_BASE + "/common/500.jsp";
	}
	

	@ResponseBody
	@RequestMapping(value="/prepay", headers="Accept=application/json")
	public String prepay(HttpServletRequest request){
		try {
			BufferedReader reader = request.getReader();
			StringBuffer buffer = new StringBuffer();
			String line;
			while((line = reader.readLine()) != null){
				buffer.append(line);
			}
			JSONObject json = JSON.parseObject(buffer.toString());
			System.out.println(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
