package cn.sowell.ddxyz.admin.controller.canteen;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.sowell.copframe.common.file.FileUploadUtils;
import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.admin.AdminConstants;
import cn.sowell.ddxyz.model.canteen.pojo.criteria.CanteenWaresListCriteria;
import cn.sowell.ddxyz.model.canteen.service.CanteenWaresService;
import cn.sowell.ddxyz.model.wares.pojo.PlainWares;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/canteen/wares")
public class AdminCanteenWaresController {
	
	@Resource
	CanteenWaresService waresService;
	
	@Resource
	FileUploadUtils fUploadUtils;
	
	Logger logger = Logger.getLogger(AdminCanteenConfigController.class); 
	
	@RequestMapping("/list")
	public String list(CanteenWaresListCriteria criteria, CommonPageInfo pageInfo, Model model) {
		List<PlainWares> waresList = waresService.getCanteenWaresList(criteria, pageInfo);
		model.addAttribute("waresList", waresList);
		return AdminConstants.PATH_CANTEEN + "/wares/canteen_wares_list.jsp";
	}
	
	@RequestMapping("/add")
	public String add() {
		return AdminConstants.PATH_CANTEEN + "/wares/canteen_wares_add.jsp";
	}
	
	
	@ResponseBody
	@RequestMapping("/do_add")
	public AjaxPageResponse doCreateWares(
			@RequestParam("thumb") MultipartFile file,
			@RequestParam("waresName") String waresName, 
			@RequestParam("unitPrice") Float unitPrice, 
			@RequestParam("priceUnit") String priceUnit
			){
		PlainWares wares = new PlainWares();
		wares.setBasePrice((int)(unitPrice * 100));
		wares.setPriceUnit(priceUnit);
		wares.setName(waresName);
		
		String[] nameSplit = file.getOriginalFilename().split("\\.");
		String suffix = nameSplit[nameSplit.length - 1];
		String fileName = "f_" + TextUtils.uuid(10, 36) + "." + suffix;
		try {
			wares.setThumbUri(fUploadUtils.saveFile(fileName, file.getInputStream()));
			waresService.saveWares(wares);
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("创建成功", "canteen-wares-list");
		} catch (IOException e) {
			logger.error("上传文件失败", e);
			return AjaxPageResponse.FAILD("创建失败");
		}
	}
	
	@RequestMapping("/update/{waresId}")
	public String update(@PathVariable Long waresId, Model model) {
		PlainWares wares = waresService.getPlainObject(PlainWares.class, waresId);
		model.addAttribute("wares", wares);
		return AdminConstants.PATH_CANTEEN + "/wares/canteen_wares_update.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/do_update")
	public AjaxPageResponse doUpdate(
			@RequestParam("waresId") Long waresId,
			@RequestParam(value="thumb", required=false) MultipartFile file,
			@RequestParam(value="waresName") String waresName, 
			@RequestParam(value="unitPrice") Float unitPrice, 
			@RequestParam(value="priceUnit") String priceUnit,
			@RequestParam(value="hasThumb", required=false) Boolean hasThumb) {
		try {
			PlainWares originWares = waresService.getPlainObject(PlainWares.class, waresId);
			
			PlainWares wares = new PlainWares();
			wares.setId(waresId);
			wares.setBasePrice((int)(unitPrice * 100));
			wares.setPriceUnit(priceUnit);
			wares.setName(waresName);
			
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
			return AjaxPageResponse.CLOSE_AND_REFRESH_PAGE("修改成功", "canteen-wares-list");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("修改时发生错误");
		}
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
	
	
}
