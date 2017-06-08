package cn.sowell.ddxyz.model.merchant.dao.impl;

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
import cn.sowell.ddxyz.model.merchant.dao.MerchantDisabledRuleDao;
import cn.sowell.ddxyz.model.merchant.pojo.MerchantDisabledRule;

@Repository
public class MerchantDisabledRuleDaoImpl implements MerchantDisabledRuleDao{
	
	@Resource
	SessionFactory sFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantDisabledRule> getRules(Long merchantId, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String hql = "from MerchantDisabledRule r @mainWhere order by r.createTime desc";
		
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		DeferedParamSnippet mainWhere = dQuery.createConditionSnippet("mainWhere");
		
		if(merchantId != null){
			mainWhere.append("and r.merchantId = :merchantId");
			dQuery.setParam("merchantId", merchantId, StandardBasicTypes.LONG);
		}
		if(pageInfo == null){
			Query query = dQuery.createQuery(session, false, null);
			return query.list();
		}else{
			Query countQuery = dQuery.createQuery(session, false, new WrapForCountFunction());
			int count = FormatUtils.toInteger(countQuery.uniqueResult());
			if(count > 0){
				pageInfo.setCount(count);
				Query query = dQuery.createQuery(session, false, null);
				QueryUtils.setPagingParamWithCriteria(query, pageInfo);
				return query.list();
			}
			return new ArrayList<MerchantDisabledRule>();
		}
	}

	@Override
	public void saveMerchantDisabledRule(MerchantDisabledRule merchantDisabledRule) {
		sFactory.getCurrentSession().save(merchantDisabledRule);
	}

	@Override
	public void deleteMerchantDisabledRule(MerchantDisabledRule merchantDisabledRule) {
		sFactory.getCurrentSession().delete(merchantDisabledRule);
	}

}
