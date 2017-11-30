package cn.sowell.ddxyz.admin.controller.kanteen;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.choose.ChooseTablePage;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.HttpRequestUtils;
import cn.sowell.copframe.utils.IPUtils;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenLocation;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenLocationCriteriaForChoose;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenLocationItemForChoose;
import cn.sowell.ddxyz.model.kanteen.service.KanteenLocationService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/kanteen/location")
public class AdminKanteenLocationController {
	
	@Resource
	KanteenLocationService locationService;
	
	@Resource
	KanteenMerchantService merchantService;
	
	@RequestMapping("/list")
	public String list(KanteenLocationCriteria criteria, PageInfo pageInfo, Model model){
		criteria.setMerchantId(merchantService.getCurrentMerchant().getId());
		List<PlainKanteenLocation> locations = locationService.queryLocationList(criteria, pageInfo);
		model.addAttribute("criteria", criteria);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("locations", locations);
		return AdminConstants.PATH_KANTEEN_LOCATION + "/location_list.jsp";
	}
	
	@RequestMapping("/detail/{locationId}")
	public String detail(@PathVariable Long locationId, Model model){
		PlainKanteenLocation location = locationService.getLocation(locationId, merchantService.getCurrentMerchant().getId());
		model.addAttribute("location", location);
		return AdminConstants.PATH_KANTEEN_LOCATION + "/location_detail.jsp";
		
	}
	
	@RequestMapping("/add")
	public String add(){
		return AdminConstants.PATH_KANTEEN_LOCATION + "/location_add.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public AjaxPageResponse doAdd(PlainKanteenLocation location){
		location.setMerchantId(merchantService.getCurrentMerchant().getId());
		location.setUpdateUserId((Long) WxUtils.getCurrentUser(UserIdentifier.class).getId());
		try {
			locationService.createLocation(location);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("添加成功！", "location_list");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("添加失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/checkCode")
	public JsonResponse checkLoacationCode(String code){
		JsonResponse jRes = new JsonResponse();
		boolean bool = locationService.checkLocationCode(merchantService.getCurrentMerchant().getId(), code);
		jRes.put("valid", bool);
		return jRes;
	}
	
	@ResponseBody
	@RequestMapping("/delete/{locationId}")
	public String delite(@PathVariable Long locationId){
		try{
			locationService.deletePlainLocation(locationId);
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
		// 获取坐标位置
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
	
	@RequestMapping("/choose_location")
	public String chooseLocation(KanteenLocationCriteriaForChoose criteria, PageInfo pageInfo, Model model){
		criteria.setMerchantId(merchantService.getCurrentMerchant().getId());
		List<KanteenLocationItemForChoose> list = locationService.queryLocationListForChoose(criteria, pageInfo);
		
		ChooseTablePage<KanteenLocationItemForChoose> tpage = new ChooseTablePage<KanteenLocationItemForChoose>("location_list_forchoose", "location_");
		tpage.setPageInfo(pageInfo)
			.setIsMulti(false)
			.setTableData(list, row->{
				row.setDataKeyGetter(data->data.getLocationId().toString());
				row.addColumn("编码", (cell, data)->cell.setText(data.getCode()));
				row.addColumn("配送地址", (cell, data)->cell.setText(data.getLocationName()));
				row.addColumn("详细地址", (cell, data)->cell.setText(data.getAddress()));
			});
		model.addAttribute("tpage", tpage);
		return AdminConstants.PATH_CHOOSE_TABLE;
	}
	
	
}
