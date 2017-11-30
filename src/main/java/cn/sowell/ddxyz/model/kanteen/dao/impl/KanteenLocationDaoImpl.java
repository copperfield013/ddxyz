package cn.sowell.ddxyz.model.kanteen.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamSnippet;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.kanteen.dao.KanteenLocationDao;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenLocation;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteriaForChoose;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenLocationItemForChoose;

@Repository
public class KanteenLocationDaoImpl implements KanteenLocationDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public List<PlainKanteenLocation> queryLocationList(
			KanteenLocationCriteria criteria, PageInfo pageInfo) {
		return QueryUtils.pagingQuery("from PlainKanteenLocation l "
				+ "where l.merchantId = :merchantId @condition order by l.updateTime desc", 
				sFactory.getCurrentSession(), pageInfo, 
				dQuery->{
					dQuery.setParam("merchantId", criteria.getMerchantId(), true);
					DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
					if(TextUtils.hasText(criteria.getCode())){
						snippet.append("and l.code like :code");
						dQuery.setParam("code", "%" + criteria.getCode() + "%");
					}
					if(TextUtils.hasText(criteria.getName())){
						snippet.append("and l.name like :name");
						dQuery.setParam("name", "%" + criteria.getName() + "%");
					}
					if(TextUtils.hasText(criteria.getAddress())){
						snippet.append("and l.address like :address");
						dQuery.setParam("address", "%" + criteria.getAddress() + "%");
					}
				});
	}

	@Override
	public boolean checkLocationCode(Long merchantId, String code) {
		Session session = sFactory.getCurrentSession();
		String sql = "select count(l.id) from t_location_base l where l.c_code = :code and l.merchant_id = :merchantId";
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("code", code);
		query.setLong("merchantId", merchantId);
		Integer count = FormatUtils.toInteger(query.uniqueResult());
		if(count > 0){
			return false;
		}
		return true;
	}

	@Override
	public void deleteLocation(Long locationId) {
		String sql = "delete from t_location_base where id = :locationId";
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sql);
		query.setLong("locationId", locationId);
		query.executeUpdate();
	}

	@Override
	public List<KanteenLocationItemForChoose> queryLocationListForChoose(
			KanteenLocationCriteriaForChoose criteria, PageInfo pageInfo) {
		return QueryUtils.pagingSQLQuery(
				"	select * " +
				"	from t_location_base l " +
				"	where l.merchant_id = :merchantId" +
				"	and l.c_disabled is null @condition" +
				"	order by l.update_time", 
				KanteenLocationItemForChoose.class, 
				sFactory.getCurrentSession(), pageInfo, 
				dQuery->{
					dQuery.setParam("merchantId", criteria.getMerchantId());
					DeferedParamSnippet snippet = dQuery.createSnippet("condition", null);
					if(TextUtils.hasText(criteria.getName())){
						snippet.append("and l.c_name like :name");
						dQuery.setParam("name", "%" + criteria.getName() + "%");
					}
					if(TextUtils.hasText(criteria.getAddress())){
						snippet.append("and l.c_address like :address");
						dQuery.setParam("address", "%" + criteria.getAddress() + "%");
					}
					if(TextUtils.hasText(criteria.getCode())){
						snippet.append("and l.c_code like :code");
						dQuery.setParam("code", "%" + criteria.getCode() + "%");
					}
					
		});
	}
}
