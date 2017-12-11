package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDistributionDao;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenuWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDistributionChooseMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuOrderStat;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuOrderStatItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenMenuItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMenuService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenOrderService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresGroupService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresService;

@Service
public class KanteenDistributionServiceImpl implements KanteenDistributionService{

	@Resource
	KanteenDistributionDao dDao;
	
	@Resource
	NormalOperateDao nDao;
	
	@Resource
	KanteenWaresService waresService;
	
	@Resource
	KanteenWaresGroupService groupService;

	@Resource
	KanteenOrderService orderService;
	
	@Resource
	KanteenMenuService menuService;

	Logger logger = Logger.getLogger(KanteenDistributionServiceImpl.class);
	
	
	@Override
	public List<KanteenDistributionItem> queryDistributions(Long merchantId, PageInfo pageInfo) {
		return dDao.queryDistributions(merchantId, pageInfo);
	}
	
	@Override
	public PlainKanteenDistribution getDistribution(Long distributionId) {
		return nDao.get(PlainKanteenDistribution.class, distributionId);
	}
	
	@Override
	public List<KanteenMenuItemForChoose> queryMenuListForChoose(
			KanteenDistributionChooseMenuCriteria criteria, PageInfo pageInfo) {
		return dDao.queryMenuListForChoose(criteria, pageInfo);
	}

