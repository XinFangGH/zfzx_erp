package com.zhiwei.credit.model.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.zhiwei.core.model.BaseModel;

/**
 * @description 部门管理
 * @class Department
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @createtime 2011-1-18AM
 * 
 */
@SuppressWarnings("serial")
public class Department extends BaseModel {
	/**
	 * 集团
	 */
	public final static Short ORG_TYPE_GROUP=0;
	/**
	 * 公司
	 */
	public final static Short ORG_TYPE_COMPANY=1;
	/**
	 * 部门
	 */
	public final static Short ORG_TYPE_DEPARTMENT=2;

	@Expose
	protected Long depId;
	@Expose
	protected String depName;
	@Expose
	protected String depDesc;
	@Expose
	protected Integer depLevel;
	@Expose
	protected Long parentId;
	@Expose
	protected String path;
	@Expose
	protected Short orgType;
	
	@Expose
	protected Long creatorId;
	@Expose
	protected java.util.Date createtime;
	@Expose
	protected Long updateId;
	@Expose
	protected java.util.Date updatetime;
	
	transient protected com.zhiwei.credit.model.system.Demension demension;
	
	protected Set appUsers = new java.util.HashSet();
	protected Set userOrgs = new java.util.HashSet();
	
	@Expose
	protected String chargeIds;
	@Expose
	protected String chargeNames;
	

	public Department() {

	}

	public Department(Long depId) {
		this.setDepId(depId);
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepDesc() {
		return depDesc;
	}

	public void setDepDesc(String depDesc) {
		this.depDesc = depDesc;
	}

	public Integer getDepLevel() {
		return depLevel;
	}

	public void setDepLevel(Integer depLevel) {
		this.depLevel = depLevel;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getChargeIds() {
		chargeIds = "";
		Set<UserOrg> uos = getUserOrgs();
		for(UserOrg uo:uos){
			if(uo.isCharge!=null&&uo.isCharge==1){
				chargeIds += uo.userOrgId+",";
			}
		}
		if(chargeIds.length()>0){chargeIds = chargeIds.substring(0,chargeIds.length()-1);}
		return chargeIds;
	}

	public void setChargeIds(String chargeIds) {
		this.chargeIds = chargeIds;
	}

	public String getChargeNames() {
		chargeNames="";
		Set<UserOrg> uos = getUserOrgs();
		for(UserOrg uo:uos){
			if(uo.isCharge!=null&&uo.isCharge==1){
				AppUser au = uo.appUser;
				chargeNames += au.getFullname()+",";
			}
		}
		if(chargeNames.length()>0){chargeNames = chargeNames.substring(0,chargeNames.length()-1);}
		return chargeNames;
	}

	public void setChargeNames(String chargeNames) {
		this.chargeNames = chargeNames;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.depId)
				.append(this.depName).append(this.depDesc)
				.append(this.depLevel).append(this.parentId).append(this.path)
				.append(this.chargeIds).append(this.chargeNames)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Department))
			return false;
		Department dep = (Department) obj;
		return new EqualsBuilder().append(this.depId, dep.depId).append(
				this.depName, dep.depName).append(this.depDesc, dep.depDesc)
				.append(this.depLevel, dep.depLevel).append(this.parentId,
						dep.parentId).append(this.path, dep.path)
				.append(this.chargeIds, dep.chargeIds).append(this.chargeNames, dep.chargeNames).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("depId", this.depId).append(
				"depName", this.depName).append("depDesc", this.depDesc)
				.append("depLevel", this.depLevel).append("parentId",
						this.parentId).append("path", this.path)
				.append("chargeIds",this.chargeIds).append("chargeNames",this.chargeNames).toString();
	}

	public Short getOrgType() {
		return orgType;
	}

	public void setOrgType(Short orgType) {
		this.orgType = orgType;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public java.util.Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

	public com.zhiwei.credit.model.system.Demension getDemension() {
		return demension;
	}

	public void setDemension(com.zhiwei.credit.model.system.Demension demension) {
		this.demension = demension;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	
	public Set getAppUsers () {
		return appUsers;
	}	
	
	public void setAppUsers (Set in_appUsers) {
		this.appUsers = in_appUsers;
	}

	public Set getUserOrgs () {
		return userOrgs;
	}	
	
	public void setUserOrgs (Set in_userOrgs) {
		this.userOrgs = in_userOrgs;
	}
	
}
