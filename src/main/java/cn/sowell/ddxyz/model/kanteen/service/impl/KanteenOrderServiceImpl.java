package cn.sowell.ddxyz.model.kanteen.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.model.kanteen.dao.KanteenOrderDao;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;
import cn.sowell.ddxyz.model.kanteen.service.KanteenOrderService;

@Repository
public class KanteenOrderServiceImpl implements KanteenOrderService{

	@Resource
	KanteenOrderDao orderDao;
	
	@Override
	public Integer statCount(KanteenOrderStatCriteria criteria) {
		return orderDao.statCount(criteria);
	}

	@Override
	public Integer statOrderAmount(KanteenOrderStatCriteria criteria) {
		return orderDao.statOrderAmount(criteria);
	}

}
