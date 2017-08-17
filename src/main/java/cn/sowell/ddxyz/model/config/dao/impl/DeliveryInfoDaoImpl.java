package cn.sowell.ddxyz.model.config.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.config.dao.DeliveryInfoDao;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryInfoCriteria;

@Repository
public class DeliveryInfoDaoImpl implements DeliveryInfoDao {
	
	@Resource
	SessionFactory sFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDelivery> getPlainDeilveryPageList(DeliveryInfoCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String sql = "select * from t_delivery_base d @mainWhere order by d.c_time_point desc, d.location_id desc";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		DeferedParamSnippet mainWhere = dQuery.createConditionSnippet("mainWhere");
		if(criteria.getDeliveryTime() != null){
			mainWhere.append("and DATE_FORMAT(d.c_time_point, '%Y-%m-%d') = :deliveryTime");
			dQuery.setParam("deliveryTime", criteria.getDeliveryTime(), StandardBasicTypes.DATE );
		}
		if(criteria.getLocationName() != null){
			mainWhere.append("and d.c_location_name like :locationName");
			dQuery.setParam("locationName", "%" + criteria.getLocationName() + "%", StandardBasicTypes.STRING);
		}
		
		if(pageInfo == null){
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainDelivery.class));
			return query.list();
		}else{
			SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
			int count = FormatUtils.toInteger(countQuery.uniqueResult());
			if(count > 0){
				pageInfo.setCount(count);
				SQLQuery query = dQuery.createSQLQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainDelivery.class));
				return query.list();
			}
			return new ArrayList<PlainDelivery>();
		}
	}

}
