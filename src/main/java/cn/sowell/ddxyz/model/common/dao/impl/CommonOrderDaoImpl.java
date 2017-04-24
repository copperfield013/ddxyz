package cn.sowell.ddxyz.model.common.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import cn.sowell.ddxyz.model.common.core.ReceiverInfo;
import cn.sowell.ddxyz.model.common.core.exception.OrderException;
import cn.sowell.ddxyz.model.common.dao.CommonOrderDao;
import cn.sowell.ddxyz.model.common.pojo.PlainOrderReceiver;

@Service
public class CommonOrderDaoImpl implements CommonOrderDao{

	@Resource
	SessionFactory sFactory;
	
	@Override
	public PlainOrderReceiver getOrderReceiver(long userId) {
		String hql = "from PlainOrderReceiver r where r.userId = :userId";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("userId", userId);
		return (PlainOrderReceiver) query.uniqueResult();
	}
	
	@Override
	public void updateReceiverInfo(long userId, ReceiverInfo receiver) throws OrderException{
		try {
			Session session = sFactory.getCurrentSession();
			PlainOrderReceiver pReceiver = getOrderReceiver(userId);
			if(pReceiver != null){
				//数据库中已经存在该用户对应的收货人信息，更新记录
				if(!receiver.getContact().equals(pReceiver.getReceiverContact())){
					pReceiver.setReceiverContact(receiver.getContact());
					pReceiver.setUpdateTime(new Date());
					session.update(pReceiver);
				}
			}else{
				//数据库中不存在用户对应的收货人信息，创建记录
				pReceiver = new PlainOrderReceiver();
				pReceiver.setReceiverName(receiver.getName());
				pReceiver.setReceiverAddress(receiver.getAddress());
				pReceiver.setReceiverContact(receiver.getContact());
				pReceiver.setUserId(userId);
				pReceiver.setCreateTime(new Date());
				session.save(pReceiver);
			}
		} catch (HibernateException e) {
			throw new OrderException("更新数据库中用户的收货人信息时出现异常", e);
		}
	}

}
