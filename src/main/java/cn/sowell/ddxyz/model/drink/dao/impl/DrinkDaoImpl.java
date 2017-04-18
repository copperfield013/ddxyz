package cn.sowell.ddxyz.model.drink.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.ddxyz.model.drink.dao.DrinkDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkTeaAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkType;

@Repository
public class DrinkDaoImpl implements DrinkDao{

	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDrinkType> getAllDrinkTypes(long waresId) {
		String hql = "from PlainDrinkType d where d.waresId = :waresId order by d.order asc";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		return query.setLong("waresId", waresId)
			.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDrinkTeaAdditionType> getAllTeaAdditionTypes(
			long waresId) {
		String sql = "select tat.* from t_drink_tea_addition_type tat left join t_drink_type t on tat.drink_type_id = t.id where t.wares_id = :waresId order by tat.c_order asc";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(new HibernateRefrectResultTransformer<PlainDrinkTeaAdditionType>(PlainDrinkTeaAdditionType.class));
		return query.setLong("waresId", waresId)
			.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDrinkAdditionType> getAllAdditionTypes(long waresId) {
		String sql = "select dat.* from t_drink_addition_type dat left join t_drink_type t on dat.drink_type_id = t.id where t.wares_id = :waresId order by dat.c_order asc";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("waresId", waresId);
		query.setResultTransformer(new HibernateRefrectResultTransformer<PlainDrinkAdditionType>(PlainDrinkAdditionType.class));
		return query.setLong("waresId", waresId)
			.list();
	}

}
