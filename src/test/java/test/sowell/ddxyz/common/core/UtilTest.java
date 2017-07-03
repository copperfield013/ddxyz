package test.sowell.ddxyz.common.core;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.quartz.CronExpression;
import org.quartz.impl.calendar.CronCalendar;

import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.format.OfDateFormat;
import cn.sowell.copframe.utils.text.TextHandler;
import cn.sowell.ddxyz.model.common.utils.DeliveryPeriodUtils;

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
		FrameDateFormat df = new OfDateFormat();
		Date theDay = df .parse("2017-6-3");
		int[] hours = DeliveryPeriodUtils.getCronHourList("0 0 2,3,4 * * ?", theDay);
		for (int hour : hours) {
			System.out.println(hour + "点");
		}
	}
	
	
}
