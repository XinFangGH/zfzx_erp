package com.zhiwei.credit.model.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Duty Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������
 */
public class Duty extends com.zhiwei.core.model.BaseModel {

    protected Long dutyId;
	protected String fullname;
	protected java.util.Date startTime;
	protected java.util.Date endTime;
	protected com.zhiwei.credit.model.personal.DutySystem dutySystem;
	protected com.zhiwei.credit.model.system.AppUser appUser;


	/**
	 * Default Empty Constructor for class Duty
	 */
	public Duty () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Duty
	 */
	public Duty (
		 Long in_dutyId
        ) {
		this.setDutyId(in_dutyId);
    }

	
	public com.zhiwei.credit.model.personal.DutySystem getDutySystem () {
		return dutySystem;
	}	
	
	public void setDutySystem (com.zhiwei.credit.model.personal.DutySystem in_dutySystem) {
		this.dutySystem = in_dutySystem;
	}
	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="dutyId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDutyId() {
		return this.dutyId;
	}
	
	/**
	 * Set the dutyId
	 */	
	public void setDutyId(Long aValue) {
		this.dutyId = aValue;
	}	

	/**
	 * 员工ID	 * @return Long
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
	 * 	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/**
	 * Set the fullname
	 * @spring.validator type="required"
	 */	
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}	

	/**
	 * 班制ID	 * @return Long
	 */
	public Long getSystemId() {
		return this.getDutySystem()==null?null:this.getDutySystem().getSystemId();
	}
	
	/**
	 * Set the systemId
	 */	
	public void setSystemId(Long aValue) {
//	    if (aValue==null) {
//	    	dutySystem = null;
//	    } else if (dutySystem == null) {
//	        dutySystem = new com.zhiwei.credit.model.personal.DutySystem(aValue);
//	        dutySystem.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//			dutySystem.setSystemId(aValue);
//	    }
	}	

	/**
	 * 开始时间	 * @return java.util.Date
	 * @hibernate.property column="startTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Set the startTime
	 * @spring.validator type="required"
	 */	
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}	

	/**
	 * 结束时间	 * @return java.util.Date
	 * @hibernate.property column="endTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Set the endTime
	 */	
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Duty)) {
			return false;
		}
		Duty rhs = (Duty) object;
		return new EqualsBuilder()
				.append(this.dutyId, rhs.dutyId)
						.append(this.fullname, rhs.fullname)
						.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.dutyId) 
						.append(this.fullname) 
						.append(this.startTime) 
				.append(this.endTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("dutyId", this.dutyId) 
						.append("fullname", this.fullname) 
						.append("startTime", this.startTime) 
				.append("endTime", this.endTime) 
				.toString();
	}



}
