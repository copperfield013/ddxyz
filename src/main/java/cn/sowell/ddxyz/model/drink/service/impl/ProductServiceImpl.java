package cn.sowell.ddxyz.model.drink.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.CommonPageInfo;
import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.ddxyz.model.common.core.ProductManager;
import cn.sowell.ddxyz.model.common.core.exception.ProductException;
import cn.sowell.ddxyz.model.common.pojo.PlainProduct;
import cn.sowell.ddxyz.model.drink.dao.DrinkAdditionDao;
import cn.sowell.ddxyz.model.drink.dao.ProductDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductCriteria;
import cn.sowell.ddxyz.model.drink.pojo.criteria.ProductionCriteria;
import cn.sowell.ddxyz.model.drink.pojo.item.ProductInfoItem;
import cn.sowell.ddxyz.model.drink.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Resource
	ProductDao productDao;
	
	@Resource
	DrinkAdditionDao drinkAdditionDao;
	
	@Resource
	ProductManager pManager;
	
	Logger logger = Logger.getLogger(ProductService.class);
	

	@Override
	public List<ProductInfoItem> getProductInfoItemList(ProductionCriteria criteria) {
		List<ProductInfoItem> list = productDao.getProductInfoItemPageList(criteria, null);
		return setProductInfoItemAdditions(list);
	}

	@Override
	public List<ProductInfoItem> getProductInfoItemListByProductIds(List<Long> productIdsList) throws ProductException {
		List<ProductInfoItem> list = productDao.getProductInfoItemListByProductIds(productIdsList);
		List<ProductInfoItem> result = setProductInfoItemAdditions(list);
		List<Long> productIds = CollectionUtils.toList(result, item->item.getProductId());
		pManager.setProductsPrinted(productIds);
		return result;
	}

	@Override
	public List<ProductInfoItem> getProductInfoItemPageList(ProductionCriteria criteria, CommonPageInfo pageInfo) {
		List<ProductInfoItem> list = productDao.getProductInfoItemPageList(criteria, pageInfo);
		return setProductInfoItemAdditions(list);
	}
	
	private List<ProductInfoItem> setProductInfoItemAdditions(List<ProductInfoItem> list){
		if(list != null && list.size()>0){
			for(ProductInfoItem productInfoItem : list){
				List<PlainDrinkAddition> additions = drinkAdditionDao.getDrinkAdditionList(productInfoItem.getDrinkProductId());
				productInfoItem.setAdditions(additions);
			}
		}
		return list;
	}

	@Override
	public int getProductPrintedCountByStatus(Integer status, Date timePoint) {
		return productDao.getProductPrintedCountByStatus(status, timePoint);
	}

	@Override
	public int getProductNotPrintCountByStatus(Integer status, Date date) {
		return productDao.getProductNotPrintCountByStatus(status, date);
	}

	@Override
	public List<ProductInfoItem> getProductInfoItemList(ProductCriteria criteria) {
		List<ProductInfoItem> list = productDao.getProductInfoItemPageList(criteria, null);
		return setProductInfoItemAdditions(list);
	}

	@Override
	public List<ProductInfoItem> getProductInfoItemPageList(ProductCriteria criteria, CommonPageInfo pageInfo) {
		List<ProductInfoItem> list = productDao.getProductInfoItemPageList(criteria, pageInfo);
		return setProductInfoItemAdditions(list);
	}
	
	@Override
	public String encodeQrCode(String dispenseCode) {
		Pattern pattern = Pattern.compile("^(\\d{8})(\\d{2})(\\d{4})(\\d{3})$");
		
		Matcher matcher = pattern.matcher(dispenseCode);
		if(matcher.matches()){
			String ymd = matcher.group(1),
					timePoint = matcher.group(2),
					locationKey = matcher.group(3),
					dispenseKey = matcher.group(4);
			StringBuffer code = new StringBuffer();
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Long result = null;
			try {
				result = ChronoUnit.DAYS.between((new Date(0l)).toInstant(), df.parse(ymd).toInstant());
			} catch (ParseException e) {
				logger.error("转换错误，无法根据分配号[" + dispenseCode + "]生成二维码原始编码", e);
			}
			if(result != null){
				code.append(TextUtils.convert(result, 62, 3));
				code.append(TextUtils.convert(Long.valueOf(timePoint), 62, 1));
				code.append(TextUtils.convert(Long.valueOf(locationKey), 62, 2));
				code.append(TextUtils.convert(Long.valueOf(dispenseKey), 62, 2));
				return code.toString();
			}
		}
		return null;
	}
	
	
	@Override
	public String decodeQrCode(String qrCode) {
		Pattern patterm = Pattern.compile("^(\\w{3})(\\w)(\\w{2})(\\w{2})$");
		Matcher matcher = patterm.matcher(qrCode);
		if(matcher.matches()){
			StringBuffer buffer = new StringBuffer();
			String daysCode = matcher.group(1),
					timepointCode = matcher.group(2),
					locationCode = matcher.group(3),
					dispenseCode = matcher.group(4);
			Date date = Date.from(ChronoUnit.DAYS.addTo((new Date(0l).toInstant()), TextUtils.decode(daysCode, 62)));
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			buffer.append(df.format(date));
			buffer.append(TextUtils.prependZeros(TextUtils.decode(timepointCode, 62), 2));
			buffer.append(TextUtils.prependZeros(TextUtils.decode(locationCode, 62), 4));
			buffer.append(TextUtils.prependZeros(TextUtils.decode(dispenseCode, 62), 3));
			return buffer.toString();
		}
		return null;
	}
	
	@Override
	public PlainProduct getProductByQrCode(String qrCode) {
		String dispenseCode = decodeQrCode(qrCode);
		return productDao.getProductionByDispenseCode(dispenseCode);
	}

}
