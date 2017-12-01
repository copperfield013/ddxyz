package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenOrderDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenSection;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenOrderService;

@Repository
public class KanteenOrderServiceImpl implements KanteenOrderService{

	@Resource
	KanteenOrderDao orderDao;
	@Resource
	KanteenDistributionService distributionService;
	
	@Override
	public Integer statCount(KanteenOrderStatCriteria criteria) {
		return orderDao.statCount(criteria);
	}

	@Override
	public Integer statOrderAmount(KanteenOrderStatCriteria criteria) {
		return orderDao.statOrderAmount(criteria);
	}

	@Transactional
	@Override
	public synchronized void recoverUnpayResource() {
		Date now = new Date();
		//获得所有已经超过支付时限，但是状态依然是可用的订单id
		Set<Long> expiredOrderIds = orderDao.getAllExpiredOrderIds();
		
		Set<PlainKanteenSection> sections = orderDao.getAllSections();
		
		Map<Long, Integer> recoverCountMap = new HashMap<Long, Integer>();
		
		sections.forEach(section->{
			Long distributionWaresId = section.getDistributionWaresId();
			Integer count = FormatUtils.coalesce(recoverCountMap.get(distributionWaresId), 0);
			recoverCountMap.put(distributionWaresId, count + section.getCount());
		});
		
		recoverCountMap.forEach((distributionWaresId, decrease)->{
			distributionService.decreaseDistributionWaresCurrentCount(distributionWaresId, decrease);
		});
	}
	
}
