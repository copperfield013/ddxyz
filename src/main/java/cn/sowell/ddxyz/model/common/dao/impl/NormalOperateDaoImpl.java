package cn.sowell.ddxyz.model.common.dao.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.ddxyz.model.common.dao.NormalOperateDao;

@Repository
public class NormalOperateDaoImpl implements NormalOperateDao{

	@Resource
	SessionFactory sFactory;
	
	@Override
	public Serializable save(Object pojo) {
		return sFactory.getCurrentSession().save(pojo);
	}

	@Override
	public void update(Object pojo) {
		sFactory.getCurrentSession().update(pojo);
	}

	@Override
	public <T> T get(Class<T> pojoClass, Long id) {
		return sFactory.getCurrentSession().get(pojoClass, id);
	}

}
