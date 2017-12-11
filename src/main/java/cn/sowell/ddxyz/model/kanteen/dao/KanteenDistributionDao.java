package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDistributionChooseMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenMenuItemForChoose;

public interface KanteenDistributionDao {

	List<KanteenDistributionItem> queryDistributions(Long merchantId, PageInfo pageInfo);

	List<KanteenMenuItemForChoose> queryMenuListForChoose(
			KanteenDistributionChooseMenuCriteria criteria, PageInfo pageInfo);

	Map<Long, PlainKanteenMenu> getMenuMapByDistributionIds(
			Set<Long> distributionIds);

	/**
	 * 获得该商家的最新配销的code
	 * @param merchantId
	 * @return
	 */
	String getLastCode(Long merchantId);

	/**
	 * 从数据库中查找配销的所有商品
	 * @param distributionId
	 * @param pageInfo 
	 * @return
	 */
	List<PlainKanteenDistributionWares> getDistributionWares(
			Long distributionId, PageInfo pageInfo);

	/**
	 * 修改配销商品的当前占用资源（减少数量）
	 * @param distributionWaresId
	 * @param decrease
	 */
	void decreaseDistributionWaresCurrentCount(Long distributionWaresId,
			Integer decrease);

	/**
	 * 获得关联到menuId的当前有效的配销
	 * @param menuId
	 * @return
	 */
	List<PlainKanteenDistribution> getEffectiveDistributionListByMenuId(
			Long menuId, Date now);

	/**
	 * 禁用配销商品
	 * @param toDisableDWaresIds
	 */
	void disableDistributionWares(Set<Long> toDisableDWaresIds);

	

}
