package cn.sowell.ddxyz.model.canteen.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.Assert;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.canteen.dao.CanteenWaresDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.service.CanteenWaresService;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Service
public class CanteenWaresServiceImpl implements CanteenWaresService{

	@Resource
	CanteenWaresDao waresDao;
	
	@Override
	public List<PlainWares> getCanteenWaresList(CanteenWaresListCriteria criteria, CommonPageInfo pageInfo) {
		return waresDao.getCanteenWaresList(criteria, pageInfo);
	}

	@Override
	public <T> T getPlainObject(Class<T> objClass, Long id) {
		return waresDao.getPlainObject(objClass, id);
	}

	@Override
	public void saveWares(PlainWares wares) {
		wares.setMerchantId(DdxyzConstants.CANTEEN_MERCHANT_ID);
		wares.setCreateTime(new Date());
		waresDao.save(wares);
	}
	
	@Override
	public void updateWares(PlainWares wares) {
		PlainWares origin = getPlainObject(PlainWares.class, wares.getId());
		Assert.notNull(origin);
		origin.setName(wares.getName());
		origin.setBasePrice(wares.getBasePrice());
		origin.setThumbUri(wares.getThumbUri());
		waresDao.update(origin);
	}

	@Override
	public void disableWares(Long waresId, boolean disabled) {
		if(waresDao.disableWares(waresId, disabled) != 1) {
			throw new RuntimeException("更新商品禁用状态失败");
		}
	}
	
	

}
