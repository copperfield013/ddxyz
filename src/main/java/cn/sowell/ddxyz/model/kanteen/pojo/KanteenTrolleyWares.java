package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_kanteen_trolley_wares")
public class KanteenTrolleyWares {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="trolley_id")
	private Long trolleyId;
	
	@Column(name="distributionwares_id")
	private Long distributionWaresId;
	
	@Column(name="wares_id")
	private Long waresId;
	
	@Column(name="c_count")
	private Integer count;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Transient
	@Column(name="c_wares_name")
	private Long waresName;
	
	@Transient
	@Column(name="c_base_price")
	private Integer basePrice;
	
	@Transient
	@Column(name="c_price_unit")
	private String priceUnit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTrolleyId() {
		return trolleyId;
	}

	public void setTrolleyId(Long trolleyId) {
		this.trolleyId = trolleyId;
	}

	public Long getDistributionWaresId() {
		return distributionWaresId;
	}

	public void setDistributionWaresId(Long distributionWaresId) {
		this.distributionWaresId = distributionWaresId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getWaresName() {
		return waresName;
	}

	public void setWaresName(Long waresName) {
		this.waresName = waresName;
	}

	public Long getWaresId() {
		return waresId;
	}

	public void setWaresId(Long waresId) {
		this.waresId = waresId;
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
	
	
}
