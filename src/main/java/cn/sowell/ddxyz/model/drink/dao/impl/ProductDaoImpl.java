package cn.sowell.ddxyz.model.drink.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.ColumnMapResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.SimpleMapWrapper;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.format.FrameDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.drink.dao.ProductDao;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Resource
	SessionFactory sFactory;
	
	@Resource
	FrameDateFormat fdFormat;
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ProductInfoItem> getProductInfoItemPageList(ProductionCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String sql = "SELECT "
				+ "o.c_order_code, o.c_time_point, o.c_receiver_contact, o.c_location_name, o.create_time, o.c_pay_time, "
				+ "d.id, d.c_drink_type_name, d.c_tea_addition_name, d.c_sweetness, d.c_heat, d.c_cup_size"
				+ " From "
				+ "t_order_base o, t_drink_product d, t_product_base p "
				+ "WHERE p.id = d.product_id "
				+ "AND o.id = p.order_id "
				+ "AND o.c_canceled_status is null "
				+ "AND p.c_status ='1' @mainWhere "
				+ "order by o.c_pay_time asc";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		DeferedParamSnippet mainWhere = dQuery.createSnippet("mainWhere", null);
		if(criteria.getStartTime() != null){
			mainWhere.append("AND o.create_time >= :startTime");
			dQuery.setParam("startTime", criteria.getStartTime(), StandardBasicTypes.TIMESTAMP);
		}
		if(criteria.getEndTime() != null){
			mainWhere.append("AND o.create_time <= :endTime");
			dQuery.setParam("endTime", criteria.getEndTime(), StandardBasicTypes.TIMESTAMP);
		}
		if(criteria.getTimePoint() != null && !criteria.getTimePoint().equals("")){
			mainWhere.append("AND DATE_FORMAT(o.c_time_point,'%H:%i:%s') = :timePoint");
			dQuery.setParam("timePoint", criteria.getTimePoint()+":00:00", StandardBasicTypes.STRING);
		}
		
		if(pageInfo == null && criteria.getPrintCount() == null){ //查询全部
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			buildProductInfoItem(query);
			return query.list();
		}else if(pageInfo == null && criteria.getPrintCount() != null){ //查询指定条数
			CommonPageInfo commonPageInfo = new CommonPageInfo();
			commonPageInfo.setPageSize(criteria.getPrintCount());
			commonPageInfo.setPageNo(1);
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			QueryUtils.setPagingParamWithCriteria(query, commonPageInfo);
			buildProductInfoItem(query);
			return query.list();
		}else{ //分页查询
			SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
			Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
			if(count > 0){
				pageInfo.setCount(count);
				SQLQuery query = dQuery.createSQLQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				buildProductInfoItem(query);
				return query.list();
			}
			return new ArrayList<ProductInfoItem>();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ProductInfoItem> getProductInfoItemListByProductIds(List<Long> productIdList) {
		Session session  = sFactory.getCurrentSession();
		String sql = "SELECT "
				+ "o.c_order_code, o.c_time_point, o.c_receiver_contact, o.c_location_name, o.create_time, "
				+ "d.id, d.c_drink_type_name, d.c_tea_addition_name, d.c_sweetness, d.c_heat, d.c_cup_size, p.c_price"
				+ " From "
				+ "t_order_base o, t_drink_product d, t_product_base p "
				+ "WHERE p.id = d.product_id "
				+ "AND o.id = p.order_id "
				+ "AND d.id in (:productIds)"
				+ "order by o.c_pay_time asc";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameterList("productIds", productIdList);
		buildProductInfoItem(query);
		return query.list();
	}


	@SuppressWarnings("serial")
	private void buildProductInfoItem(SQLQuery query){
		query.setResultTransformer(new ColumnMapResultTransformer<ProductInfoItem>() {
			@Override
			protected ProductInfoItem build(SimpleMapWrapper mapWrapper) {
				ProductInfoItem item = new ProductInfoItem();
				item.setOrderCode(mapWrapper.getString("c_order_code"));
				item.setTimePoint(mapWrapper.getDate("c_time_point"));
				item.setReceiverContent(mapWrapper.getString("c_receiver_contact"));
				item.setLocationName(mapWrapper.getString("c_location_name"));
				item.setOrderCreateTime(mapWrapper.getDate("create_time"));
				item.setPayTime(mapWrapper.getDate("c_pay_time"));
				item.setDrinkProductId(mapWrapper.getLong("id"));
				item.setDrinkName(mapWrapper.getString("c_drink_type_name"));
				item.setTeaAdditionName(mapWrapper.getString("c_tea_addition_name"));
				item.setSweetness(mapWrapper.getInteger("c_sweetness"));
				item.setHeat(mapWrapper.getInteger("c_heat"));
				item.setCupSize(mapWrapper.getInteger("c_cup_sie"));
				return item;
			}
		});
	}
}
