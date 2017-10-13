package cn.sowell.ddxyz.model.kanteen.pojo;

import javax.persistence.Column;

import cn.sowell.ddxyz.model.kanteen.service.impl.Orderable;

/**
 * 
 * <p>Title: KanteenMenuItem</p>
 * <p>Description: </p><p>
 * 
 * </p>
 * @author Copperfield Zhang
 * @date 2017年10月12日 上午9:42:01
 */
public class KanteenDistributionMenuItem implements Orderable{
	//配销中商品的id
	@Column(name="distributionwares_id")
	private Long distributionWaresId;
	
	@Column(name="distribution_id")
	private Long distributionId;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="group_id")
	private Long groupId;
	
	@Column(name="menu_id")
	private Long menuId;
	
	//关联到waresGroup
	@Column(name="menuwares_id")
	private Long menuWaresId;
	
	@Column(name="wares_name")
	private String waresName;
	
	@Column(name="c_pic_uri")
	private String picUri;
	
	@Column(name="c_max_count")
	private Integer maxCount;
	
	@Column(name="c_current_count")
	private Integer currentCount;
	
	@Column(name="c_base_price")
	private Integer basePrice;
	
	@Column(name="c_price_unit")
	private String priceUnit;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	@Column(name="c_order")
	private Integer order;
	
	public Long getDistributionWaresId() {
		return distributionWaresId;
	}
	public void setDistributionWaresId(Long distributionWaresId) {
		this.distributionWaresId = distributionWaresId;
	}
	public Long getDistributionId() {
		return distributionId;
	}
	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
	public Long getMenuWaresId() {
		return menuWaresId;
	}
	public void setMenuWaresId(Long menuWaresId) {
		this.menuWaresId = menuWaresId;
	}
	public String getWaresName() {
		return waresName;
	}
	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}
	public String getPicUri() {
		return picUri;
	}
	public void setPicUri(String picUri) {
		this.picUri = picUri;
	}
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public Integer getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}
	
}
