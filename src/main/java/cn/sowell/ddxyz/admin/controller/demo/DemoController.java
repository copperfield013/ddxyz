package cn.sowell.ddxyz.admin.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/admin/demo")
public class DemoController {
	
	@RequestMapping("/index")
	public String index(){
		return "/admin/demo/index.jsp";
	}
	
	@RequestMapping(value="/testJson",headers="ACCEPT=application/json")
	public String testJson(JSONObject json){
		return json.toJSONString();
	}
	
	
	
}
