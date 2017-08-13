package cn.sowell.ddxyz.model.canteen.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.canteen.dao.CanteenManageDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWeekTableCriteria;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrderWaresItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenDeliveryOrdersItem;
import cn.sowell.ddxyz.model.canteen.pojo.item.CanteenWeekTableItem;

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
						"	ob.delivery_id = :deliveryId" +
						" GROUP BY" +
						"	p.delivery_wares_id, co.order_id";
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
			long deliveryId, CommonPageInfo pageInfo) {
		String sql = 
				"	select " +
				"	co.order_id," +
				"	ob.c_order_code," +
				"	ob.c_receiver_name," +
				"	co.c_depart," +
				"	ob.c_receiver_contact," +
				"	ob.c_total_price" +
				"	from t_canteen_order co " +
				"	left join t_order_base ob on co.order_id = ob.id" +
				"	where ob.delivery_id = :deliveryId" +
				"	order by co.c_depart asc";
		
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		dQuery.setParam("deliveryId", deliveryId);
		
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

	@SuppressWarnings("unchecked")
	private List<CanteenDeliveryOrderWaresItem> getOrderWaresItemList(
			Set<Long> orderIdSet) {
		String sql = 
				"	select p.order_id, p.c_name, p.wares_id, p.delivery_wares_id, count(p.id) p_count" +
				"	from t_product_base p" +
				"	where p.order_id in (:orderIds)" +
				"	group by p.order_id, p.delivery_wares_id";
		
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameterList("orderIds", orderIdSet);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(CanteenDeliveryOrderWaresItem.class));
		return query.list();
	}
}
