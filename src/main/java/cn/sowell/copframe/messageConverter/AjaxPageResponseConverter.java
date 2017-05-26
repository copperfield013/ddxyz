package cn.sowell.copframe.messageConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import cn.sowell.copframe.common.property.PropertyPlaceholder;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class AjaxPageResponseConverter implements HttpMessageConverter<AjaxPageResponse>{

	private Charset defaultCharset = Charset.forName(PropertyPlaceholder.getProperty("charset"));
	
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return AjaxPageResponse.class.isAssignableFrom(clazz);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return AjaxPageResponse.class.isAssignableFrom(clazz);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.APPLICATION_JSON);
	}

	@Override
	public AjaxPageResponse read(Class<? extends AjaxPageResponse> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return new AjaxPageResponse();
	}

	@Override
	public void write(AjaxPageResponse t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		outputMessage.getHeaders().set(HttpHeaders.CONTENT_TYPE, "text/json");
		OutputStream body = outputMessage.getBody();
		StreamUtils.copy(toJson(t), defaultCharset, body);
		body.close();
	}
	
	
	
	private String toJson(AjaxPageResponse t){
		SerializeWriter out = new SerializeWriter();
		String result = null;
        try {  
            JSONSerializer serializer = new JSONSerializer(out);  
            serializer.config(SerializerFeature.WriteEnumUsingName, false);
            serializer.config(SerializerFeature.WriteEnumUsingToString, true);
            serializer.write(t);
            result = out.toString();
        } finally {  
            out.close();  
        }  
        return result;
	}
	
	
	
	
}
