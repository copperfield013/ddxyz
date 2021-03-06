package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.KeyValueMapResultTransformer;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDeliveryDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDeliveryCriteria;

@Repository
public class KanteenDeliveryDaoImpl implements KanteenDeliveryDao{

	@Resource
	SessionFactory sFactory;
	
	@Override
	public List<PlainKanteenDelivery> queryDeliveries(
			KanteenDeliveryCriteria criteria, PageInfo pageInfo) {
		return QueryUtils.pagingQuery(
				"from PlainKanteenDelivery d where d.merchantId = :merchantId @condition order by d.updateTime desc", 
				sFactory.getCurrentSession(), pageInfo, 
				dQuery->{
					dQuery.setParam("merchantId", criteria.getMerchantId());
					DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
					if(criteria.getDistributionId() != null){
						snippet.append("and d.distributionId = :distributionId");
						dQuery.setParam("distributionId", criteria.getDistributionId());
					}
		});
	}
	
	@Override
	public Map<Long, Integer> getDeliveryOrderCountMap(Set<Long> deliveryIds) {
		if(deliveryIds != null && !deliveryIds.isEmpty()){
			String sql = "	SELECT" +
					"		o.delivery_id," +
					"		count(o.id) order_count" +
					"	FROM t_order_base o" +
					"	WHERE o.c_status is not null and o.c_status <> :defaultStatus and o.c_canceled_status is null and o.delivery_id IN (:deliveryIds)" +
					"	GROUP BY o.delivery_id";
			
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			
			query.setParameterList("deliveryIds", deliveryIds, StandardBasicTypes.LONG);
			query.setString("defaultStatus", PlainKanteenOrder.STATUS_DEFAULT);
			KeyValueMapResultTransformer<Long, Integer> transfer = KeyValueMapResultTransformer.build(wrapper->wrapper.getLong("delivery"), wrapper->wrapper.getInteger("order_count"));
			query.setResultTransformer(transfer);
			query.list();
			return transfer.getMap();
		}else{
			return new HashMap<Long, Integer>();
		}
		
	}

	@Override
	public String getLastCode(Long merchantId) {
		String sql = "select d.c_code from t_delivery_base d where d.merchant_id = :merchantId and d.c_code is not null order by d.create_time desc";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("merchantId", merchantId);
		query.setMaxResults(1);
		return FormatUtils.toString(query.uniqueResult());
	}
	
}
