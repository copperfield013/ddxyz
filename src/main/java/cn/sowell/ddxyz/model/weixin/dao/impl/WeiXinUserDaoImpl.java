package cn.sowell.ddxyz.model.weixin.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.sowell.ddxyz.model.weixin.dao.WeiXinUserDao;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

@Repository
public class WeiXinUserDaoImpl implements WeiXinUserDao{

	@Resource
	SessionFactory sFactory;
	
	@Override
	public WeiXinUser getWeiXinUserByOpenid(String openid) {
		Assert.hasText(openid);
		Session session = sFactory.getCurrentSession();
		String hql = "from WeiXinUser u where u.openid = :openid";
		return (WeiXinUser) session
				.createQuery(hql)
				.setString("openid", openid)
				.uniqueResult();
	}
	
	@Override
	public Long save(WeiXinUser wxUser) {
		Session session = sFactory.getCurrentSession();
		return (Long) session.save(wxUser);
	}


}
