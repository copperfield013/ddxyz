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
}
