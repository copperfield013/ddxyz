package cn.sowell.ddxyz.model.canteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_trolley_option")
public class PlainKanteenTrolleyWaresOption {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="trolleywares_id")
	private Long trolleyWaresId;
	
	@Column(name="waresoption_id")
	private Long waresOptionId;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Transient
	@Column(name="option_name")
	private String optionName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTrolleyWaresId() {
		return trolleyWaresId;
	}
	public void setTrolleyWaresId(Long trolleyWaresId) {
		this.trolleyWaresId = trolleyWaresId;
	}
	public Long getWaresOptionId() {
		return waresOptionId;
	}
	public void setWaresOptionId(Long waresOptionId) {
		this.waresOptionId = waresOptionId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	
}
