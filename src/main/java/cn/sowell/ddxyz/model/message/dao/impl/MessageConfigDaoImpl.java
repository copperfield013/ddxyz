package cn.sowell.ddxyz.model.message.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dto.format.FormatUtils;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.message.dao.MessageConfigDao;
import cn.sowell.ddxyz.model.message.pojo.MessageConfig;

@Repository
public class MessageConfigDaoImpl implements MessageConfigDao{
	
	@Resource
	SessionFactory sFactory;

	@Override
	public void save(MessageConfig messageConfig) {
		sFactory.getCurrentSession().save(messageConfig);
	}
	
	@Override
	public void delete(MessageConfig messageConfig) {
		sFactory.getCurrentSession().delete(messageConfig);
	}

	@Override
	public void update(MessageConfig messageConfig) {
		sFactory.getCurrentSession().update(messageConfig);
	}
	
	@Override
	public MessageConfig getMessageConfig(Long id) {
		return sFactory.getCurrentSession().get(MessageConfig.class, id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MessageConfig> getList(CommonPageInfo pageInfo) {
		Session session = sFactory.getCurrentSession();
		String sql = "select "
				+ "mc.id, "
				+ "mc.c_rule_expression, "
				+ "mc.c_back_conntent, "
				+ "mc.c_level, "
				+ "mc.c_remarks, "
				+ "mc.create_time "
				+ "from t_message_config mc order by mc.create_time desc";
		
		SQLQuery query = session.createSQLQuery(sql);
		if(pageInfo != null){
			String countSQL = "select count(*) from t_message_config";
			SQLQuery countQuery = session.createSQLQuery(countSQL);
			Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
			if(count > 0){
				pageInfo.setCount(count);
				query.setFirstResult(pageInfo.getFirstIndex());
				query.setMaxResults(pageInfo.getPageSize());
			}
		}
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(MessageConfig.class));
		return  query.list();
		
	}

	@Override
	public int getMaxLevel() {
		int result;
		Session session = sFactory.getCurrentSession();
		String sql = "select MAX(mc.c_level) from t_message_config mc";
		SQLQuery query = session.createSQLQuery(sql);
		Integer maxLevel = FormatUtils.toInteger(query.uniqueResult());
		if(maxLevel == null){
			result = 0;
		}else{
			result = maxLevel;
		}
		return result;
	}

}
