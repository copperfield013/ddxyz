package cn.sowell.ddxyz.model.drink.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.model.common.pojo.PlainOrder;
import cn.sowell.ddxyz.model.drink.dao.OrderDao;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Resource
	SessionFactory sFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<PlainOrder> getOrderList(Long userId) {
		Session session = sFactory.getCurrentSession();
		String hql = "from PlainOrder where orderUserId = :userId";
		Query query = session.createQuery(hql);
		query.setLong("userId", userId);
		return query.list();
	}

}