	@Transactional
	@Override
	public void saveDistribution(PlainKanteenDistribution distribution) {
		Assert.notNull(distribution.getMenuId());
		Assert.notNull(distribution.getStartTime());
		Assert.notNull(distribution.getEndTime());
		Assert.isTrue(distribution.getStartTime().before(distribution.getEndTime()));
		Date now = new Date();
		distribution.setCreateTime(now);
		distribution.setUpdateTime(now);
		distribution.setCode(generateDistributionCode(distribution.getMerchantId()));
		
		List<PlainKanteenWaresGroup> groupList = menuService.getWaresGroupList(distribution.getMenuId());
		List<PlainKanteenWaresGroupWaresItem> waresGroupWaresList = menuService.getWaresGroupWaresList(CollectionUtils.toSet(groupList, group->group.getId())); 
		nDao.save(distribution);
		waresGroupWaresList.forEach(item->{
			PlainKanteenDistributionWares dWares = new PlainKanteenDistributionWares();
			String description = null;
			Integer maxCount = 1000;
			dWares.setWaresId(item.getWaresId());
			dWares.setMenuWaresId(item.getId());
			dWares.setCurrentCount(0);
			dWares.setMaxCount(maxCount);
			dWares.setCreateTime(new Date());
			dWares.setDescription(description);
			dWares.setCreateUserId(distribution.getUpdateUserId());
			dWares.setDistributionId(distribution.getId());
			nDao.save(dWares);
		});
		
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
	private String generateDistributionCode(Long merchantId) {
		synchronized(codeLock(merchantId)){
			long num = 0;
			String code = dDao.getLastCode(merchantId);
			if(code != null){
				num = TextUtils.parse(code.substring(1), 36);
			}
			return "K" + TextUtils.convert(num + 1, 36, 4);
		}
	}

	@Override
	public Map<Long, PlainKanteenMenu> getMenuMapByDistributionId(
			Set<Long> distributionIds) {
		return dDao.getMenuMapByDistributionIds(distributionIds);
	}
	
	@Override
	public List<KanteenMenuOrderStatItem> queryMenuOrderStats(
			Long distributionId, PageInfo pageInfo) {
		List<PlainKanteenDistributionWares> distributionWares = dDao.getDistributionWares(distributionId, pageInfo);
		Map<Long, PlainKanteenWares> waresMap = waresService.getWaresMap(CollectionUtils.toSet(distributionWares, w->w.getWaresId()));
		Map<Long, PlainKanteenWaresGroup> groupMap = groupService.getGroupMapByGroupWaresIds(CollectionUtils.toSet(distributionWares, dWares->dWares.getMenuWaresId()));
		
		List<KanteenMenuOrderStatItem> list = new ArrayList<KanteenMenuOrderStatItem>();
		distributionWares.forEach(dWares->{
			PlainKanteenWares wares = waresMap.get(dWares.getWaresId());
			PlainKanteenWaresGroup group = groupMap.get(dWares.getMenuWaresId());
			list.add(new KanteenMenuOrderStatItem(dWares, wares, group));
		});
		return list;
	}
	
	@Override
	public KanteenMenuOrderStat getDistributionOrderStat(Long distributionId) {
		KanteenMenuOrderStat stat = new KanteenMenuOrderStat();
		KanteenOrderStatCriteria criteria = new KanteenOrderStatCriteria();
		criteria.setDistributionId(distributionId);
		//待完成订单
		stat.setEffective(orderService.statCount(criteria.setStatus(PlainKanteenOrder.STATUS_PAIED, PlainKanteenOrder.STATUS_CONFIRMED)));
		//已完成订单
		stat.setCompleted(orderService.statCount(criteria.setStatus(PlainKanteenOrder.STATUS_COMPLETED)));
		//待支付订单
		stat.setWaitToPay(orderService.statCount(criteria.setStatus(PlainKanteenOrder.STATUS_DEFAULT).setPayway(PlainKanteenOrder.PAYWAY_WXPAY)));
		//总数
		stat.setTotalCount(orderService.statCount(criteria.setPayway(null).setStatus()));
		//已支付的总金额
		stat.setPaiedAmount(orderService.statOrderAmount(criteria.setStatus(PlainKanteenOrder.STATUS_PAIED)));
		//已确认但是未支付的总金额
		stat.setConfirmedAmount(orderService.statOrderAmount(criteria.setStatus(PlainKanteenOrder.STATUS_CONFIRMED)));
		//未领取订单
		stat.setMissed(orderService.statCount(criteria.setCanceledStatus(PlainKanteenOrder.CANSTATUS_MISSED)));
		//已关闭的订单数
		stat.setClosed(orderService.statCount(criteria.setCanceledStatus(PlainKanteenOrder.CANSTATUS_CLOSED)));
		//已取消或已关闭订单
		stat.setCanceled(orderService.statCount(criteria.setAllCanceled(true)));
		return stat;
	}
	
	@Override
	public void decreaseDistributionWaresCurrentCount(Long distributionWaresId,
			Integer decrease) {
		dDao.decreaseDistributionWaresCurrentCount(distributionWaresId, decrease);
	}
	
	@Override
	public void adaptEffectiveDistributionWaresByMenuId(Long menuId) {
		Date now = new Date();
		//获得菜单的所有可用商品
		List<KanteenMenuWares> menuWaresItems = menuService.getMenuWaresItems(menuId, true);
		//获得关联到menuId的当前有效的配销
		List<PlainKanteenDistribution> distributionList = dDao.getEffectiveDistributionListByMenuId(menuId, now);
		//遍历所有配销
		for (PlainKanteenDistribution distribution : distributionList) {
			//获得配销的商品
			List<PlainKanteenDistributionWares> dWaresList = dDao.getDistributionWares(distribution.getId(), null);
			//比较配销商品和菜单商品
			Set<PlainKanteenDistributionWares> toCreateWares = new LinkedHashSet<PlainKanteenDistributionWares>();
			Set<Long> toDisableDWaresIds = new HashSet<Long>(CollectionUtils.toSet(dWaresList, dWares->dWares.getMenuWaresId()));
			menuWaresItems.forEach(menuWares->{
				if(toDisableDWaresIds.contains(menuWares.getGroupWaresId())){
					toDisableDWaresIds.remove(menuWares.getGroupWaresId());
				}else{
					//构造配销商品对象
					PlainKanteenDistributionWares dWares = new PlainKanteenDistributionWares();
					dWares.setDistributionId(distribution.getId());
					dWares.setWaresId(menuWares.getWaresId());
					dWares.setMenuWaresId(menuWares.getGroupWaresId());
					dWares.setCurrentCount(0);
					dWares.setMaxCount(1000);
					dWares.setCreateTime(now);
					toCreateWares.add(dWares);
				}
			});
			//添加配销商品
			toCreateWares.forEach(wares->nDao.save(wares));
			//禁用配销商品
			dDao.disableDistributionWares(toDisableDWaresIds);
		}
	}
	
	@Override
	public void adaptEffectiveDistributionWaresByGroupId(Long waresGroupId) {
		List<PlainKanteenMenu> menuList = menuService.getMenusByWaresGroupId(waresGroupId);
		menuList.forEach(menu->adaptEffectiveDistributionWaresByMenuId(menu.getId()));
	}
	
}
