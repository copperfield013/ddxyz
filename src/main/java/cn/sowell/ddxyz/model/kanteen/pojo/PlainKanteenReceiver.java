package cn.sowell.ddxyz.model.kanteen.pojo;

import javax.persistence.Column;

public class PlainKanteenReceiver {
	@Column(name="order_user_id")
	private Long id;
	
	@Column(name="c_receiver_name")
	private String name;
	
	@Column(name="c_receiver_contact")
	private String contact;
	
	@Column(name="c_receiver_depart")
	private String depart;
	
	@Column(name="location_id")
	private Long locationId;
	
	@Column(name="c_payway")
	private Integer payway;
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Integer getPayway() {
		return payway;
	}
	public void setPayway(Integer payway) {
		this.payway = payway;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	
}
