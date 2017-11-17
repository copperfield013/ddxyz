package cn.sowell.ddxyz.model.kanteen.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.model.kanteen.dao.KanteenMerchantDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;

@Repository
public class KanteenMerchantDaoImpl implements KanteenMerchantDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public PlainKanteenMerchant getUserMerchant(Long userId) {
		String hql = "from PlainKanteenMerchant m where m.updateUserId = :userId";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("userId", userId);
		query.setMaxResults(1);
		return (PlainKanteenMerchant) query.uniqueResult();
	}

}
