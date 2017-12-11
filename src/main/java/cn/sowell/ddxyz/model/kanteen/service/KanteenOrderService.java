package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenOrderItem;

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

	/**
	 * 回收没有支付的资源
	 */
	void recoverUnpayResource();

	/**
	 * 查询订单
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenOrderItem> queryOrderList(KanteenOrderListCriteria criteria,
			PageInfo pageInfo);

	/**
	 * 根据配销id获得各个配销的有效订单数（非默认状态的，并且未被取消的订单）
	 * @param distributionIdSet
	 * @return
	 */
	Map<Long, Integer> getDistributionEffectiveOrderCountMap(
			Set<Long> distributionIdSet);

}
