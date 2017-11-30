package cn.sowell.ddxyz.model.kanteen.service;

import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;

public interface KanteenOrderService {

	/**
	 * 计算符合条件的订单的总单数
	 * @param setStatus
	 * @return
	 */
	Integer statCount(KanteenOrderStatCriteria setStatus);

	/**
	 * 计算符合条件的订单的总金额
	 * @param setStatus
	 * @return
	 */
	Integer statOrderAmount(KanteenOrderStatCriteria setStatus);

}
