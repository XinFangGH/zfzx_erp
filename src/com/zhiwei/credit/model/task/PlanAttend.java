package com.zhiwei.credit.model.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * PlanAttend Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlanAttend extends com.zhiwei.core.model.BaseModel {

    protected Long attendId;
	protected Short isDep;
	protected Short isPrimary;
	protected com.zhiwei.credit.model.task.WorkPlan workPlan;
	protected com.zhiwei.credit.model.system.Department department;
	protected com.zhiwei.credit.model.system.AppUser appUser;


	/**
	 * Default Empty Constructor for class PlanAttend
	 */
	public PlanAttend () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlanAttend
	 */
	public PlanAttend (
		 Long in_attendId
        ) {
		this.setAttendId(in_attendId);
    }

	
	public com.zhiwei.credit.model.task.WorkPlan getWorkPlan () {
		return workPlan;
	}	
	
	public void setWorkPlan (com.zhiwei.credit.model.task.WorkPlan in_workPlan) {
		this.workPlan = in_workPlan;
	}
	
	public com.zhiwei.credit.model.system.Department getDepartment () {
		return department;
	}	
	
	public void setDepartment (com.zhiwei.credit.model.system.Department in_department) {
		this.department = in_department;
	}
	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="attendId" type="java.lang.Long" generator-class="native"
	 */
	public Long getAttendId() {
		return this.attendId;
	}
	
	/**
	 * Set the attendId
	 */	
	public void setAttendId(Long aValue) {
		this.attendId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getDepId() {
		return this.getDepartment()==null?null:this.getDepartment().getDepId();
	}
	
	/**
	 * Set the depId
	 */	
	public void setDepId(Long aValue) {
	    if (aValue==null) {
	    	department = null;
	    } else if (department == null) {
	        department = new com.zhiwei.credit.model.system.Department(aValue);
	        department.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			department.setDepId(aValue);
	    }
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getUserId() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else if (appUser == null) {
	        appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appUser.setUserId(aValue);
	    }
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getPlanId() {
		return this.getWorkPlan()==null?null:this.getWorkPlan().getPlanId();
	}
	
	/**
	 * Set the planId
	 */	
	public void setPlanId(Long aValue) {
	    if (aValue==null) {
	    	workPlan = null;
	    } else if (workPlan == null) {
	        workPlan = new com.zhiwei.credit.model.task.WorkPlan(aValue);
	        workPlan.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			workPlan.setPlanId(aValue);
	    }
	}	

	/**
	 * 是否为部门	 * @return Short
	 * @hibernate.property column="isDep" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsDep() {
		return this.isDep;
	}
	
	/**
	 * Set the isDep
	 * @spring.validator type="required"
	 */	
	public void setIsDep(Short aValue) {
		this.isDep = aValue;
	}	

	/**
	 * 是否负责人	 * @return Short
	 * @hibernate.property column="isPrimary" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsPrimary() {
		return this.isPrimary;
	}
	
	/**
	 * Set the isPrimary
	 */	
	public void setIsPrimary(Short aValue) {
		this.isPrimary = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlanAttend)) {
			return false;
		}
		PlanAttend rhs = (PlanAttend) object;
		return new EqualsBuilder()
				.append(this.attendId, rhs.attendId)
										.append(this.isDep, rhs.isDep)
				.append(this.isPrimary, rhs.isPrimary)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.attendId) 
										.append(this.isDep) 
				.append(this.isPrimary) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("attendId", this.attendId) 
										.append("isDep", this.isDep) 
				.append("isPrimary", this.isPrimary) 
				.toString();
	}



}
