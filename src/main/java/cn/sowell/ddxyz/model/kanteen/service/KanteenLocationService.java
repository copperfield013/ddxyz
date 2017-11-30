package cn.sowell.ddxyz.model.kanteen.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenLocation;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteriaForChoose;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenLocationItemForChoose;

public interface KanteenLocationService {

	/**
	 * 根据id获得配送地点的对象。传入merchantId时，表示验证地点的可获取性。当不可获取时，将抛出异常
	 * @param locationId
	 * @param merchantId
	 * @return
	 */
	PlainKanteenLocation getLocation(Long locationId, Long merchantId);

	/**
	 * 根据条件查找配送地点列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<PlainKanteenLocation> queryLocationList(
			KanteenLocationCriteria criteria, PageInfo pageInfo);

	/**
	 * 检查配送编码是否存在
	 * @param merchantId
	 * @param code
	 * @return
	 */
	boolean checkLocationCode(Long merchantId, String code);

	/**
	 * 删除配送地点
	 * @param locationId
	 */
	void deletePlainLocation(Long locationId);

	/**
	 * 创建配送点
	 * @param location
	 */
	void createLocation(PlainKanteenLocation location);

	/**
	 * 查询用于弹出框显示的配送地址列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<KanteenLocationItemForChoose> queryLocationListForChoose(
			KanteenLocationCriteriaForChoose criteria, PageInfo pageInfo);


}
