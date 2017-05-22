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

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;
import cn.sowell.ddxyz.model.config.dao.DeliveryPlanDao;

@Repository
public class DeliveryPlanDaoImpl implements DeliveryPlanDao{
	
	@Resource
	SessionFactory sFactory;

	@Override
	public void savePlan(PlainDeliveryPlan plan) {
		sFactory.getCurrentSession().save(plan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDeliveryPlan> getPlainDeliveryPlanPageList(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String hql = "from PlainDeliveryPlan p @mainWhere order by updateTime desc, createTime desc";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		DeferedParamSnippet mainWhere = dQuery.createConditionSnippet("mainWhere");
		if(criteria.getLocationName() != null && !criteria.getLocationName().trim().equals("")){
			mainWhere.append("and p.location.name like :locationName");
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
			return new ArrayList<PlainDeliveryPlan>();
		}
	}

	@Override
	public boolean changePlanDisabled(Long planId, Integer disabled) {
		Session session = sFactory.getCurrentSession();
		String sql = "update t_delivery_plan p set p.c_disabled = :disabled where p.id = :planId";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("disabled", disabled, StandardBasicTypes.INTEGER);
		query.setLong("planId", planId);
		int updated = query.executeUpdate();
		if(updated > 0){
			return true;
		}
		return false;
	}

}
