package cn.sowell.ddxyz.model.message.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.message.pojo.MessageConfig;

public interface MessageConfigDao {
	
	/**
	 * 保存messageConfig对象
	 * @param messageConfig
	 */
	void save(MessageConfig messageConfig);
	
	/**
	 * 删除数据库中的messageConfig对象
	 * @param messageConfig
	 */
	void delete(MessageConfig messageConfig);
	
	/**
	 * 更新数据库中的messageConfig对象
	 * @param messageConfig
	 */
	void update(MessageConfig messageConfig);
	
	/**
	 * 根据messageConfig的id来获取messageConfig对象
	 * @param id
	 * @return
	 */
	MessageConfig getMessageConfig(Long id);
	
	/**
	 * 获取数据库中的所有messageConfig,以List形式返回
	 * @return
	 */
	List<MessageConfig> getList(MessageConfig messageConfig, CommonPageInfo pageInfo);
	
	/**
	 * 获取系统当前最高的优先级级别
	 * @return 当前最高的优先级级别
	 */
	int getMaxLevel();

}
