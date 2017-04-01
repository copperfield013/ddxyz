package cn.sowell.ddxyz.admin.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;

@Controller
@RequestMapping("/admin/demo")
public class DemoController {
	
	@RequestMapping("/index")
	public String index(){
		return "/admin/demo/index.jsp";
	}
	
	@ResponseBody
	@RequestMapping(value="/testJson")
	public Object testJson(@RequestBody JsonRequest jReq, JsonResponse jRes){
		System.out.println(jReq);
		return jRes.setJsonObject(jReq.getJsonObject());
	}
	
	
	
}
