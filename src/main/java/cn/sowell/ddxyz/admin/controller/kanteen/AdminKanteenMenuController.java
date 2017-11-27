package cn.sowell.ddxyz.admin.controller.kanteen;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenuWaresGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenChooseWaresGroupListCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenMenuCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenMenuWaresGroupItem;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.waresgroup.KanteenWaresGroupItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMenuService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/kanteen/menu")
public class AdminKanteenMenuController {

	@Resource
	KanteenMerchantService merchantService;
	
	@Resource
	KanteenMenuService menuService;
	
	Logger logger = Logger.getLogger(AdminKanteenMenuController.class);
	
	
	@RequestMapping({"", "/"})
	public String main(KanteenMenuCriteria criteria, PageInfo pageInfo, Model model){
		criteria.setMerchantId(merchantService.getCurrentMerchant().getId());
		List<KanteenMenuItem> menuList = menuService.queryMenuList(criteria, pageInfo);
		
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("menuList", menuList);
		return AdminConstants.PATH_KANTEEN_MENU + "/menu_list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(){
		return AdminConstants.PATH_KANTEEN_MENU + "/menu_add.jsp";
	}
	
	@RequestMapping("/update/{menuId}")
	public String update(@PathVariable Long menuId, Model model){
		PlainKanteenMenu menu = menuService.getMenu(menuId);
		model.addAttribute("menu", menu);
		List<KanteenMenuWaresGroupItem> waresGroupList = menuService.getMenuWaresGroups(menuId);
		model.addAttribute("waresGroupList", waresGroupList);
		return AdminConstants.PATH_KANTEEN_MENU + "/menu_update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(PlainKanteenMenu menu){
		menu.setMerchantId(merchantService.getCurrentMerchant().getId());
		try {
			menu.setUpdateUserId((Long) WxUtils.getCurrentUser(UserIdentifier.class).getId());
			menuService.saveMenu(menu);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("保存成功", "menu_list");
		} catch (Exception e) {
			logger.error("添加菜单时发生错误", e);
			return AjaxPageResponse.FAILD("保存失败");
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(PlainKanteenMenu menu, HttpServletRequest request){
		if(menu.getId() != null){
			PlainKanteenMenu origin = menuService.getMenu(menu.getId());
			origin.setName(menu.getName());
			origin.setDescription(menu.getDescription());
			origin.setUpdateUserId((Long) WxUtils.getCurrentUser(UserIdentifier.class).getId());
			try {
				Integer count = FormatUtils.toInteger(request.getParameter("group-count"));
				
				PlainKanteenMenuWaresGroup[] items = null;
				if(count != null && count > 0){
					items = new PlainKanteenMenuWaresGroup[count];
					for (int i = 0; i < count; i++) {
						items[i] = new PlainKanteenMenuWaresGroup();
						items[i].setWaresgourpId(FormatUtils.toLong(request.getParameter("group-id-" + i)));
						items[i].setId(FormatUtils.toLong(request.getParameter("id-" + i)));
						items[i].setOrder(i);
					}
				}
				menuService.updateMenu(origin, items);
				return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "menu_list");
			} catch (Exception e) {
				logger.debug("", e);
			}
		}
		return AjaxPageResponse.FAILD("修改失败");
	}
	
	@ResponseBody
	@RequestMapping("/disable/{menuId}")
	public AjaxPageResponse disableMenu(@PathVariable Long menuId){
		try {
			menuService.disableMenu(menuId, true);
			return AjaxPageResponse.REFRESH_LOCAL("操作成功");
		} catch (Exception e) {
			logger.error("禁用菜单时发生错误", e);
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/enable/{menuId}")
	public AjaxPageResponse enableMenu(@PathVariable Long menuId){
		try {
			menuService.disableMenu(menuId, false);
			return AjaxPageResponse.REFRESH_LOCAL("操作成功");
		} catch (Exception e) {
			logger.error("禁用菜单时发生错误", e);
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	@RequestMapping("/choose_waresgroup")
	public String chooseWaresGroup(KanteenChooseWaresGroupListCriteria criteria, PageInfo pageInfo, Model model){
		criteria.setMerchantId(merchantService.getCurrentMerchant().getId());
		List<KanteenWaresGroupItemForChoose> waresGroupList = menuService.queryWaresGroupForChoose(criteria, pageInfo);
		model.addAttribute("waresGroupList", waresGroupList);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		
		JSONObject waresGroupInfo = new JSONObject();
		waresGroupList.forEach(item->{
			waresGroupInfo.put("group_" + item.getWaresGroupId(), JSON.toJSON(item));
		});
		model.addAttribute("waresGroupInfo", waresGroupInfo);
		return AdminConstants.PATH_KANTEEN_MENU + "/menu_choose_waresgroup.jsp";
	}
	
	
	
}
