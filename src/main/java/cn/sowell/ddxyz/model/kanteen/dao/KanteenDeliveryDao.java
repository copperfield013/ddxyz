package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDeliveryCriteria;

public interface KanteenDeliveryDao {

	List<PlainKanteenDelivery> queryDeliveries(
			KanteenDeliveryCriteria criteria, PageInfo pageInfo);

	/**
	 * 统计配送的所有可用订单数
	 * @param deliveryIds
	 * @return
	 */
	Map<Long, Integer> getDeliveryOrderCountMap(Set<Long> deliveryIds);

}
