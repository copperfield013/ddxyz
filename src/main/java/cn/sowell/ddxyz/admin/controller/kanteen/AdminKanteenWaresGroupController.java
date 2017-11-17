package cn.sowell.ddxyz.admin.controller.kanteen;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresGroupCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresGroupWaresItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresGroupService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/kanteen/waresgroup")
public class AdminKanteenWaresGroupController {
	
	@Resource
	KanteenMerchantService merchantService;
	
	@Resource
	KanteenWaresGroupService waresGroupService;
	
	Logger logger = Logger.getLogger(AdminKanteenWaresGroupController.class);
	
	@RequestMapping({"", "/"})
	public String main(KanteenWaresGroupCriteria criteria, PageInfo pageInfo, Model model){
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		criteria.setMerchantId(merchant.getId());
		List<KanteenWaresGroupItem> waresGroups = waresGroupService.queryWaresGroups(criteria, pageInfo);
		
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("waresGroups", waresGroups);
		return AdminConstants.PATH_KANTEEN_WARESGROUP + "/waresgroup_list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(){
		return AdminConstants.PATH_KANTEEN_WARESGROUP + "/waresgroup_add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(PlainKanteenWaresGroup waresGroup){
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		waresGroup.setMerchantId(merchant.getId());
		try {
			waresGroup.setUpdateUserId((Long) WxUtils.getCurrentUser(UserIdentifier.class).getId());
			waresGroupService.saveWaresGroup(waresGroup);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("创建成功", "waresgroup_list");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("创建失败");
		}
	}
	
	
	@RequestMapping("/update/{waresGroupId}")
	public String update(@PathVariable Long waresGroupId, Model model){
		PlainKanteenWaresGroup waresGroup = waresGroupService.getWaresGroup(waresGroupId);
		model.addAttribute("waresGroup", waresGroup);
		List<KanteenWaresGroupWaresItem> waresList = waresGroupService.getGroupWares(waresGroupId);
		model.addAttribute("waresList", waresList);
		return AdminConstants.PATH_KANTEEN_WARESGROUP + "/waresgroup_update.jsp";
	}
	
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(PlainKanteenWaresGroup waresGroup){
		if(waresGroup.getId() != null){
			PlainKanteenWaresGroup origin = waresGroupService.getWaresGroup(waresGroup.getId());
			origin.setName(waresGroup.getName());
			origin.setDescription(waresGroup.getDescription());
			origin.setUpdateUserId((Long) WxUtils.getCurrentUser(UserIdentifier.class).getId());
			try {
				waresGroupService.updateWaresGroup(origin);
				return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "waresgroup_list");
			} catch (Exception e) {
				logger.debug("", e);
			}
		}
		return AjaxPageResponse.FAILD("修改失败");
	}
	@ResponseBody
	@RequestMapping("/disable/{waresGroupId}")
	public AjaxPageResponse disableWaresGroup(Long waresGroupId){
		try {
			waresGroupService.disableWaresGroup(waresGroupId, true);
			return AjaxPageResponse.REFRESH_LOCAL("操作成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/enable/{waresGroupId}")
	public AjaxPageResponse enableWaresGroup(Long waresGroupId){
		try {
			waresGroupService.disableWaresGroup(waresGroupId, false);
			return AjaxPageResponse.REFRESH_LOCAL("操作成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	
	@RequestMapping("/choose_wares")
	public String chooseWares(KanteenChooseWaresListCriteria criteria, PageInfo pageInfo, Model model){
		criteria.setMerchantId(merchantService.getCurrentMerchant().getId());
		List<KanteenWaresItemForChoose> waresList = waresGroupService.queryWaresesForChoose(criteria, pageInfo);
		model.addAttribute("waresList", waresList);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		return AdminConstants.PATH_KANTEEN_WARESGROUP + "/waresgroup_choose_wares.jsp";
	}
	
	
	
}
