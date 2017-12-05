package cn.sowell.ddxyz.model.kanteen.service;

import java.util.Map;
import java.util.Set;

public interface KanteenWaresOptionService {

	/**
	 * 根据商品id获得其商品选项的数量
	 * @param waresIdSet
	 * @return
	 */
	Map<Long, Integer> queryEnabledOptionCount(Set<Long> waresIdSet);
	
	/**
	 * 根据商品id获得所有商品选项组的数量
	 * @param waresIdSet
	 * @return
	 */
	Map<Long, Integer> queryEnabledOptionGroupCount(Set<Long> waresIdSet);

}
