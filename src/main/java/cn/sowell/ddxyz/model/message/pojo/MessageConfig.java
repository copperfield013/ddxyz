package cn.sowell.ddxyz.model.message.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_message_config")
public class MessageConfig {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="c_rule_expression")
	private String ruleExpression; //规则表达式
	
	@Column(name="c_back_conntent")
	private String backContent; //回复内容
	
	@Column(name="c_level")
	private Integer level; //优先级
	
	@Column(name="c_remarks")
	private String remarks; //备注
	
	@Column(name="create_time")
	private Date createTime; //创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleExpression() {
		return ruleExpression;
	}

	public void setRuleExpression(String ruleExpression) {
		this.ruleExpression = ruleExpression;
	}

	public String getBackContent() {
		return backContent;
	}

	public void setBackContent(String backContent) {
		this.backContent = backContent;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
