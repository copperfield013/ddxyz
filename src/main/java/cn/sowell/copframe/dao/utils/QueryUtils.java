package cn.sowell.copframe.dao.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.HibernateRefrectResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;

public class QueryUtils {
	/**
	 * 根据BaseModel的参数设置Query对象的分页参数
	 * @param query
	 * @param criteria
	 */
	public static void setPagingParamWithCriteria(Query query,
			PageInfo pageInfo) {
		if(query != null && pageInfo != null){
			if(pageInfo.getCount() != null && pageInfo.getPageSize() != null){
				if(pageInfo.getCount() <= (pageInfo.getPageNo() - 1) * pageInfo.getPageSize()){
					pageInfo.setPageNo((pageInfo.getCount() - 1) / pageInfo.getPageSize());
				}
			}
			query.setFirstResult(pageInfo.getFirstIndex());
			query.setMaxResults(pageInfo.getPageSize());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> queryList(String sql, Class<T> itemClass, Session session, Consumer<DeferedParamQuery> consumer){
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		consumer.accept(dQuery);
		SQLQuery query = dQuery.createSQLQuery(session, false, null);
		query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(itemClass));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> pagingSQLQuery(String sql, Class<T> itemClass, Session session, PageInfo pageInfo, Consumer<DeferedParamQuery> consumer){
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		consumer.accept(dQuery);
		SQLQuery countQuery = dQuery.createSQLQuery(session, false, new WrapForCountFunction());
		pageInfo.setCount(FormatUtils.toInteger(countQuery.uniqueResult()));
		if(pageInfo.getCount() > 0){
			SQLQuery query = dQuery.createSQLQuery(session, false, null);
			query.setResultTransformer(HibernateRefrectResultTransformer.getInstance(itemClass));
			setPagingParamWithCriteria(query, pageInfo);
			return query.list();
		}else{
			return new ArrayList<T>();
		}
	}
	
}
