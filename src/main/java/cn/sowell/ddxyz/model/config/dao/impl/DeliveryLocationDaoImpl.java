package cn.sowell.ddxyz.model.config.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.config.dao.DeliveryLocationDao;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryLocationCriteria;

@Repository
public class DeliveryLocationDaoImpl implements DeliveryLocationDao {
	
	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainLocation> getPlainLocationPageList(DeliveryLocationCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String hql = "from PlainLocation @mainWhere order by createTime desc";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		DeferedParamSnippet mainWhere = dQuery.createConditionSnippet("mainWhere");
		if(criteria.getCode() != null && StringUtils.hasText(criteria.getCode())){
			mainWhere.append("and code like :code");
			dQuery.setParam("code", "%" + criteria.getCode() + "%", StandardBasicTypes.STRING);
		}
		if(criteria.getName() != null && StringUtils.hasText(criteria.getName())){
			mainWhere.append("and name like :name");
			dQuery.setParam("name", "%" + criteria.getName() + "%", StandardBasicTypes.STRING);
		}
		if(criteria.getAddress() != null && StringUtils.hasText(criteria.getAddress())){
			mainWhere.append("and address like :address");
			dQuery.setParam("address", "%" + criteria.getAddress() + "%", StandardBasicTypes.STRING);
		}
		
		Query countQuery = dQuery.createQuery(session, false, new WrapForCountFunction());
		int count = FormatUtils.toInteger(countQuery.uniqueResult());
		if(count > 0){
			pageInfo.setCount(count);
			Query query = dQuery.createQuery(session, false, null);
			QueryUtils.setPagingParamWithCriteria(query, pageInfo);
			return query.list();
		}
		return new ArrayList<PlainLocation>();
	}

	@Override
	public PlainLocation getPlainLocaitionById(Long locationId) {
		return sFactory.getCurrentSession().get(PlainLocation.class, locationId);
	}

	@Override
	public void savePlainLocation(PlainLocation location) {
		sFactory.getCurrentSession().save(location);
	}

	@Override
	public boolean checkLocationCode(String code) {
		Session session = sFactory.getCurrentSession();
		String sql = "select count(l.id) from t_delivery_location l where l.c_code = :code";
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("code", code);
		Integer count = FormatUtils.toInteger(query.uniqueResult());
		if(count > 0){
			return false;
		}
		return true;
	}

	@Override
	public void deletePlainLocation(Long locationId) {
		Session session = sFactory.getCurrentSession();
		String sql = "delete from t_delivery_location where id = :locationId";
		SQLQuery query = session.createSQLQuery(sql);
		query.setLong("locationId", locationId);
		query.executeUpdate();
	}

}
