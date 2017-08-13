package cn.sowell.ddxyz.model.canteen.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.model.canteen.dao.CanteenWaresDao;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWaresListCriteria;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Repository
public class CanteenWaresDaoImpl implements CanteenWaresDao{

	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainWares> getCanteenWaresList(CanteenWaresListCriteria criteria, CommonPageInfo pageInfo) {
		String hql = "from PlainWares w where w.merchantId = :merchantId";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		
		dQuery.setParam("merchantId", DdxyzConstants.CANTEEN_MERCHANT_ID);
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), false, new WrapForCountFunction());
		int count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0) {
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), false, null);
			return query.list();
		}
		return new ArrayList<PlainWares>();
		
	}

	@Override
	public <T> T getPlainObject(Class<T> objClass, Long id) {
		return sFactory.getCurrentSession().get(objClass, id);
	}
	
	@Override
	public void save(Object obj) {
		sFactory.getCurrentSession().save(obj);
	}

	@Override
	public void update(Object origin) {
		sFactory.getCurrentSession().update(origin);
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
