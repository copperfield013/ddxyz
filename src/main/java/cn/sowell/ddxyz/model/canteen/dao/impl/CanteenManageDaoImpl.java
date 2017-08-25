package cn.sowell.ddxyz.model.canteen.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.ConditionSnippet;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.canteen.dao.CanteenManageDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenOrdersCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrderWaresItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;
import cn.sowell.ddxyz.model.common.core.Order;

@Repository
public class CanteenManageDaoImpl implements CanteenManageDao{
	
	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenWeekTableItem> queryDeliveryTableItems(Long deliveryId,
			CanteenWeekTableCriteria criteria, CommonPageInfo pageInfo) {
		String sql =
				"SELECT" +
						"	co.order_id," +
						"	ob.c_order_code," +
						"	ob.c_receiver_name," +
						"	ob.c_receiver_contact," +
						"	co.c_depart," +
						"	p.delivery_wares_id," +
						"	p.c_name wares_name," +
						"	count(p.id) p_count," +
						"	w.c_base_price," +
						"	w.c_price_unit," +
						"	ob.create_time order_time" +
						" FROM" +
						"	t_canteen_order co" +
						" LEFT JOIN t_order_base ob ON co.order_id = ob.id" +
						" LEFT JOIN t_product_base p ON ob.id = p.order_id" +
						" LEFT JOIN t_wares_base w ON p.wares_id = w.id" +
						" WHERE" +
						"	ob.delivery_id = :deliveryId and ob.c_canceled_status is null" +
						" GROUP BY" +
						"	p.delivery_wares_id, co.order_id" + 
						" order by ob.create_time desc "
						
						;
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("deliveryId", deliveryId);
		Session session = sFactory.getCurrentSession();
		SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
		
		int count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			QueryUtils.setPagingParamWithCriteria(query, pageInfo);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(CanteenWeekTableItem.class));
			return query.list();
		}
		return new ArrayList<CanteenWeekTableItem>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenDeliveryOrdersItem> queryDeliveryOrderItems(
			CanteenOrdersCriteria criteria, CommonPageInfo pageInfo) {
		String sql = 
				"	select " +
				"	co.order_id," +
				"	ob.c_order_code," +
				"	ob.c_receiver_name," +
				"	co.c_depart," +
				"	ob.c_receiver_contact," +
				"	ob.c_total_price," +
				"	ob.c_canceled_status," +
				"	ob.c_status," +
				"	ob.create_time," +
				"	ob.c_comment" +
				"	from t_canteen_order co " +
				"	left join t_order_base ob on co.order_id = ob.id" +
				/*"	where ob.delivery_id = :deliveryId and (ob.c_canceled_status is null or ob.c_canceled_status in (:showCancelStatus))" +*/
				"	where ob.delivery_id = :deliveryId and (@containsCondition)" +
				"	order by co.c_depart asc, ob.create_time desc";
		
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("deliveryId", criteria.getDeliveryId());
		/*dQuery.setParam("showCancelStatus", Arrays.asList(Order.CAN_STATUS_CLOSED, Order.CAN_STATUS_MISS));*/
		DeferedParamSnippet snippet = dQuery.createSnippet("containsCondition", null);
		appendContainsSQL(snippet, criteria).forEach((key, val)->dQuery.setParam(key, val));;
		
		
		Session session = sFactory.getCurrentSession();
		SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
		
		int count = FormatUtils.toInteger(countQuery.uniqueResult());
		if(pageInfo != null) {
			pageInfo.setCount(count);
		}
		if(count > 0){
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			QueryUtils.setPagingParamWithCriteria(query, pageInfo);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(CanteenDeliveryOrdersItem.class));
			List<CanteenDeliveryOrdersItem> result = query.list();
			//按照订单id做映射
			Map<Long, CanteenDeliveryOrdersItem> orderMap = CollectionUtils.toMap(result, item->item.getOrderId());
			//查询这些订单的所有明细
			List<CanteenDeliveryOrderWaresItem> orderWaresItemList = getOrderWaresItemList(orderMap.keySet());
			//将订单明细放到对应的订单对象内
			CollectionUtils.toListMap(orderWaresItemList, item->item.getOrderId())
				.forEach((orderId, orderWaresItem)->orderMap.get(orderId).setWaresItemsList(orderWaresItem));
			return result;
			
		}
		return new ArrayList<CanteenDeliveryOrdersItem>();
		
		
	}

	private Map<String, Object> appendContainsSQL(DeferedParamSnippet snippet, CanteenOrdersCriteria criteria) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		snippet.append("ob.c_canceled_status is null and (");
		if(criteria.getContainsDefault()){
			snippet.append("ob.c_status = :defaultStatus");
		}else{
			snippet.append("ob.c_status <> :defaultStatus");
		}
		if(criteria.getContainsCompleted()){
			snippet.append("or ob.c_status = :completedStatus");
		}else{
			snippet.append("and ob.c_status <> :completedStatus");
		}
		snippet.append(")");
		
		map.put("defaultStatus", Order.STATUS_DEFAULT);
		map.put("completedStatus", Order.STATUS_COMPLETED);
		
		if(criteria.getContainsClosed()){
			snippet.append("or ob.c_canceled_status = :closedStatus");
			map.put("closedStatus", Order.CAN_STATUS_CLOSED);
		}
		if(criteria.getContainsCanceled()){
			snippet.append("or ob.c_canceled_status = :canceledStatus");
			map.put("canceledStatus", Order.CAN_STATUS_CANCELED);
		}
		if(criteria.getContainsMiss()){
			snippet.append("or ob.c_canceled_status = :missStatus");
			map.put("missStatus", Order.CAN_STATUS_MISS);
		}
		
		
		return map;
	}

	@SuppressWarnings("unchecked")
	private List<CanteenDeliveryOrderWaresItem> getOrderWaresItemList(
			Set<Long> orderIdSet) {
		String sql = 
				"	select p.order_id, p.c_name, p.wares_id, p.delivery_wares_id, count(p.id) p_count, p.c_price_unit" +
				"	from t_product_base p" +
				"	where p.order_id in (:orderIds)" +
				"	group by p.order_id, p.delivery_wares_id";
		
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameterList("orderIds", orderIdSet);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(CanteenDeliveryOrderWaresItem.class));
		return query.list();
	}
	
	@Override
	public Integer amountDelivery(long deliveryId) {
		String sql = "select sum(o.c_total_price) from t_order_base o where o.delivery_id = :deliveryId and o.c_canceled_status is null";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("deliveryId", deliveryId);
		return FormatUtils.toInteger(query.uniqueResult());
	}
	
	
	@Override
	public void setOrderStatus(List<Long> orderIds, int orderStatus) {
		String sql = "update t_order_base set c_status = :status where c_canceled_status is null and id in (:orderIds)";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("status", orderStatus);
		query.setParameterList("orderIds", orderIds);
		query.executeUpdate();
	}
	
	@Override
	public void setOrderProductsStatus(List<Long> orderIds, int productStatus) {
		String sql = "update t_product_base set c_status = :status where c_canceled_status is null and order_id in (:orderIds)";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("status", productStatus);
		query.setParameterList("orderIds", orderIds);
		query.executeUpdate();
	}
	
	@Override
	public void setOrderCancelStatus(Long orderId, String cancelStatus) {
		String sql = "update t_order_base set c_canceled_status = :cancelStatus where id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("orderId", orderId);
		query.setParameter("cancelStatus", cancelStatus, StandardBasicTypes.STRING);
		query.executeUpdate();
	}
	
	@Override
	public void setOrderProductsCancelStatus(Long orderId, String cancelStatus) {
		String sql = "update t_product_base set c_canceled_status = :cancelStatus where order_id = :orderId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("orderId", orderId);
		query.setParameter("cancelStatus", cancelStatus, StandardBasicTypes.STRING);
		query.executeUpdate();
	}
	
	
	
}
