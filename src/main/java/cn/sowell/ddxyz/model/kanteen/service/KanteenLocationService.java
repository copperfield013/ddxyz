package cn.sowell.ddxyz.model.kanteen.service;

import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenLocation;

public interface KanteenLocationService {

	/**
	 * 根据id获得配送地点的对象。传入merchantId时，表示验证地点的可获取性。当不可获取时，将抛出异常
	 * @param locationId
	 * @param merchantId
	 * @return
	 */
	PlainKanteenLocation getLocation(Long locationId, Long merchantId);

}
