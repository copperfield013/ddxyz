package cn.sowell.ddxyz.model.common.pojo.criteria;


import cn.sowell.copframe.utils.date.CommonDateFormat;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;

public class DeliveryPlanWrap {
	
	
	
	

	
	private PlainDeliveryPlan plan =  new PlainDeliveryPlan();
	
	FrameDateFormat dateformat = new CommonDateFormat();
	

	public void setWaresId(Long waresId) {
		plan.setWaresId(waresId);
	}


	public void setLocationId(Long locationId) {
		plan.setLocationId(locationId);
	}


	public void setPeriod(String period) {
		plan.setPeriod(period);
	}


	public void setMaxCount(Integer maxCount) {
		plan.setMaxCount(maxCount);
	}


	public void setLeadMinutes(Integer leadMinutes) {
		plan.setLeadMinutes(leadMinutes);
	}


	public void setStartDate(String startDate) {
		plan.setStartDate(dateformat.parse(startDate, "yyyy-MM-dd"));
	}


	public void setEndDate(String endDate) {
		plan.setEndDate(dateformat.parse(endDate, "yyyy-MM-dd"));
	}


	public PlainDeliveryPlan getPlan() {
		return plan;
	}

}
