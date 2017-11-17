package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresGroupDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresGroupCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresGroupService;

@Service
public class KanteenWaresGroupServiceImpl implements KanteenWaresGroupService{
	
	@Resource
	KanteenWaresGroupDao wgDao;
	
	@Resource
	NormalOperateDao nDao;
	
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
	public void updateWaresGroup(PlainKanteenWaresGroup origin) {
		nDao.update(origin);
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
	
}
