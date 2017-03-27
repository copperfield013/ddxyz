package cn.sowell.copframe.utils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DateTimeUtil {

	/**
	 * 
	 * 传入一个dateSring的规则字符串，包含年，月，时，日(或星期几)。将dateString按照年，月，时，日（或星期）解析分组，存储为不同的数组。
	 * 将今天的日期与dateString分割后的各项分组匹配。若匹配，则返回时间的列表；若不匹配，则返回内容为空的list列表。
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	public static List getTimeList(String dateString) throws ParseException{
		Calendar cal = Calendar.getInstance();
		List timeList = getTimeList(dateString, cal);
		return timeList;
	}
	
	@SuppressWarnings("rawtypes")
	public static List getTimeList(String dateString,Calendar cal) throws ParseException{
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
			}
			if(dateList[i].contains("M")){ //月
				dateM = dateList[i].split(",");
			}
			if(dateList[i].contains("D")){ //日
				dateD = dateList[i].split(",");
			}
			if(dateList[i].contains("T")){ //时间
				dateT = dateList[i].split(",");
				if(dateT.length > 0){
					dateT[0] = dateT[0].substring(1);
				}
			}
			if(dateList[i].contains("W")){ //周
				dateW = dateList[i].split(",");
			}
		}
		if(dateY != null){
			for(int i=0; i<dateY.length; i++){
				if(dateY[i].contains(String.valueOf(year))){
					if(dateM != null){
						for(int j=0; j<dateM.length; j++){
							if(dateM[j].contains(String.valueOf(month))){
								if(dateD != null){
									for(int m=0; m<dateD.length; m++){
										if(dateD[m].contains(String.valueOf(day))){
											if(dateT != null){
												return Arrays.asList(dateT);
											}
										}
									}
								}else if(dateW != null){
									for(int n=0; n<dateW.length; n++){
										if(dateW[n].contains(String.valueOf(day_of_week))){
											if(dateT != null){
												return Arrays.asList(dateT);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return Arrays.asList("");
	}
}
