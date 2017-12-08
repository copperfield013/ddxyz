package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOptionGroup;

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

	/**
	 * 根据商品id获得其所属的所有的选项组
	 * @param waresId
	 * @return
	 */
	List<PlainKanteenWaresOptionGroup> queryOptionGroups(Long waresId);

	/**
	 * 根据选项组获得所有选项
	 * @param set
	 * @return
	 */
	Map<Long, List<PlainKanteenWaresOption>> queryOptionsMap(
			Set<Long> groupIdSet);

	/**
	 * 更新商品选项
	 * @param waresId
	 * @param groupMap
	 */
	void updateWaresOption(Long waresId, Map<PlainKanteenWaresOptionGroup, List<PlainKanteenWaresOption>> groupMap);

}
