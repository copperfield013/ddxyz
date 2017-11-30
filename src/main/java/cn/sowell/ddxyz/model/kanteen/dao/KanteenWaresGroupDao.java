package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresGroupCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresItemForChoose;

public interface KanteenWaresGroupDao {

	/**
	 * 
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenWaresGroupItem> queryWaresGroups(
			KanteenWaresGroupCriteria criteria, PageInfo pageInfo);
	/**
	 * 更新商品组的禁用状态
	 * @param waresGroupId
	 * @param toDisable
	 */
	int updateWaresGroupDisabledStatus(Long waresGroupId, boolean toDisable);
	
	/**
	 * 根据条件查询用于选择的商品
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenWaresItemForChoose> queryWaresesForChoose(
			KanteenChooseWaresListCriteria criteria, PageInfo pageInfo);
	
	/**
	 * 
	 * @param waresGroupId
	 * @return
	 */
	List<KanteenWaresGroupWaresItem> getGroupWares(Long waresGroupId);
	/**
	 * 保存商品组元素的顺序
	 * @param item
	 * @return
	 */
	void updateGroupWaresItemOrder(PlainKanteenWaresGroupWaresItem item);
	/**
	 * 将商品组中某几个商品设置为禁用
	 * @param itemIds
	 */
	void disableGroupWaresItemOrder(Set<Long> itemIds);
	
	/**
	 * 
	 * @param groupWaresIds
	 * @return
	 */
	Map<Long, PlainKanteenWaresGroup> getGroupMapByGroupWaresIds(
			Set<Long> groupWaresIds);

}
