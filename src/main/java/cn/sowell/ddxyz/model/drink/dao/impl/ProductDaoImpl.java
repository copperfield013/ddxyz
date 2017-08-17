package cn.sowell.ddxyz.model.drink.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.date.FrameDateFormat;
import cn.sowell.ddxyz.model.common.core.Order;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.drink.dao.ProductDao;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductCriteria;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Resource
	SessionFactory sFactory;
	
	@Resource
	FrameDateFormat fdFormat;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoItem> getProductInfoItemPageList(ProductionCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String sql = "SELECT "
				+ "o.c_order_code, "
				+ "o.c_time_point, "
				+ "o.c_receiver_contact, "
				+ "o.c_location_name, "
				+ "o.create_time, "
				+ "o.c_pay_time, "
				+ "d.id drink_product_id, "
				+ "d.c_drink_type_name, "
				+ "d.c_tea_addition_name, "
				+ "d.c_sweetness, "
				+ "d.c_heat, "
				+ "d.c_cup_size, "
				+ "p.id product_id, "
				+ "p.c_status product_status "
				+ " From "
				+ "t_order_base o, t_drink_product d, t_product_base p "
				+ "WHERE p.id = d.product_id "
				+ "AND o.id = p.order_id "
				+ "AND o.c_canceled_status is null "
				+ "@mainWhere "
				+ "order by o.c_pay_time asc";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		DeferedParamSnippet mainWhere = dQuery.createSnippet("mainWhere", null);
		mainWhere.append("and o.c_status = :paiedStatus");
		dQuery.setParam("paiedStatus", Order.STATUS_PAYED);
		if(criteria.getStartTime() != null){
			mainWhere.append("AND o.c_pay_time >= :startTime");
			dQuery.setParam("startTime", criteria.getStartTime(), StandardBasicTypes.TIMESTAMP);
		}
		if(criteria.getEndTime() != null){
			mainWhere.append("AND o.c_pay_time < :endTime");
			dQuery.setParam("endTime", criteria.getEndTime(), StandardBasicTypes.TIMESTAMP);
		}
		if(criteria.getTimePoint() != null && !criteria.getTimePoint().equals("")){
			mainWhere.append("AND DATE_FORMAT(o.c_time_point,'%H:%i:%s') = :timePoint");
			dQuery.setParam("timePoint", criteria.getTimePoint() + ":00:00", StandardBasicTypes.STRING);
		}
		if(criteria.getProductStatus() != null){
			mainWhere.append("and p.c_status = :productStatus");
			dQuery.setParam("productStatus", criteria.getProductStatus());
		}
		
		if(pageInfo == null && criteria.getPrintCount() == null){ //查询全部
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(ProductInfoItem.class));
			return query.list();
		}else if(pageInfo == null && criteria.getPrintCount() != null){ //查询指定条数
			CommonPageInfo commonPageInfo = new CommonPageInfo();
			commonPageInfo.setPageSize(criteria.getPrintCount());
			commonPageInfo.setPageNo(1);
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			QueryUtils.setPagingParamWithCriteria(query, commonPageInfo);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(ProductInfoItem.class));
			return query.list();
		}else{ //分页查询
			SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
			Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
			pageInfo.setCount(count);
			if(count > 0){
				SQLQuery query = dQuery.createSQLQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(ProductInfoItem.class));
				return query.list();
			}
			return new ArrayList<ProductInfoItem>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoItem> getProductInfoItemListByProductIds(List<Long> productIdList) {
		Session session  = sFactory.getCurrentSession();
		String sql = "SELECT "
				+ "o.c_order_code, "
				+ "o.c_time_point, "
				+ "o.c_receiver_contact, "
				+ "o.c_location_name, "
				+ "o.create_time, "
				+ "d.id drink_product_id, "
				+ "d.c_drink_type_name, "
				+ "d.c_tea_addition_name, "
				+ "d.c_sweetness, "
				+ "d.c_heat, "
				+ "d.c_cup_size, "
				+ "p.c_price, "
				+ "p.id product_id, "
				+ "p.c_status product_status, "
				+ "p.c_dispense_code "
				+ " From "
				+ "t_order_base o, t_drink_product d, t_product_base p "
				+ "WHERE p.id = d.product_id "
				+ "AND o.id = p.order_id "
				+ "AND d.id in (:productIds)"
				+ "order by o.c_pay_time asc";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameterList("productIds", productIdList);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(ProductInfoItem.class));
		return query.list();
	}

	/**
	 * 根据状态查找已打印产品的数目
	 */
	@Override
	public int getProductPrintedCountByStatus(Integer status, Date timePoint) {
		Session session = sFactory.getCurrentSession();
		String sql = "SELECT count(p.id) FROM t_product_base p, t_order_base o WHERE p.order_id = o.id AND o.c_time_point = :timePoint AND p.c_status > :status";
		SQLQuery query = session.createSQLQuery(sql);
		query.setDate("timePoint", timePoint);
		query.setInteger("status", status);
		return ((Number)query.uniqueResult()).intValue();
	}

	@Override
	public int getProductNotPrintCountByStatus(Integer status, Date date) {
		Session session = sFactory.getCurrentSession();
		String sql = "SELECT count(p.id) FROM t_product_base p, t_order_base o WHERE p.order_id = o.id AND o.c_time_point = :timePoint AND p.c_status = :status";
		SQLQuery query = session.createSQLQuery(sql);
		query.setDate("timePoint", date);
		query.setInteger("status", status);
		return ((Number)query.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoItem> getProductInfoItemPageList(ProductCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String sql = "SELECT "
				+ "o.c_order_code, "
				+ "o.c_time_point, "
				+ "o.c_receiver_contact, "
				+ "o.c_location_name, "
				+ "o.create_time, "
				+ "o.c_pay_time, "
				+ "d.id drink_product_id, "
				+ "d.c_drink_type_name, "
				+ "d.c_tea_addition_name, "
				+ "d.c_sweetness, "
				+ "d.c_heat, "
				+ "d.c_cup_size, "
				+ "p.id product_id, "
				+ "p.c_status product_status,"
				+ "p.c_dispense_key "
				+ " From "
				+ "t_order_base o, t_drink_product d, t_product_base p "
				+ "WHERE p.id = d.product_id "
				+ "AND o.id = p.order_id "
				+ "AND o.c_canceled_status is null "
				+ "@mainWhere "
				+ "order by o.c_pay_time asc";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		DeferedParamSnippet mainWhere = dQuery.createSnippet("mainWhere", null);
		/*mainWhere.append("and o.c_status = :paiedStatus");
		dQuery.setParam("paiedStatus", Order.STATUS_PAYED);*/
		if(criteria.getPayTimeStr() != null && !criteria.getPayTimeStr().equals("")){
			mainWhere.append("AND o.c_pay_time >= :startTime AND o.c_pay_time <= :endTime");
			dQuery.setParam("startTime", criteria.getPayTimeStr() + " 00:00:00", StandardBasicTypes.STRING);
			dQuery.setParam("endTime", criteria.getPayTimeStr() + " 23:59:59", StandardBasicTypes.STRING);
		}
		if(criteria.getTimePoint() != null && !criteria.getTimePoint().equals("")){
			mainWhere.append("AND DATE_FORMAT(o.c_time_point,'%H:%i:%s') = :timePoint");
			dQuery.setParam("timePoint", criteria.getTimePoint() + ":00:00", StandardBasicTypes.STRING);
		}
		if(criteria.getLocationId() != null){
			mainWhere.append("AND o.location_id = :locationId");
			dQuery.setParam("locationId", criteria.getLocationId(), StandardBasicTypes.LONG);
		}
		
		if(criteria.getPrintStatus() != null){
			if(criteria.getPrintStatus() == 0){
				mainWhere.append("AND p.c_status = :productStatus");
			}else if(criteria.getPrintStatus() == 1){
				mainWhere.append("AND p.c_status > :productStatus");
			}
			dQuery.setParam("productStatus", 1, StandardBasicTypes.INTEGER);
		}
		
		if(criteria.getOrderCode() != null && !criteria.getOrderCode().trim().equals("")){
			mainWhere.append("AND o.c_order_code like :orderCode");
			dQuery.setParam("orderCode", "%" + criteria.getOrderCode() + "%", StandardBasicTypes.STRING);
		}
		
		if(pageInfo == null){ //查询全部
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(ProductInfoItem.class));
			return query.list();
		}else{ //分页查询
			SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
			Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
			pageInfo.setCount(count);
			if(count > 0){
				SQLQuery query = dQuery.createSQLQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(ProductInfoItem.class));
				return query.list();
			}
			return new ArrayList<ProductInfoItem>();
		
		}
	}

	@Override
	public PlainProduct getProductionByDispenseCode(String dispenseCode) {
		String hql = "from PlainProduct p where p.dispenseCode = :dispenseCode";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setString("dispenseCode", dispenseCode);
		return (PlainProduct) query.uniqueResult();
	}
	
}
