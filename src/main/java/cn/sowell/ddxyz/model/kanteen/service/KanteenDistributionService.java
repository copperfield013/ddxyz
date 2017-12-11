package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDistributionChooseMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuOrderStat;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuOrderStatItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenMenuItemForChoose;

public interface KanteenDistributionService {

	List<KanteenDistributionItem> queryDistributions(Long merchantId, PageInfo pageInfo);

	/**
	 * 根据配销id获得配销对象
	 * @param distributionId
	 * @return
	 */
	PlainKanteenDistribution getDistribution(Long distributionId);

	/**
	 * 根据条件查询菜单列表
	 * @param criteria
	 * @param pageInfo
	 * @return 
	 */
	List<KanteenMenuItemForChoose> queryMenuListForChoose(KanteenDistributionChooseMenuCriteria criteria,
			PageInfo pageInfo);

	/**
	 * 保存新的配销
	 * @param distribution
	 */
	void saveDistribution(PlainKanteenDistribution distribution);

	/**
	 * 根据配销id获得配销对应的菜单
	 * @param distributionIds
	 * @return
	 */
	Map<Long, PlainKanteenMenu> getMenuMapByDistributionId(
			Set<Long> distributionIds);

	/**
	 * 查询配销对应的菜单的各个商品的统计
	 * @param distributionId
	 * @return
	 */
	List<KanteenMenuOrderStatItem> queryMenuOrderStats(Long distributionId, PageInfo pageInfo);

	/**
	 * 配销的各个订单状态统计
	 * @param distributionId
	 * @return
	 */
	KanteenMenuOrderStat getDistributionOrderStat(Long distributionId);

	/**
	 * 修改各个配销商品的余量
	 * @param distributionWaresId
	 * @param decrease
	 */
	void decreaseDistributionWaresCurrentCount(Long distributionWaresId,
			Integer decrease);

	/**
	 * 调整当前所有关联到菜单的当前有效的配销商品
	 * @param menuId
	 */
	void adaptEffectiveDistributionWaresByMenuId(Long menuId);

	/**
	 * 调整当前所有关联到商品组的当前有效的配销商品
	 * @param id
	 */
	void adaptEffectiveDistributionWaresByGroupId(Long waresGroupId);

	
	
}
