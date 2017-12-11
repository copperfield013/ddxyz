package cn.sowell.ddxyz.model.kanteen.pojo;

import javax.persistence.Column;

public class KanteenMenuWares {
	@Column(name="menu_id")
	private Long menuId;
	
	@Column(name="menugroup_id")
	private Long menuGroupId;
	
	@Column(name="group_id")
	private Long groupId;
	
	@Column(name="groupwares_id")
	private Long groupWaresId;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="menu_name")
	private String menuName;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="wares_name")
	private String waresName;
	
	@Column(name="menu_disabled")
	private Integer menuDisabled;
	
	@Column(name="menugroup_disabled")
	private Integer menuGroupDisabled;
	
	@Column(name="group_disabled")
	private Integer groupDisabled;
	
	@Column(name="groupwares_disabled")
	private Integer groupWaresDisabled;
	
	@Column(name="wares_disabled")
	private Integer waresDisabled;
	
	@Column(name="wares_deleted")
	private Integer waresDeleted;
	
	@Column(name="c_base_price")
	private Integer basePrice;
	
	@Column(name="c_price_unit")
	private String priceUnit;
	
	@Column(name="c_unsalable")
	private Integer unsalable;
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getMenuGroupId() {
		return menuGroupId;
	}
	public void setMenuGroupId(Long menuGroupId) {
		this.menuGroupId = menuGroupId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getGroupWaresId() {
		return groupWaresId;
	}
	public void setGroupWaresId(Long groupWaresId) {
		this.groupWaresId = groupWaresId;
	}
	public Long getWaresId() {
		return waresId;
	}
	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getWaresName() {
		return waresName;
	}
	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}
	public Integer getMenuDisabled() {
		return menuDisabled;
	}
	public void setMenuDisabled(Integer menuDisabled) {
		this.menuDisabled = menuDisabled;
	}
	public Integer getMenuGroupDisabled() {
		return menuGroupDisabled;
	}
	public void setMenuGroupDisabled(Integer menuGroupDisabled) {
		this.menuGroupDisabled = menuGroupDisabled;
	}
	public Integer getGroupDisabled() {
		return groupDisabled;
	}
	public void setGroupDisabled(Integer groupDisabled) {
		this.groupDisabled = groupDisabled;
	}
	public Integer getGroupWaresDisabled() {
		return groupWaresDisabled;
	}
	public void setGroupWaresDisabled(Integer groupWaresDisabled) {
		this.groupWaresDisabled = groupWaresDisabled;
	}
	public Integer getWaresDisabled() {
		return waresDisabled;
	}
	public void setWaresDisabled(Integer waresDisabled) {
		this.waresDisabled = waresDisabled;
	}
	public Integer getWaresDeleted() {
		return waresDeleted;
	}
	public void setWaresDeleted(Integer waresDeleted) {
		this.waresDeleted = waresDeleted;
	}
	public Integer getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public Integer getUnsalable() {
		return unsalable;
	}
	public void setUnsalable(Integer unsalable) {
		this.unsalable = unsalable;
	}
}
