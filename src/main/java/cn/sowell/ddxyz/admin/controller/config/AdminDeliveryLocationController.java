package cn.sowell.ddxyz.admin.controller.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.utils.IPUtils;
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
	public AjaxPageResponse doAdd(PlainLocation location){
		location.setMerchantId(DdxyzConstants.MERCHANT_ID);
		deliveryLocationService.addPlainLocation(location);
		return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功！", "delivery-location-list");
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
	
	@ResponseBody
	@RequestMapping("/getLocationPoint")
	public JsonResponse getLocationPoint(HttpServletRequest request) throws MalformedURLException, IOException{
		String ip = HttpRequestUtils.getIpAddress(request);
		String url = "http://api.map.baidu.com/location/ip?ak=YhhQe0yOr3L0f1mHLZcjHIZyvQixl4Zq&coor=bd09ll";
		if(!IPUtils.isInternalIp(ip)){
			url += "&ip=" + ip;
		}
		InputStream is = new URL(url).openStream();
		InputStreamReader in = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(in);
		StringBuffer sb = new StringBuffer();
		String str;
		do {
			str = reader.readLine();
			sb.append(str);
		} while (null != str);

		str = sb.toString();
		if (null == str || str.isEmpty()) {
			return null;
		}
		// 获取坐标位子
		int index = str.indexOf("point");
		int end = str.indexOf("}}", index);
		if (index == -1 || end == -1) {
			return null;
		}

		str = str.substring(index - 1, end + 1);
		if (null == str || str.isEmpty()) {
			return null;
		}

		String[] ss = str.split(":");
		if (ss.length != 4) {
			return null;
		}

		String x = ss[2].split(",")[0];
		String y = ss[3];

		x = x.substring(x.indexOf("\"") + 1, x.indexOf("\"", 1));
		y = y.substring(y.indexOf("\"") + 1, y.indexOf("\"", 1));

		Map<String, String> map = new HashMap<String, String>();
		map.put("lng", x);
		map.put("lat", y);
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
		
		JsonResponse jres = new JsonResponse();
		jres.setJsonObject(jsonObject);
		return jres;
	}
	
}
