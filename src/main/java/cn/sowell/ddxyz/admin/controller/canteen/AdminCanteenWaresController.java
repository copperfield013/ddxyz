package cn.sowell.ddxyz.admin.controller.canteen;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
import cn.sowell.copframe.dto.ajax.JsonResponse;
import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.copframe.utils.qrcode.QrCodeUtils;
import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.ddxyz.DdxyzConstants;
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
	
	@Resource
	WxConfigService configService;
	
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
			PlainWares wares,
			String waresName,
			Float unitPrice
			){
		wares.setBasePrice((int)(unitPrice * 100));
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
			PlainWares wares,
			String waresName,
			Float unitPrice,
			@RequestParam(value="hasThumb", required=false) Boolean hasThumb) {
		try {
			PlainWares originWares = waresService.getPlainObject(PlainWares.class, waresId);
			
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
	@RequestMapping("/enable_sale/{waresId}")
	public AjaxPageResponse enableSale(@PathVariable Long waresId){
		try {
			waresService.updateWaresSalable(waresId, true);
			return AjaxPageResponse.REFRESH_LOCAL("修改成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/disable_sale/{waresId}")
	public AjaxPageResponse disableSale(@PathVariable Long waresId){
		try {
			waresService.updateWaresSalable(waresId, false);
			return AjaxPageResponse.REFRESH_LOCAL("修改成功");
		} catch (Exception e) {
			return AjaxPageResponse.FAILD("修改失败");
		}
	}
	
	
	
	
}
