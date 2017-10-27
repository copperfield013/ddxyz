package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_delivery_canceloption")
public class PlainKanteenCancelOption {
	/**
	 * 不可取消
	 */
	public static final int OPTION_BANNED = 1;
	
	/**
	 * 限时取消
	 */
	public static final int OPTION_LIMITED = 2;
	/**
	 * 协商取消
	 */
	public static final int OPTION_NEGOTIATE = 3;
	/**
	 * 可直接取消
	 */
	public static final int OPTION_DIRECTED = 4;
	
	
	/**
	 * 只在订单完成前有效
	 */
	public static final int VALIDITY_PRECOMPLETED = 1;
	/**
	 * 订单完成后依然有效
	 */
	public static final int VALIDITY_ALWAYS = 2;
	/**
	 * 订单完成前，以及未领取前有效
	 */
	public static final int VALIDITY_PRECOMPLETED_UNMISSED = 3;
	
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="distribution_id")
	private Long distributionId;
	
	@Column(name="delivery_id")
	private Long deliveryId;
	
	@Column(name="c_canceloption")
	private Integer cancelOption;
	
	@Column(name="c_deadlines")
	private Date deadline;
	
	@Column(name="c_contact")
	private String contact;
	
	@Column(name="c_validity")
	private Integer validity;
	
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
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public Integer getCancelOption() {
		return cancelOption;
	}
	public void setCancelOption(Integer cancelOption) {
		this.cancelOption = cancelOption;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
