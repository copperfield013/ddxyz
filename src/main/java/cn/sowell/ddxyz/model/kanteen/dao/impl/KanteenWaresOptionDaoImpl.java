package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresOptionDao;

@Repository
public class KanteenWaresOptionDaoImpl implements KanteenWaresOptionDao{

	@Resource
	SessionFactory sFactory;

	@Override
	public Map<Long, Integer> queryEnabledOptionCount(Set<Long> waresIdSet) {
		return QueryUtils.queryMap(
				"	SELECT" +
				"		og.wares_id," +
				"		count(o.id) wares_count" +
				"	FROM" +
				"		t_wares_optiongroup og" +
				"	LEFT JOIN t_wares_option o ON og.id = o.optiongroup_id" +
				"	WHERE" +
				"		og.wares_id IN (:waresIds)" +
				"	AND og.c_deleted IS NULL" +
				"	AND o.c_deleted IS NULL" +
				"	and og.c_disabled is null " +
				"	and o.c_disabled is null" +
				"	group by og.wares_id", 
				sFactory.getCurrentSession(), 
				mw->mw.getLong("wares_id"), mw->mw.getInteger("wares_count"), 
				dQuery->dQuery.setParam("waresIds", waresIdSet, StandardBasicTypes.LONG));
	}

	@Override
	public Map<Long, Integer> queryEnabledOptionGroupCount(Set<Long> waresIdSet) {
		return QueryUtils.queryMap(
				"	select " +
				"		og.wares_id," +
				"		count(og.id) group_count" +
				"	from t_wares_optiongroup og" +
				"	WHERE" +
				"		og.wares_id IN (:waresIds)" +
				"	AND og.c_deleted IS NULL" +
				"	and og.c_disabled is null " +
				"	group by og.wares_id", 
				sFactory.getCurrentSession(), 
				mw->mw.getLong("wares_id"), mw->mw.getInteger("group_count"), 
				dQuery->dQuery.setParam("waresIds", waresIdSet, StandardBasicTypes.LONG));
	}

}
