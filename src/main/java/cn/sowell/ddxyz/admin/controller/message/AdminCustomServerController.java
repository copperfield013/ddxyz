package cn.sowell.ddxyz.admin.controller.message;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.service.WxCredentialService;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.message.service.CustomServerService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/message/customServer")
public class AdminCustomServerController {
	
	@Resource
	CustomServerService customServerService;
	
	@Resource
	WxCredentialService wxCredentialService;
	
	@Resource
	WxConfigService wxConfigService;
	
	@RequestMapping("/list")
	public String list(Model model){
		String accessToken = wxCredentialService.getAccessToken();
		JSONObject jo = customServerService.getList(accessToken);
		model.addAttribute("customs", jo);
		return AdminConstants.PATH_MESSAGE_CONFIG + "/customserver/list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		String wxAccount = wxConfigService.getAppWxcount();
		model.addAttribute("wxAccount", wxAccount);
		return AdminConstants.PATH_MESSAGE_CONFIG + "/customserver/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public AjaxPageResponse doAdd(@RequestParam String kf_account_prefix, @RequestParam String nickname){
		String kf_account = kf_account_prefix + "@" + wxConfigService.getAppWxcount();
		String text = "{\"kf_account\":\"" + kf_account + "\",\"nickname\":\"" + nickname + "\"}";
		JSONObject jo = JSONObject.parseObject(text);
		customServerService.addCustomServer(wxCredentialService.getAccessToken(), jo);
		return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功", "custom-server-list");
	}
	
	@RequestMapping("/invite")
	public String invite(@RequestParam String kf_account, Model model){
		model.addAttribute("kf_account", kf_account);
		return AdminConstants.PATH_MESSAGE_CONFIG + "/customserver/invite.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doInvite")
	public AjaxPageResponse doInvite(@RequestParam String kf_account, @RequestParam String invite_wx){
		String text = "{\"kf_account\":\"" + kf_account + "\", \"invite_wx\":\"" + invite_wx + "\"}";
		JSONObject jo = JSONObject.parseObject(text);
		customServerService.inviteCustom(wxCredentialService.getAccessToken(), jo);
		return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("邀请成功！", "custom-server-invite");
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public AjaxPageResponse delete(@RequestParam String kf_account){
		 JSONObject jo = customServerService.deleteCustomServer(wxCredentialService.getAccessToken(), kf_account);
		 if((int) jo.get("errcode") == 0){
			 return AjaxPageResponse.REFRESH_LOCAL("删除成功！");
		 }else{
			 return AjaxPageResponse.FAILD("删除失败！");
		 }	
	}
	
	@RequestMapping("/edit")
	public String edit(@RequestParam String kf_account, @RequestParam String nickname, Model model){
		model.addAttribute("kf_account", kf_account);
		model.addAttribute("nickname", nickname);
		return AdminConstants.PATH_MESSAGE_CONFIG + "/customserver/edit.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doEdit")
	public AjaxPageResponse doEdit(@RequestParam String kf_account, @RequestParam String nickname){
		String accessToken = wxCredentialService.getAccessToken();
		String text = "{\"kf_account\":\"" + kf_account + "\",\"nickname\":\"" + nickname + "\"}";
		JSONObject jo = JSONObject.parseObject(text);
		JSONObject result = customServerService.updateCustomServer(accessToken, jo);
		if((int) result.get("errcode") == 0){
			 return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功！", "custom-server-list");
		 }else{
			 return AjaxPageResponse.FAILD("修改失败！");
		 }	
	}

}
