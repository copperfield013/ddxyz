package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenTrolleyWares;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenTrolleyWaresOption;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDao;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenDistributionMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenOrderCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyWares;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenWaresOptionGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenCancelOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchantAnnounce;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenReceiver;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenSection;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOptionGroup;

@Repository
public class KanteenDaoImpl implements KanteenDao {

	@Resource
	SessionFactory sFactory;

	@Resource
	FrameDateFormat dateFormat;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenMerchantAnnounce> getOrderedAnnounces(
			String annouceKey) {
		String hql = "from PlainKanteenMerchantAnnounce anno where anno.key = :key order by anno.order asc";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setString("key", annouceKey);
		return query.list();
	}

	@Override
	public PlainKanteenDistribution getDistribution(Long merchantId,
			Date[] range) {
		String hql = "from PlainKanteenDistribution dis where dis.merchantId = :merchantId and dis.startTime >= :begin and dis.startTime <= :end order by dis.createTime asc";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("merchantId", merchantId)
				.setTimestamp("begin", range[0])
				.setTimestamp("end", range[1]);
		return (PlainKanteenDistribution) query.setMaxResults(1).uniqueResult();
	}

	@Override
	public <T> T get(Long id, Class<T> clazz) {
		return sFactory.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenWaresGroup> getMenuWaresGroups(Long menuId) {
		String sql = "SELECT" +
				"	wg.*," +
				"	mwg.menu_id," +
				"	mwg.c_order" +
				" FROM" +
				"	t_menu_waresgroup mwg" +
				" LEFT JOIN t_waresgroup_base wg ON mwg.waresgroup_id = wg.id" +
				" WHERE" +
				"	mwg.menu_id = :menuId";
		
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainKanteenWaresGroup.class));
		query.setLong("menuId", menuId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KanteenDistributionMenuItem> getMenuItems(Long distributionId) {
		String sql = "SELECT" +
				"	dw.id as distributionwares_id," +
				"	dw.distribution_id," +
				"	wgw.wares_id," +
				"	wgw.group_id," +
				"	db.menu_id," +
				"	dw.menuwares_id," +
				"	wb.c_name as wares_name," +
				"	wb.c_base_price," +
				"	wb.c_price_unit," +
				"	wb.c_disabled," +
				"	dw.c_current_count," +
				"	dw.c_max_count," +
				"	wgw.c_order" +
				" FROM" +
				"	t_distribution_wares dw" +
				" LEFT JOIN t_distribution_base db ON dw.distribution_id = db.id" +
				" left join t_waresgroup_wares wgw on dw.menuwares_id = wgw.id" +
				" left join t_wares_base wb on wgw.wares_id = wb.id" +
				" where dw.distribution_id = :distributionId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("distributionId", distributionId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(KanteenDistributionMenuItem.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, List<KanteenWaresOptionGroup>> getMenuWaresOptionGroupsMap(
			Set<Long> waresIds) {
		if(waresIds != null && waresIds.size() > 0){
			String hql = "from PlainKanteenWaresOptionGroup g where g.waresId in (:waresIds) and g.disabled is null";
			Query query = sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("waresIds", waresIds, StandardBasicTypes.LONG);
			List<PlainKanteenWaresOptionGroup> plainGroups = query.list();
			
			if(plainGroups != null && plainGroups.size() > 0){
				hql = "from PlainKanteenWaresOption o where o.optiongroupId in (:groupIds) and o.disabled is null";
				query =  sFactory.getCurrentSession().createQuery(hql);
				query.setParameterList("groupIds", CollectionUtils.toList(plainGroups, group->group.getId()));
				List<PlainKanteenWaresOption> plainOptions = query.list();
				
				//groupId->group的map
				Map<Long, PlainKanteenWaresOptionGroup> groupMap = CollectionUtils.toMap(plainGroups, group->group.getId());
				//groupId->optionList的map
				Map<Long, List<PlainKanteenWaresOption>> optionListMap = CollectionUtils.toListMap(plainOptions, option->option.getOptiongroupId());
				//存放kanteenGroup
				List<KanteenWaresOptionGroup> groups = new ArrayList<KanteenWaresOptionGroup>();
				groupMap.forEach((groupId, plainGroup)->{
					KanteenWaresOptionGroup group = new KanteenWaresOptionGroup();
					group.setPlainGroup(plainGroup);
					group.setOptionList(optionListMap.get(groupId));
					groups.add(group);
				});
				return CollectionUtils.toListMap(groups, group->group.getWaresId());
			}
		}
		return new HashMap<Long, List<KanteenWaresOptionGroup>>();
	}
	
	
	@Override
	public PlainKanteenTrolley getTrolley(Long userId, Long distributionId) {
		String hql = "from PlainKanteenTrolley t where t.userId = :userId and t.distributionId = :distributionId and t.disabled is null order by t.createTime desc";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("userId", userId)
			.setLong("distributionId", distributionId)
			.setMaxResults(1)
			;
		return (PlainKanteenTrolley) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KanteenTrolleyWares> getTrolleyWares(Long trolleyId) {
		String sql = "SELECT" +
				"	tw.*," + 
				" wb.c_name AS c_wares_name," +
				" dw.wares_id," + 
				" wb.c_base_price," + 
				" wb.c_price_unit" + 
				" FROM" +
				"	t_trolley_wares tw" +
				" LEFT JOIN t_distribution_wares dw ON dw.id = tw.distributionwares_id" +
				" LEFT JOIN t_wares_base wb ON wb.id = dw.wares_id" +
				" WHERE" +
				"	tw.trolley_id = :trolleyId" +
				" AND dw.id IS NOT NULL" +
				" ORDER BY" +
				"	tw.create_time ASC";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("trolleyId", trolleyId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(KanteenTrolleyWares.class));
		List<KanteenTrolleyWares> list = query.list();
		
		List<PlainKanteenTrolleyWaresOption> options = getTrolleyOptions(CollectionUtils.toSet(list, item->item.getId()));
		Map<Long, List<PlainKanteenTrolleyWaresOption>> optionsListMap = CollectionUtils.toListMap(options, option->option.getTrolleyWaresId());
		Map<Long, PlainKanteenWaresOption> wOptionMap = getWaresOptionsMap(CollectionUtils.toSet(options, option->option.getWaresOptionId()));
		
		
		for (KanteenTrolleyWares tWares : list) {
			if(optionsListMap.containsKey(tWares.getId())){
				List<PlainKanteenTrolleyWaresOption> waresOptions = optionsListMap.get(tWares.getId());
				tWares.setWareOptionIds(CollectionUtils.toSet(waresOptions, option->option.getWaresOptionId()));
				tWares.setTrolleyOptionIds(CollectionUtils.toSet(waresOptions, option->option.getId()));
				tWares.setOptionMap(CollectionUtils.filter(wOptionMap, key->tWares.getWareOptionIds().contains(key)));
			}
		}
		return list;
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	private Map<Long, PlainKanteenWaresOption> getWaresOptionsMap(
			LinkedHashSet<Long> waresOptionIds) {
		if(waresOptionIds != null && !waresOptionIds.isEmpty()){
			String hql = "from PlainKanteenWaresOption o where o.id in (:waresOptionIds)";
			Query query = sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("waresOptionIds", waresOptionIds, StandardBasicTypes.LONG);
			List<PlainKanteenWaresOption> list = query.list();
			if(list != null){
				return CollectionUtils.toMap(list, option->option.getId());
			}
		}
		return new HashMap<Long, PlainKanteenWaresOption>();
	}

	@SuppressWarnings("unchecked")
	private List<PlainKanteenTrolleyWaresOption> getTrolleyOptions(
			Set<Long> trolleyWaresIds) {
		if(trolleyWaresIds != null && trolleyWaresIds.size() > 0){
			String hql = "from PlainKanteenTrolleyWaresOption o where o.trolleyWaresId in (:trolleyWaresIds)";
			Query query = sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("trolleyWaresIds", trolleyWaresIds, StandardBasicTypes.LONG);
			return query.list();
		}
		return new ArrayList<PlainKanteenTrolleyWaresOption>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, PlainKanteenTrolleyWares> getTrolleyWaresMap(long trolleyId){
		String hql = "from PlainKanteenTrolleyWares w where w.trolleyId = :trolleyId";
		
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("trolleyId", trolleyId);
		List<PlainKanteenTrolleyWares> list = query.list();
		return CollectionUtils.toMap(list, item->item.getId());
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenDistributionWares> getDistributionWares(
			Set<Long> distributionWaresIds) {
		String hql = "from PlainKanteenDistributionWares dw where dw.id in (:distributionWaresIds)";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("distributionWaresIds", distributionWaresIds, StandardBasicTypes.LONG);
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenDelivery> getEnabledDeliveries(Long distributionId, Date theTime) {
		String hql = "from PlainKanteenDelivery d where d.distributionId = :distributionId and d.disabled is null and d.startTime <= :theTime and (d.endTime is null or d.endTime > :theTime)";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("distributionId", distributionId);
		query.setTimestamp("theTime", theTime);
		return query.list();
	}
	
	@Override
	public void create(Class<?> pojoClass) {
		Object pojo = BeanUtils.instantiate(pojoClass);
		create(pojo);
	}
	
	@Override
	public Long create(Object pojo){
		return (Long) sFactory.getCurrentSession().save(pojo);
	}
	
	
	@Override
	public void removeTrolleyWares(Set<Long> toRemove) {
		if(toRemove != null && toRemove.size() > 0){
			String sql = "delete from t_trolley_wares where id in (:toRemove)";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameterList("toRemove", toRemove, StandardBasicTypes.LONG);
			query.executeUpdate();
		}
	}
	
	@Override
	public void updateTrolleyWares(Map<Long, Integer> toUpdate) {
		if(toUpdate != null && toUpdate.size() > 0){
			String sql = "update t_trolley_wares set c_count = :count, update_time = :updateTime where id = :distributionWaresId"; 
			toUpdate.forEach((distributionWaresId, count) -> {
				SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
				query.setLong("distributionWaresId", distributionWaresId)
					.setInteger("count", count)
					.setTimestamp("updateTime", new Date());
				query.executeUpdate();
			});
		}
	}
	
	
	@Override
	public PlainKanteenReceiver getLastReceiver(Long userId) {
		String sql = "SELECT" +
				"	o.order_user_id," +
				"	o.c_receiver_name," +
				"	o.c_receiver_contact," +
				"	o.c_receiver_depart," +
				"	o.c_payway," +
				"	o.location_id" +
				" FROM t_order_kanteen o" +
				" WHERE o.order_user_id = :userId" + 
				" order by o.create_time desc";
		
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		
		return (PlainKanteenReceiver) query.setLong("userId", userId)
			.setMaxResults(1)
			.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainKanteenReceiver.class))
			.uniqueResult()
			;
	}
	
	@Override
	public void disableTrolley(Long id) {
		String sql = "update t_trolley_base set c_disabled = 1 where id = :trolleyId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("trolleyId", id);
		query.executeUpdate();
	}
	
	@Override
	public boolean updateDistributionWareCurrentCount(Long distributionWaresId,
			Integer addition) {
		String sql = "UPDATE t_distribution_wares" +
				" SET c_current_count = c_current_count + :count" +
				" WHERE id = :distributionWaresId AND (" +
				"	c_max_count IS NULL" +
				"	OR c_current_count + :count <= c_max_count)" + 
				" and c_current_count + :count >= 0";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("distributionWaresId", distributionWaresId)
				.setInteger("count", addition);
		return query.executeUpdate() == 1;
	}
	
	@Override
	public void updateOrderWxPayFields(PlainKanteenOrder pOrder) {
		String sql = "update t_order_kanteen set c_wx_out_trade_no = :outTradeNo, c_wx_prepay_id = :prepayId where id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("outTradeNo", pOrder.getWxOutTradeNo())
			.setString("prepayId", pOrder.getWxPrepayId())
			.setLong("orderId", pOrder.getId())
			.executeUpdate();
	}
	
	@Override
	public void updateOrderAsPaied(PlainKanteenOrder order) {
		if(PlainKanteenOrder.STATUS_PAIED.equals(order.getStatus())){
			String sql = "update t_order_kanteen set c_status = :paiedStatus , c_actual_pay = :actualPay where id = :orderId";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setString("paiedStatus", order.getStatus())
					.setInteger("actualPay", order.getActualPay())
					.setLong("orderId", order.getId())
					.executeUpdate()
					;
		}
	}
	
	@Override
	public PlainKanteenOrder getOrderByOutTradeNo(String outTradeNo) {
		String hql = "from PlainKanteenOrder h where h.wxOutTradeNo = :outTradeNo";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setString("outTradeNo", outTradeNo).setMaxResults(1);
		return (PlainKanteenOrder) query.uniqueResult();
	}
	
	@Override
	public PlainKanteenMerchant getMerchantByDistributionId(Long distributionId) {
		String sql = "select m.* from t_distribution_base db left join t_merchant_base m on db.merchant_id = m.id where db.id = :distributionId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("distributionId", distributionId).setMaxResults(1);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainKanteenMerchant.class));
		return (PlainKanteenMerchant) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenSection> getSections(Set<Long> orderIdSet) {
		if(orderIdSet != null && orderIdSet.size() > 0){
			String hql = "from PlainKanteenSection s where s.orderId in (:orderIds)";
			Query query = sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("orderIds", orderIdSet);
			return query.list();
		}else{
			return new ArrayList<PlainKanteenSection>();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenOrder> query(KanteenOrderCriteria criteria,
			PageInfo pageInfo) {
		String sql = "select o.* from t_order_kanteen o "
				+ "where o.order_user_id = :userId "
				+ "and o.c_deleted is null "
				+ "@condition order by o.create_time desc";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("userId", criteria.getUserId());
		
		DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
		if("week".equals(criteria.getRange())){
			snippet.append("and o.create_time >= :weekStart and o.create_time <= :weekEnd");
			Date[] weekRange = dateFormat.getTheWeekRange(new Date(), Calendar.MONDAY);
			dQuery.setParam("weekStart", weekRange[0])
					.setParam("weekEnd", weekRange[1]);
					
		}else if(!"all".equals(criteria.getRange())){
			throw new RuntimeException("range没有传入可用值");
		}
		
		if(TextUtils.hasText(criteria.getKeyword())){
			snippet.append("and (o.c_merchant_name like :keyword "
					+ "or o.c_order_code like :keyword "
					+ "or o.c_location_name like :keyword "
					+ "or o.c_receiver_name like :keyword "
					+ "or o.c_receiver_contact like :keyword "
					+ "or o.c_receiver_depart like :keyword"
					+ "or exists(select s.id from t_section_base s where s.order_id = o.id and s.c_waresname like :keyword)"
					+ ")");
			dQuery.setParam("keyword", criteria.getKeyword());
		}
		
		Query query = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
		QueryUtils.setPagingParamWithCriteria(query, pageInfo);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainKanteenOrder.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenDelivery> getDeliveries(Set<Long> deliveryIds) {
		String hql = "from PlainKanteenDelivery d where d.id in (:deliveryIds)";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("deliveryIds", deliveryIds, StandardBasicTypes.LONG);
		return query.list();
	}
	
	@Override
	public void updateOrderAsDeleted(Long orderId, boolean toDel) {
		String sql = "update t_order_kanteen set c_deleted = :toDel, update_time = :now where id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("toDel", toDel ? 1 : null, StandardBasicTypes.INTEGER)
				.setLong("orderId", orderId)
				.setTimestamp("now", new Date());
		query.executeUpdate();
	}
	
	@Override
	public void updateOrderAsRefunded(Long orderId, Integer refundFee) {
		String sql = "update t_order_kanteen set c_canceled_status = :refundStatus, c_refund_fee = :refundFee, update_time = :now where id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("refundStatus", PlainKanteenOrder.CANSTATUS_REFUNDED)
				.setInteger("refundFee", refundFee)
				.setTimestamp("now", new Date())
				.setLong("orderId", orderId);
		query.executeUpdate();
	}
	
	@Override
	public void cancelOrder(Long orderId) {
		String sql = "update t_order_kanteen set c_canceled_status = :canceledStatus, update_time = :now where id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("canceledStatus", PlainKanteenOrder.CANSTATUS_CANCELED)
				.setTimestamp("now", new Date())
				.setLong("orderId", orderId);
		query.executeUpdate();
	}
	
	@Override
	public PlainKanteenCancelOption getOrderCancelOption(
			PlainKanteenOrder plainOrder) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
