package cn.sowell.ddxyz.admin.controller.test;

import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.ddxyz.admin.AdminConstants;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/test")
public class AdminTestController {
	
	@RequestMapping("/upload")
	public String upload() throws ParseException{
		return AdminConstants.PATH_TEST + "/test_upload.jsp";
	}
	
	
	@ResponseBody
	@RequestMapping("/doUpload")
	public JsonResponse doUpload(@RequestParam MultipartFile file){
		JsonResponse jRes = new JsonResponse();
		System.out.println(file.getOriginalFilename());
		jRes.setStatus("suc");
		return jRes;
	}
	
	@RequestMapping("/showFile")
	public String showFile(){
		return AdminConstants.PATH_TEST + "/test_upload_file.jsp";
	}
	
}
