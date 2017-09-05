package cn.sowell.ddxyz.model.canteen.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWaresListCriteria;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public interface CanteenWaresDao {

	List<PlainWares> getCanteenWaresList(CanteenWaresListCriteria criteria, CommonPageInfo pageInfo);

	<T> T getPlainObject(Class<T> objClass, Long id);

	void update(Object origin);
	
	void save(Object obj);

	int disableWares(Long waresId, Boolean disabled);

	void updateWaresSalable(Long waresId, boolean salable);

}
