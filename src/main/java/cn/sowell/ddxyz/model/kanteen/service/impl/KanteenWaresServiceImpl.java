package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWares;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresItem;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresService;

@Service
public class KanteenWaresServiceImpl implements KanteenWaresService{

	@Resource
	KanteenWaresDao wDao;
	
	@Override
	public List<KanteenWaresItem> queryWares(KanteenWaresCriteria criteria,
			PageInfo pageInfo) {
		Assert.notNull(criteria.getMerchantId());
		return wDao.queryWares(criteria, pageInfo);
	}

	@Override
	public void saveWares(PlainKanteenWares wares) {
		wares.setCreateTime(new Date());
		wDao.save(wares);
	}

	@Override
	public <T> T getPlainObject(Class<T> pojoClass, Long pojoId) {
		return wDao.get(pojoClass, pojoId);
	}

	@Override
	public void updateWares(PlainKanteenWares wares) {
		wares.setUpdateTime(new Date());
		wDao.update(wares);
	}
	
	Map<String, String> detailPreviewMap = new HashMap<String, String>();
	@Override
	public void updateWaresDetailPreview(String uuid, String detail) {
		detailPreviewMap.put(uuid, detail);
	}
	
	@Override
	public void updateWaresSalable(Long waresId, boolean salable) {
		wDao.updateWaresSalable(waresId, salable);
	}
	
	@Override
	public void disableWares(Long waresId, boolean disabled) {
		if(wDao.disableWares(waresId, disabled) != 1) {
			throw new RuntimeException("更新商品禁用状态失败");
		}
	}

}
