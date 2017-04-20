package cn.sowell.ddxyz.model.drink.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.ColumnMapResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.SimpleMapWrapper;
import cn.sowell.ddxyz.model.drink.dao.PlainDrinkAdditionDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;

@Repository
public class PlainDrinkAdditionDaoImpl implements PlainDrinkAdditionDao {

	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<PlainDrinkAddition> getDrinkAdditionList(Long productId) {
		Session session = sFactory.getCurrentSession();
		String sql = "select c_addition_type_name from t_drink_addition where drink_product_id = :productId";
		SQLQuery query = session.createSQLQuery(sql);
		query.setLong("productId", productId);
		query.setResultTransformer(new ColumnMapResultTransformer<PlainDrinkAddition>() {
			@Override
			protected PlainDrinkAddition build(SimpleMapWrapper mapWrapper) {
				PlainDrinkAddition addition = new PlainDrinkAddition();
				addition.setAdditionTypeName(mapWrapper.getString("c_addition_type_name"));
				return addition;
			}
		});
		return query.list();
	}

}
