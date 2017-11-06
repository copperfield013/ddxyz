package cn.sowell.ddxyz.model.kanteen.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.weixin.pay.exception.WeiXinPayException;
import cn.sowell.copframe.weixin.pay.prepay.H5PayParameter;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenOrderCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyItem;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenReceiver;
import cn.sowell.ddxyz.model.kanteen.service.impl.OrderPayException;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

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
	List<PlainKanteenDelivery> getEnabledDeliveries(Long distributionId, Date theTime);

	/**
	 * 从json中解析购物车数据放到List
	 * @param trolleyData
	 * @return key为distributionWaresId，value为个数
	 */
	List<KanteenTrolleyItem> extractTrolleyItems(JSONObject jsonObject);

	/**
	 * 更新当前购物车内的商品
	 * @param id
	 * @param existsItems
	 */
	void mergeTrolleyWares(Long id, List<KanteenTrolleyItem> existsItems);
	

	/**
	 * 根据微信用户的id获得最新的收件人信息
	 * @param id
	 * @return
	 */
	PlainKanteenReceiver getLastReceiver(Long userId);

	/**
	 * 将购物车内的商品包装成订单对象
	 * @param trolley
	 * 
	 */
	void packOrder(KanteenOrder order, KanteenTrolley trolley);

	/**
	 * 保存订单数据到数据库
	 * @param order
	 * @return 如果是微信支付，那么将返回预支付订单的参数对象
	 * @throws WeiXinPayException 
	 */
	H5PayParameter saveOrder(KanteenOrder order) throws WeiXinPayException;

	/**
	 * 清空购物车
	 * @param trolley
	 */
	void clearTrolley(Long trolleyId);

	/**
	 * 从前台传入的json对象中提取并生成订单对象
	 * @param jsonObject
	 * @return
	 */
	KanteenOrder extractOrder(JSONObject jsonObject);

	/**
	 * 更改订单状态为已支付
	 * @param order
	 * @param user
	 * @throws OrderPayException 
	 */
	void payOrder(PlainKanteenOrder order, WeiXinUser user) throws OrderPayException;

	/**
	 * 获得订单对象
	 * @param orderId
	 * @return
	 */
	PlainKanteenOrder getOrder(Long orderId);

	PlainKanteenOrder getOrderByOutTradeNo(String outTradeNo);

	/**
	 * 根据用户id查询所有订单
	 * @param userId
	 * @return
	 */
	List<KanteenOrder> queryOrder(KanteenOrderCriteria criteria, PageInfo pageInfo);

	/**
	 * 获得所有订单的对应的配送数据对象
	 * @param deliveryIds
	 * @return
	 */
	public Map<Long, PlainKanteenDelivery> getDeliveryMap(
			Set<Long> deliveryIds);
	
	/**
	 * 隐藏订单
	 * @param user
	 * @param orderId
	 */
	void hideOrder(WeiXinUser user, Long orderId);

	/**
	 * 订单退款
	 * @param user
	 * @param orderId
	 */
	void refundOrder(WeiXinUser user, Long orderId);

	/**
	 * 取消订单
	 * @param user
	 * @param orderId
	 */
	void cancelOrder(WeiXinUser user, Long orderId);


	
}
