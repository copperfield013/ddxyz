package cn.sowell.ddxyz.api.controller.qrcode;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.utils.qrcode.QrCodeUtils;
import cn.sowell.ddxyz.api.controller.ApiConstants;

@Controller
@RequestMapping(ApiConstants.QRCODE_PATH)
public class ApiQrCodeController {
	
	@ResponseBody
	@RequestMapping(value={"/{sourceCode}.{suffix}"}, method=RequestMethod.GET)
	public ResponseEntity<byte[]> generateImage(
			@PathVariable("sourceCode") String sourceCode, 
			@PathVariable("suffix") String suffix, 
			@RequestParam(required=false, defaultValue="400") Integer size,
			@RequestParam(required=false) String cacheMode, HttpServletRequest request){
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		QrCodeUtils.encodeQRCodeImage(sourceCode, "utf-8", bo, suffix, size, size);
		byte[] byteArray = bo.toByteArray();
		HttpHeaders header = new HttpHeaders();
		header.add("content-type", "image/" + suffix + ";charset=utf-8");
		return new ResponseEntity<byte[]>(byteArray, header, HttpStatus.CREATED);
	}
	
}
