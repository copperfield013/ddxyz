package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresOptionDao;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresOptionService;

@Repository
public class KanteenWaresOptionServiceImpl implements KanteenWaresOptionService{

	@Resource
	KanteenWaresOptionDao optionDao;
	
	@Override
	public Map<Long, Integer> queryEnabledOptionCount(Set<Long> waresIdSet) {
		return optionDao.queryEnabledOptionCount(waresIdSet);
	}

	@Override
	public Map<Long, Integer> queryEnabledOptionGroupCount(Set<Long> waresIdSet) {
		return optionDao.queryEnabledOptionGroupCount(waresIdSet);
	}

}
