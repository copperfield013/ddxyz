package cn.sowell.ddxyz.model.common.core;

import java.util.Calendar;
import java.util.Date;

import cn.sowell.copframe.utils.TextUtils;

/**
 * 
 * <p>Title: DeliveryTimePoint</p>
 * <p>Description: </p><p>
 * 配送时间点
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月29日 上午10:36:49
 */
public class DeliveryTimePoint {
	private Calendar timePoint;
	
	public DeliveryTimePoint(Date dateTime) {
		timePoint = Calendar.getInstance();
		timePoint.setTime(dateTime);
		timePoint.set(Calendar.MINUTE, 0);
		timePoint.set(Calendar.SECOND, 0);
		timePoint.set(Calendar.MILLISECOND, 0);
	}
	
	public Integer getYear(){
		return timePoint.get(Calendar.YEAR);
	}
	
	public Integer getMonth(){
		return timePoint.get(Calendar.MONTH);
	}
	
	public Integer getDay(){
		return timePoint.get(Calendar.DAY_OF_MONTH);
	}
	
	public Integer getHour(){
		return timePoint.get(Calendar.HOUR_OF_DAY);
	}
	
	public Date getDatetime(){
		return timePoint.getTime();
	}
	
	public String getKey(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(TextUtils.convert(getYear(), null, 2));
		buffer.append(TextUtils.convert(getMonth(), 12, 1));
		buffer.append(TextUtils.convert(getDay() - 1, 31, 1));
		buffer.append(TextUtils.convert(getHour(), 24, 1));
		return buffer.toString();
	}
	
	
	
	/**
	 * 比较两个时间点，如果两个时间点年月日小时都一致的话，那么
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof DeliveryTimePoint){
			return getKey().equals(((DeliveryTimePoint) obj).getKey());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return getKey().hashCode();
	}
	
	@Override
	public String toString() {
		return getDatetime().toString();
	}
	
}
