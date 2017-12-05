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

import cn.sowell.copframe.dao.deferedQuery.ColumnMapResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.SimpleMapWrapper;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDistributionDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenDistributionChooseMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenDistributionItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenMenuItemForChoose;

@Repository
public class KanteenDistributionDaoImpl implements KanteenDistributionDao{

	@Resource
	SessionFactory sFactory;
	
	
	@Override
	public List<KanteenDistributionItem> queryDistributions(Long merchantId, PageInfo pageInfo) {
		return QueryUtils.pagingSQLQuery(
				"	SELECT" +
				"		d.id d_id," +
				"		d.c_code," +
				"		m.c_name menu_name," +
				"		d.c_start_time," +
				"		d.c_end_time," +
				"		count(del.id) delivery_count," +
				"		d.c_disabled, d.create_time, d.update_time" +
				"	FROM" +
				"		t_distribution_base d" +
				"	LEFT JOIN t_menu_base m ON d.menu_id = m.id" +
				"	left join t_delivery_base del on d.id = del.distribution_id" +
				"	where d.merchant_id = :merchantId" +
				"	group by d.id order by d.update_time desc"
				, KanteenDistributionItem.class, sFactory.getCurrentSession(), pageInfo, 
				dQuery->{
					dQuery.setParam("merchantId", merchantId);
		});
	}

	@Override
	public List<KanteenMenuItemForChoose> queryMenuListForChoose(
			KanteenDistributionChooseMenuCriteria criteria, PageInfo pageInfo) {
		return QueryUtils.pagingSQLQuery(
				"	select " +
				"	m.c_name menu_name," +
				"	m.id menu_id," +
				"	m.c_desc," +
				"	m.create_time," +
				"	m.update_time" +
				"	from t_menu_base m " +
				"	where m.merchant_id = :merchantId", 
				KanteenMenuItemForChoose.class, sFactory.getCurrentSession(), pageInfo, 
				dQuery->{
					dQuery.setParam("merchantId", criteria.getMerchantId());
				});
	}
	
	@SuppressWarnings({ "serial" })
	@Override
	public Map<Long, PlainKanteenMenu> getMenuMapByDistributionIds(
			Set<Long> distributionIds) {
		Map<Long, PlainKanteenMenu> menuMap = new HashMap<Long, PlainKanteenMenu>();
		if(distributionIds != null && !distributionIds.isEmpty()){
			String sql = "select m.*, d.id distribution_id from t_distribution_base d "
					+ "left join t_menu_base m on d.menu_id = m.id where d.id in (:distributionIds)";
			SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameterList("distributionIds", distributionIds, StandardBasicTypes.LONG);
			query.setResultTransformer(new ColumnMapResultTransformer<Void>() {

				@Override
				protected Void build(SimpleMapWrapper mapWrapper) {
					PlainKanteenMenu menu = HibernateRefrectResultTransformer.getInstance(PlainKanteenMenu.class).build(mapWrapper);
					menuMap.put(mapWrapper.getLong("distribution_id"), menu);
					return null;
				}
			});
			query.list();
		}
		return menuMap;
	}
	
	@Override
	public String getLastCode(Long merchantId) {
		String sql = "select d.c_code from t_distribution_base d where d.merchant_id = :merchantId and d.c_code is not null order by d.create_time desc";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("merchantId", merchantId);
		query.setMaxResults(1);
		return FormatUtils.toString(query.uniqueResult());
	}
	
	@Override
	public List<PlainKanteenDistributionWares> getDistributionWares(
			Long distributionId, PageInfo pageInfo) {
		return QueryUtils.pagingQuery(
				"from PlainKanteenDistributionWares dw where dw.distributionId = :distributionId", 
				sFactory.getCurrentSession(), pageInfo, 
				dQuery->{
					dQuery.setParam("distributionId", distributionId);
				});
	}
	
	@Override
	public void decreaseDistributionWaresCurrentCount(Long distributionWaresId,
			Integer decrease) {
		String sql = "update t_distribution_wares set c_current_count = c_current_count - :dec where id = :distributionId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("distributionId", distributionWaresId)
				.setInteger("dec", decrease)
				.executeUpdate();
	}
	
	
}
