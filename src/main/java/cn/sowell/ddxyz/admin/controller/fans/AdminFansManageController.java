package cn.sowell.ddxyz.admin.controller.fans;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.weixin.common.service.WxCredentialService;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.fans.service.FansService;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/fans")
public class AdminFansManageController {
	
	@Resource
	FansService fansService;
	
	@Resource
	WxCredentialService wxCredentialService;
	
	@RequestMapping("/list")
	public String list(CommonPageInfo pageInfo, Model model){
		String accessToken = wxCredentialService.getAccessToken();
		JSONObject fansListJson = fansService.getFansList(accessToken, null);
		List<String> openIds = fansService.getFansPageList(fansListJson, pageInfo);
		JSONObject fansInfoJosn = fansService.getFansInfoByOpenIds(accessToken, openIds);
		model.addAttribute("fansInfo", fansInfoJosn);
		model.addAttribute("pageInfo", pageInfo);
		return AdminConstants.PATH_BASE + "/fans/fans_list.jsp";
	}
	
	@RequestMapping("/edit")
	public String edit(String openId, Model model){
		String accessToken = wxCredentialService.getAccessToken();
		JSONObject fansInfo = fansService.getFansInfoByOpenId(accessToken, openId);
		model.addAttribute("fans", fansInfo);
		return AdminConstants.PATH_BASE + "/fans/fans_edit.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doEdit")
	public AjaxPageResponse doEdit(@RequestParam String openid, @RequestParam String remark){
		JSONObject jo = new JSONObject();
		jo.put("openid", openid);
		jo.put("remark", remark);
		String  accessToken = wxCredentialService.getAccessToken();
		JSONObject result = fansService.updateFansRemark(accessToken, jo);
		if((int)result.get("errcode") == 0){
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功！", "fans-list");
		}else{
			return AjaxPageResponse.FAILD("修改失败");
		}
	}

}
