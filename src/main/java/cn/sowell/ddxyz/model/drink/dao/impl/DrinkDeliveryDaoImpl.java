package cn.sowell.ddxyz.model.drink.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.sowell.copframe.dao.deferedQuery.ColumnMapResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.SimpleMapWrapper;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.dao.DrinkDeliveryDao;
import cn.sowell.ddxyz.model.drink.pojo.criteria.DeliveryCriteria;

@Repository
public class DrinkDeliveryDaoImpl implements DrinkDeliveryDao {
	

	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainOrder> getOrderList(DeliveryCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String sql = "select "
				+ "o.id, o.c_order_code, o.c_time_point, o.c_receiver_contact, o.c_location_name, o.create_time, o.c_pay_time"
				+ " from t_order_base o where o.c_status <> 0 @mainWhere order by o.c_pay_time asc";
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
		if(criteria.getTimePoint() != null && !criteria.getTimePoint().equals("")){
			mainWhere.append("and DATE_FORMAT(o.c_time_point,'%H:%i:%s') = :timePoint");
			dQuery.setParam("timePoint", criteria.getTimePoint()+":00:00", StandardBasicTypes.STRING);
		}
		if(StringUtils.hasText(criteria.getOrderCode())){
			mainWhere.append("and o.c_order_code like :orderCode");
			dQuery.setParam("orderCode", "%" + criteria.getOrderCode() + "%");
		}
		if(StringUtils.hasText(criteria.getLocationName())){
			mainWhere.append("and o.c_location_name like :locationName");
			dQuery.setParam("locationName", "%" + criteria.getLocationName() + "%");
		}
		
		
		if(pageInfo == null){ //查询全部
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			buildPlainOrder(query);
			return query.list();
		}else{ //分页查询
			SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
			Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
			if(count > 0){
				pageInfo.setCount(count);
				SQLQuery query = dQuery.createSQLQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				buildPlainOrder(query);
				return query.list();
			}
			return new ArrayList<PlainOrder>();
		}
	}
	
	@SuppressWarnings("serial")
	private void buildPlainOrder(SQLQuery query){
		query.setResultTransformer(new ColumnMapResultTransformer<PlainOrder>() {
			@Override
			protected PlainOrder build(SimpleMapWrapper mapWrapper) {
				PlainOrder plainOrder = new PlainOrder();
				plainOrder.setId(mapWrapper.getLong("id"));
				plainOrder.setOrderCode(mapWrapper.getString("c_order_code"));
				plainOrder.setTimePoint(mapWrapper.getDate("c_time_point"));
				plainOrder.setReceiverContact(mapWrapper.getString("c_receiver_contact"));
				plainOrder.setLocationName(mapWrapper.getString("c_location_name"));
				plainOrder.setCreateTime(mapWrapper.getDate("create_time"));
				plainOrder.setPayTime(mapWrapper.getDate("pay_time"));
				return plainOrder;
			}
		});
	}

}
