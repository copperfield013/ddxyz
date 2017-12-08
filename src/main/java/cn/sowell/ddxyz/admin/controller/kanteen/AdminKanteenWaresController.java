package cn.sowell.ddxyz.admin.controller.kanteen;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.common.file.FileUploadUtils;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.ajax.JsonRequest;
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.utils.qrcode.QrCodeUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.common.utils.WxUtils;
import cn.sowell.ddxyz.DdxyzConstants;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMerchant;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOption;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresOptionGroup;
import cn.sowell.ddxyz.model.kanteen.pojo.adminCriteria.KanteenWaresCriteria;
import cn.sowell.ddxyz.model.kanteen.pojo.adminItem.KanteenWaresItem;
import cn.sowell.ddxyz.model.kanteen.service.KanteenMerchantService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresOptionService;
import cn.sowell.ddxyz.model.kanteen.service.KanteenWaresService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/kanteen/wares")
public class AdminKanteenWaresController {
	
	@Resource
	KanteenMerchantService merchantService;
	
	@Resource
	KanteenWaresService waresService;

	@Resource
	FileUploadUtils fUploadUtils;
	
	@Resource
	WxConfigService configService;
	
	@Resource
	KanteenWaresOptionService waresOptionService;
	
	
	Logger logger = Logger.getLogger(AdminKanteenWaresController.class);

	
	
	@RequestMapping({"", "/"})
	public String main(KanteenWaresCriteria criteria, PageInfo pageInfo, Model model){
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		if(merchant != null){
			criteria.setMerchantId(merchant.getId());
			List<KanteenWaresItem> waresList = waresService.queryWares(criteria, pageInfo);
			Set<Long> waresIdSet = CollectionUtils.toSet(waresList, wares->wares.getId());
			Map<Long, Integer> optionCountMap = waresOptionService.queryEnabledOptionCount(waresIdSet);
			Map<Long, Integer> optionGroupCountMap = waresOptionService.queryEnabledOptionGroupCount(waresIdSet);
			model.addAttribute("optionCountMap", optionCountMap);
			model.addAttribute("optionGroupCountMap", optionGroupCountMap);
			model.addAttribute("waresList", waresList);
			model.addAttribute("pageInfo", pageInfo);
		}
		return AdminConstants.PATH_KANTEEN_WARES + "/wares_list.jsp";
	}
	
	
	@RequestMapping("/add")
	public String add(){
		return AdminConstants.PATH_KANTEEN_WARES + "/wares_add.jsp";
	}
	
