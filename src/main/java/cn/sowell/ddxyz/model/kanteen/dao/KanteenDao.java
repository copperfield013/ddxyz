package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenDistributionMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchantAnnounce;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;

public interface KanteenDao {
	/**
	 * 从数据库获得公告信息
	 * @param annouceKey
	 * @return
	 */
	List<PlainKanteenMerchantAnnounce> getOrderedAnnounces(String annouceKey);

	/**
	 * 找到商家的可预订时间在某个时间范围内的唯一的配销（如果有多个，则选择创建时间最靠前的那个）
	 * @param merchantId 商家id
	 * @param range 时间范围
	 * @return 
	 */
	PlainKanteenDistribution getDistribution(Long merchantId, Date[] range);

	/**
	 * 根据pojo的类型和id获得pojo对象
	 * @param <T>
	 * @param distributionId
	 * @param clazz
	 * @return
	 */
	<T> T get(Long id, Class<T> clazz);

	/**
	 * 根据菜单的id获得菜单下的所有商品组
	 * @param menuId
	 * @return
	 */
	List<PlainKanteenWaresGroup> getMenuWaresGroups(Long menuId);

	/**
	 * 根据配销id获得对应菜单的所有商品（要求在t_distribution_wares表中存在）
	 * @param distributionId
	 * @return
	 */
	List<KanteenDistributionMenuItem> getMenuItems(Long distributionId);

	/**
	 * 根据用户id和配销的id获得购物车数据对象
	 * @param userId
	 * @param distributionId
	 * @return
	 */
	PlainKanteenTrolley getTrolley(Long userId, Long distributionId);
	
	
	/**
	 * 根据购物车id获得购物车内的所有商品数据
	 * @param trolleyId
	 * @return
	 */
	List<KanteenTrolleyWares> getTrolleyWares(Long trolleyId);
	
	/**
	 * 根据配销商品的id集合，获得对应的配销商品信息列表
	 * @param distributionWaresIds
	 * @return
	 */
	List<PlainKanteenDistributionWares> getDistributionWares(Set<Long> distributionWaresIds);

	/**
	 * 
	 * @param distributionId
	 * @return
	 */
	List<PlainKanteenDelivery> getEnabledDeliveries(Long distributionId);
	

}
