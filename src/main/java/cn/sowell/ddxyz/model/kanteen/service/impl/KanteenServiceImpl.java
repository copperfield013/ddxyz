package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDao;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenDistributionMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchantAnnounce;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.service.KanteenService;

@Service
public class KanteenServiceImpl implements KanteenService {

	@Resource
	KanteenDao kDao;
	
	@Resource
	FrameDateFormat dateFormat;
	
	@Override
	public PlainKanteenMerchant getMerchant(Long merchantId) {
		PlainKanteenMerchant merchant = kDao.get(merchantId, PlainKanteenMerchant.class);
		if(merchant != null){
			List<PlainKanteenMerchantAnnounce> announces = kDao.getOrderedAnnounces(merchant.getAnnounceKey());
			merchant.setAnnounces(CollectionUtils.toList(announces, anno->anno.getText()));
		}
		return merchant;
	}

	@Override
	public PlainKanteenDistribution getDistributionOfThisWeek(Long merchantId,
			Date date) {
		if(date != null){
			Date[] range = dateFormat.getTheWeekRange(date, Calendar.MONDAY);
			return kDao.getDistribution(merchantId, range);
		}
		return null;
	}

	@Override
	public KanteenMenu getKanteenMenu(Long distributionId) {
		PlainKanteenDistribution distribution = kDao.get(distributionId, PlainKanteenDistribution.class);
		if(distribution != null){
			KanteenMenu menu = new KanteenMenu();
			PlainKanteenMenu pmenu = kDao.get(distribution.getMenuId(), PlainKanteenMenu.class);
			menu.setPlainMenu(pmenu);
			//获得菜单内所有的商品组
			List<PlainKanteenWaresGroup> menuGroups = kDao.getMenuWaresGroups(pmenu.getId());
			//获得配销对应的所有商品
			List<KanteenDistributionMenuItem> menuItems = kDao.getMenuItems(distributionId);
			//获得group关于id的map
			Map<Long, PlainKanteenWaresGroup> menuGroupMap = CollectionUtils.toMap(menuGroups, group->group.getId());
			//整合商品组和商品
			Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap = CollectionUtils.toListMap(menuItems, item->menuGroupMap.get(item.getGroupId()));
			//根据条件筛掉菜单内的商品和商品组
			filter(menuItemMap);
			//对菜单进行排序
			menuItemMap = sort(menuItemMap);
			menu.setMenuItemMap(menuItemMap);
			return menu;
		}
		return null;
	}

	static final Integer DISABLED = 1;
	
	/**
	 * 对menuItemMap内的所有元素进行筛选
	 * @param menuItemMap
	 */
	private void filter(
			Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap) {
		Iterator<Entry<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>>> itrEntry = menuItemMap.entrySet().iterator();
		while(itrEntry.hasNext()){
			Entry<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> entry = itrEntry.next();
			PlainKanteenWaresGroup group = entry.getKey();
			if(DISABLED.equals(group.getDisabled())){
				//禁用所有disabled的商品组
				itrEntry.remove();
			}else{
				Iterator<KanteenDistributionMenuItem> itrItems = entry.getValue().iterator();
				while(itrItems.hasNext()){
					KanteenDistributionMenuItem item = itrItems.next();
					if(DISABLED.equals(item.getDisabled())){
						//禁用所有disabled的商品
						itrItems.remove();
					}
				}
			}
		}
		
	}
	
	/**
	 * 对menuItemMap进行排序，并且返回排序后的对象
	 * @param menuItemMap
	 * @return
	 */
	private Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> sort(
			Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap) {
		TreeMap<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> map = new TreeMap<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>>(Orderable.COMPARATOR);
		map.putAll(menuItemMap);
		map.forEach((group, itemList) ->{
			itemList.sort(Orderable.COMPARATOR);
		});
		return map;
	}


	@Override
	public KanteenTrolley getTrolley(Long userId, Long distributionId) {
		PlainKanteenTrolley pTrolley = kDao.getTrolley(userId, distributionId);
		if(pTrolley != null){
			//获得购物车内的所有商品
			List<KanteenTrolleyWares> trolleyWares = kDao.getTrolleyWares(pTrolley.getId()),
					validWares = new ArrayList<KanteenTrolleyWares>(),
					invalidWares = new ArrayList<KanteenTrolleyWares>();
			//获得配销对应的所有商品
			List<PlainKanteenDistributionWares> distributionWares = kDao.getDistributionWares(new HashSet<Long>(CollectionUtils.toList(trolleyWares, wares->wares.getDistributionWaresId())));
			Map<Long, PlainKanteenDistributionWares> distributionWaresMap = CollectionUtils.toMap(distributionWares, wares->wares.getId());
			//将根据当前配销的商品余量限制，筛选购物车内的商品
			trolleyWares.forEach(tWares->{
				PlainKanteenDistributionWares dWares = distributionWaresMap.get(tWares.getDistributionWaresId());
				if(dWares != null &&
						dWares.getMaxCount() == null || tWares.getCount() + dWares.getCurrentCount() <= dWares.getMaxCount()){
					validWares.add(tWares);
				}else{
					invalidWares.add(tWares);
				}
			});
			
			//构造购物车对象
			KanteenTrolley trolley = new KanteenTrolley();
			trolley.setPlainTrolley(pTrolley);
			trolley.setValidWares(validWares);
			trolley.setInvalidWares(invalidWares);
			return trolley;
		}
		return null;
	}

	@Override
	public List<PlainKanteenDelivery> getEnabledDeliveries(Long distributionId) {
		return kDao.getEnabledDeliveries(distributionId);
	}
	
	@Override
	public KanteenTrolley getTrolley(Long trolleyId) {
		
		return null;
	}
	
}
