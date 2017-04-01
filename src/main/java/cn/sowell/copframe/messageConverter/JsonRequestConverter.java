package cn.sowell.copframe.messageConverter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.dto.ajax.JsonRequest;

public class JsonRequestConverter implements HttpMessageConverter<JsonRequest>{
	
	private Charset defaultCharset = Charset.forName(PropertyPlaceholder.getProperty("charset"));
	
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return JsonRequest.class.isAssignableFrom(clazz) && MediaType.APPLICATION_JSON.includes(mediaType);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.APPLICATION_JSON);
	}

	@Override
	public JsonRequest read(Class<? extends JsonRequest> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		InputStream input = inputMessage.getBody();
		String body = StreamUtils.copyToString(input, defaultCharset);
		JSONObject json = null;
		try {
			json = JSON.parseObject(body);
		} catch (Exception e) {
		}
		if(json != null){
			JsonRequest jr = new JsonRequest();
			jr.setJsonObject(json);
			return jr;
		}else{
			return null;
		}
	}

	@Override
	public void write(JsonRequest t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
	}


}
