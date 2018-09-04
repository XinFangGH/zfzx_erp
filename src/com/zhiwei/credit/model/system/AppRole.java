package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/


import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import org.jbpm.api.identity.Group;
import org.jbpm.api.task.Participation;
import org.springframework.security.GrantedAuthority;

import com.google.gson.annotations.Expose;
import com.zhiwei.core.model.BaseModel;

@SuppressWarnings("serial")
//@XmlRootElement
//@XmlAccessorType(XmlAccessType.PROPERTY)
public class AppRole extends BaseModel implements GrantedAuthority,Group{
	
	public static String ROLE_PUBLIC="ROLE_PUBLIC";
	
	public static String ROLE_ANONYMOUS="ROLE_ANONYMOUS";
	
	/**
	 * 超级管理员的角色ID
	 */
	public static final Long SUPER_ROLEID=-1l;
	/**
	 * 超级权限
	 */
	public static final String SUPER_RIGHTS="__ALL";
	@Expose
	private Long roleId;
	@Expose
	private String roleName;
	@Expose
	private String roleDesc;
	@Expose
	private Short status;
	@Expose
	private Short isDefaultIn;
	
	@Expose
	private String rights;
	@Expose
	private String roleType;//角色类型(control是管控，business是业务)
	@Expose
	private String controlCompanyId;//管控公司id
	@Expose
	private String controlRights;//管控公司权限
	@Expose
	private Short org_type;//公司的分类（总公司是0,分公司是1）
	
	/**
	 * 个人桌面需要加载的功能
	 * 每个角色一种桌面
	 */
	@Expose
	private String desks;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	private Set<AppFunction> functions=new HashSet<AppFunction>();
	private Set<AppFunction> controlFunctions=new HashSet<AppFunction>();
	@XmlTransient
	private Set<AppUser> appUsers=new HashSet<AppUser>();
	public AppRole() {
		
	}

	public Short getIsDefaultIn() {
		return isDefaultIn;
	}

	public void setIsDefaultIn(Short isDefaultIn) {
		this.isDefaultIn = isDefaultIn;
	}
	
	
	public Set<AppUser> getAppUsers() {
		return appUsers;
	}

	public void setAppUsers(Set<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}

	public String getAuthority() {
		return roleName;
	}

	public int compareTo(Object o) {
		return 0;
	}


	@Override
	public String getId() {
		return roleId.toString();
	}


	@Override
	public String getName() {
		return roleName;
	}


	@Override
	public String getType() {//作为参与的侯选人
		return Participation.CANDIDATE;
	}

	public Set<AppFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<AppFunction> functions) {
		this.functions = functions;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getControlCompanyId() {
		return controlCompanyId;
	}

	public void setControlCompanyId(String controlCompanyId) {
		this.controlCompanyId = controlCompanyId;
	}

	public String getControlRights() {
		return controlRights;
	}

	public void setControlRights(String controlRights) {
		this.controlRights = controlRights;
	}

	public Short getOrg_type() {
		return org_type;
	}

	public void setOrg_type(Short orgType) {
		org_type = orgType;
	}

	public Set<AppFunction> getControlFunctions() {
		return controlFunctions;
	}

	public void setControlFunctions(Set<AppFunction> controlFunctions) {
		this.controlFunctions = controlFunctions;
	}

	public String getDesks() {
		return desks;
	}

	public void setDesks(String desks) {
		this.desks = desks;
	}

	
}
