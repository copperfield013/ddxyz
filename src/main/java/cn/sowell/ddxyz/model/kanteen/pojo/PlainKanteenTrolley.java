package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_trolley_base")
public class PlainKanteenTrolley {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="distribution_id")
	private Long distributionId;
	
	@Column(name="merchant_id")
	private Long merchantId;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="c_disabled")
	private Integer disabled;
	
	@Column(name="create_time")
	private Date createTime;
	
	
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
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	
}
