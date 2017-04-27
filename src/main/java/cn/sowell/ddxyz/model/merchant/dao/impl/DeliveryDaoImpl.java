package cn.sowell.ddxyz.model.merchant.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.merchant.dao.DeliveryDao;

@Repository
public class DeliveryDaoImpl implements DeliveryDao{

	@Resource
	SessionFactory sFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlainDelivery> getAllDelivery(long waresId, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		String hql = "from PlainDelivery d where d.waresId = :waresId and d.timePoint >= :theDayZero and d.timePoint < :theSecondZero and d.disabled is null";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("waresId", waresId)
				.setTimestamp("theDayZero", cal.getTime())
				;
		cal.add(Calendar.DATE, 1);
		query.setTimestamp("theSecondZero", cal.getTime());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<PlainLocation> getAllLocation(long merchantId) {
		String hql = "from PlainLocation l where l.merchantId = :merchantId and l.disabled is null";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("merchantId", merchantId);
		return new LinkedHashSet<PlainLocation>(query.list());
	}
	
	@Override
	public void savePlan(PlainDeliveryPlan plan) {
		sFactory.getCurrentSession().save(plan);
	}

}
