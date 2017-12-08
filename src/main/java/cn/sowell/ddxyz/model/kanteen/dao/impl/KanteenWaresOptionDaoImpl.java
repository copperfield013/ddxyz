package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresOptionDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOptionGroup;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenWaresOptionGroup> queryOptionGroups(Long waresId) {
		String hql = "from PlainKanteenWaresOptionGroup g where g.waresId = :waresId and g.deleted is null order by g.order asc";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("waresId", waresId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenWaresOption> queryOptions(Set<Long> groupIdSet) {
		if(groupIdSet != null && !groupIdSet.isEmpty()){
			String hql = "from PlainKanteenWaresOption o where o.optiongroupId in (:groupIds) and o.deleted is null order by o.order asc";
			Query query = sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("groupIds", groupIdSet, StandardBasicTypes.LONG);
			return query.list();
		}else{
			return new ArrayList<PlainKanteenWaresOption>();
		}
	}
	
	@Override
	public void deleteGroups(Set<Long> toRemoveGroupIds) {
		if(toRemoveGroupIds != null && toRemoveGroupIds.size() > 0){
			String sql = "update t_wares_optiongroup set c_deleted = 1 where id in (:groupIds)";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameterList("groupIds", toRemoveGroupIds);
			query.executeUpdate();
		}
	}
	
	@Override
	public void deleteOptions(Set<Long> toRemoveOptionIds) {
		if(toRemoveOptionIds != null && toRemoveOptionIds.size() > 0){
			String sql = "update t_wares_option set c_deleted = 1 where id in (:optionIds)";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameterList("optionIds", toRemoveOptionIds);
			query.executeUpdate();
		}
		
	}

}
