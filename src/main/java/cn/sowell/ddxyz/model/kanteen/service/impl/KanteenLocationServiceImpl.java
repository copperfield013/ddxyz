package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenLocationDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenLocation;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteriaForChoose;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenLocationItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenLocationService;

@Service
public class KanteenLocationServiceImpl implements KanteenLocationService{

	@Resource
	NormalOperateDao nDao;
	
	@Resource
	KanteenLocationDao lDao;
	
	@Override
	public PlainKanteenLocation getLocation(Long locationId, Long merchantId) {
		PlainKanteenLocation location = nDao.get(PlainKanteenLocation.class, locationId);
		if(location != null){
			if(location.getMerchantId() != null && !location.getMerchantId().equals(merchantId)){
				throw new RuntimeException("不能获取id为" + locationId + "的配送地址，权限不足");
			}
		}
		return location;
	}

	@Override
	public List<PlainKanteenLocation> queryLocationList(
			KanteenLocationCriteria criteria, PageInfo pageInfo) {
		return lDao.queryLocationList(criteria, pageInfo);
	}

	@Override
	public boolean checkLocationCode(Long merchantId, String code) {
		return lDao.checkLocationCode(merchantId, code);
	}

	@Override
	public void deletePlainLocation(Long locationId) {
		lDao.deleteLocation(locationId);
	}
	
	@Override
	public void createLocation(PlainKanteenLocation location) {
		Date now = new Date();
		location.setCreateTime(now);
		location.setUpdateTime(now);
		nDao.save(location);
	}
	
	@Override
	public List<KanteenLocationItemForChoose> queryLocationListForChoose(
			KanteenLocationCriteriaForChoose criteria, PageInfo pageInfo) {
		return lDao.queryLocationListForChoose(criteria, pageInfo);
	}

}
