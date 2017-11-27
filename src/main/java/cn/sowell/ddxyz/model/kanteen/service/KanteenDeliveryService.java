package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDeliveryCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDeliveryItem;

public interface KanteenDeliveryService {

	/**
	 * 根据条件查找配送数据
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenDeliveryItem> queryDeliveryItems(KanteenDeliveryCriteria criteria,
			PageInfo pageInfo);

	/**
	 * 创建配送
	 * @param delivery
	 */
	void saveDelivery(PlainKanteenDelivery delivery);
	
}
