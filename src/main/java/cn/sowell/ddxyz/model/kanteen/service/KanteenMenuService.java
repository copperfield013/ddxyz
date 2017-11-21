package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
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
	void updateMenu(PlainKanteenMenu origin, KanteenMenuWaresGroupItem[] items);
	
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
	

}
