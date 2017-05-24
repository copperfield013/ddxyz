package cn.sowell.ddxyz.admin.controller.config;

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
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.common.pojo.PlainLocation;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryLocationCriteria;
import cn.sowell.ddxyz.model.config.service.DeliveryLocationService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/config/location")
public class AdminDeliveryLocationController {
	
	@Resource
	DeliveryLocationService deliveryLocationService;
	
	@RequestMapping("/list")
	public String list(DeliveryLocationCriteria criteria, CommonPageInfo pageInfo, Model model){
		List<PlainLocation> locations = deliveryLocationService.getPlainLocationPageList(criteria, pageInfo);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("locations", locations);
		return AdminConstants.PATH_CONFIG + "/location/delivery_location_list.jsp";
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam("id")Long locationId, Model model){
		PlainLocation location = deliveryLocationService.getPlainLocationById(locationId);
		model.addAttribute("location", location);
		return AdminConstants.PATH_CONFIG + "/location/delivery_location_detail.jsp";
		
	}
	
	@RequestMapping("/add")
	public String add(){
		return AdminConstants.PATH_CONFIG + "/location/delivery_location_add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public JsonResponse doAdd(PlainLocation location){
		location.setMerchantId(DdxyzConstants.MERCHANT_ID);
		deliveryLocationService.addPlainLocation(location);
		JsonResponse jres = new JsonResponse();
		jres.setJsonObject((JSONObject) JSONObject.toJSON(AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功！", "delivery-location-list")));
		return jres;
	}
	
	@ResponseBody
	@RequestMapping("/checkCode")
	public JsonResponse checkLoacationCode(String code){
		JsonResponse jRes = new JsonResponse();
		boolean bool = deliveryLocationService.checkLocationCode(code);
		jRes.put("valid", bool);
		return jRes;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public String delite(@RequestParam("id")Long locationId){
		try{
			deliveryLocationService.deletePlainLocation(locationId);
		}catch(Exception e){
			return JSON.toJSONString(AjaxPageResponse.FAILD("操作失败！"), SerializerFeature.WriteEnumUsingToString);
		}
		return JSON.toJSONString(AjaxPageResponse.REFRESH_LOCAL("操作成功！"), SerializerFeature.WriteEnumUsingToString);
	}

}
