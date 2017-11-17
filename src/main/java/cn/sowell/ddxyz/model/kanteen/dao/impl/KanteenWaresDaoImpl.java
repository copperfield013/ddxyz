package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresDao;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresItem;

@Repository
public class KanteenWaresDaoImpl implements KanteenWaresDao {

	@Resource
	SessionFactory sFactory;
	
	
	@Override
	public List<KanteenWaresItem> queryWares(KanteenWaresCriteria criteria,
			PageInfo pageInfo) {
		return 
			QueryUtils.pagingSQLQuery("	SELECT" +
					"		w.id," +
					"		w.c_name," +
					"		w.c_base_price," +
					"		w.c_price_unit," +
					"		w.create_time," +
					"		w.c_disabled, w.c_thumb_uri" +
					"	FROM" +
					"		t_wares_base w" +
					"	WHERE" +
					"		w.merchant_id = :merchantId" + 
					"       @condition" + 
					"   order by w.create_time desc",
					KanteenWaresItem.class, 
					sFactory.getCurrentSession(), pageInfo, 
			dQuery->{
				dQuery.setParam("merchantId", criteria.getMerchantId());
				DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
				if(TextUtils.hasText(criteria.getWaresName())){
					snippet.append("and w.c_name like :waresName");
					dQuery.setParam("waresName", "%" + criteria.getWaresName() + "%");
				}
		});
	}


	@Override
	public void save(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}


	@Override
	public <T> T get(Class<T> pojoClass, Long pojoId) {
		return sFactory.getCurrentSession().get(pojoClass, pojoId);
	}


	@Override
	public void update(Object pojo) {
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void updateWaresSalable(Long waresId, boolean salable) {
		String sql = "update t_wares_base set c_unsalable = :unsalable where id = :waresId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("waresId", waresId);
		if(salable){
			query.setParameter("unsalable", null, StandardBasicTypes.INTEGER);
		}else{
			query.setInteger("unsalable", 1);
		}
		query.executeUpdate();
	}
	
	@Override
	public int disableWares(Long waresId, Boolean disabled) {
		String sql = "update t_wares_base set c_disabled = :disabled where id = :waresId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("waresId", waresId);
		if(disabled) {
			query.setInteger("disabled", 1);
		}else {
			query.setParameter("disabled", null, StandardBasicTypes.INTEGER);
		}
		return query.executeUpdate();
	}

}
