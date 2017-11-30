package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
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
			item.setOrderCount(FormatUtils.coalesce(deliveryOrderCountMap.get(delivery.getId()), 0));
			items.add(item);
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
		delivery.setCode(generateDeliveryCode(delivery.getMerchantId()));
		nDao.save(delivery);
	}
	
	Map<Long, byte[]> lockMap = new HashMap<Long, byte[]>();
	private Object codeLock(Long merchantId){
		byte[] lock = lockMap.get(merchantId);
		if(lock == null){
			lock = new byte[0];
			lockMap.put(merchantId, lock);
		}
		return lock;
	}
	private String generateDeliveryCode(Long merchantId) {
		synchronized(codeLock(merchantId)){
			long num = 0;
			String code = deDao.getLastCode(merchantId);
			if(code != null){
				num = TextUtils.decode(code.substring(1), 36);
			}
			return "S" + TextUtils.convert(num + 1, 36, 4);
		}
	}
	@Override
	public PlainKanteenDelivery getDelivery(Long deliveryId) {
		return nDao.get(PlainKanteenDelivery.class, deliveryId);
	}
}
