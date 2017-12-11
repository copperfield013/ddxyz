package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
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
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenMenuWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenuWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroupWaresItem;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenWaresGroup> getWaresGroupList(Long menuId) {
		String sql = "select g.*, mg.menu_id from t_waresgroup_base g "
				+ "left join t_menu_waresgroup mg on g.id = mg.waresgroup_id "
				+ "where mg.menu_id = :menuId";
		
		Query query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("menuId", menuId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainKanteenWaresGroup.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenWaresGroupWaresItem> getWaresGroupWaresList(
			Set<Long> groupIds) {
		if(groupIds != null && !groupIds.isEmpty()){
			String hql = "from PlainKanteenWaresGroupWaresItem gw where gw.groupId in (:groupIds) and gw.disabled is null";
			Query query = sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("groupIds", groupIds, StandardBasicTypes.LONG);
			return query.list();
		}else{
			return new ArrayList<PlainKanteenWaresGroupWaresItem>();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<KanteenMenuWares> getMenuWaresItems(Long menuId,
			boolean effectiveWares) {
		String sql = 
				"	select " +
				"		m.id menu_id," +
				"		menu_group.id menugroup_id," +
				"		g.id group_id," +
				"		group_wares.id groupwares_id," +
				"		w.id wares_id," +
				"		m.c_name menu_name," +
				"		g.c_name group_name," +
				"		w.c_name wares_name," +
				"		m.c_disabled menu_disabled," +
				"		menu_group.c_disabled menugroup_disabled," +
				"		g.c_disabled group_disabled," +
				"		group_wares.c_disabled groupwares_disabled," +
				"		w.c_disabled wares_disabled," +
				"		w.c_deleted wares_deleted," +
				"		w.c_base_price," +
				"		w.c_price_unit," +
				"		w.c_unsalable" +
				"	from t_menu_base m" +
				"	left join t_menu_waresgroup menu_group on m.id = menu_group.menu_id" +
				"	left join t_waresgroup_base g on g.id = menu_group.waresgroup_id" +
				"	left join t_waresgroup_wares group_wares on g.id = group_wares.group_id" +
				"	left join t_wares_base w on w.id = group_wares.wares_id" +
				"	where m.id = :menuId"
				;
		if(effectiveWares){
			sql += 	"	and m.c_disabled is null " +
					"	and menu_group.c_disabled is NULL" +
					"	and g.c_disabled is NULL" +
					"	and group_wares.c_disabled is NULL" +
					"	and w.c_disabled is NULL" +
					"	and w.c_deleted is null";
		}
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("menuId", menuId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(KanteenMenuWares.class));
		return query.list();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenMenu> getMenusByWaresGroupId(Long waresGroupId) {
		String sql = "select m.* from t_menu_base m "
				+ "left join t_menu_waresgroup mg on m.id = mg.menu_id where mg.waresgroup_id = :groupId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainKanteenMenu.class));
		query.setLong("groupId", waresGroupId);
		return query.list();
	}
	
}
