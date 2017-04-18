package cn.sowell.copframe.dao.deferedQuery;

import java.util.List;

import javax.persistence.Column;

import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.BeanUtils;

import cn.sowell.copframe.utils.binder.FieldRefectUtils;

public class HibernateRefrectResultTransformer<T> implements ResultTransformer{

	private static final long serialVersionUID = 4246893639652328864L;
	
	private ColumnMapResultTransformer<T> cTrans;
	
	
	public HibernateRefrectResultTransformer(Class<T> clazz) {
		FieldRefectUtils<T> refectUtils = new FieldRefectUtils<T>(clazz, composite -> {
			Column anno = composite.getFieldAnno(Column.class);
			if(anno != null){
				String columnName = anno.name();
				if(columnName != null){
					return columnName;
				}
			}
			return composite.getFieldName();
		});
		//拿到所有的字段以及字段的注解，还有字段对应的getter和setter
		cTrans = new ColumnMapResultTransformer<T>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -305475977693720378L;

			@Override
			protected T build(SimpleMapWrapper mapWrapper) {
				T obj = BeanUtils.instantiate(clazz);
				if(obj != null){
					refectUtils.iterateField((fieldName, composite) -> {
							try {
								composite.setValue(obj, mapWrapper.get(fieldName));
							} catch (Exception e) {
								e.printStackTrace();
							}
					});
				}
				return obj;
			}
		};
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public T transformTuple(Object[] tuple, String[] aliases) {
		return (T) cTrans.transformTuple(tuple, aliases);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return cTrans.transformList(collection);
	}

}
