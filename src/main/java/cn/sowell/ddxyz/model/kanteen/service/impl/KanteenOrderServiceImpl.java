package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenOrderDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenSection;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenOrderItem;
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
		Set<Long> expiredOrderIds = orderDao.getAllExpiredOrderIds(now);
		//根据订单id获得所有订单条目
		Set<PlainKanteenSection> sections = orderDao.getAllSections(expiredOrderIds);
		
		Map<Long, Integer> recoverCountMap = new HashMap<Long, Integer>();
		//计算所有超过支付时限的配销商品的数量
		sections.forEach(section->{
			Long distributionWaresId = section.getDistributionWaresId();
			Integer count = FormatUtils.coalesce(recoverCountMap.get(distributionWaresId), 0);
			recoverCountMap.put(distributionWaresId, count + section.getCount());
		});
		//修改各个配销商品的余量
		recoverCountMap.forEach((distributionWaresId, decrease)->{
			//减少配送
			distributionService.decreaseDistributionWaresCurrentCount(distributionWaresId, decrease);
		});
		orderDao.setOrderPayExpired(expiredOrderIds);
	}
	
	@Override
	public List<KanteenOrderItem> queryOrderList(
			KanteenOrderListCriteria criteria, PageInfo pageInfo) {
		return orderDao.queryOrderList(criteria, pageInfo);
	}
	
	@Override
	public Map<Long, Integer> getDistributionEffectiveOrderCountMap(
			Set<Long> distributionIdSet) {
		return orderDao.getDistributionEffectiveOrderCountMap(distributionIdSet);
	}
	
}
