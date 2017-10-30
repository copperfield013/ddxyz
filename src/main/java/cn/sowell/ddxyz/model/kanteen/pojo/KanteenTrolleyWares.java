package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import cn.sowell.copframe.utils.CollectionUtils;

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
	private String waresName;
	
	@Transient
	@Column(name="c_base_price")
	private Integer basePrice;
	
	@Transient
	@Column(name="c_price_unit")
	private String priceUnit;
	
	@Transient
	private Set<Long> wareOptionIds;
	
	@Transient
	private Set<Long> trolleyOptionIds;
	
	@Transient
	private List<String> optionNames;
	
	public String getOptionDesc(){
		if(optionNames != null && optionNames.size() > 0){
			return CollectionUtils.toChain(optionNames);
		}
		return null;
	}
	
	
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

	public String getWaresName() {
		return waresName;
	}

	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}

	public Set<Long> getWareOptionIds() {
		return wareOptionIds;
	}

	public void setWareOptionIds(Set<Long> wareOptionIds) {
		this.wareOptionIds = wareOptionIds;
	}

	public Set<Long> getTrolleyOptionIds() {
		return trolleyOptionIds;
	}

	public void setTrolleyOptionIds(Set<Long> trolleyOptionIds) {
		this.trolleyOptionIds = trolleyOptionIds;
	}

	public List<String> getOptionNames() {
		return optionNames;
	}

	public void setOptionNames(List<String> optionNames) {
		this.optionNames = optionNames;
	}


	
}
