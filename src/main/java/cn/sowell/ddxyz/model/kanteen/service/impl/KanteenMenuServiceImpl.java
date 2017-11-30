package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenMenuDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenuWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresGroupListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresGroupItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMenuService;

@Service
public class KanteenMenuServiceImpl implements KanteenMenuService{

	@Resource
	KanteenMenuDao mDao;
	
	@Resource
	NormalOperateDao nDao;
	
	@Override
	public List<KanteenMenuItem> queryMenuList(KanteenMenuCriteria criteria,
			PageInfo pageInfo) {
		return mDao.queryMenuList(criteria, pageInfo);
	}
	
	@Override
	public PlainKanteenMenu getMenu(Long menuId) {
		return nDao.get(PlainKanteenMenu.class, menuId);
	}
	
	@Override
	public List<KanteenMenuWaresGroupItem> getMenuWaresGroups(Long menuId) {
		return mDao.getMenuWaresGroups(menuId);
	}
	
	@Override
	public void saveMenu(PlainKanteenMenu menu) {
		Date now = new Date();
		menu.setCreateTime(now);
		menu.setUpdateTime(now);
		nDao.save(menu);
	}
	
	
	@Override
	public void updateMenu(PlainKanteenMenu origin,
			PlainKanteenMenuWaresGroup[] items) {
		origin.setUpdateTime(new Date());
		nDao.update(origin);
		List<KanteenMenuWaresGroupItem> originItems = mDao.getMenuWaresGroups(origin.getId());
		
		Map<Long, KanteenMenuWaresGroupItem> originItemMap = CollectionUtils.toMap(originItems, item->item.getMenuGroupId());
		Set<PlainKanteenMenuWaresGroup> toUpdate = new HashSet<PlainKanteenMenuWaresGroup>();
		Set<PlainKanteenMenuWaresGroup> toCreate = new HashSet<PlainKanteenMenuWaresGroup>();
		Set<Long> toDelete = CollectionUtils.toSet(originItems, item->item.getMenuGroupId());
		if(items != null){
			for (PlainKanteenMenuWaresGroup item : items) {
				if(item.getWaresgourpId() != null){
					item.setMenuId(origin.getId());
					if(item.getId() != null){
						KanteenMenuWaresGroupItem originItem = originItemMap.get(item.getId());
						if(originItem != null){
							if(!item.getOrder().equals(originItem.getOrder())){
								toUpdate.add(item);
							}
							toDelete.remove(originItem.getMenuGroupId());
						}
					}else{
						toCreate.add(item);
					}
				}
			}
		}
		toUpdate.forEach(item->mDao.updateMenuWaresGroupItemOrder(item));
		toCreate.forEach(item->nDao.save(item));
		mDao.disableMenuWaresGroupItem(toDelete);
	}
	
	@Override
	public void disableMenu(Long menuId, boolean toDisable) {
		mDao.disbaleMenu(menuId, toDisable);
	}
	
	@Override
	public List<KanteenWaresGroupItemForChoose> queryWaresGroupForChoose(
			KanteenChooseWaresGroupListCriteria criteria, PageInfo pageInfo) {
		return mDao.queryWaresGroupForChoose(criteria, pageInfo);
	}
	
	@Override
	public List<PlainKanteenWaresGroup> getWaresGroupList(Long menuId) {
		return mDao.getWaresGroupList(menuId);
	}
	
	@Override
	public List<PlainKanteenWaresGroupWaresItem> getWaresGroupWaresList(
			Set<Long> groupIds) {
		return mDao.getWaresGroupWaresList(groupIds);
	}
	
}
