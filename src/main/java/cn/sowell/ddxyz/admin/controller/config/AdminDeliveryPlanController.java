package cn.sowell.ddxyz.admin.controller.config;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.common.pojo.PlainDeliveryPlan;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanCriteria;
import cn.sowell.ddxyz.model.common.pojo.criteria.DeliveryPlanWrap;
import cn.sowell.ddxyz.model.merchant.service.DeliveryService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/config")
public class AdminDeliveryPlanController {
	
	@Resource
	DeliveryService deliveryService;
	
	@RequestMapping("/plan-list")
	public String list(DeliveryPlanCriteria criteria, CommonPageInfo pageInfo, Model model){
		List<PlainDeliveryPlan> list = deliveryService.getPlainDeliveryPlanPageList(criteria, pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("criteria", criteria);
		return AdminConstants.PATH_CONFIG + "/delivery_plan_list.jsp";
	}
	
	@RequestMapping("/plan-add")
	public String add(Model model){
		List<PlainLocation> locationList = deliveryService.getAllDeliveryLocation(DdxyzConstants.MERCHANT_ID);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		model.addAttribute("locationList", locationList);
		model.addAttribute("year", year);
		return AdminConstants.PATH_CONFIG + "/delivery_plan_add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/plan-doAdd")
	public JsonResponse doAdd(DeliveryPlanWrap deliveryPlanWrap){
		deliveryPlanWrap.getPlan().setWaresId(DdxyzConstants.WARES_ID);
		deliveryService.addPlan(deliveryPlanWrap.getPlan());
		JsonResponse jres = new JsonResponse();
		jres.setJsonObject((JSONObject) JSONObject.toJSON( 
				AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("配送计划添加成功！", "delivery-plan-list")));
		return jres;
	}
	
	@ResponseBody
	@RequestMapping("/change-disabled")
	public String changePlanDisabled(@RequestParam("id") Long planId, Integer disabled){
		boolean changeResult = deliveryService.changePlanDisabled(planId, disabled);
		if(changeResult){
			return JSON.toJSONString(AjaxPageResponse.REFRESH_LOCAL("操作成功"), SerializerFeature.WriteEnumUsingToString);
		}else{
			AjaxPageResponse response = new AjaxPageResponse();
			response.setNotice("操作失败");
			response.setNoticeType(NoticeType.ERROR);
			return JSON.toJSONString(response, SerializerFeature.WriteEnumUsingToString);
		}
	}
}
