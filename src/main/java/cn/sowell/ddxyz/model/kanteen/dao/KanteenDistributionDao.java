package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;

public interface KanteenDistributionDao {

	List<KanteenDistributionItem> queryDistributions(Long merchantId, PageInfo pageInfo);

}
