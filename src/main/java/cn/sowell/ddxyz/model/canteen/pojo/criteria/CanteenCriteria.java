package cn.sowell.ddxyz.model.canteen.pojo.criteria;

import java.util.Calendar;
import java.util.Date;

public class CanteenCriteria {
	private String date;

	private Date startDate;
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public Date getEndDateIncluded() {
		if(endDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.MILLISECOND, -1);
			return cal.getTime();
		}
		return null;
	}
}
