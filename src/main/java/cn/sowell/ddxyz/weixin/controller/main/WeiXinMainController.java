package cn.sowell.ddxyz.weixin.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixin")
public class WeiXinMainController {
	
	@RequestMapping({"/", ""})
	public String index(){
		return "/weixin/index.jsp";
	}
	
	
}
