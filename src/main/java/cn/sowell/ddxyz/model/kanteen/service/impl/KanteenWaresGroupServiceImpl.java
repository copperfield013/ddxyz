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
import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresGroupDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresGroupCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresGroupService;

@Service
public class KanteenWaresGroupServiceImpl implements KanteenWaresGroupService{
	
	@Resource
	KanteenWaresGroupDao wgDao;
	
	@Resource
	NormalOperateDao nDao;

	@Resource
	KanteenDistributionService distributionService;
	
	@Override
	public List<KanteenWaresGroupItem> queryWaresGroups(
			KanteenWaresGroupCriteria criteria, PageInfo pageInfo) {
		return wgDao.queryWaresGroups(criteria, pageInfo);
	}
	
	@Override
	public void saveWaresGroup(PlainKanteenWaresGroup waresGroup) {
		waresGroup.setCreateTime(new Date());
		nDao.save(waresGroup);
	}
	
	@Override
	public PlainKanteenWaresGroup getWaresGroup(Long waresGroupId) {
		return nDao.get(PlainKanteenWaresGroup.class, waresGroupId);
	}
	
	@Override
	public void updateWaresGroup(PlainKanteenWaresGroup origin, PlainKanteenWaresGroupWaresItem[] items) {
		nDao.update(origin);
		List<KanteenWaresGroupWaresItem> originItems = wgDao.getGroupWares(origin.getId());
		
		Map<Long, KanteenWaresGroupWaresItem> originItemMap = CollectionUtils.toMap(originItems, item->item.getId());
		Set<PlainKanteenWaresGroupWaresItem> toUpdate = new HashSet<PlainKanteenWaresGroupWaresItem>();
		Set<PlainKanteenWaresGroupWaresItem> toCreate = new HashSet<PlainKanteenWaresGroupWaresItem>();
		Set<Long> toDelete = CollectionUtils.toSet(originItems, item->item.getId());
		if(items != null){
			for (PlainKanteenWaresGroupWaresItem item : items) {
				if(item.getWaresId() != null){
					item.setGroupId(origin.getId());
					if(item.getId() != null){
						KanteenWaresGroupWaresItem originItem = originItemMap.get(item.getId());
						if(originItem != null){
							if(!item.getOrder().equals(originItem.getOrder())){
								toUpdate.add(item);
							}
							toDelete.remove(originItem.getId());
						}
					}else{
						toCreate.add(item);
					}
				}
			}
		}
		toUpdate.forEach(item->wgDao.updateGroupWaresItemOrder(item));
		toCreate.forEach(item->nDao.save(item));
		wgDao.disableGroupWaresItemOrder(toDelete);
		
		//调整所有关联到当前菜单的配销商品
		distributionService.adaptEffectiveDistributionWaresByGroupId(origin.getId());
		
	}
	
	@Override
	public void disableWaresGroup(Long waresGroupId, boolean toDisable) {
		wgDao.updateWaresGroupDisabledStatus(waresGroupId, toDisable);
	}
	
	@Override
	public List<KanteenWaresItemForChoose> queryWaresesForChoose(
			KanteenChooseWaresListCriteria criteria, PageInfo pageInfo) {
		return wgDao.queryWaresesForChoose(criteria, pageInfo);
	}
	
	@Override
	public List<KanteenWaresGroupWaresItem> getGroupWares(Long waresGroupId) {
		return wgDao.getGroupWares(waresGroupId);
	}
	
	@Override
	public Map<Long, PlainKanteenWaresGroup> getGroupMapByGroupWaresIds(
			Set<Long> groupWaresIds) {
		return wgDao.getGroupMapByGroupWaresIds(groupWaresIds);
	}
	
}
