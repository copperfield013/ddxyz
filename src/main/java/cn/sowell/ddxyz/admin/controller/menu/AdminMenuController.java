package cn.sowell.ddxyz.admin.controller.menu;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.service.WxCredentialService;
import cn.sowell.copframe.weixin.config.WxApp;
import cn.sowell.copframe.weixin.config.WxConfig;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.menu.service.MenuService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/menu")
public class AdminMenuController {
	
	@Resource
	MenuService menuService;
	
	@Resource
	WxCredentialService wxCredentialService;
	
	@Resource
	WxConfigService configService;
	
	@RequestMapping("menu")
	public String menu(Model model){
		String accessToken = wxCredentialService.getAccessToken();
		JSONObject jo = menuService.getMenu(accessToken);
		model.addAttribute("menuContent", jo);
		return AdminConstants.PATH_MENU + "/menu.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doAddMenu")
	public AjaxPageResponse doAdd(@RequestBody JsonRequest jReq){
		JSONObject json = menuService.createMenu(wxCredentialService.getAccessToken(), jReq.getJsonObject());
		if(Integer.parseInt(json.getString("errcode")) == 0){
			return AjaxPageResponse.REFRESH_LOCAL("菜单配置成功！");
		}
		return AjaxPageResponse.REFRESH_LOCAL_BY_TYPE("菜单配置失败！", NoticeType.ERROR);
	}

}
