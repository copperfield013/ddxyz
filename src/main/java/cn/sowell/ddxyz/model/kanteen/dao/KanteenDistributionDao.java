package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
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

}