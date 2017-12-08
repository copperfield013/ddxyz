package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresOptionDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOptionGroup;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresOptionService;

@Repository
public class KanteenWaresOptionServiceImpl implements KanteenWaresOptionService{

	@Resource
	KanteenWaresOptionDao optionDao;
	
	@Resource
	NormalOperateDao nDao;
	Logger logger = Logger.getLogger(KanteenWaresOptionServiceImpl.class);
	
	@Override
	public Map<Long, Integer> queryEnabledOptionCount(Set<Long> waresIdSet) {
		return optionDao.queryEnabledOptionCount(waresIdSet);
	}

	@Override
	public Map<Long, Integer> queryEnabledOptionGroupCount(Set<Long> waresIdSet) {
		return optionDao.queryEnabledOptionGroupCount(waresIdSet);
	}
	
	@Override
	public List<PlainKanteenWaresOptionGroup> queryOptionGroups(Long waresId) {
		return optionDao.queryOptionGroups(waresId);
	}
	
	@Override
	public Map<Long, List<PlainKanteenWaresOption>> queryOptionsMap(
			Set<Long> groupIdSet) {
		List<PlainKanteenWaresOption> optionList = optionDao.queryOptions(groupIdSet);
		return CollectionUtils.toListMap(optionList, option->option.getOptiongroupId());
	}
	
	@Override
	public void updateWaresOption(
			Long waresId,
			Map<PlainKanteenWaresOptionGroup, List<PlainKanteenWaresOption>> groupMap) {
		List<PlainKanteenWaresOptionGroup> originOptionGroupList = queryOptionGroups(waresId);
		Map<Long, PlainKanteenWaresOptionGroup> originGroupMap = CollectionUtils.toMap(originOptionGroupList, group->group.getId());
		
		Set<Long> toRemoveGroupIds = new HashSet<Long>(originGroupMap.keySet());
		Set<Long> toRemoveOptionIds = new HashSet<Long>();
		
		
		Map<Long, List<PlainKanteenWaresOption>> originOptionMap = queryOptionsMap(toRemoveGroupIds);
		
		originOptionMap.values().forEach(optionList->optionList.forEach(option->toRemoveOptionIds.add(option.getId())));
		
		groupMap.forEach((group, optionList)->{
			if(group.getId() != null){
				if(originGroupMap.containsKey(group.getId())){
					//已存在的商品组
					PlainKanteenWaresOptionGroup originGroup = originGroupMap.get(group.getId());
					originGroup.setDisabled(group.getDisabled());
					originGroup.setMultiple(group.getMultiple());
					originGroup.setRequired(group.getRequired());
					originGroup.setOrder(group.getOrder());
					nDao.update(originGroup);
					toRemoveGroupIds.remove(group.getId());
					
					Map<Long, PlainKanteenWaresOption> originGroupOptionMap = CollectionUtils.toMap(originOptionMap.get(group.getId()), option->option.getId());
					
					optionList.forEach(option->{
						if(option.getId() != null){
							if(originGroupOptionMap.containsKey(option.getId())){
								//已存在选项，仅支持修改部分属性
								PlainKanteenWaresOption originGroupOption = originGroupOptionMap.get(option.getId());
								originGroupOption.setAdditionPrice(option.getAdditionPrice());
								originGroupOption.setOrder(option.getOrder());
								originGroupOption.setDisabled(option.getDisabled());
								originGroupOption.setRule(option.getRule());
								nDao.update(originGroupOption);
								toRemoveOptionIds.remove(option.getId());
							}else{
								logger.error("更新商品选项时，传入的选项id[" + option.getId() + "]在选项组[id=" + group.getId() + "]中不存在");
							}
						}else{
							option.setOptiongroupId(group.getId());
							nDao.save(option);
						}
					});
					
				}else{
					logger.error("更新商品选项时，传入的选项组id[" + group.getId() + "]在商品[id=" + waresId + "]的原选项组中不存在");
				}
			}else{
				//新的商品组
				group.setWaresId(waresId);
				Long groupId = (Long) nDao.save(group);
				optionList.forEach(option->{
					option.setId(null);
					option.setOptiongroupId(groupId);
					nDao.save(option);
				});
			}
		});
		optionDao.deleteGroups(toRemoveGroupIds);
		optionDao.deleteOptions(toRemoveOptionIds);
		
		
		
	}
}
