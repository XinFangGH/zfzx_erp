package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * CartRepair Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CartRepair extends com.zhiwei.core.model.BaseModel {

    protected Long repairId;
	protected java.util.Date repairDate;
	protected String reason;
	protected String executant;
	protected String notes;
	protected String repairType;
	protected java.math.BigDecimal fee;
	protected com.zhiwei.credit.model.admin.Car car;


	/**
	 * Default Empty Constructor for class CartRepair
	 */
	public CartRepair () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CartRepair
	 */
	public CartRepair (
		 Long in_repairId
        ) {
		this.setRepairId(in_repairId);
    }

	
	public com.zhiwei.credit.model.admin.Car getCar () {
		return car;
	}	
	
	public void setCar (com.zhiwei.credit.model.admin.Car in_car) {
		this.car = in_car;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="repairId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRepairId() {
		return this.repairId;
	}
	
	/**
	 * Set the repairId
	 */	
	public void setRepairId(Long aValue) {
		this.repairId = aValue;
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
	 * 维护日期	 * @return java.util.Date
	 * @hibernate.property column="repairDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getRepairDate() {
		return this.repairDate;
	}
	
	/**
	 * Set the repairDate
	 * @spring.validator type="required"
	 */	
	public void setRepairDate(java.util.Date aValue) {
		this.repairDate = aValue;
	}	

	/**
	 * 维护原因	 * @return String
	 * @hibernate.property column="reason" type="java.lang.String" length="128" not-null="true" unique="false"
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
	 * 经办人	 * @return String
	 * @hibernate.property column="executant" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getExecutant() {
		return this.executant;
	}
	
	/**
	 * Set the executant
	 * @spring.validator type="required"
	 */	
	public void setExecutant(String aValue) {
		this.executant = aValue;
	}	

	/**
	 * 备注	 * @return String
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
	 * 维修类型
            保养
            维修	 * @return String
	 * @hibernate.property column="repairType" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getRepairType() {
		return this.repairType;
	}
	
	/**
	 * Set the repairType
	 * @spring.validator type="required"
	 */	
	public void setRepairType(String aValue) {
		this.repairType = aValue;
	}	

	/**
	 * 费用	 * @return java.math.BigDecimal
	 * @hibernate.property column="fee" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getFee() {
		return this.fee;
	}
	
	/**
	 * Set the fee
	 */	
	public void setFee(java.math.BigDecimal aValue) {
		this.fee = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CartRepair)) {
			return false;
		}
		CartRepair rhs = (CartRepair) object;
		return new EqualsBuilder()
				.append(this.repairId, rhs.repairId)
						.append(this.repairDate, rhs.repairDate)
				.append(this.reason, rhs.reason)
				.append(this.executant, rhs.executant)
				.append(this.notes, rhs.notes)
				.append(this.repairType, rhs.repairType)
				.append(this.fee, rhs.fee)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.repairId) 
						.append(this.repairDate) 
				.append(this.reason) 
				.append(this.executant) 
				.append(this.notes) 
				.append(this.repairType) 
				.append(this.fee) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("repairId", this.repairId) 
						.append("repairDate", this.repairDate) 
				.append("reason", this.reason) 
				.append("executant", this.executant) 
				.append("notes", this.notes) 
				.append("repairType", this.repairType) 
				.append("fee", this.fee) 
				.toString();
	}



}
