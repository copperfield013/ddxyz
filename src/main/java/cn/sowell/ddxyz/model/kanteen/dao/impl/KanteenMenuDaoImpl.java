package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.KeyValueMapResultTransformer;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenMenuDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenuWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresGroupListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresGroupItemForChoose;

@Repository
public class KanteenMenuDaoImpl implements KanteenMenuDao{
	
	@Resource
	SessionFactory sFactory;
	

	@Override
	public List<KanteenMenuItem> queryMenuList(KanteenMenuCriteria criteria,
			PageInfo pageInfo) {
		List<KanteenMenuItem> list = QueryUtils.pagingSQLQuery(
				"	SELECT" +
				"		m.* FROM t_menu_base m" +
				"	WHERE" +
				"		m.merchant_id = :merchantId @condition" +
				"	order by m.update_time desc"
				, KanteenMenuItem.class, sFactory.getCurrentSession(), pageInfo, dQuery->{
				dQuery.setParam("merchantId", criteria.getMerchantId());
				DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
				if(TextUtils.hasText(criteria.getName())){
					snippet.append("and m.c_name like :name");
					dQuery.setParam("name", "%" + criteria.getName() + "%");
				}
		});
		LinkedHashSet<Long> menuIdSet = CollectionUtils.toSet(list, item->item.getId());
		if(!menuIdSet.isEmpty()){
			Map<Long, Integer> menuWaresGroupCountMap = getMenuWaresGroupCountMap(menuIdSet),
								menuWaresCountMap =  getMenuWaresCountMap(menuIdSet);
			list.forEach(item->{
				item.setGroupCount(FormatUtils.coalesce(menuWaresGroupCountMap.get(item.getId()), 0));
				item.setWaresCount(FormatUtils.coalesce(menuWaresCountMap.get(item.getId()), 0));
			});
		}
		
		return list;
	}


	private Map<Long, Integer> getMenuWaresGroupCountMap(
			LinkedHashSet<Long> menuIdSet) {
		String sql = 
				"	SELECT" +
						"		mwg.menu_id, count(distinct mwg.id) group_count FROM t_menu_waresgroup mwg" +
						"	LEFT JOIN t_waresgroup_base wgb ON mwg.waresgroup_id = wgb.id" +
						"	WHERE" +
						"		mwg.menu_id in (:menuIds) and mwg.c_disabled IS NULL" +
						"	AND wgb.c_disabled IS NULL" +
						"	group by mwg.menu_id";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameterList("menuIds", menuIdSet);
		KeyValueMapResultTransformer<Long, Integer> map = KeyValueMapResultTransformer.build(m->m.getLong("menu_id"), m->m.getInteger("group_count"));
		query.setResultTransformer(map);
		query.list();
		return map.getMap();
	}
	
