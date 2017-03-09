package cn.sowell.ddxyz.admin.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/demo")
public class DemoController {
	
	@RequestMapping("/index")
	public String index(){
		return "/admin/demo/index.jsp";
	}
	
	
	
	
}
