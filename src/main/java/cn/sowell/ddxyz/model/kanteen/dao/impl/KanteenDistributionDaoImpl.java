package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDistributionDao;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;

@Repository
public class KanteenDistributionDaoImpl implements KanteenDistributionDao{

	@Resource
	SessionFactory sFactory;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<KanteenDistributionItem> queryDistributions(Long merchantId, PageInfo pageInfo) {
		String sql = "	SELECT" +
				"		d.id d_id," +
				"		m.c_name menu_name," +
				"		d.c_start_time," +
				"		d.c_end_time," +
				"		count(del.id) delivery_count," +
				"		d.c_disabled" +
				"	FROM" +
				"		t_distribution_base d" +
				"	LEFT JOIN t_menu_base m ON d.menu_id = m.id" +
				"	left join t_delivery_base del on d.id = del.distribution_id" +
				"	where d.merchant_id = :merchantId" +
				"	group by d.id";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		
		dQuery.setParam("merchantId", merchantId);
		
		SQLQuery countQuery = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, new WrapForCountFunction());
		pageInfo.setCount(FormatUtils.toInteger(countQuery.uniqueResult()));
		if(pageInfo.getCount() > 0){
			SQLQuery query = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(KanteenDistributionItem.class));
			return query.list();
		}else{
			return new ArrayList<KanteenDistributionItem>();
		}
	}

}
