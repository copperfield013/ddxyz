package cn.sowell.ddxyz.model.kanteen.dao;

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

}
