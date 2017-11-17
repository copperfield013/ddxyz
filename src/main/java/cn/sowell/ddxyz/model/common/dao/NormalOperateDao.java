package cn.sowell.ddxyz.model.common.dao;


public interface NormalOperateDao {

	void save(Object pojo);
	
	void update(Object pojo);
	
	<T> T get(Class<T> pojoClass, Long id);

}
