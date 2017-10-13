package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenDao;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenDistributionMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.KanteenTrolleyWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistribution;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchantAnnounce;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenTrolley;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;

@Repository
public class KanteenDaoImpl implements KanteenDao {

	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenMerchantAnnounce> getOrderedAnnounces(
			String annouceKey) {
		String hql = "from PlainKanteenMerchantAnnounce anno where anno.key = :key order by anno.order asc";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setString("key", annouceKey);
		return query.list();
	}

	@Override
	public PlainKanteenDistribution getDistribution(Long merchantId,
			Date[] range) {
		String hql = "from PlainKanteenDistribution dis where dis.merchantId = :merchantId and dis.startTime >= :begin and dis.startTime <= :end order by dis.createTime asc";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("merchantId", merchantId)
				.setTimestamp("begin", range[0])
				.setTimestamp("end", range[1]);
		return (PlainKanteenDistribution) query.setMaxResults(1).uniqueResult();
	}

	@Override
	public <T> T get(Long id, Class<T> clazz) {
		return sFactory.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenWaresGroup> getMenuWaresGroups(Long menuId) {
		String sql = "SELECT" +
				"	wg.*," +
				"	mwg.menu_id," +
				"	mwg.c_order" +
				" FROM" +
				"	t_menu_waresgroup mwg" +
				" LEFT JOIN t_waresgroup_base wg ON mwg.waresgroup_id = wg.id" +
				" WHERE" +
				"	mwg.menu_id = : menuId";
		
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainKanteenWaresGroup.class));
		query.setLong("menuId", menuId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KanteenDistributionMenuItem> getMenuItems(Long distributionId) {
		String sql = "SELECT" +
				"	dw.id as distributionwares_id," +
				"	dw.distribution_id," +
				"	wgw.wares_id," +
				"	wgw.group_id," +
				"	db.menu_id," +
				"	dw.menuwares_id," +
				"	wb.c_name as wares_name," +
				"	wb.c_base_price," +
				"	wb.c_price_unit," +
				"	wb.c_disabled," +
				"	wgw.c_order" +
				" FROM" +
				"	t_distribution_wares dw" +
				" LEFT JOIN t_distribution_base db ON dw.distribution_id = db.id" +
				" left join t_waresgroup_wares wgw on dw.menuwares_id = wgw.id" +
				" left join t_wares_base wb on wgw.wares_id = wb.id" +
				" where dw.distribution_id = :distributionId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("distributionId", distributionId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(KanteenDistributionMenuItem.class));
		return query.list();
	}

	@Override
	public PlainKanteenTrolley getTrolley(Long userId, Long distributionId) {
		String hql = "from PlainKanteenTrolley t where t.userId = :userId and t.distributionId = :distributionId";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("userId", userId)
			.setLong("distributionId", distributionId)
			.setMaxResults(1)
			;
		return (PlainKanteenTrolley) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KanteenTrolleyWares> getTrolleyWares(Long trolleyId) {
		String sql = "	SELECT" +
				"		tw.*, dw.c_name AS wares_name" +
				"	FROM" +
				"		t_kanteen_trolley_wares tw" +
				"	LEFT JOIN t_distribution_wares dw ON dw.id = tw.distributionwares_id" +
				"	LEFT JOIN t_wares_base wb ON tw.id = tw.wares_id" +
				"	WHERE" +
				"		tw.trolley_id = :trolleyId" +
				"	AND dw.id IS NOT NULL order" + 
				"   order by tw.create_time asc";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("trolleyId", trolleyId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(KanteenTrolleyWares.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenDistributionWares> getDistributionWares(
			Set<Long> distributionWaresIds) {
		String hql = "from PlainKanteenDistributionWares dw where dw.distributionId in (:distributionIds)";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("distributionIds", distributionWaresIds, StandardBasicTypes.LONG);
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainKanteenDelivery> getEnabledDeliveries(Long distributionId) {
		String hql = "from PlainKanteenDelivery d where d.distributionId = :distributionId and d.c_disabled is null";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("distributionId", distributionId);
		return query.list();
	}
	
	
	
	
}
