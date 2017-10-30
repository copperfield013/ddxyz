package cn.sowell.ddxyz.model.kanteen.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_section_option")
public class PlainKanteenSectionOption {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="section_id")
	private Long sectionId;
	
	@Column(name="waresoption_id")
	private Long waresOptionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getWaresOptionId() {
		return waresOptionId;
	}

	public void setWaresOptionId(Long waresOptionId) {
		this.waresOptionId = waresOptionId;
	}
	
	
	
}
