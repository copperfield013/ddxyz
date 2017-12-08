package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOptionGroup;

public interface KanteenWaresOptionDao {

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
	 * 根据商品选项组id获得所有商品选项
	 * @param groupIdSet
	 * @return
	 */
	List<PlainKanteenWaresOption> queryOptions(Set<Long> groupIdSet);

	/**
	 * 将商品选项组的状态设置为已删除
	 * @param toRemoveGroupIds
	 */
	void deleteGroups(Set<Long> toRemoveGroupIds);

	/**
	 * 将商品选项的状态设置为已删除
	 * @param toRemoveOptionIds
	 */
	void deleteOptions(Set<Long> toRemoveOptionIds);

	
	
}
