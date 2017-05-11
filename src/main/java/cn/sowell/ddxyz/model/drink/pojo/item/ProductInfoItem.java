package cn.sowell.ddxyz.model.drink.pojo.item;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAddition;

public class ProductInfoItem {
	
	/**
	 * order的相关信息
	 */
	@Column(name="c_order_code")
	private String orderCode;
	
	
	@Column(name="c_time_point")
	private Date timePoint;
	
	@Column(name="c_receiver_contact")
	private String receiverContact;
	
	
	@Column(name="c_location_name")
	private String locationName;
	
	@Column(name="create_time")
	private Date orderCreateTime;
	
	@Column(name="c_pay_time")
	private Date payTime;
	
	@Column(name="product_id")
	private Long productId;
	
	/**
	 * DrinkProduct相关信息
	 */
	
	@Column(name="drink_product_id")
	private Long drinkProductId;
	
	
	@Column(name="c_drink_type_name")
	private String drinkName;
	
	@Column(name="c_tea_addition_name")
	private String teaAdditionName;
	
	
	@Column(name="c_sweetness")
	private Integer sweetness;
	
	
	@Column(name="c_heat")
	private Integer heat;
	
	@Column(name="c_cup_size")
	private Integer cupSize;
	
	/**
	 * plainProduct价格
	 */
	@Column(name="c_price")
	private Integer price;
	
	@Column(name="product_status")
	private Integer productStatus;
	
	/**
	 * DrinkAddition加料
	 */
	private List<PlainDrinkAddition> additions;
	
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

	public String getReceiverContact() {
		return receiverContact;
	}

	public void setReceiverContent(String receiverContact) {
		this.receiverContact = receiverContact;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	
	public Long getDrinkProductId() {
		return drinkProductId;
	}

	public void setDrinkProductId(Long drinkProductId) {
		this.drinkProductId = drinkProductId;
	}

	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public String getTeaAdditionName() {
		return teaAdditionName;
	}

	public void setTeaAdditionName(String teaAdditionName) {
		this.teaAdditionName = teaAdditionName;
	}

	public Integer getSweetness() {
		return sweetness;
	}

	public void setSweetness(Integer sweetness) {
		this.sweetness = sweetness;
	}

	public Integer getHeat() {
		return heat;
	}

	public void setHeat(Integer heat) {
		this.heat = heat;
	}

	public Integer getCupSize() {
		return cupSize;
	}

	public void setCupSize(Integer cupSize) {
		this.cupSize = cupSize;
	}

	public List<PlainDrinkAddition> getAdditions() {
		return additions;
	}

	public void setAdditions(List<PlainDrinkAddition> additions) {
		this.additions = additions;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

}
