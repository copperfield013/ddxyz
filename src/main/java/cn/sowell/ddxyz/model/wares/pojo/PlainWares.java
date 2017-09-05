package cn.sowell.ddxyz.model.wares.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Lazy;
/**
 * 
 * <p>Title: PlainWares</p>
 * <p>Description: </p><p>
 * 商品POJO
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月11日 下午6:48:13
 */
@Entity
@Table(name="t_wares_base")
public class PlainWares {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="c_name")
	private String name;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	@Column(name="c_base_price")
	private Integer basePrice;
	
	@Column(name="c_price_unit")
	private String priceUnit;
	
	@Column(name="merchant_id")
	private Long merchantId;
	
	@Column(name="c_thumb_uri")
	private String thumbUri;
	
	@Lazy
	@Column(name="c_detail")
	private String detail;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="c_unsalable")
	private Integer unsalable;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Integer basePrice) {
		this.basePrice = basePrice;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getThumbUri() {
		return thumbUri;
	}
	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getUnsalable() {
		return unsalable;
	}
	public void setUnsalable(Integer unsalable) {
		this.unsalable = unsalable;
	}
}
