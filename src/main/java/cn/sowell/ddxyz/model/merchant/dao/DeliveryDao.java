package cn.sowell.ddxyz.model.merchant.dao;

import java.util.Date;
import java.util.List;

import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;

public interface DeliveryDao {

	/**
	 * 获得商户的在该天的所有配送
	 * @param merchantId
	 * @return
	 */
	List<PlainDelivery> getAllDelivery(long merchantId, Date date);

}
