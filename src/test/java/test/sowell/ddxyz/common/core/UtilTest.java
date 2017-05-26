package test.sowell.ddxyz.common.core;

import org.junit.Test;

import cn.sowell.copframe.utils.text.TextHandler;

public class UtilTest {
	
	@Test
	public void textHandlerTest(){
		TextHandler handler = new TextHandler("ssssa我是#{ a }    a，你是#{ c }aaaasasdasaaasaassssss");
		handler.setParameter("a", "aaaa");
		handler.setParameter("x", "xxx");
		System.out.println(handler.trim("s").getText(true));
	}
}
