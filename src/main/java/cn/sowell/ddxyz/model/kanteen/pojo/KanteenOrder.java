package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.weixin.pay.prepay.GoodsDetail;
import cn.sowell.copframe.weixin.pay.service.impl.WxPayOrder;
import cn.sowell.ddxyz.model.weixin.pojo.WeiXinUser;

public class KanteenOrder implements WxPayOrder{
	private PlainKanteenOrder plainOrder = new PlainKanteenOrder();
	private List<PlainKanteenSection> sectionList = new ArrayList<PlainKanteenSection>();
	private Map<Long, Integer> waresCountMap = new LinkedHashMap<Long, Integer>();
	private boolean createProduct = false;
	private WeiXinUser orderUser;
	private String payTitle = "林家食堂";
	
	public PlainKanteenOrder getPlainOrder() {
		return plainOrder;
	}
	public void setPlainOrder(PlainKanteenOrder plainOrder) {
		this.plainOrder = plainOrder;
	}
	public List<PlainKanteenSection> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<PlainKanteenSection> sectionList) {
		this.sectionList = sectionList;
	}
	
	public void putWares(Long distrbutionWaresId, Integer count){
		waresCountMap.put(distrbutionWaresId, count);
	}
	
	public Map<Long, Integer> getWaresCountMap() {
		return waresCountMap;
	}
	@Override
	public WeiXinUser getOrderUser() {
		return orderUser;
	}
	@Override
	public Integer getTotalPrice() {
		return plainOrder.getTotalPrice();
	}
	@Override
	public Date getPayExpireTime() {
		return plainOrder.getPayExpiredTime();
	}
	@Override
	public List<GoodsDetail> getGoodsDetails() {
		List<GoodsDetail> gds = new ArrayList<GoodsDetail>();
		this.sectionList.forEach(section->{
			GoodsDetail gd = new GoodsDetail();
			gd.setGoodsId(FormatUtils.toString(section.getId()));
			gd.setGoodsName(section.getWaresName());
			gd.setPrice(section.getBasePrice());
			gd.setQuantity(section.getCount());
		});
		return gds;
	}
	
	
	public void setOrderUser(WeiXinUser orderUser) {
		this.orderUser = orderUser;
	}
	public boolean isCreateProduct() {
		return createProduct;
	}
	public void setCreateProduct(boolean createProduct) {
		this.createProduct = createProduct;
	}
	
	public Long getOrderId(){
		return plainOrder.getId();
	}
	
	public void setOrderId(Long orderId){
		plainOrder.setId(orderId);
	}
	public String getPayTitle() {
		return payTitle;
	}
	public void setPayTitle(String payTitle) {
		this.payTitle = payTitle;
	}
	
	
	
}
