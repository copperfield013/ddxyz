package cn.sowell.ddxyz.model.kanteen.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenMerchantDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;

@Service
public class KanteenMerchantServiceImpl implements KanteenMerchantService{

	@Resource
	KanteenMerchantDao mDao;
	
	@Override
	public PlainKanteenMerchant getCurrentMerchant() {
		UserIdentifier user = WxUtils.getCurrentUser(UserIdentifier.class);
		return mDao.getUserMerchant((Long) user.getId());
	}

}
