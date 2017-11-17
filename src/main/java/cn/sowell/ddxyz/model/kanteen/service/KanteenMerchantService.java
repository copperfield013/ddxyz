package cn.sowell.ddxyz.model.kanteen.service;

import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;

public interface KanteenMerchantService {

	/**
	 * 根据当前登录的用户获得当前管理的商家
	 * @return
	 */
	PlainKanteenMerchant getCurrentMerchant();
	
}
