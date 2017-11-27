package cn.sowell.copframe.dao.deferedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.BeanUtils;

import cn.sowell.copframe.spring.binder.FieldRefectUtils;
/**
 * 
 * <p>Title: HibernateRefrectResultTransformer</p>
 * <p>Description: </p><p>
 * 用于hibernate的{@linkplain Query}类在执行{@linkplain Query#list() list}等查询方法之前，
 * 将查询的结果映射成pojo对象
 * 当前对象只支持单层的字段映射。默认字段名是字段的名称。
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月26日 上午9:57:02
 * @param <T>
 */
public class HibernateRefrectResultTransformer<T> implements ResultTransformer{

	private static final long serialVersionUID = 4246893639652328864L;
	
	private ColumnMapResultTransformer<T> cTrans;
	
	HibernateRefrectResultTransformer(Class<T> clazz) {
		FieldRefectUtils<T> refectUtils = new FieldRefectUtils<T>(clazz, composite -> {
			Column anno = composite.getFieldAnno(Column.class);
			if(anno != null){
				String columnName = anno.name();
				if(columnName != null){
					return columnName.toLowerCase();
				}
			}
			return composite.getFieldName().toLowerCase();
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
	
	public T build(SimpleMapWrapper mapWrapper){
		return cTrans.build(mapWrapper);
	}
	
	
	private static Map<Class<?>, HibernateRefrectResultTransformer<?>> instanceMap = new HashMap<Class<?>, HibernateRefrectResultTransformer<?>>();
	@SuppressWarnings("unchecked")
	public static <C> HibernateRefrectResultTransformer<C> getInstance(Class<C> clazz){
		synchronized (instanceMap) {
			HibernateRefrectResultTransformer<C> value = (HibernateRefrectResultTransformer<C>) instanceMap.get(clazz);
			if(value == null){
				value = new HibernateRefrectResultTransformer<C>(clazz);
				instanceMap.put(clazz, value);
			}
			return value;
		}
	}

}
