package cn.sowell.ddxyz.model.kanteen.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenOrderDao;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenOrderStatCriteria;

@Repository
public class KanteenOrderDaoImpl implements KanteenOrderDao{
	
	@Resource
	SessionFactory sFactory;
	
	@Override
	public Integer statCount(KanteenOrderStatCriteria criteria) {
		String sql = "select count(o.id) from t_order_kanteen o where o.id is not null @condition";
		SQLQuery query = buildStatQuery(sql, criteria);
		return FormatUtils.toInteger(query.uniqueResult());
	}

	private SQLQuery buildStatQuery(String sql, KanteenOrderStatCriteria criteria) {
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		DeferedParamSnippet conditionSnippet = dQuery.createSnippet("condition", null);
		
		if(criteria.getDeliveryId() != null){
			conditionSnippet.append("and o.delivery_id = :deliveryId");
			dQuery.setParam("deliveryId", criteria.getDeliveryId());
		}else if(criteria.getDistributionId() != null){
			conditionSnippet.append("and o.distribution_id = :distributionId");
			dQuery.setParam("distributionId", criteria.getDistributionId());
		}
		
		if(criteria.getAllCanceled()){
			conditionSnippet.append("and o.c_canceled_status is not null");
		}else{
			if(criteria.getCanceledStatus() != null && !criteria.getCanceledStatus().isEmpty()){
				conditionSnippet.append("and o.c_canceled_status in (:canceledStatus)");
				dQuery.setParam("canceledStatus", criteria.getCanceledStatus());
			}
		}
		if(criteria.getStatus() != null && !criteria.getStatus().isEmpty()){
			conditionSnippet.append("and o.c_status in (:status)");
			dQuery.setParam("status", criteria.getStatus());
		}
		return dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
	}

	@Override
	public Integer statOrderAmount(KanteenOrderStatCriteria criteria) {
		String sql = "select COALESCE(sum(o.c_total_price), 0) from t_order_kanteen o where o.id is not null @condition";
		SQLQuery query = buildStatQuery(sql, criteria);
		return FormatUtils.toInteger(query.uniqueResult());
	}
	
}
