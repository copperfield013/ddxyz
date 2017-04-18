package cn.sowell.ddxyz.model.weixin.pojo;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cn.sowell.copframe.common.UserIdentifier;
import cn.sowell.copframe.weixin.authentication.WxUserPrincipal;
import cn.sowell.ddxyz.DdxyzConstants;

@Entity
@Table(name="t_weixin_user")
public class WeiXinUser implements WxUserPrincipal, UserIdentifier{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="c_openid")
	private String openid;
	
	@Column(name="c_subscribe")
	private Integer subscribe;
	
	//用户关注时间。如果用户曾多次关注，则取最后关注时间
	@Column(name="c_subscribe_time")
	private Date subscribeTime;
	
	@Column(name="c_nickname")
	private String nickname;
	
	@Column(name="c_sex")
	private String sex;
	
	//用户的语言，简体中文为zh_CN
	@Column(name="c_language")
	private String language;
	
	@Column(name="c_city")
	private String city;
	
	@Column(name="c_province")
	private String province;
	
	@Column(name="c_country")
	private String country;
	
	//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	@Column(name="c_headimg_url")
	private String headimgUrl;
	
	@Column(name="c_contact_num")
	private String contactNumber;
	
	//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	@Column(name="c_union_id")
	private String unionid;
	
	//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	@Column(name="c_remark")
	private String remark;
	
	//用户所在的分组ID
	@Column(name="c_group_id")
	private String groupId;
	
	//权限列表
	@Column(name="c_authority_chain")
	private String authorityChain;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="c_privilege")
	private String privilege;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String getOpenid() {
		return openid;
	}
	@Override
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Override
	public Integer getSubscribe() {
		return subscribe;
	}
	@Override
	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}
	@Override
	public String getNickname() {
		return nickname;
	}
	@Override
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String getSex() {
		return sex;
	}
	@Override
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String getLanguage() {
		return language;
	}
	@Override
	public void setLanguage(String language) {
		this.language = language;
	}
	@Override
	public String getCity() {
		return city;
	}
	@Override
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String getProvince() {
		return province;
	}
	@Override
	public void setProvince(String province) {
		this.province = province;
	}
	@Override
	public String getCountry() {
		return country;
	}
	@Override
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String getHeadimgUrl() {
		return headimgUrl;
	}
	@Override
	public void setHeadimgUrl(String headimgUrl) {
		this.headimgUrl = headimgUrl;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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
	@Override
	public Date getSubscribeTime() {
		return subscribeTime;
	}
	@Override
	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	@Override
	public String getUnionid() {
		return unionid;
	}
	@Override
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	@Override
	public String getRemark() {
		return remark;
	}
	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String getGroupId() {
		return groupId;
	}
	@Override
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAuthorityChain() {
		return authorityChain;
	}
	public void setAuthorityChain(String authorityChain) {
		this.authorityChain = authorityChain;
	}
	@Override
	public Set<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> set = new LinkedHashSet<GrantedAuthority>();
		if(this.authorityChain != null){
			String[] split = this.authorityChain.split(DdxyzConstants.COMMON_SPLITER);
			for (String str : split) {
				if(!str.isEmpty()){
					set.add(new SimpleGrantedAuthority(str));
				}
			}
		}
		return set;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
}
