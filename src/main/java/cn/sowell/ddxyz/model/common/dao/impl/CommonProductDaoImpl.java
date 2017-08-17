package cn.sowell.ddxyz.model.common.dao.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.common.dao.CommonProductDao;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.common.pojo.criteria.ProductCriteria;

@Repository
public class CommonProductDaoImpl implements CommonProductDao{

	@Resource
	SessionFactory sFactory;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainProduct> getProducts(ProductCriteria criteria) {
		String hql = "from PlainProduct p";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		Set<Long> orderIdRange = criteria.getOrderIdRange();
		//产品所在订单的条件
		if(orderIdRange != null && !orderIdRange.isEmpty()){
			dQuery.appendCondition("and p.orderId in (:orderIds)")
					.setParam("orderIds", orderIdRange, StandardBasicTypes.LONG);
		}
		Integer maxStatus = criteria.getProductStatusRangeMax(),
				minStatus = criteria.getProductStatusRangeMin();
		if(maxStatus != null && minStatus != null && maxStatus < minStatus){
			dQuery.appendCondition("and (p.status >= :minStatus or p.status <= :maxStatus)")
				.setParam("minStatus", minStatus)
				.setParam("maxStatus", maxStatus);
		}else{
			//产品状态的最小值条件
			if(minStatus != null){
				dQuery.appendCondition("and p.status >= :minStatus")
				.setParam("minStatus", minStatus);
			}
			//产品状态的最大值条件
			if(maxStatus != null){
				dQuery.appendCondition("and p.status <= :maxStatus")
				.setParam("maxStatus", maxStatus);
			}
		}
		
		//产品是否取消条件
		if(!criteria.getCanceled()){
			dQuery.appendCondition("and p.canceledStatus is null");
		}else{
			dQuery.appendCondition("and p.canceledStatus is not null");
		}
		//产品的取消状态条件
		if(criteria.getCanceledStatus() != null){
			dQuery.appendCondition("and p.canceledStatus = :canceledStatus")
					.setParam("canceledStatus", criteria.getCanceledStatus());
		}
		
		Query query = dQuery.createQuery(sFactory.getCurrentSession(), true, null);
		return query.list();
	}
	
	@Override
	public String getDrinkThumbUri(Long drinkTypeId) {
		String sql = "select dt.c_pic_uri from t_drink_type dt where dt.id = :drinkTypeId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		return FormatUtils.toString(query.setLong("drinkTypeId", drinkTypeId).uniqueResult());
	}
	
}
