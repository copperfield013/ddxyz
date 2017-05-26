package cn.sowell.ddxyz.model.weixin.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.sowell.copframe.dao.deferedQuery.ColumnMapResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.SimpleMapWrapper;
import cn.sowell.copframe.dto.format.FormatUtils;
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

	
	@Override
	public WeiXinUser getUser(long userId) {
		return sFactory.getCurrentSession().get(WeiXinUser.class, userId);
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public String[] getConfigedMessageOpenids(Long merchantId,
			String messageType) {
		String sql = "select distinct m.c_openid as open_id from t_weixin_msg_config m where m.merchant_id = :merchantId and m.c_msg_type = :msgType";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("merchantId", merchantId)
			.setString("msgType", messageType);
		query.setResultTransformer(new ColumnMapResultTransformer<String>() {
			@Override
			protected String build(SimpleMapWrapper mapWrapper) {
				return FormatUtils.toString(mapWrapper.get("open_id"));
			}
		});
		List<String> list = query.list();
		return list.toArray(new String[list.size()]);
	}

}
