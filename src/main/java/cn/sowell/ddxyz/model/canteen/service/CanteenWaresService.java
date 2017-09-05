package cn.sowell.ddxyz.model.canteen.service;
import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWaresListCriteria;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

public interface CanteenWaresService {

	List<PlainWares> getCanteenWaresList(CanteenWaresListCriteria criteria, CommonPageInfo pageInfo);

	<T> T getPlainObject(Class<T> class1, Long id);
	/**
	 * 创建商品
	 * @param wares
	 */
	void saveWares(PlainWares wares);
	
	/**
	 * 更新餐品
	 * @param wares
	 */
	void updateWares(PlainWares wares);

	void disableWares(Long waresId, boolean b);

	/**
	 * 
	 * @param uuid
	 * @param detail
	 */
	void updateWaresDetailPreview(String uuid, String detail);

	/**
	 * 根据uuid获得对应的详情预览
	 * @param uuid
	 * @return
	 */
	String getPreviewDetail(String uuid);

	/**
	 * 修改商品的可出售状态
	 * @param waresId
	 * @param salable
	 */
	void updateWaresSalable(Long waresId, boolean salable);

}
