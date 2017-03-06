package cn.sowell.ddxyz.wxport.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixin")
public class MainController {
	
	@RequestMapping("/")
	public String index(){
		return "/weixin/index.jsp";
	}
	
	
}
