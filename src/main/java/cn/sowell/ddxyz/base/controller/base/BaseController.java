package cn.sowell.ddxyz.base.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BaseController {
	
	
	@RequestMapping("/blank_page")
	String blank(){
		return "/common/blank.jsp";
	}
}
