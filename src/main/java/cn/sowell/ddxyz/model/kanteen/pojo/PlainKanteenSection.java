package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_section_base")
public class PlainKanteenSection {
	
	public static final String STATUS_DEFAULT = "default";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="c_waresname")
	private String waresName;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="distributionwares_id")
	private Long distributionWaresId;
	
	@Column(name="menuwares_id")
	private Long menuWaresId;
	
	@Column(name="c_count")
	private Integer count;
	
	@Column(name="c_order")
	private Integer order;
	
	@Column(name="c_total_price")
	private Integer totalPrice;
	
	@Column(name="c_base_price")
	private Integer basePrice;
	
	@Column(name="c_price_unit")
	private String priceUnit;
	
	@Column(name="c_status")
	private String status;
	
	@Column(name="c_canceled_status")
	private String canceledStatus;
	
	@Column(name="c_refund_fee")
	private Integer refundFee;
	
	@Column(name="c_thumb_uri")
	private String thumbUri;
	
	@Column(name="c_option_desc")
	private String optionsDesc;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getWaresName() {
		return waresName;
	}

	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}

	public Long getWaresId() {
		return waresId;
	}

	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}

	public Long getDistributionWaresId() {
		return distributionWaresId;
	}

	public void setDistributionWaresId(Long distributionWaresId) {
		this.distributionWaresId = distributionWaresId;
	}

	public Long getMenuWaresId() {
		return menuWaresId;
	}

	public void setMenuWaresId(Long menuWaresId) {
		this.menuWaresId = menuWaresId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCanceledStatus() {
		return canceledStatus;
	}

	public void setCanceledStatus(String canceledStatus) {
		this.canceledStatus = canceledStatus;
	}

	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	public String getThumbUri() {
		return thumbUri;
	}

	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOptionsDesc() {
		return optionsDesc;
	}

	public void setOptionsDesc(String optionsDesc) {
		this.optionsDesc = optionsDesc;
	}
}
