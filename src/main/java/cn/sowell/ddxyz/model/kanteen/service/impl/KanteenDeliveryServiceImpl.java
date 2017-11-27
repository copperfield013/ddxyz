package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDeliveryDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDeliveryCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDeliveryItem;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDeliveryService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;

@Service
public class KanteenDeliveryServiceImpl implements KanteenDeliveryService{

	@Resource
	KanteenDeliveryDao deDao;
	
	@Resource
	NormalOperateDao nDao;
	
	@Resource
	KanteenDistributionService distributionService;
	
	@Override
	public List<KanteenDeliveryItem> queryDeliveryItems(
			KanteenDeliveryCriteria criteria, PageInfo pageInfo) {
		//根据条件查找配送对象
		List<PlainKanteenDelivery> list = deDao.queryDeliveries(criteria, pageInfo);
		//获得配送对象的id和对应配销的id
		Set<Long> distributionIds = CollectionUtils.toSet(list, delivery->delivery.getDistributionId()),
					deliveryIds = CollectionUtils.toSet(list, delivery->delivery.getId());
		//获得配销对象对应的菜单
		Map<Long, PlainKanteenMenu> distributionMenuMap = distributionService.getMenuMapByDistributionId(distributionIds);
		//获得配送的订单数
		Map<Long, Integer> deliveryOrderCountMap = getDeliveryOrderCountMap(deliveryIds);
		//构造返回数组
		List<KanteenDeliveryItem> items = new ArrayList<KanteenDeliveryItem>();
		list.forEach(delivery->{
			KanteenDeliveryItem item = new KanteenDeliveryItem();
			item.setDelivery(delivery);
			PlainKanteenMenu menu = distributionMenuMap.get(delivery.getDistributionId());
			item.setMenu(menu);
			item.setOrderCount(deliveryOrderCountMap.get(delivery.getId()));
		});
		
		return items;
	}
	/**
	 * 查询各个配送的所有可用订单数
	 * @param deliveryIds
	 * @return
	 */
	private Map<Long, Integer> getDeliveryOrderCountMap(Set<Long> deliveryIds) {
		return deDao.getDeliveryOrderCountMap(deliveryIds);
	}

	@Override
	public void saveDelivery(PlainKanteenDelivery delivery) {
		Assert.notNull(delivery.getMerchantId());
		delivery.setId(null);
		Date now = new Date();
		delivery.setCreateTime(now);
		delivery.setUpdateTime(now);
		nDao.save(delivery);
	}
}
