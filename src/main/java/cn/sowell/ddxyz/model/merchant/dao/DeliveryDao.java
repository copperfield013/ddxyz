package cn.sowell.ddxyz.model.merchant.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;

public interface DeliveryDao {

	/**
	 * 获得商户的在该天的所有配送
	 * @param merchantId
	 * @return
	 */
	List<PlainDelivery> getAllDelivery(long merchantId, Date date);

	/**
	 * 查询所有与商户关联的配送点
	 * @param merchantId
	 * @return
	 */
	Set<PlainLocation> getAllLocation(long merchantId);

}
