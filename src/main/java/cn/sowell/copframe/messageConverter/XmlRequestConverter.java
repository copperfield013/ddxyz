package cn.sowell.copframe.messageConverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import cn.sowell.copframe.dto.xml.XMLRequest;
import cn.sowell.copframe.utils.xml.Dom4jNode;
import cn.sowell.copframe.utils.xml.XmlNode;

public class XmlRequestConverter implements HttpMessageConverter<XMLRequest>{

	static final SAXReader reader = new SAXReader();
	
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return XMLRequest.class.isAssignableFrom(clazz) && MediaType.TEXT_XML.includes(mediaType);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.TEXT_XML);
	}

	@Override
	public XMLRequest read(Class<? extends XMLRequest> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		InputStream input = inputMessage.getBody();
		Document ele;
		try {
			ele = reader.read(input);
			XmlNode node = new Dom4jNode(ele.getRootElement());
			return new XMLRequest(node);
		} catch (DocumentException e) {
			return null;
		}
	}

	@Override
	public void write(XMLRequest t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		
	}

}
