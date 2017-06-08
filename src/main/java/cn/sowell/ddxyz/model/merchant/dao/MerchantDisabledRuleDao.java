package cn.sowell.ddxyz.model.merchant.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.merchant.pojo.MerchantDisabledRule;


public interface MerchantDisabledRuleDao {

	List<MerchantDisabledRule> getRules(Long merchantId, CommonPageInfo pageInfo);
	
	void saveMerchantDisabledRule(MerchantDisabledRule merchantDisabledRule);
	
	void deleteMerchantDisabledRule(MerchantDisabledRule merchantDisabledRule );
	
}
