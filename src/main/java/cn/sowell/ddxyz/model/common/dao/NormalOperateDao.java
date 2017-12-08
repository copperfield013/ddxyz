package cn.sowell.ddxyz.model.common.dao;

import java.io.Serializable;


public interface NormalOperateDao {

	Serializable save(Object pojo);
	
	void update(Object pojo);
	
	<T> T get(Class<T> pojoClass, Long id);

}