	private Map<Long, Integer> getMenuWaresCountMap(
			LinkedHashSet<Long> menuIdSet) {
		String sql = 
				"	SELECT" +
				"		mwg.menu_id, count(distinct wgw.id) group_count FROM t_menu_waresgroup mwg" +
				"	LEFT JOIN t_waresgroup_base wgb ON mwg.waresgroup_id = wgb.id" +
				"	left join t_waresgroup_wares wgw on wgb.id = wgw.group_id" +
				"	left join t_wares_base wb on wgw.wares_id = wb.id" +
				"	WHERE" +
				"		mwg.menu_id in (:menuIds) and mwg.c_disabled IS NULL" +
				"	AND wgb.c_disabled IS NULL" +
				"	and wgw.c_disabled is NULL" +
				"	and wb.c_disabled is null" +
				"	group by mwg.menu_id";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameterList("menuIds", menuIdSet);
		KeyValueMapResultTransformer<Long, Integer> map = KeyValueMapResultTransformer.build(m->m.getLong("menu_id"), m->m.getInteger("group_count"));
		query.setResultTransformer(map);
		query.list();
		return map.getMap();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KanteenMenuWaresGroupItem> getMenuWaresGroups(Long menuId) {
		String sql = 
				"	SELECT" +
				"		wgb.id group_id," +
				"		mwg.id menu_group_id," +
				"		wgb.c_name group_name," +
				"		mwg.c_order " +
				"	FROM t_menu_waresgroup mwg" +
				"	LEFT JOIN t_waresgroup_base wgb ON mwg.waresgroup_id = wgb.id" +
				"	WHERE" +
				"		mwg.menu_id = :menuId" +
				"	AND mwg.c_disabled IS NULL" +
				"	AND wgb.c_disabled IS NULL order by mwg.c_order asc";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("menuId", menuId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(KanteenMenuWaresGroupItem.class));
		return query.list();
	}
	
	@Override
	public void updateMenuWaresGroupItemOrder(PlainKanteenMenuWaresGroup item) {
		String sql = "update t_menu_waresgroup set c_order = :order where id = :id";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("order", item.getOrder())
				.setLong("id", item.getId());
		query.executeUpdate();
	}
	
	
	@Override
	public void disableMenuWaresGroupItem(Set<Long> menuWaresGroupIds) {
		if(menuWaresGroupIds != null && !menuWaresGroupIds.isEmpty()){
			String sql = "update t_menu_waresgroup set c_disabled = 1 where id in (:ids)";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameterList("ids", menuWaresGroupIds);
			query.executeUpdate();
		}
	}
	
	
	
	
	@Override
	public void disbaleMenu(Long menuId, boolean toDisable) {
		String sql = "update t_menu_base set c_disabled = :toDisable, update_time = :now where id = :menuId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("menuId", menuId)
				.setTimestamp("now", new Date())
				.setParameter("toDisable", toDisable? 1: null, StandardBasicTypes.INTEGER)
				.executeUpdate();
	}

	@Override
	public List<KanteenWaresGroupItemForChoose> queryWaresGroupForChoose(
			KanteenChooseWaresGroupListCriteria criteria, PageInfo pageInfo) {
		List<KanteenWaresGroupItemForChoose> list = QueryUtils.pagingSQLQuery(
				"	select wgb.*" +
				"	from t_waresgroup_base wgb" +
				"	where wgb.merchant_id = :merchantId " +
				"	and wgb.c_disabled is null @condition order by wgb.update_time desc", 
				KanteenWaresGroupItemForChoose.class, sFactory.getCurrentSession(), pageInfo, 
				dQuery->{
					dQuery.setParam("merchantId", criteria.getMerchantId());
					DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
					if(criteria.getExceptGroupIds() != null && !criteria.getExceptGroupIds().isEmpty()){
						snippet.append("and wgb.id not in (:exceptGroupIds)");
						dQuery.setParam("exceptGroupIds", criteria.getExceptGroupIds());
					}
					if(TextUtils.hasText(criteria.getWaresGroupName())){
						snippet.append("and wgb.c_name like :groupName");
						dQuery.setParam("groupName", "%" + criteria.getWaresGroupName() + "%");
					}
		});
		Set<Long> groupIds = CollectionUtils.toSet(list, item->item.getWaresGroupId());
		
		if(groupIds != null && !groupIds.isEmpty()){
			String sql = 
					"	SELECT wgw.group_id, count(DISTINCT wb.id) wares_count" +
					"	FROM t_waresgroup_wares wgw" +
					"	LEFT JOIN t_wares_base wb ON wgw.wares_id = wb.id" +
					"	WHERE" +
					"		wgw.c_disabled IS NULL" +
					"	AND wb.c_disabled IS NULL" +
					"	AND wgw.group_id in (:groupIds)" +
					"	GROUP BY wgw.group_id";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameterList("groupIds", groupIds);
			KeyValueMapResultTransformer<Long, Integer> transfer = KeyValueMapResultTransformer.build(map->map.getLong("group_id"), map->map.getInteger("wares_count"));
			query.setResultTransformer(transfer);
			query.list();
			list.forEach(item->{
				item.setWaresCount(transfer.getMap().get(item.getWaresGroupId()));
			});
		}
		return list;
	}
	
	
}
