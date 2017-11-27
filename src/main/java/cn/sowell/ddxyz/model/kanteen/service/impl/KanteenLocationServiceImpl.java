package cn.sowell.ddxyz.model.kanteen.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenLocation;
import cn.sowell.ddxyz.model.kanteen.service.KanteenLocationService;

@Service
public class KanteenLocationServiceImpl implements KanteenLocationService{

	@Resource
	NormalOperateDao nDao;
	
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

}
