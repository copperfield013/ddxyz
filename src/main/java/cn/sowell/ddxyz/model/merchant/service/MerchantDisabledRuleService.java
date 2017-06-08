package cn.sowell.ddxyz.model.merchant.service;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.merchant.pojo.MerchantDisabledRule;

public interface MerchantDisabledRuleService {
	
	/**
	 * 判断当前时间是否在设置的时间规则内，如果在，返回true；如果不在，则返回false
	 * @param merchantId
	 * @return
	 */
	 boolean getIsInRules(Long merchantId);
	 
	 void getRulesList();
	 
	 List<MerchantDisabledRule> getRulesList(Long merchantId, CommonPageInfo pageInfo);
	 
	 void addMerchantDisabledRule(MerchantDisabledRule merchantDisabledRule);
	 
	 void deleteMerchantDisabledRule(Long ruleId);
	 
	 void forceRuleDisabled();
	 
	 void forceRuleOpen();
	 
	 boolean getForceOpen();
	 
	 boolean getForceClose();
}
