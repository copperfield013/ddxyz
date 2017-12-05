package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.Date;
import java.util.Set;

import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenSection;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;

public interface KanteenOrderDao {

	/**
	 * 根据条件统计订单的单数
	 * @param criteria
	 * @return
	 */
	Integer statCount(KanteenOrderStatCriteria criteria);

	/**
	 * 根据条件统计订单的总金额
	 * @param criteria
	 * @return
	 */
	Integer statOrderAmount(KanteenOrderStatCriteria criteria);

	/**
	 * 获得所有已经超过支付时限，但是状态依然是可用的订单id
	 * @param now 
	 * @return
	 */
	Set<Long> getAllExpiredOrderIds(Date now);

	/**
	 * 根据订单id获得所有订单条目
	 * @param expiredOrderIds
	 * @return
	 */
	Set<PlainKanteenSection> getAllSections(Set<Long> expiredOrderIds);

	/**
	 * 将订单的状态设置为已过期
	 * @param expiredOrderIds
	 */
	void setOrderPayExpired(Set<Long> expiredOrderIds);

}
