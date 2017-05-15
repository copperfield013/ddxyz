package cn.sowell.ddxyz.admin.controller.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sowell.ddxyz.admin.AdminConstants;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/config")
public class AdminDeliveryPlanController {
	
	@RequestMapping("/plan-add")
	public String add(){
		return AdminConstants.PATH_CONFIG + "/delivery_plan_add.jsp";
	}
}
