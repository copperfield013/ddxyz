package cn.sowell.ddxyz.model.kanteen.service;

import cn.sowell.ddxyz.model.canteen.pojo.KanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMerchant;

/**
 * 
 * <p>Title: KanteenService</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年9月28日 上午8:56:20
 */
public interface KanteenService {
	/**
	 * 
	 * @param merchantId
	 * @return
	 */
	KanteenMerchant getMerchant(Long merchantId);

	/**
	 * 
	 * @param merchantId
	 * @return
	 */
	KanteenMenu getMenuOfThisWeek(Long merchantId);

	/**
	 * 
	 * @param merchantId
	 * @return
	 */
	KanteenDelivery getDelieryOfThisWeek(Long merchantId);
	
}
