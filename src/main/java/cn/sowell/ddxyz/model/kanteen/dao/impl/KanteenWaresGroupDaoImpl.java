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
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenWaresGroupDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresGroupCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresItemForChoose;

@Repository
public class KanteenWaresGroupDaoImpl implements KanteenWaresGroupDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public List<KanteenWaresGroupItem> queryWaresGroups(
			KanteenWaresGroupCriteria criteria, PageInfo pageInfo) {
		return QueryUtils.pagingSQLQuery(
				"	SELECT" +
				"		wg.id waresgroup_id," +
				"		wg.c_name waresgroup_name," +
				"		wg.c_desc waresgroup_desc," +
				"		count(w.id) wares_count," +
				"		wg.create_time," +
				"		wg.c_disabled" +
				"	FROM" +
				"		t_waresgroup_base wg" +
				"	LEFT JOIN t_waresgroup_wares w ON wg.id = w.group_id and w.c_disabled is null" +
				"	where wg.merchant_id = :merchantId @condition" +
				"	group by wg.id order by wg.create_time desc", 
				KanteenWaresGroupItem.class, sFactory.getCurrentSession(), pageInfo, 
				dQuery->{
					dQuery.setParam("merchantId", criteria.getMerchantId());
					
					DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
					if(TextUtils.hasText(criteria.getGroupName())){
						snippet.append("and wg.c_name like :groupName");
						dQuery.setParam("groupName", "%" + criteria.getGroupName() + "%");
					}
		});
	}
	
	@Override
	public int updateWaresGroupDisabledStatus(Long waresGroupId,
			boolean toDisable) {
		String sql = "update t_waresgroup_base set c_disabled = :toDisable where id = :waresgroupId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("waresgroupId", waresGroupId);
		query.setParameter("toDisable", toDisable?1:null, StandardBasicTypes.INTEGER);
		return query.executeUpdate();
	}
	
	
	@Override
	public List<KanteenWaresItemForChoose> queryWaresesForChoose(
			KanteenChooseWaresListCriteria criteria, PageInfo pageInfo) {
		return QueryUtils.pagingSQLQuery(
				"	SELECT" +
				"		wb.id wares_id," +
				"		wb.c_name wares_name," +
				"		wb.c_base_price," +
				"		wb.c_price_unit," +
				"		wb.create_time" +
				"	FROM t_wares_base wb" +
				"	WHERE wb.merchant_id = :merchantId" +
				"		and wb.c_disabled IS NULL @condition order by wb.create_time desc", 
				KanteenWaresItemForChoose.class, sFactory.getCurrentSession(), 
				pageInfo, dQuery->{
					dQuery.setParam("merchantId", criteria.getMerchantId());
					DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
					if(criteria.getExcept() != null && !criteria.getExcept().isEmpty()){
						snippet.append("and wb.id not in (:except)");
						dQuery.setParam("except", criteria.getExcept(), StandardBasicTypes.LONG);
					}
					if(TextUtils.hasText(criteria.getWareName())){
						snippet.append("and wb.c_name like :waresName");
						dQuery.setParam("waresName", "%" + criteria.getWareName() + "%");
					}
					
				});
	}

	@Override
	public List<KanteenWaresGroupWaresItem> getGroupWares(Long waresGroupId) {
		return QueryUtils.queryList(
				"	SELECT" +
				"		w.id wares_id," +
				"		w.c_name wares_name," +
				"		w.c_base_price," +
				"		w.c_price_unit," +
				"		wg.c_order," +
				"		w.create_time, wg.id id" +
				"	FROM" +
				"		t_waresgroup_wares wg" +
				"	LEFT JOIN t_wares_base w ON wg.wares_id = w.id" + 
				"	where wg.group_id = :groupId and wg.c_disabled is null order by wg.c_order asc", 
				KanteenWaresGroupWaresItem.class, 
				sFactory.getCurrentSession(), dQuery->{
					dQuery.setParam("groupId", waresGroupId);
		});
	}
	
	@Override
	public void updateGroupWaresItemOrder(PlainKanteenWaresGroupWaresItem item) {
		String sql = "update t_waresgroup_wares set c_order = :order where id = :id";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("order", item.getOrder())
			.setLong("id", item.getId())
			.executeUpdate()
			;
	}
	
	@Override
	public void disableGroupWaresItemOrder(Set<Long> itemIds) {
		if(itemIds != null && itemIds.size() > 0){
			String sql = "update t_waresgroup_wares set c_disabled = :status where id in (:itemIds)";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setInteger("status", 1)
				.setParameterList("itemIds", itemIds, StandardBasicTypes.LONG)
				.executeUpdate();
		}
	}
	
	@Override
	public Map<Long, PlainKanteenWaresGroup> getGroupMapByGroupWaresIds(
			Set<Long> groupWaresIds) {
		if(groupWaresIds != null && !groupWaresIds.isEmpty()){
			return QueryUtils.queryMap(
					"select gw.id groupwares_id, g.* "
							+ "from t_waresgroup_base g left join t_waresgroup_wares gw on g.id = gw.group_id "
							+ "where gw.id in (:groupWaresIds)", 
							sFactory.getCurrentSession(), mw->mw.getLong("groupwares_id"), PlainKanteenWaresGroup.class, 
							dQuery->dQuery.setParam("groupWaresIds", groupWaresIds)
					);
		}else{
			return new HashMap<Long, PlainKanteenWaresGroup>();
		}
		
	}
	
}
