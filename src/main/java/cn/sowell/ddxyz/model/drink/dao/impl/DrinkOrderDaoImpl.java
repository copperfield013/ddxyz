package cn.sowell.ddxyz.model.drink.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.dao.DrinkOrderDao;
import cn.sowell.ddxyz.model.drink.pojo.item.PlainOrderDrinkItem;

@Repository
public class DrinkOrderDaoImpl implements DrinkOrderDao {
	
	@Resource
	SessionFactory sFactory;

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<PlainOrderDrinkItem> getOrderDrinkItemList(Long orderId) {
		Session session = sFactory.getCurrentSession();
		String sql = "SELECT "
				+ "d.id drink_product_id, "
				+ "d.drink_type_id drink_type_id,"
				+ "d.c_drink_type_name,"
				+ "p.c_price, "
				+ "d.tea_addition_id,"
				+ "d.c_tea_addition_name, "
				+ "d.c_sweetness, "
				+ "d.c_heat, "
				+ "d.c_cup_size "
				+ "FROM "
				+ "t_product_base p, t_drink_product d "
				+ "WHERE p.id = d.product_id "
				+ "AND p.order_id = :orderId";
		SQLQuery query = session.createSQLQuery(sql);
		query.setLong("orderId", orderId);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(PlainOrderDrinkItem.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainOrder> getOrderList(Long userId) {
		Session session = sFactory.getCurrentSession();
		String hql = "from PlainOrder where orderUserId = :userId order by createTime desc";
		Query query = session.createQuery(hql);
		query.setLong("userId", userId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainOrder> getOrderList(Long userId, CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String hql = "from PlainOrder where orderUserId = :userId order by createTime desc";
		Query query = session.createQuery(hql);
		query.setLong("userId", userId);
		QueryUtils.setPagingParamWithCriteria(query, pageInfo);
		return query.list();
	}
	
}
