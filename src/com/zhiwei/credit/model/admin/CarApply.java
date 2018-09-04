package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * CarApply Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class CarApply extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long applyId;
	@Expose
	protected String department;
	@Expose
	protected String userFullname;
	@Expose
	protected java.util.Date applyDate;
	@Expose
	protected String reason;
	@Expose
	protected java.util.Date startTime;
	@Expose
	protected java.util.Date endTime;
	@Expose
	protected String proposer;
	@Expose
	protected Long userId;
	@Expose
	protected java.math.BigDecimal mileage;
	@Expose
	protected java.math.BigDecimal oilUse;
	@Expose
	protected String notes;
	@Expose
	protected Short approvalStatus;
	@Expose
	protected com.zhiwei.credit.model.admin.Car car;


	/**
	 * Default Empty Constructor for class CarApply
	 */
	public CarApply () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CarApply
	 */
	public CarApply (
		 Long in_applyId
        ) {
		this.setApplyId(in_applyId);
    }

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public com.zhiwei.credit.model.admin.Car getCar () {
		return car;
	}	
	
	public void setCar (com.zhiwei.credit.model.admin.Car in_car) {
		this.car = in_car;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="applyId" type="java.lang.Long" generator-class="native"
	 */
	public Long getApplyId() {
		return this.applyId;
	}
	
	/**
	 * Set the applyId
	 */	
	public void setApplyId(Long aValue) {
		this.applyId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getCarId() {
		return this.getCar()==null?null:this.getCar().getCarId();
	}
	
	/**
	 * Set the carId
	 */	
	public void setCarId(Long aValue) {
	    if (aValue==null) {
	    	car = null;
	    } else if (car == null) {
	        car = new com.zhiwei.credit.model.admin.Car(aValue);
	        car.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			car.setCarId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="department" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getDepartment() {
		return this.department;
	}
	
	/**
	 * Set the department
	 * @spring.validator type="required"
	 */	
	public void setDepartment(String aValue) {
		this.department = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="userFullname" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getUserFullname() {
		return this.userFullname;
	}
	
	/**
	 * Set the userFullname
	 * @spring.validator type="required"
	 */	
	public void setUserFullname(String aValue) {
		this.userFullname = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="applyDate" type="java.util.Date" length="10" not-null="true" unique="false"
	 */
	public java.util.Date getApplyDate() {
		return this.applyDate;
	}
	
	/**
	 * Set the applyDate
	 * @spring.validator type="required"
	 */	
	public void setApplyDate(java.util.Date aValue) {
		this.applyDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="reason" type="java.lang.String" length="512" not-null="true" unique="false"
	 */
	public String getReason() {
		return this.reason;
	}
	
	/**
	 * Set the reason
	 * @spring.validator type="required"
	 */	
	public void setReason(String aValue) {
		this.reason = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
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
	 * 	 * @return java.util.Date
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
	 * 	 * @return String
	 * @hibernate.property column="proposer" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getProposer() {
		return this.proposer;
	}
	
	/**
	 * Set the proposer
	 * @spring.validator type="required"
	 */	
	public void setProposer(String aValue) {
		this.proposer = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="mileage" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMileage() {
		return this.mileage;
	}
	
	/**
	 * Set the mileage
	 */	
	public void setMileage(java.math.BigDecimal aValue) {
		this.mileage = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="oilUse" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOilUse() {
		return this.oilUse;
	}
	
	/**
	 * Set the oilUse
	 */	
	public void setOilUse(java.math.BigDecimal aValue) {
		this.oilUse = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="notes" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getNotes() {
		return this.notes;
	}
	
	/**
	 * Set the notes
	 */	
	public void setNotes(String aValue) {
		this.notes = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="approvalStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getApprovalStatus() {
		return this.approvalStatus;
	}
	
	/**
	 * Set the approvalStatus
	 * @spring.validator type="required"
	 */	
	public void setApprovalStatus(Short aValue) {
		this.approvalStatus = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CarApply)) {
			return false;
		}
		CarApply rhs = (CarApply) object;
		return new EqualsBuilder()
				.append(this.applyId, rhs.applyId)
						.append(this.department, rhs.department)
				.append(this.userFullname, rhs.userFullname)
				.append(this.applyDate, rhs.applyDate)
				.append(this.reason, rhs.reason)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.proposer, rhs.proposer)
				.append(this.userId, rhs.userId)
				.append(this.mileage, rhs.mileage)
				.append(this.oilUse, rhs.oilUse)
				.append(this.notes, rhs.notes)
				.append(this.approvalStatus, rhs.approvalStatus)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.applyId) 
						.append(this.department) 
				.append(this.userFullname) 
				.append(this.applyDate) 
				.append(this.reason) 
				.append(this.startTime) 
				.append(this.endTime) 
				.append(this.proposer) 
				.append(this.userId) 
				.append(this.mileage) 
				.append(this.oilUse) 
				.append(this.notes) 
				.append(this.approvalStatus) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("applyId", this.applyId) 
						.append("department", this.department) 
				.append("userFullname", this.userFullname) 
				.append("applyDate", this.applyDate) 
				.append("reason", this.reason) 
				.append("startTime", this.startTime) 
				.append("endTime", this.endTime) 
				.append("proposer", this.proposer) 
				.append("userId", this.userId) 
				.append("mileage", this.mileage) 
				.append("oilUse", this.oilUse) 
				.append("notes", this.notes) 
				.append("approvalStatus", this.approvalStatus) 
				.toString();
	}



}
