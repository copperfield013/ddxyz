package test.sowell.ddxyz.common.core;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.quartz.impl.calendar.CronCalendar;

import cn.sowell.copframe.dto.format.OfDateFormat;
import cn.sowell.copframe.utils.text.TextHandler;

public class UtilTest {
	
	@Test
	public void textHandlerTest(){
		TextHandler handler = new TextHandler("ssssa我是#{ a }    a，你是#{ c }aaaasasdasaaasaassssss");
		handler.setParameter("a", "aaaa");
		handler.setParameter("x", "xxx");
		System.out.println(handler.trim("s").getText(true));
	}
	
	@Test
	public void cronTest() throws ParseException{
		CronCalendar cal = new CronCalendar("* * 1 * * ?");
		OfDateFormat df = new OfDateFormat();
		Date date = df.parse("2017-6-3 1:00:01");
		System.out.println(cal.isTimeIncluded(date.getTime()));
	}
}
