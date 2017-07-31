package test.sowell.ddxyz.common.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.soap.Text;

import org.junit.Test;
import org.quartz.CronExpression;
import org.quartz.impl.calendar.CronCalendar;

import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.format.OfDateFormat;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.utils.qrcode.QrCodeUtils;
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
	
	@Test
	public void qrCodeTest() throws ParseException {
		String source = "20170426130001001";
		
		Pattern pattern = Pattern.compile("^(\\d{8})(\\d{2})(\\d{4})(\\d{3})$");
		
		
		Matcher matcher = pattern.matcher(source);
		if(matcher.matches()){
			String ymd = matcher.group(1),
					timePoint = matcher.group(2),
					locationCode = matcher.group(3),
					dispenseKey = matcher.group(4);
			StringBuffer code = new StringBuffer();
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			long result = ChronoUnit.DAYS.between((new Date(0l)).toInstant(), df.parse(ymd).toInstant());
			code.append(TextUtils.convert(result, 62, 3));
			code.append(TextUtils.convert(Long.valueOf(timePoint), 62, 1));
			code.append(TextUtils.convert(Long.valueOf(locationCode), 62, 2));
			code.append(TextUtils.convert(Long.valueOf(dispenseKey), 62, 2));
			QrCodeUtils.encodeQRCodeImage(code.toString(), null, "d://" + code + ".png", 400, 400);
			System.out.println(code);
		}
	}
	
	
}
