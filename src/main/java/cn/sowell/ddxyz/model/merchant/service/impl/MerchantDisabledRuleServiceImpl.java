package cn.sowell.ddxyz.model.merchant.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.impl.calendar.CronCalendar;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.model.merchant.dao.MerchantDisabledRuleDao;
import cn.sowell.ddxyz.model.merchant.pojo.MerchantDisabledRule;
import cn.sowell.ddxyz.model.merchant.service.MerchantDisabledRuleService;

@Service
public class MerchantDisabledRuleServiceImpl implements MerchantDisabledRuleService {
	
	@Resource
	MerchantDisabledRuleDao merchantDisabledRuleDao;
	
	boolean forceOpen = false;
	boolean forceClose = false;
	List<MerchantDisabledRule> merchantDisabledRuleList;
	
	@Override
	public boolean getIsInRules(Long merchantId) {
		boolean result = false;
		
		if(forceOpen){
			return result;
		}else if(forceClose){
			result = true;
			return result;
		}
		if(merchantDisabledRuleList != null && merchantDisabledRuleList.size() > 0){
			Date date = new Date();
			for(MerchantDisabledRule rule : merchantDisabledRuleList){
				if(rule.getMerchantId() == merchantId){
					try {
						CronCalendar cal = new CronCalendar(rule.getRule());
						if(!cal.isTimeIncluded(date.getTime())){
							result = true;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public void getRulesList() {
		merchantDisabledRuleList = merchantDisabledRuleDao.getRules(null, null);
	}
	
	@Override
	public List<MerchantDisabledRule> getRulesList(Long merchantId, CommonPageInfo pageInfo) {
		List<MerchantDisabledRule> ruleList = merchantDisabledRuleDao.getRules(merchantId, pageInfo);
		return ruleList;
	}

	@Override
	public void addMerchantDisabledRule(MerchantDisabledRule merchantDisabledRule) {
		merchantDisabledRule.setCreateTime(new Date());
		merchantDisabledRuleDao.saveMerchantDisabledRule(merchantDisabledRule);
		merchantDisabledRuleList.add(merchantDisabledRule);
	}
	
	@Override
	public void deleteMerchantDisabledRule(Long ruleId) {
		// TODO Auto-generated method stub
		MerchantDisabledRule merchantDisabledRule = new MerchantDisabledRule();
		merchantDisabledRule.setId(ruleId);
		merchantDisabledRuleDao.deleteMerchantDisabledRule(merchantDisabledRule);
		for(MerchantDisabledRule rule : merchantDisabledRuleList){
			if(rule.getId() == ruleId){
				merchantDisabledRuleList.remove(rule);
				break;
			}
		}
	}

	@Override
	public void forceRuleDisabled() {
		if(forceOpen){
			forceOpen = false;
		}
		if(forceClose){
			forceClose = false;
		}else{
			forceClose = true;
		}
	}

	@Override
	public void forceRuleOpen() {
		if(forceClose){
			forceClose = false;
		}
		if(forceOpen){
			forceOpen = false;
		}else{
			forceOpen = true;
		}
	}

	@Override
	public boolean getForceOpen() {
		return forceOpen;
	}

	@Override
	public boolean getForceClose() {
		return forceClose;
	}

}
