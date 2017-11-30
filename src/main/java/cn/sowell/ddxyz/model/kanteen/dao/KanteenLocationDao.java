package cn.sowell.ddxyz.model.kanteen.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenLocation;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteriaForChoose;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenLocationItemForChoose;

public interface KanteenLocationDao {

	/**
	 * 从数据库中根据条件查找配送地点的id
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<PlainKanteenLocation> queryLocationList(
			KanteenLocationCriteria criteria, PageInfo pageInfo);

	/**
	 * 检查某个商户内的code是否可用
	 * @param merchantId
	 * @param code
	 * @return
	 */
	boolean checkLocationCode(Long merchantId, String code);

	/**
	 * 
	 * @param locationId
	 */
	void deleteLocation(Long locationId);

	/**
	 * 
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenLocationItemForChoose> queryLocationListForChoose(
			KanteenLocationCriteriaForChoose criteria, PageInfo pageInfo);

}
