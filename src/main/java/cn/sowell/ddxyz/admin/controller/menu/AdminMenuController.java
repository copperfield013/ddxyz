package cn.sowell.ddxyz.admin.controller.menu;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.weixin.common.service.WxCredentialService;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.menu.service.MenuService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/menu")
public class AdminMenuController {
	
	@Resource
	MenuService menuService;
	@Resource
	WxCredentialService wxCredentialService;
	
	@RequestMapping("add-menu")
	public String menu(Model model){
		String accessToken = wxCredentialService.getAccessToken();
		JSONObject jo = menuService.getMenu(accessToken);
		model.addAttribute("menuConteng", jo);
		return AdminConstants.PATH_MENU + "/add_menu.jsp";
	}

}
