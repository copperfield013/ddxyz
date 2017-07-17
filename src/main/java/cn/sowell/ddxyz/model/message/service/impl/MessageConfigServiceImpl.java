package cn.sowell.ddxyz.model.message.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.message.dao.MessageConfigDao;
import cn.sowell.ddxyz.model.message.pojo.MessageConfig;
import cn.sowell.ddxyz.model.message.service.MessageConfigService;

@Service
public class MessageConfigServiceImpl implements MessageConfigService{
	
	List<MessageConfig> messageConfigList;
	
	@Resource
	MessageConfigDao messageConfigDao;

	public MessageConfig composeMessageContent(int type, MessageConfig messageConfig) {
		String ruleExpression = "";
		if(type == 1){ //包含
			ruleExpression += ".*" + messageConfig.getRuleExpression() + ".*";
		}else if(type == 2){ //匹配开头
			ruleExpression += "^(" + messageConfig.getRuleExpression() + ").*";
		}else if(type == 3){ //匹配结尾
			ruleExpression += ".*("+ messageConfig.getRuleExpression() + ")$";
		}else if(type == 4){ //完全匹配
			ruleExpression = messageConfig.getRuleExpression();
		}
		messageConfig.setRuleExpression(ruleExpression);
		return messageConfig;
	}
	
	@Override
	public MessageConfig getMessageConfigById(Long id) {
		return messageConfigDao.getMessageConfig(id);
	}

	@Override
	public void addMessageConfig(MessageConfig messageConfig) {
		messageConfig.setCreateTime(new Date());
		messageConfigDao.save(messageConfig);
		messageConfigList.add(messageConfig); //新增messageConfig到系统缓存中
	}
	
	@Override
	public void updateMessageConfig(MessageConfig messageConfig) {
		messageConfigDao.update(messageConfig);
		for(MessageConfig message : messageConfigList){
			if(message.getId() == messageConfig.getId()){
				/*messageConfigList.remove(message); //删除缓存中的messageConfig
				messageConfigList.add(messageConfig);*/
				System.out.println("----->before update list size:" + messageConfigList.size());
				Collections.replaceAll(messageConfigList, message, messageConfig);
				System.out.println("----->after update list size:" + messageConfigList.size());
				break;
			}
		}
	}

	@Override
	public void deleteMessageConfig(Long messageConfigId) {
		MessageConfig messageConfig = new MessageConfig();
		messageConfig.setId(messageConfigId);
		messageConfigDao.delete(messageConfig); //删除数据库中的messageConfig
		for(MessageConfig message : messageConfigList){
			if(message.getId() == messageConfigId){
				messageConfigList.remove(message); //删除缓存中的messageConfig
				break;
			}
		}
	}

	@Override
	public List<MessageConfig> getList(MessageConfig messageConfig, CommonPageInfo pageInfo) {
		return messageConfigDao.getList(messageConfig, pageInfo);
	}

	@Override
	public void getList() {
		messageConfigList = messageConfigDao.getList(null, null);
	}
	
	@Override
	public List<MessageConfig> getListFromCache() {
		return messageConfigList;
	}

	@Override
	public int getMaxLevel() {
		return messageConfigDao.getMaxLevel();
	}

}
