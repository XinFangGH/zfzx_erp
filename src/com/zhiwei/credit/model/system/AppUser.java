package com.zhiwei.credit.model.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.identity.User;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import com.google.gson.annotations.Expose;
import com.zhiwei.core.menu.TopModule;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;

/**
 * AppUser Base Java Bean, base class for the.credit.model, mapped directly to
 * database table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 * TODO: add class/table comments
 */
//@SuppressWarnings("serial")
public class AppUser extends com.zhiwei.core.model.BaseModel implements UserDetails, User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 系统用户ID，由初始化数据加入
	 */
	public static final Long SYSTEM_USER = new Long(-1);
	/**
	 * 超级管理员ID,由初始化数据加入
	 */
	public static final Long SUPER_USER = new Long(1);
	/**
	 * 动态密码状态，０＝未绑定，１＝绑定
	 */
	public static final Short DYNPWD_STATUS_BIND = 1;
	public static final Short DYNPWD_STATUS_UNBIND = 0;

	@Expose
	protected Long userId;
	@Expose
	protected String username;
	protected String password;
	@Expose
	protected String email;
	@Expose
	protected Department department;
	@Expose
	protected Long jobId;
	@Expose
	protected String phone;
	@Expose
	protected String mobile;
	@Expose
	protected String fax;
	@Expose
	protected String address;
	@Expose
	protected String zip;
	@Expose
	protected String photo;
	@Expose
	protected java.util.Date accessionTime;
	@Expose
	protected Short status;
	@Expose
	protected String education;
	@Expose
	protected Short title;
	@Expose
	protected String fullname;
	@Expose
	protected Short delFlag;
	@Expose
	protected String dynamicPwd;
	@Expose
	protected Short dyPwdStatus;
	@Expose
	protected String depNames;
	@Expose
	protected String posNames;
	@Expose
	protected String roleNames;
	//用来判断是不是系统用户  systemUser表示系统用户  null表示非系统用户
	@Expose
	protected String userKey; 
	
	//用户的编号
	@Expose
	protected String userNumber;

	@XmlTransient
	protected Set<AppRole> roles;
	/**
	 * 用户所属公司(一般指分公司),其值在登录后加载
	 */
	protected Organization company;
	
	/**
	 * 用户头部的模块菜单，由用户登录后设置 
	 */
	private Map<String,TopModule> topModules=new LinkedHashMap<String,TopModule>();
	
	/**
	 * 用于存储该用户的桌面功能,需要合并公共的
	 * 每个角色都有一套属于该角色的功能集合
	 * @desk
	 */
	protected Set<String> deskRights = new LinkedHashSet<String>();//必须定义成LinkedHashSet(deskRights必须和数据库保存的顺序相同)
	
	/**
	 * 用于存储该用户的权限,需要合并公共的
	 */
	protected Set<String> rights = new HashSet<String>();
	/**
	 * 用户所属的组织架构
	 */
	protected Set orgs=new HashSet();
	
	protected Set positions=new HashSet();
	
	//不与数据库映射字段，以下属性只是针对于线下业绩统计而用
	protected String p2pAccount;
	protected String plainpassword;
	protected String companyName;
	protected Integer isForbidden;
	protected Long relationId;
	protected Long bpCustMemberId;
	protected Integer recommandNum;//一级推荐个数
	protected Integer secondRecommandNum;//二级推荐个数
	protected String registrationDate;//开通P2P账户日期
	protected BigDecimal infoMoneyone;//一级散标推荐金额
	protected BigDecimal infoMoneytwo;//二级散标推荐金额
	protected BigDecimal mmplanMoneyone;//一级理财计划推荐金额
	protected BigDecimal mmplanMoneytwo;//二级理财计划推荐金额
	
	protected Long offlineCusId;//系统用户id
	protected String truename;//客户姓名
	protected String cardcode;//证件号码
	protected String keystr;//投资类型
	protected String investDate;//投资日期
	protected String investlimit;//投资期限
	protected String payMoneyTimeType;//投资期限
	protected String oneDept;//一级推荐人的一级部门
	protected String secDept;//一级推荐人的二级部门
	protected String roleName;//角色
	protected String recommended;//一级推荐人
	protected String investProjectName;//投资项目名称
	protected Integer investCount;//投资次数
	protected BigDecimal sumInvestMoney;//投资金额
	//end
	
	@Expose
	protected Long shopId;//门店id
	
	protected String desks;//个人桌面需要加载的功能
	
	protected Long photoId;//上传图片fileAttach的id
	
	
	
	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public Set<String> getRights() {
		return rights;
	}

	public Map<String, TopModule> getTopModules() {
		return topModules;
	}
	public void setTopModules(Map<String, TopModule> topModules) {
		this.topModules = topModules;
	}

	/**
	 * 取得所有的Function的权限，则以_为开头的权限
	 * 
	 * @return
	 */
	public String getFunctionRights() {
		StringBuffer sb = new StringBuffer();

		Iterator<String> it = rights.iterator();

		while (it.hasNext()) {
			sb.append(it.next()).append(",");
		}

		if (rights.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	public void setRights(Set<String> rights) {
		this.rights = rights;
	}

	/**
	 * Default Empty Constructor for class AppUser
	 */
	public AppUser() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class AppUser
	 */
	public AppUser(Long in_userId) {
		this.setUserId(in_userId);
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="userId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * Set the userId
	 */
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="username" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Set the username
	 * 
	 * @spring.validator type="required"
	 */
	public void setUsername(String aValue) {
		this.username = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="password" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Set the password
	 * 
	 * @spring.validator type="required"
	 */
	public void setPassword(String aValue) {
		this.password = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="email" type="java.lang.String" length="128"
	 *                     not-null="true" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set the email
	 * 
	 * @spring.validator type="required"
	 */
	public void setEmail(String aValue) {
		this.email = aValue;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Long getJobId() {
		return this.jobId;
	}

	public void setJobId(Long aValue) {
		this.jobId = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="phone" type="java.lang.String" length="32"
	 *                     not-null="false" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Set the phone
	 */
	public void setPhone(String aValue) {
		this.phone = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="mobile" type="java.lang.String" length="32"
	 *                     not-null="false" unique="false"
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * Set the mobile
	 */
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="fax" type="java.lang.String" length="32"
	 *                     not-null="false" unique="false"
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * Set the fax
	 */
	public void setFax(String aValue) {
		this.fax = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="address" type="java.lang.String" length="64"
	 *                     not-null="false" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set the address
	 */
	public void setAddress(String aValue) {
		this.address = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="zip" type="java.lang.String" length="32"
	 *                     not-null="false" unique="false"
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * Set the zip
	 */
	public void setZip(String aValue) {
		this.zip = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="photo" type="java.lang.String" length="128"
	 *                     not-null="false" unique="false"
	 */
	public String getPhoto() {
		return this.photo;
	}

	/**
	 * Set the photo
	 */
	public void setPhoto(String aValue) {
		this.photo = aValue;
	}

	/**
	 * * @return java.util.Date
	 * 
	 * @hibernate.property column="accessionTime" type="java.util.Date"
	 *                     length="19" not-null="true" unique="false"
	 */
	public java.util.Date getAccessionTime() {
		return this.accessionTime;
	}

	/**
	 * Set the accessionTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setAccessionTime(java.util.Date aValue) {
		this.accessionTime = aValue;
	}

	/**
	 * * @return Short
	 * 
	 * @hibernate.property column="status" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}

	/**
	 * Set the status
	 * 
	 * @spring.validator type="required"
	 */
	public void setStatus(Short aValue) {
		this.status = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="education" type="java.lang.String"
	 *                     length="64" not-null="false" unique="false"
	 */
	public String getEducation() {
		return this.education;
	}

	/**
	 * Set the education
	 */
	public void setEducation(String aValue) {
		this.education = aValue;
	}

	/**
	 * * @return Short
	 * 
	 * @hibernate.property column="title" type="java.lang.Short" length="32"
	 *                     not-null="false" unique="false"
	 */
	public Short getTitle() {
		return this.title;
	}

	/**
	 * Set the title
	 */
	public void setTitle(Short aValue) {
		this.title = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="fullname" type="java.lang.String"
	 *                     length="128" not-null="false" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}

	/**
	 * Set the fullname
	 */
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */

	public Short getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Short delFlag) {
		this.delFlag = delFlag;
	}

	public String getDynamicPwd() {
		return dynamicPwd;
	}

	public void setDynamicPwd(String dynamicPwd) {
		this.dynamicPwd = dynamicPwd;
	}

	public Short getDyPwdStatus() {
		return dyPwdStatus;
	}

	public void setDyPwdStatus(Short dyPwdStatus) {
		this.dyPwdStatus = dyPwdStatus;
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "userId";
	}

	public Set<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<AppRole> roles) {
		this.roles = roles;
	}
	

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public GrantedAuthority[] getAuthorities() {
		GrantedAuthority[] rights = roles.toArray(new GrantedAuthority[roles
				.size() + 1]);
		rights[rights.length - 1] = new GrantedAuthorityImpl("ROLE_PUBLIC");
		return rights;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		if (status == 1) {
			return true;
		}
		return false;
	}

	// overwrite for

	/**
	 * Return the Id (pk) of the entity
	 */
	public String getId() {
		return userId.toString();
	}

	@Override
	public String getBusinessEmail() {
		return email;
	}

	@Override
	public String getFamilyName() {
		return fullname;
	}

	@Override
	public String getGivenName() {
		return fullname;
	}

	public boolean isSupperManage() {
		Set<AppRole> roles = getRoles();
		boolean flag = false;
		for (Iterator<AppRole> it = roles.iterator(); it.hasNext();) {
			AppRole role = it.next();
			if (role.getRoleId().shortValue() == AppRole.SUPER_ROLEID
					.shortValue()) {
				flag = true;
			}
		}
		return flag;
	}

	public Set getOrgs() {
		return orgs;
	}

	public void setOrgs(Set orgs) {
		this.orgs = orgs;
	}

	public Set<Position> getPositions() {
		return positions;
	}

	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}

	

	public Organization getCompany() {
		return company;
	}

	public void setCompany(Organization company) {
		this.company = company;
	}
	
	/**
	 * 取得用户所在部门
	 * @return
	 */
	public String getDepNames() {
		depNames = "";
		Iterator<Organization> it = getOrgs().iterator();
		while(it.hasNext()){
			Organization org = it.next();
			depNames += org.getOrgName() + ",";
		}
		if(depNames.length()>0){depNames = depNames.substring(0, depNames.length()-1);}
		return depNames;
	}

	public void setDepNames(String depNames) {
		this.depNames = depNames;
	}
	
	/**
	 * 取得用户所担任的职位
	 * @return
	 */
	public String getPosNames() {
		posNames = "";
		Iterator<Position> it =getPositions().iterator();
		while(it.hasNext()){
			Position p = it.next();
			posNames += p.getPosName() + ",";
		}
		if(posNames.length()>0){posNames = posNames.substring(0, posNames.length()-1);}
		return posNames;
	}

	public void setPosNames(String posNames) {
		this.posNames = posNames;
	}
	
	/**
	 * 取得用户所拥有的角色
	 * @return
	 */
	public String getRoleNames() {
		roleNames = "";
		Iterator<AppRole> it =getRoles().iterator();
		while(it.hasNext()){
			AppRole ar = it.next();
			roleNames += ar.getRoleName() + ",";
		}
		if(roleNames.length()>0){roleNames = roleNames.substring(0, roleNames.length()-1);}
		return roleNames;
	}
	/**
	 * 是否为超级管理员
	 * @return
	 */
	public boolean isSuperAdmin(){
		Iterator<AppRole> it =getRoles().iterator();
		while(it.hasNext()){
			AppRole ar = it.next();
			if("超级管理员".equals(ar.getRoleName())){
				return true;
			}
		}
		return false;
	}
	
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	
	public void init(){
		// 进行合并权限的处理
		Set<AppRole> roleSet = getRoles();
	
		Set<Position> posSet = getPositions();
		Iterator<Position> posit = posSet.iterator();
		while(posit.hasNext()){
			Position pos=posit.next();
			roleSet.addAll(pos.getRoles());
		}
		Iterator<AppRole> it = roleSet.iterator();
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());		
		
		if(flag){
			String roleType=ContextUtil.getRoleTypeSession();
			getRights().clear();
			getTopModules().clear();
			while(it.hasNext()){
				AppRole role=it.next();
				if(role.getRoleId().equals(AppRole.SUPER_ROLEID)||"__ALL".equals(role.getRights())){//具有超级权限
					getDeskRights().clear();//清空桌面角色
					getRights().clear();
					getTopModules().clear();
					getRights().add(AppRole.SUPER_RIGHTS);
					getTopModules().putAll(AppUtil.getAllTopModels());
					break;
				}else{
					if((null!=roleType && roleType.equals("control") && null!=role.getRoleType() && role.getRoleType().equals("control")) ||
					   (null!=roleType && roleType.equals("business") && null!=role.getRoleType() && role.getRoleType().equals("business"))){
						if(StringUtils.isNotEmpty(role.getRights())){
							String[]items=role.getRights().split("[,]");
							for(int i=0;i<items.length;i++){
								String item=items[i];
								//System.out.println("item-group==="+item);
								//代表模板菜单,即上面的菜单
								if(item.startsWith("Mod_")){
									if(!getTopModules().containsKey(item)){
										getTopModules().put(items[i], AppUtil.getAllTopModels().get(item));
									}
									continue;
								}
								if(!getRights().contains(item)){
									getRights().add(item);
								}
							}
						}
					}
					//@desk  合并当前用户下所有角色里的桌面功能_开始
					if(StringUtils.isNotEmpty(role.getDesks())){
						String[] items=role.getDesks().split("[,]");
						for(int i=0;i<items.length;i++){
							String item=items[i];
							if(!getDeskRights().contains(item)){
								getDeskRights().add(item);
							}
						}
					}
					//@desk  合并当前用户下所有角色里的桌面功能_结束
				}
			}
		}else{
			while(it.hasNext()){
				AppRole role=it.next();
				if(role.getRoleId().equals(AppRole.SUPER_ROLEID)||"__ALL".equals(role.getRights())){//具有超级权限
					getDeskRights().clear();//清空桌面角色
					getRights().clear();
					getTopModules().clear();
					getRights().add(AppRole.SUPER_RIGHTS);
					getTopModules().putAll(AppUtil.getAllTopModels());
					break;
				}else{			
					if(StringUtils.isNotEmpty(role.getRights())){
						String[]items=role.getRights().split("[,]");
						for(int i=0;i<items.length;i++){
							String item=items[i];
							//System.out.println("item==="+item);
							//代表模板菜单,即上面的菜单
							if(item.startsWith("Mod_")){
								if(!getTopModules().containsKey(item)){
									getTopModules().put(items[i], AppUtil.getAllTopModels().get(item));
								}
								continue;
							}
							if(!getRights().contains(item)){
								getRights().add(item);
							}
						}
					}
					//@desk  合并当前用户下所有角色里的桌面功能_开始
					if(StringUtils.isNotEmpty(role.getDesks())){
						String[] items=role.getDesks().split("[,]");
						for(int i=0;i<items.length;i++){
							String item=items[i];
							if(!getDeskRights().contains(item)){
								getDeskRights().add(item);
							}
						}
					}
					//@desk  合并当前用户下所有角色里的桌面功能_结束
				}
			}
		
		}

	}

	
	/**
	 * 初始化菜单权限信息
	 */
	public void initMenuRights(){
		//取得公共TopMenuId
		getTopModules().putAll(AppUtil.getPublicTopModules());
		
		// 进行合并权限的处理
		Set<AppRole> roleSet = getRoles();
		Iterator<AppRole> it = roleSet.iterator();

		while (it.hasNext()) {
			AppRole role = it.next();
			if (role.getRoleId().equals(
					AppRole.SUPER_ROLEID)) {// 具有超级权限
				getRights().clear();
				getTopModules().clear();
				getRights().add(AppRole.SUPER_RIGHTS);
				getTopModules().putAll(AppUtil.getAllTopModels());
				break;
			} else {
				if (StringUtils.isNotEmpty(role.getRights())) {
					String[] items = role.getRights().split("[,]");
					for (int i = 0; i < items.length; i++) {
						String item = items[i];
						// 代表模板菜单,即上面的菜单
						if (item.startsWith("Mod_")) {
							if (!getTopModules().containsKey(item)) {
								getTopModules().put(items[i],AppUtil.getAllTopModels().get(item));
							}
							continue;
						}
						if (!getRights().contains(item)) {
							getRights().add(item);
						}
					}
				}
			}
		}
		
		//排序用户的topModules;
		List<TopModule> list=new ArrayList<TopModule>();
		list.addAll(getTopModules().values());
		Collections.sort(list, new Comparator<TopModule>() {
			@Override
			public int compare(TopModule o1, TopModule o2) {
				if(o1.getSn()>o2.getSn()){
					return 1;
				}
				return 0;
			}
		});
		getTopModules().clear();
		for(TopModule topMod:list){
			getTopModules().put(topMod.getId(),topMod);
		}
	}

	public String getP2pAccount() {
		return p2pAccount;
	}

	public void setP2pAccount(String p2pAccount) {
		this.p2pAccount = p2pAccount;
	}

	public String getPlainpassword() {
		return plainpassword;
	}

	public void setPlainpassword(String plainpassword) {
		this.plainpassword = plainpassword;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getBpCustMemberId() {
		return bpCustMemberId;
	}

	public void setBpCustMemberId(Long bpCustMemberId) {
		this.bpCustMemberId = bpCustMemberId;
	}

	public Integer getIsForbidden() {
		return isForbidden;
	}

	public void setIsForbidden(Integer isForbidden) {
		this.isForbidden = isForbidden;
	}

	public Integer getRecommandNum() {
		return recommandNum;
	}

	public void setRecommandNum(Integer recommandNum) {
		this.recommandNum = recommandNum;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Integer getSecondRecommandNum() {
		return secondRecommandNum;
	}

	public void setSecondRecommandNum(Integer secondRecommandNum) {
		this.secondRecommandNum = secondRecommandNum;
	}

	public BigDecimal getInfoMoneyone() {
		return infoMoneyone;
	}

	public void setInfoMoneyone(BigDecimal infoMoneyone) {
		this.infoMoneyone = infoMoneyone;
	}

	public BigDecimal getInfoMoneytwo() {
		return infoMoneytwo;
	}

	public void setInfoMoneytwo(BigDecimal infoMoneytwo) {
		this.infoMoneytwo = infoMoneytwo;
	}

	public BigDecimal getMmplanMoneyone() {
		return mmplanMoneyone;
	}

	public void setMmplanMoneyone(BigDecimal mmplanMoneyone) {
		this.mmplanMoneyone = mmplanMoneyone;
	}

	public BigDecimal getMmplanMoneytwo() {
		return mmplanMoneytwo;
	}

	public void setMmplanMoneytwo(BigDecimal mmplanMoneytwo) {
		this.mmplanMoneytwo = mmplanMoneytwo;
	}

	public Long getOfflineCusId() {
		return offlineCusId;
	}

	public void setOfflineCusId(Long offlineCusId) {
		this.offlineCusId = offlineCusId;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public String getKeystr() {
		return keystr;
	}

	public void setKeystr(String keystr) {
		this.keystr = keystr;
	}

	public String getInvestDate() {
		return investDate;
	}

	public void setInvestDate(String investDate) {
		this.investDate = investDate;
	}

	public String getInvestlimit() {
		return investlimit;
	}

	public void setInvestlimit(String investlimit) {
		this.investlimit = investlimit;
	}

	public String getOneDept() {
		return oneDept;
	}

	public void setOneDept(String oneDept) {
		this.oneDept = oneDept;
	}

	public String getSecDept() {
		return secDept;
	}

	public void setSecDept(String secDept) {
		this.secDept = secDept;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getInvestProjectName() {
		return investProjectName;
	}

	public void setInvestProjectName(String investProjectName) {
		this.investProjectName = investProjectName;
	}

	public Integer getInvestCount() {
		return investCount;
	}

	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
	}

	public BigDecimal getSumInvestMoney() {
		return sumInvestMoney;
	}

	public void setSumInvestMoney(BigDecimal sumInvestMoney) {
		this.sumInvestMoney = sumInvestMoney;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getDesks() {
		return desks;
	}

	public void setDesks(String desks) {
		this.desks = desks;
	}

	public Set<String> getDeskRights() {
		return deskRights;
	}

	public void setDeskRights(Set<String> deskRights) {
		this.deskRights = deskRights;
	}

	public String getPayMoneyTimeType() {
		return payMoneyTimeType;
	}

	public void setPayMoneyTimeType(String payMoneyTimeType) {
		this.payMoneyTimeType = payMoneyTimeType;
	}
	
}