	@RequestMapping("/update/{waresId}")
	public String update(@PathVariable Long waresId, Model model){
		PlainKanteenWares wares = waresService.getPlainObject(PlainKanteenWares.class, waresId);
		model.addAttribute("wares", wares);
		return AdminConstants.PATH_KANTEEN_WARES + "/wares_update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doAdd(@RequestParam("thumb") MultipartFile file,
			PlainKanteenWares wares,
			String waresName,
			Float basePrice
			){
		UserIdentifier user = WxUtils.getCurrentUser(UserIdentifier.class);
		wares.setUpdateUserId((Long) user.getId());
		PlainKanteenMerchant merchant = merchantService.getCurrentMerchant();
		wares.setMerchantId(merchant.getId());
		wares.setBasePrice((int)(basePrice * 100));
		wares.setName(waresName);
		String[] nameSplit = file.getOriginalFilename().split("\\.");
		String suffix = nameSplit[nameSplit.length - 1];
		String fileName = "f_" + TextUtils.uuid(10, 36) + "." + suffix;
		try {
			wares.setThumbUri(fUploadUtils.saveFile(fileName, file.getInputStream()));
			waresService.saveWares(wares);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("创建成功", "wares_list");
		} catch (IOException e) {
			logger.error("上传文件失败", e);
			return AjaxPageResponse.FAILD("创建失败");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(@RequestParam("waresId") Long waresId,
			@RequestParam(value="thumb", required=false) MultipartFile file,
			PlainKanteenWares wares,
			String waresName,
			Float unitPrice,
			@RequestParam(value="hasThumb", required=false) Boolean hasThumb) {
		try {
			PlainKanteenWares originWares = waresService.getPlainObject(PlainKanteenWares.class, waresId);
			
			wares.setBasePrice((int)(unitPrice * 100));
			wares.setName(waresName);
			wares.setId(waresId);
			
			if(hasThumb != null && hasThumb){
				if(file !=null) {
					String[] nameSplit = file.getOriginalFilename().split("\\.");
					String suffix = nameSplit[nameSplit.length - 1];
					String fileName = "f_" + TextUtils.uuid(10, 36) + "." + suffix;
					try {
						wares.setThumbUri(fUploadUtils.saveFile(fileName, file.getInputStream()));
					} catch (IOException e) {
						logger.error("上传文件失败", e);
					}
				}else{
					wares.setThumbUri(originWares.getThumbUri());
				}
			}
			
			waresService.updateWares(wares);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "wares_list");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("修改时发生错误");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/preview_detail")
	public JsonResponse previewDetail(Integer waresKey, String detail, HttpSession session){
		JsonResponse jRes = new JsonResponse();
		String uuid = (String) session.getAttribute(DdxyzConstants.PREVIEW_WARES_KEY_PREFIX + waresKey);
		if(uuid == null){
			uuid = TextUtils.uuid();
			session.setAttribute(DdxyzConstants.PREVIEW_WARES_KEY_PREFIX + waresKey, uuid);
			OutputStream outputStream = null;
			try {
				outputStream = fUploadUtils.createFile(uuid + ".png");
				String redirectUrl = configService.getProjectURL() + "/weixin/canteen/preview_detail/" + uuid;
				QrCodeUtils.encodeQRCodeImage(redirectUrl, "utf-8", outputStream, "png", 150, 150);
			} catch (IOException e) {
				logger.error("创建二维码时发生错误", e);
				jRes.setStatus("error");
				jRes.put("msg", "创建二维码时发生错误");
				return jRes;
			}finally{
				try {
					outputStream.close();
				} catch (Exception e) {
				}
			}
		}
		waresService.updateWaresDetailPreview(uuid, detail);
		String qrCodeIrl = configService.getProjectURL() + fUploadUtils.getFolderUri() + "/" + uuid + ".png";
		jRes.put("qrCodeUrl", qrCodeIrl);
		return jRes;
	}
	@ResponseBody
	@RequestMapping("/disable/{waresId}")
	public AjaxPageResponse disableWares(@PathVariable Long waresId) {
		try {
			waresService.disableWares(waresId, true);
			return AjaxPageResponse.REFRESH_LOCAL("操作成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/enable/{waresId}")
	public AjaxPageResponse enableWares(@PathVariable Long waresId) {
		try {
			waresService.disableWares(waresId, false);
			return AjaxPageResponse.REFRESH_LOCAL("操作成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("操作失败");
		}
	}
	
	@RequestMapping("/options/{waresId}")
	public String options(@PathVariable Long waresId, Model model){
		PlainKanteenWares wares = waresService.getPlainObject(PlainKanteenWares.class, waresId);
		//根据商品id获得其所属的所有的选项组
		List<PlainKanteenWaresOptionGroup> groupList = waresOptionService.queryOptionGroups(waresId);
		//根据选项组获得所有选项
		Map<Long, List<PlainKanteenWaresOption>> optionMap = waresOptionService.queryOptionsMap(CollectionUtils.toSet(groupList, group->group.getId()));
		model.addAttribute("wares", wares);
		model.addAttribute("groupList", groupList);
		model.addAttribute("optionMap", optionMap);
		return AdminConstants.PATH_KANTEEN_WARES + "/wares_options.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/option_update")
	public AjaxPageResponse optionUpdate(@RequestBody JsonRequest jReq){
		JSONObject req = jReq.getJsonObject();
		Long waresId = req.getLong("waresId");
		try {
			if(waresId != null){
				JSONArray optionData = req.getJSONArray("optionData");
				if(optionData != null){
					Map<PlainKanteenWaresOptionGroup, List<PlainKanteenWaresOption>> groupMap = toOptionGroupMap(optionData);
					waresOptionService.updateWaresOption(waresId, groupMap);
					return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("更新成功", "wares_list");
				}
			}
		} catch (Exception e) {
			logger.error("更新商品选项时发生错误", e);
		}
		return AjaxPageResponse.FAILD("更新失败");
	}


	private Map<PlainKanteenWaresOptionGroup, List<PlainKanteenWaresOption>> toOptionGroupMap(JSONArray optionData) {
		Date now = new Date();
		Map<PlainKanteenWaresOptionGroup, List<PlainKanteenWaresOption>> groupMap = new LinkedHashMap<PlainKanteenWaresOptionGroup, List<PlainKanteenWaresOption>>(); 
		for (int i = 0; i < optionData.size(); i++) {
			JSONObject groupData = optionData.getJSONObject(i);
			JSONArray groupOptionsData = groupData.getJSONArray("options");
			if(groupOptionsData != null && !groupOptionsData.isEmpty()){
				PlainKanteenWaresOptionGroup group = new PlainKanteenWaresOptionGroup();
				group.setId(groupData.getLong("id"));
				group.setTitle(groupData.getString("name"));
				group.setMultiple(groupData.getBoolean("isMulti")?1:null);
				group.setRequired(groupData.getBoolean("isRequired")?1:null);
				group.setCreateTime(now);
				group.setOrder(i);
				List<PlainKanteenWaresOption> optionList = new ArrayList<PlainKanteenWaresOption>();
				groupMap.put(group, optionList);
				for (int j = 0; j < groupOptionsData.size(); j++) {
					JSONObject groupOptionData = groupOptionsData.getJSONObject(j);
					PlainKanteenWaresOption option = new PlainKanteenWaresOption();
					option.setId(groupOptionData.getLong("id"));
					option.setName(groupOptionData.getString("name"));
					option.setRule(groupOptionData.getString("rule"));
					option.setAdditionPrice(groupOptionData.getInteger("additionPrice"));
					option.setOptiongroupId(group.getId());
					option.setOrder(j);
					option.setCreateTime(now);
					optionList.add(option);
				}
			}
		}
		return groupMap;
	}
	
	
}
