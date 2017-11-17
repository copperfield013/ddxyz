package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDistributionDao;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;

@Service
public class KanteenDistributionServiceImpl implements KanteenDistributionService{

	@Resource
	KanteenDistributionDao dDao;
	
	
	@Override
	public List<KanteenDistributionItem> queryDistributions(Long merchantId, PageInfo pageInfo) {
		return dDao.queryDistributions(merchantId, pageInfo);
	}

}
