package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDistributionDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDistributionChooseMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenMenuItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenDistributionService;

@Service
public class KanteenDistributionServiceImpl implements KanteenDistributionService{

	@Resource
	KanteenDistributionDao dDao;
	
	@Resource
	NormalOperateDao nDao;
	
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

	@Override
	public void saveDistribution(PlainKanteenDistribution distribution) {
		Assert.notNull(distribution.getMenuId());
		Assert.notNull(distribution.getStartTime());
		Assert.notNull(distribution.getEndTime());
		Assert.isTrue(distribution.getStartTime().before(distribution.getEndTime()));
		Date now = new Date();
		distribution.setCreateTime(now );
		distribution.setUpdateTime(now);
		nDao.save(distribution);
	}
	
	@Override
	public Map<Long, PlainKanteenMenu> getMenuMapByDistributionId(
			Set<Long> distributionIds) {
		return dDao.getMenuMapByDistributionIds(distributionIds);
	}
}
