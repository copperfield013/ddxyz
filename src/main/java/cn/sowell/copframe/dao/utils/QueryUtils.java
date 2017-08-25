package cn.sowell.copframe.dao.utils;

import org.hibernate.Query;

import cn.sowell.copframe.dto.page.PageInfo;

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
	
}
