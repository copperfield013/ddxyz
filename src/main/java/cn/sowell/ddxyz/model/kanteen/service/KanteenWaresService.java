package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWares;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresItem;

public interface KanteenWaresService {

	
	/**
	 * 查询产品列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenWaresItem> queryWares(KanteenWaresCriteria criteria,
			PageInfo pageInfo);

	void saveWares(PlainKanteenWares wares);

	<T> T getPlainObject(Class<T> pojoClass, Long pojoId);

	void updateWares(PlainKanteenWares wares);

	void updateWaresDetailPreview(String uuid, String detail);

	void updateWaresSalable(Long waresId, boolean salable);

	void disableWares(Long waresId, boolean disabled);



}
