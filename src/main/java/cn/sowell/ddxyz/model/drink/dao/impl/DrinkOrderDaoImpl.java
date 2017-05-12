package cn.sowell.ddxyz.model.drink.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HeaderElementIterator;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.sowell.copframe.dao.deferedQuery.ColumnMapResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.SimpleMapWrapper;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.dao.DrinkOrderDao;
import cn.sowell.ddxyz.model.drink.pojo.criteria.OrderCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.OrderStatisticsListItem;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

@Repository
public class DrinkOrderDaoImpl implements DrinkOrderDao {
	
	@Resource
	SessionFactory sFactory;

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId) {
		Session session = sFactory.getCurrentSession();
		String sql = "SELECT "
				+ "d.id drink_product_id, "
				+ "d.drink_type_id drink_type_id,"
				+ "d.c_drink_type_name,"
				+ "p.c_price, "
				+ "d.tea_addition_id,"
				+ "d.c_tea_addition_name, "
				+ "d.c_sweetness, "
				+ "d.c_heat, "
				+ "d.c_cup_size "
				+ "FROM "
				+ "t_product_base p, t_drink_product d "
				+ "WHERE p.id = d.product_id "
				+ "AND p.order_id = :orderId";
		SQLQuery query = session.createSQLQuery(sql);
		query.setLong("orderId", orderId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainOrderDrinkItem.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainOrder> getOrderList(OrderCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String hql = "from PlainOrder @mainWhere order by payTime desc";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		DeferedParamSnippet mainWhere = dQuery.createConditionSnippet("mainWhere");
		if(criteria.getStartTime() != null){
			mainWhere.append("and payTime >= :startTime");
			dQuery.setParam("startTime", criteria.getStartTime(), StandardBasicTypes.TIMESTAMP);
		}
		if(criteria.getEndTime() !=null){
			mainWhere.append("and payTime < :endTime");
			dQuery.setParam("endTime", criteria.getEndTime(), StandardBasicTypes.TIMESTAMP);
		}
		if(criteria.getUserId() != null){
			mainWhere.append("and orderUserId = :userId");
			dQuery.setParam("userId", criteria.getUserId(), StandardBasicTypes.LONG);
		}
		if(StringUtils.hasText(criteria.getOrderCode())){
			mainWhere.append("and orderCode like :orderCode");
			dQuery.setParam("orderCode", "%" + criteria.getOrderCode(), StandardBasicTypes.STRING);
		}
		if(criteria.getTimePoint() != null && !criteria.getTimePoint().equals("")){
			mainWhere.append("and DATE_FORMAT(timePoint,'%H:%i:%s') = :timePoint");
			dQuery.setParam("timePoint", criteria.getTimePoint()+":00:00", StandardBasicTypes.STRING);
		}
		if(StringUtils.hasText(criteria.getLocationName())){
			mainWhere.append("and locationName like :locationName");
			dQuery.setParam("locationName", "%" + criteria.getLocationName() + "%", StandardBasicTypes.STRING);
		}
		
		if(pageInfo == null){
			Query query = dQuery.createQuery(session, false, null);
			return query.list();
		}else{
			Query countQuery = dQuery.createQuery(session, true, new WrapForCountFunction());
			Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
			if(count > 0){
				pageInfo.setCount(count);
				Query query = dQuery.createQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				return query.list();
			}
			return new ArrayList<PlainOrder>();
		}
	}
	
	@SuppressWarnings({ "serial" })
	@Override
	public Map<Long, Integer> getOrderCupCount(List<Long> orderIdList) {
		Session session = sFactory.getCurrentSession();
		String sql = "select count(id) cup_count, p.order_id from t_product_base p @mainWhere group by p.order_id";
		
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		DeferedParamSnippet mainWhere = dQuery.createConditionSnippet("mainWhere");
		if(!orderIdList.isEmpty()){
			mainWhere.append("and p.order_id in (:orderIdList)");
			dQuery.setParam("orderIdList", orderIdList, StandardBasicTypes.LONG);
		}
		Query query = dQuery.createSQLQuery(session, false, null);
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		query.setResultTransformer(new ColumnMapResultTransformer<Byte>() {
			@Override
			protected Byte build(SimpleMapWrapper mapWrapper) {
				Long orderId = mapWrapper.getLong("order_id");
				Integer cupCount = mapWrapper.getInteger("cup_count");
				result.put(orderId, cupCount);
				return 1;
			}
		});
		query.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderStatisticsListItem> statisticsOrder(OrderCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String sql = "SELECT "
				+ "SUM(o.c_total_price) income, "
				+ "DATE(o.c_pay_time) order_date, "
				+ "COUNT(o.id) order_count, "
				+ "COUNT(p.id) cup_count "
				+ "FROM "
				+ "t_order_base o, t_product_base p "
				+ "WHERE "
				+ "o.c_status <> 0 "
				+ "AND o.c_canceled_status IS NULL "
				+ "AND o.id = p.order_id @mainWhere "
				+ "GROUP BY order_date "
				+ "ORDER BY	order_date DESC";
		
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		DeferedParamSnippet mainWhere = dQuery.createSnippet("mainWhere", null);
		if(criteria.getStartTime() != null){
			mainWhere.append("and o.c_pay_time >= :startTime");
			dQuery.setParam("startTime", criteria.getStartTime(), StandardBasicTypes.TIMESTAMP);
		}
		if(criteria.getEndTime() !=null){
			mainWhere.append("and o.c_pay_time < :endTime");
			dQuery.setParam("endTime", criteria.getEndTime(), StandardBasicTypes.TIMESTAMP);
		}
		if(pageInfo == null){ //查询全部
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(OrderStatisticsListItem.class));
			return query.list();
		}else{ //分页查询
			SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
			Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
			if(count > 0){
				pageInfo.setCount(count);
				SQLQuery query = dQuery.createSQLQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(OrderStatisticsListItem.class));
				return query.list();
			}
			return new ArrayList<OrderStatisticsListItem>();
		}
	}
	
}
