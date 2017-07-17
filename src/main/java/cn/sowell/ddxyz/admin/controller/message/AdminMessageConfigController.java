package cn.sowell.ddxyz.admin.controller.message;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.message.pojo.MessageConfig;
import cn.sowell.ddxyz.model.message.service.MessageConfigService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/message/autoReply")
public class AdminMessageConfigController {
	
	@Resource
	MessageConfigService messageConfigService;
	
	@RequestMapping("/list")
	public String list(CommonPageInfo pageInfo, Model model){
		List<MessageConfig> list = messageConfigService.getList(pageInfo);
		model.addAttribute("messageConfigList", list);
		model.addAttribute("pageInfo", pageInfo);
		return AdminConstants.PATH_MESSAGE_CONFIG + "/autoreply/list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		Integer maxLevel = messageConfigService.getMaxLevel();
		model.addAttribute("maxLevel", maxLevel);
		return AdminConstants.PATH_MESSAGE_CONFIG + "/autoreply/add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public AjaxPageResponse doAdd(@RequestParam int type, MessageConfig messageConfig){
		messageConfigService.composeMessageContent(type, messageConfig);
		messageConfigService.addMessageConfig(messageConfig);
		return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功！", "message-config-list");
	}
	
	@RequestMapping("/edit")
	public String edit(Long id, Model model){
		MessageConfig messageConfig = messageConfigService.getMessageConfigById(id);
		model.addAttribute("messageConfig", messageConfig);
		return AdminConstants.PATH_MESSAGE_CONFIG + "/autoreply/edit.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doEdit")
	public AjaxPageResponse doEdit(MessageConfig messageConfig){
		MessageConfig messageOld = messageConfigService.getMessageConfigById(messageConfig.getId());
		messageConfig.setCreateTime(messageOld.getCreateTime());
		messageConfigService.updateMessageConfig(messageConfig);
		return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("编辑成功！", "message-config-list");
		
	}
	
	@RequestMapping("/detail")
	public String detail(Long id, Model model){
		MessageConfig messageConfig = messageConfigService.getMessageConfigById(id);
		model.addAttribute("messageConfig", messageConfig);
		return AdminConstants.PATH_MESSAGE_CONFIG + "/autoreply/detail.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public AjaxPageResponse delete(Long id){
		messageConfigService.deleteMessageConfig(id);
		return AjaxPageResponse.REFRESH_LOCAL("删除成功！");
	}

}
