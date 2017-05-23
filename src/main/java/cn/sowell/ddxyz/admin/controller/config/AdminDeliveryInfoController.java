package cn.sowell.ddxyz.admin.controller.config;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.NoticeType;
import cn.sowell.copframe.dto.format.OfDateFormat;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;
import cn.sowell.ddxyz.model.config.pojo.criteria.DeliveryInfoCriteria;
import cn.sowell.ddxyz.model.config.service.DeliveryInfoService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/config/info")
public class AdminDeliveryInfoController {
	
	@Resource
	DeliveryInfoService deliveryInfoService;
	
	@Resource
	DeliveryManager deliveryManager;
	
	@Resource
	OfDateFormat ofDateFormat;
	
	@RequestMapping("/list")
	public String list(@RequestParam(required=false) String from, DeliveryInfoCriteria criteria, CommonPageInfo pageInfo, Model model){
		if(from != null && from.equals("index")){
			criteria.setDeliveryTime(new Date());
			criteria.setReceiveTime(ofDateFormat.format(new Date(), "yyyy-MM-dd"));
		}
		/*if(criteria.getReceiveTime() == null){
			criteria.setDeliveryTime(new Date());
		}*/
		List<PlainDelivery> list = deliveryInfoService.getPlainDeliveryPageList(criteria, pageInfo);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("list", list);
		return AdminConstants.PATH_CONFIG + "/delivery_info_list.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/showToday")
	public String loadTodayDelivery(){
		try{
			deliveryManager.loadTodayDeliveries();
		}catch(Exception e){
			return JSON.toJSONString(AjaxPageResponse.FAILD("操作失败"), SerializerFeature.WriteEnumUsingToString);
		}
		AjaxPageResponse response = new AjaxPageResponse();
		response.setLocalPageRedirectURL("admin/config/info/list?from=index");
		response.setNotice("操作成功");
		response.setNoticeType(NoticeType.SUC);
		return JSON.toJSONString(response, SerializerFeature.WriteEnumUsingToString);
	}

}
