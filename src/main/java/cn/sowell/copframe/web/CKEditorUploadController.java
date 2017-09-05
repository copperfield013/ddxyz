package cn.sowell.copframe.web;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.sowell.copframe.common.file.FileUploadUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;

@Controller
@RequestMapping("/ckeditor/upload")
public class CKEditorUploadController {
	
	@Resource
	FileUploadUtils uploadUtils;
	
	@Resource
	WxConfigService configService;
	
	@ResponseBody
	@RequestMapping("/image")
	public String uploadImage(MultipartFile upload, @RequestParam String CKEditorFuncNum){
		String url = "";
		String errMsg = "";
		try {
			String uri = uploadUtils.saveFile(upload);
			url = configService.getProjectURL() + uri;
		} catch (IOException e) {
			errMsg = "上传图片时发生异常";
		}
		return "<script type=\"text/javaScript\">"
				+ "window.parent.CKEDITOR.tools.callFunction('" + CKEditorFuncNum + "' , '" + url + "' ,'" + errMsg + "');</script>";
	}
	
	
}
