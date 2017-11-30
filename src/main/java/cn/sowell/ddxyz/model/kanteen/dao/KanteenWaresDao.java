package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWares;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresItem;

public interface KanteenWaresDao {
	
	/**
	 * 从数据库中查询商品列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenWaresItem> queryWares(KanteenWaresCriteria criteria,
			PageInfo pageInfo);

	void save(Object wares);

	<T> T get(Class<T> pojoClass, Long pojoId);

	void update(Object pojo);

	void updateWaresSalable(Long waresId, boolean salable);

	int disableWares(Long waresId, Boolean disabled);

	Map<Long, PlainKanteenWares> getWaresMap(Set<Long> waresIds);

}
