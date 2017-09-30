package cn.sowell.ddxyz.model.kanteen.service.impl;

import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.model.canteen.pojo.KanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.service.KanteenService;

@Repository
public class KanteenServiceImpl implements KanteenService {

	@Override
	public KanteenMerchant getMerchant(Long merchantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KanteenMenu getMenuOfThisWeek(Long merchantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KanteenDelivery getDelieryOfThisWeek(Long merchantId) {
		// TODO Auto-generated method stub
		return null;
	}

}
