package cn.sowell.ddxyz.model.common.dao;

import java.util.List;

import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupWaresItem;


public interface NormalOperateDao {

	void save(Object pojo);
	
	void update(Object pojo);
	
	<T> T get(Class<T> pojoClass, Long id);

}
