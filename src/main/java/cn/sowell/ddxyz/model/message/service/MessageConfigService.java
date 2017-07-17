package cn.sowell.ddxyz.model.message.service;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.message.pojo.MessageConfig;

public interface MessageConfigService {
	
	/**
	 * 根据类型和内容拼接成自动回复的内容
	 * @param type 选择的规则类型
	 * @param messageConfig
	 * @return
	 */
	MessageConfig composeMessageContent(int type, MessageConfig messageConfig);
	
	/**
	 * 根据id获取messageConfig对象
	 * @param id messageConfig的id
	 * @return
	 */
	MessageConfig getMessageConfigById(Long id);
	
	/**
	 * 添加messageConfig到数据库并同步到系统缓存
	 * @param messageConfig
	 */
	void addMessageConfig(MessageConfig messageConfig);
	
	/**
	 * 更新数据库中的messageConfig对象并同步系统缓存中的message
	 * @param messageConfig
	 */
	void updateMessageConfig(MessageConfig messageConfig);
	
	/**
	 * 删除数据库和系统缓存中的messageConfig对象
	 * @param messageConfigId
	 */
	void deleteMessageConfig(Long messageConfigId);
	
	/**
	 * 获取messageConfig
	 * @param messageConfig
	 * @param pageInfo
	 * @return
	 */
	List<MessageConfig> getList(MessageConfig messageConfig, CommonPageInfo pageInfo);
	
	/**
	 * 系统初始化时获取messageConfig放入系统缓存中
	 */
	void getList();
	
	/**
	 * 从系统缓存中获取messageConfig对象列表
	 * @return
	 */
	List<MessageConfig> getListFromCache();
	
	/**
	 * 获取系统当前最高优先级级别
	 * @return
	 */
	int getMaxLevel();
	
}
