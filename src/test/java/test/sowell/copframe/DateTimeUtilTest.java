package test.sowell.copframe;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sowell.ddxyz.model.common.utils.DeliveryPeriodUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
public class DateTimeUtilTest {
	
	//DateTimeUtil dateTimeUtil;
	@Test
	public void testGetTimeList() throws ParseException{
		String dateString1 ="Y2017,2018。M3,4,5。W1,3,4,5。T13,16,18。";
		@SuppressWarnings("rawtypes")
		List list1 = DeliveryPeriodUtils.getHourList(dateString1);
		System.out.println("list1 size = " + list1.size());
		
		String dateString2 = "Y2017,2018。M3,4,5。W1,2,3。T13,16,18。";
		@SuppressWarnings("rawtypes")
		List list2 = DeliveryPeriodUtils.getHourList(dateString2);
		System.out.println("list2 size = " + list2.size());
		
		String dateString3 = "Y2017,2018。M3,4,5。D1,2,3。T13,16,18。";
		@SuppressWarnings("rawtypes")
		List list3 = DeliveryPeriodUtils.getHourList(dateString3);
		System.out.println("list3 size = " + list3.size());
		
		String dateString4 = "Y2017,2018。M3,4,5。D12,23,24。T13,16,18。";
		@SuppressWarnings("rawtypes")
		List list4 = DeliveryPeriodUtils.getHourList(dateString4);
		System.out.println("list4 size = " + list4.size());
	}
}
