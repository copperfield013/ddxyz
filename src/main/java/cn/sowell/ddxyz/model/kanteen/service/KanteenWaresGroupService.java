package cn.sowell.ddxyz.model.kanteen.service;

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

public interface KanteenWaresGroupService {

	List<KanteenWaresGroupItem> queryWaresGroups(
			KanteenWaresGroupCriteria criteria, PageInfo pageInfo);

	void saveWaresGroup(PlainKanteenWaresGroup waresGroup);

	PlainKanteenWaresGroup getWaresGroup(Long waresGroupId);

	void updateWaresGroup(PlainKanteenWaresGroup origin, PlainKanteenWaresGroupWaresItem[] items);

	/**
	 * 更新商品组的禁用状态
	 * @param waresGroupId 要禁用的商品组的id
	 * @param toDisable 是否禁用
	 */
	void disableWaresGroup(Long waresGroupId, boolean toDisable);

	/**
	 * 查询
	 * @param criteria
	 * @return
	 */
	List<KanteenWaresItemForChoose> queryWaresesForChoose(
			KanteenChooseWaresListCriteria criteria, PageInfo pageInfo);

	/**
	 * 获得商品组关联的所有可用商品
	 * @param waresGroupId
	 * @return
	 */
	List<KanteenWaresGroupWaresItem> getGroupWares(Long waresGroupId);

	/**
	 * 根据groupWaresIds获得对应的产品组，返回map
	 * @param groupWaresIds t_waresgroup_wares表的id
	 * @return
	 */
	Map<Long, PlainKanteenWaresGroup> getGroupMapByGroupWaresIds(
			Set<Long> groupWaresIds);


}
