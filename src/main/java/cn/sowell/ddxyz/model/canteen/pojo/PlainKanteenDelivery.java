package cn.sowell.ddxyz.model.canteen.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_delivery_base")
public class PlainKanteenDelivery {
	
	public static final int PAYWAY_WXPAY = 1;
	public static final int PAYWAY_SPOT = 2;
	public static final int PAYWAY_WXPAY_AND_SPOT = 3;
	public static final String DELIVERY_METHOD_FIXED = "fixed";
	public static final String DELIVERY_METHOD_HOME = "home";
	
	@SuppressWarnings("serial")
	public static final Map<Integer, String> PAYWAY_MAP = new HashMap<Integer, String>(){
		{
			this.put(PAYWAY_WXPAY, "微信支付");
			this.put(PAYWAY_SPOT, "现场支付");
			this.put(PAYWAY_WXPAY_AND_SPOT, "微信/现场支付");
		}
	};
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="c_code")
	private String code;
	
	@Column(name="distribution_id")
	private Long distributionId;
	
	@Column(name="location_id")
	private Long locationId;
	
	@Column(name="c_location_name")
	private String locationName;
	
	@Column(name="c_location_coordinate")
	private String locationCoordinate;
	
	@Column(name="c_max_distance")
	private Integer maxDistance;
	
	@Column(name="c_fee")
	private Integer fee;
	
	@Column(name="c_start_time")
	private Date startTime;
	
	@Column(name="c_end_time")
	private Date endTime;
	
	@Column(name="c_payway")
	private Integer payWay;
	
	@Column(name="c_delivery_method")
	private String deliveryMethod;
	
	@Column(name="merchant_id")
	private Long merchantId;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="update_user_id")
	private Long updateUserId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDistributionId() {
		return distributionId;
	}
	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
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
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getLocationCoordinate() {
		return locationCoordinate;
	}
	public void setLocationCoordinate(String locationCoordinate) {
		this.locationCoordinate = locationCoordinate;
	}
	public Integer getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(Integer maxDistance) {
		this.maxDistance = maxDistance;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	
}
