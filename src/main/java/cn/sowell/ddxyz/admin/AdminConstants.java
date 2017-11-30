package cn.sowell.ddxyz.admin;

public interface AdminConstants {
	final String URI_BASE = "/admin";
	
	
	
	final String PATH_BASE = "/admin";


	/**
	 * 订单管理
	 */
	final String PATH_ORDER = PATH_BASE + "/order";
	/**
	 * 生产管理
	 */
	final String PATH_PRODUCTION = PATH_BASE + "/production"; 
	/**
	 * 配送管理
	 */
	final String PATH_DELIVERY = PATH_BASE + "/delivery";
	/**
	 * 配送计划（配置）
	 */
	final String PATH_CONFIG = PATH_BASE + "/config";
	/**
	 * 门店下单可用规则配置
	 */
	final String PATH_MERCHANT = PATH_BASE + "/merchant";

	/**
	 * 测试模块
	 */
	final String PATH_TEST = PATH_BASE + "/test";
	
	/**
	 * 自定义菜单
	 */
	final String PATH_MENU = PATH_BASE + "/menu";
	
	/**
	 * 自动回复
	 */
	final String PATH_MESSAGE_CONFIG = PATH_BASE + "/message";


	/**
	 * 餐厅模块
	 */
	final String PATH_CANTEEN = PATH_BASE + "/canteen";


	
	final String PATH_KANTEEN = PATH_BASE + "/kanteen";
	
	/**
	 * 配销
	 */
	final String PATH_KANTEEN_DISTRIBUTION = PATH_KANTEEN + "/distribution";



	final String PATH_KANTEEN_WARES = PATH_KANTEEN + "/wares";



	final String PATH_KANTEEN_WARESGROUP = PATH_KANTEEN + "/waresgroup";



	final String PATH_KANTEEN_MENU = PATH_KANTEEN + "/menu";



	final String PATH_KANTEEN_DELIVERY = PATH_KANTEEN + "/delivery";



	final String PATH_KANTEEN_LOCATION = PATH_KANTEEN + "/location";



	/**
	 * 用于弹出框选择的页面。
	 * 该页面需要一个name为tpage的{@linkplain cn.sowell.copframe.dto.choose.ChooseTablePage ChooseTablePage}对象传到attribute中
	 * <p>ChooseTablePage对象需要设置的几个属性</p>
	 * <ol>
	 * <li>构造时需要传入页面id和构造json数据对象的前缀</li>
	 * <li>分页对象pageInfo</li>
	 * <li>是否多选</li>
	 * <li>设置表格中的数据{@linkplain cn.sowell.copframe.dto.choose.ChooseTablePage#setTableData(java.util.List, java.util.function.Consumer) setTableData}</li>
	 * </ol>
	 */
	final String PATH_CHOOSE_TABLE = PATH_BASE + "/common/choose_table.jsp";
}
