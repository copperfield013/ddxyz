package cn.sowell.ddxyz.model.kanteen.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;

import com.alibaba.fastjson.JSONObject;

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
	PlainKanteenMerchant getMerchant(Long merchantId);

	/**
	 * 根据店铺和当前时间，获得该时间所在周的配销
	 * @param merchantId
	 * @param date
	 * @return
	 */
	PlainKanteenDistribution getDistributionOfThisWeek(Long merchantId,
			Date date);
	/**
	 * 根据配销的id获得对应的菜单。菜单会根据配销的预定情况和限制情况绑定数据
	 * @param distributionId
	 * @return
	 */
	KanteenMenu getKanteenMenu(Long distributionId);
	/**
	 * 根据用户和配销的id获得该配销的购物车,
	 * 如果根据userId和distributionId找不到，那么创建一个并返回
	 * @param userId
	 * @param distributionId
	 * @return
	 */
	KanteenTrolley getTrolley(Long userId, Long distributionId);
	/**
	 * 根据配销获得所有可用配送
	 * @param distributionId
	 * @return
	 */
	List<PlainKanteenDelivery> getEnabledDeliveries(Long distributionId);

	/**
	 * 根据id获得购物车信息对象
	 * @param trolleyId
	 * @return
	 */
	KanteenTrolley getTrolley(Long trolleyId);

	/**
	 * 从json中解析购物车数据放到Map中
	 * @param trolleyData
	 * @return key为distributionWaresId，value为个数
	 */
	Map<Long, Integer> extractTrolley(JSONObject trolleyData);

	/**
	 * 更新购物车内的数据
	 * @param trolleyId
	 * @param trolleyWares
	 * @return
	 */
	void mergeTrolley(Long trolleyId, Map<Long, Integer> trolleyWares);

	
}
