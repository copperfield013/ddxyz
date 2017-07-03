package cn.sowell.ddxyz.model.common.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;

import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.format.OfDateFormat;

public class DeliveryPeriodUtils {
	static FrameDateFormat dateFormat = new OfDateFormat();
	static Logger logger = Logger.getLogger(DeliveryPeriodUtils.class);
	/**
	 * 
	 * 传入一个dateSring的规则字符串，包含年，月，时，日(或星期几)。将dateString按照年，月，时，日（或星期）解析分组，存储为不同的数组。
	 * 将今天的日期与dateString分割后的各项分组匹配。若匹配，则返回时间的列表；若不匹配，则返回内容为空的list列表。
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static List<Integer> getHourList(String dateString) throws ParseException{
		Calendar cal = Calendar.getInstance();
		List<Integer> timeList = getHourList(dateString, cal);
		return timeList;
	}
	
	public static List<Integer> getHourList(String dateString, Calendar cal) throws ParseException{
		
		//一周第一天是否为星期天
		boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
		if(isFirstSunday){ //当天是周几
			day_of_week = day_of_week -1;
			if(day_of_week == 0){
				day_of_week = 7; 
			}
		}
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DATE);
		String[] dateList = dateString.split("。");
		String[] dateY = null;
		String[] dateM = null;
		String[] dateD = null;
		String[] dateT = null;
		String[] dateW = null;
		for(int i=0; i<dateList.length; i++){
			if(dateList[i].contains("Y")){ //年
				dateY = dateList[i].split(",");
				if(dateY.length > 0){
					dateY[0] = dateY[0].substring(1);
				}
			}
			if(dateList[i].contains("M")){ //月
				dateM = dateList[i].split(",");
				if(dateM.length > 0){
					dateM[0] = dateM[0].substring(1);
				}
			}
			if(dateList[i].contains("D")){ //日
				dateD = dateList[i].split(",");
				if(dateD.length > 0){
					dateD[0] = dateD[0].substring(1);
				}
			}
			if(dateList[i].contains("T")){ //时间
				dateT = dateList[i].split(",");
				if(dateT.length > 0){
					dateT[0] = dateT[0].substring(1);
				}
			}
			if(dateList[i].contains("W")){ //周
				dateW = dateList[i].split(",");
				if(dateW.length > 0){
					dateW[0] = dateW[0].substring(1);
				}
			}
		}
		if(dateY == null || !Arrays.asList(dateY).contains(String.valueOf(year))){
			return new ArrayList<Integer>();
		}
		if(dateM == null || !Arrays.asList(dateM).contains(String.valueOf(month))){
			return new ArrayList<Integer>();
		}
		if((dateD != null && !Arrays.asList(dateD).contains(String.valueOf(day))) || (dateW != null && !Arrays.asList(dateW).contains(String.valueOf(day_of_week)))){
			return new ArrayList<Integer>();
		}
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (String hour : dateT) {
			result.add(FormatUtils.toInteger(hour));
		}
		return result;
	}

	public static int[] getCronHourList(String period, Date theDay) {
		try {
			CronExpression cron = new CronExpression(period);
			Date zero = dateFormat.getTheDayZero(theDay);
			Date date = new Date(zero.getTime() - 1);
			Set<Integer> hours = new TreeSet<Integer>();
			Calendar cal = Calendar.getInstance();
			while(date != null){
				date = cron.getNextValidTimeAfter(date);
				if(dateFormat.inDay(date, zero)){
					cal.setTime(date);
					hours.add(cal.get(Calendar.HOUR_OF_DAY));
				}else{
					break;
				}
			}
			int[] result = new int[hours.size()];
			int i = 0;
			for (Integer hour : hours) {
				result[i++] = hour;
			}
			return result;
		} catch (ParseException e) {
			logger.error("cron表达式解析错误", e);
		}
		return new int[0];
	}
}
