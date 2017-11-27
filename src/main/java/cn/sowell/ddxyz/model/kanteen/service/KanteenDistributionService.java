package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDistributionChooseMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
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

}
