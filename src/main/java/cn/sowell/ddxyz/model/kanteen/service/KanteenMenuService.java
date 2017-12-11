package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenuWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenuWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresGroupListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresGroupItemForChoose;

public interface KanteenMenuService {

	/**
	 * 查询菜单列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenMenuItem> queryMenuList(KanteenMenuCriteria criteria,
			PageInfo pageInfo);

	/**
	 * 根据菜单id获得菜单对象
	 * @param menuId
	 * @return
	 */
	PlainKanteenMenu getMenu(Long menuId);

	/**
	 * 根据菜单id获得该菜单的所有可用商品组
	 * @param menuId
	 * @return
	 */
	List<KanteenMenuWaresGroupItem> getMenuWaresGroups(Long menuId);
	
	/**
	 * 创建菜单
	 * @param menu
	 */
	void saveMenu(PlainKanteenMenu menu);

	/**
	 * 修改菜单信息
	 * @param origin
	 * @param items
	 */
	void updateMenu(PlainKanteenMenu origin, PlainKanteenMenuWaresGroup[] items);
	
	/**
	 * 将菜单的状态设置为启用/禁用
	 * @param menuId
	 * @param b
	 */
	void disableMenu(Long menuId, boolean toDisable);

	/**
	 * 查询商品组列表用于选择
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenWaresGroupItemForChoose> queryWaresGroupForChoose(
			KanteenChooseWaresGroupListCriteria criteria, PageInfo pageInfo);

	/**
	 * 根据菜单id获得所有商品组
	 * @param menuId
	 * @return
	 */
	List<PlainKanteenWaresGroup> getWaresGroupList(Long menuId);

	/**
	 * 根据商品组id获得所有商品
	 * @param groupIds
	 * @return
	 */
	List<PlainKanteenWaresGroupWaresItem> getWaresGroupWaresList(
			Set<Long> groupIds);

	/**
	 * 查找菜单内的所有可用商品
	 * @param menuId
	 * @return
	 */
	List<KanteenMenuWares> getMenuWaresItems(Long menuId, boolean effectiveWares);

	/**
	 * 获得关联到指定商品组的所有菜单
	 * @param waresGroupId
	 * @return
	 */
	List<PlainKanteenMenu> getMenusByWaresGroupId(Long waresGroupId);

}
