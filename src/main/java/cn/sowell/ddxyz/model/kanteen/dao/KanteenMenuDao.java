package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.List;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenuWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresGroupListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresGroupItemForChoose;

public interface KanteenMenuDao {

	/**
	 * 从数据中查找订单列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenMenuItem> queryMenuList(KanteenMenuCriteria criteria,
			PageInfo pageInfo);

	/**
	 * 从数据库中根据菜单id获得该菜单的所有菜单组
	 * @param menuId
	 * @return
	 */
	List<KanteenMenuWaresGroupItem> getMenuWaresGroups(Long menuId);

	/**
	 * 更新菜单内商品组的顺序
	 * @param item
	 * @return
	 */
	void updateMenuWaresGroupItemOrder(PlainKanteenMenuWaresGroup item);

	/**
	 * 将指定的所有菜单-商品组的关联的状态设置为禁用
	 * @param toDelete
	 */
	void disableMenuWaresGroupItem(Set<Long> menuWaresGroupIds);

	/**
	 * 更改数据库中菜单的禁用状态
	 * @param menuId
	 * @param toDisable
	 */
	void disbaleMenu(Long menuId, boolean toDisable);

	
	/**
	 * 查询商品用于选择
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenWaresGroupItemForChoose> queryWaresGroupForChoose(
			KanteenChooseWaresGroupListCriteria criteria, PageInfo pageInfo);
	
}
