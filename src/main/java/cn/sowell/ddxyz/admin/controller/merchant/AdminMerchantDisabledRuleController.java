package cn.sowell.ddxyz.admin.controller.merchant;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.merchant.pojo.MerchantDisabledRule;
import cn.sowell.ddxyz.model.merchant.service.MerchantDisabledRuleService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/merchant/rule")
public class AdminMerchantDisabledRuleController {
	
	@Resource
	MerchantDisabledRuleService ruleService;
	
	@RequestMapping("/list")
	public String list(CommonPageInfo pageInfo, Model model){
		List<MerchantDisabledRule> list = ruleService.getRulesList(DdxyzConstants.MERCHANT_ID, pageInfo);
		boolean forceOpen = ruleService.getForceOpen();
		boolean forceDisabled = ruleService.getForceClose();
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("ruleList", list);
		model.addAttribute("forceOpen", forceOpen);
		model.addAttribute("forceDisabled", forceDisabled);
		return AdminConstants.PATH_MERCHANT + "/rule_list.jsp";
	}
	
	@RequestMapping("/addRule")
	public String add(){
		return AdminConstants.PATH_MERCHANT + "/rule_add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/saveRule")
	public AjaxPageResponse doAdd(MerchantDisabledRule rule){
		rule.setMerchantId(DdxyzConstants.MERCHANT_ID);
		ruleService.addMerchantDisabledRule(rule);
		return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功！", "merchant-disabled-rule-list");
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public AjaxPageResponse delete(Long ruleId){
		ruleService.deleteMerchantDisabledRule(ruleId);
		return AjaxPageResponse.REFRESH_LOCAL("删除成功！");
	}
	
	@ResponseBody
	@RequestMapping("/merchantOpen")
	public AjaxPageResponse forceOpen(){
		ruleService.forceRuleOpen();
		return AjaxPageResponse.REFRESH_LOCAL("操作成功！");
	}
	
	@ResponseBody
	@RequestMapping("/merchantDisabled")
	public AjaxPageResponse forceDisabled(){
		ruleService.forceRuleDisabled();
		return AjaxPageResponse.REFRESH_LOCAL("操作成功！");
	}

}
