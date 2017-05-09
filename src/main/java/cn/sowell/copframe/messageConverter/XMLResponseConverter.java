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
import cn.sowell.copframe.dto.xml.XMLResponse;

public class XMLResponseConverter  implements HttpMessageConverter<XMLResponse>{

	
	private Charset defaultCharset = Charset.forName(PropertyPlaceholder.getProperty("charset"));
	
	
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return XMLResponse.class.isAssignableFrom(clazz);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return XMLResponse.class.isAssignableFrom(clazz);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.TEXT_XML);
	}

	@Override
	public XMLResponse read(Class<? extends XMLResponse> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return new XMLResponse();
	}

	@Override
	public void write(XMLResponse t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		outputMessage.getHeaders().set(HttpHeaders.CONTENT_TYPE, "text/xml");
		OutputStream body = outputMessage.getBody();
		StreamUtils.copy(t.toString(), defaultCharset, body);
		body.close();
	}

}
