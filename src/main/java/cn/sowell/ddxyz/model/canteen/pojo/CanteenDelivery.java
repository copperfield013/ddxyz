package cn.sowell.ddxyz.model.canteen.pojo;

import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryWares;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public class CanteenDelivery {
	private String deliveryId;
	private String locationName;
	private Date timePointStart;
	private Date timePointEnd;
	
	List<PlainDeliveryWares> wares;
	
}
