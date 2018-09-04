package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * DepreRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class DepreRecord extends com.zhiwei.core.model.BaseModel {

    protected Long recordId;
	protected java.math.BigDecimal workCapacity;
	protected java.math.BigDecimal depreAmount;
	protected java.util.Date calTime;
	protected com.zhiwei.credit.model.admin.FixedAssets fixedAssets;


	/**
	 * Default Empty Constructor for class DepreRecord
	 */
	public DepreRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class DepreRecord
	 */
	public DepreRecord (
		 Long in_recordId
        ) {
		this.setRecordId(in_recordId);
    }

	
	public com.zhiwei.credit.model.admin.FixedAssets getFixedAssets () {
		return fixedAssets;
	}	
	
	public void setFixedAssets (com.zhiwei.credit.model.admin.FixedAssets in_fixedAssets) {
		this.fixedAssets = in_fixedAssets;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="recordId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRecordId() {
		return this.recordId;
	}
	
	/**
	 * Set the recordId
	 */	
	public void setRecordId(Long aValue) {
		this.recordId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getAssetsId() {
		return this.getFixedAssets()==null?null:this.getFixedAssets().getAssetsId();
	}
	
	/**
	 * Set the assetsId
	 */	
	public void setAssetsId(Long aValue) {
	    if (aValue==null) {
	    	fixedAssets = null;
	    } else if (fixedAssets == null) {
	        fixedAssets = new com.zhiwei.credit.model.admin.FixedAssets(aValue);
	        fixedAssets.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			fixedAssets.setAssetsId(aValue);
	    }
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="workCapacity" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getWorkCapacity() {
		return this.workCapacity;
	}
	
	/**
	 * Set the workCapacity
	 */	
	public void setWorkCapacity(java.math.BigDecimal aValue) {
		this.workCapacity = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="depreAmount" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getDepreAmount() {
		return this.depreAmount;
	}
	
	/**
	 * Set the depreAmount
	 * @spring.validator type="required"
	 */	
	public void setDepreAmount(java.math.BigDecimal aValue) {
		this.depreAmount = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="calTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCalTime() {
		return this.calTime;
	}
	
	/**
	 * Set the calTime
	 * @spring.validator type="required"
	 */	
	public void setCalTime(java.util.Date aValue) {
		this.calTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DepreRecord)) {
			return false;
		}
		DepreRecord rhs = (DepreRecord) object;
		return new EqualsBuilder()
				.append(this.recordId, rhs.recordId)
						.append(this.workCapacity, rhs.workCapacity)
				.append(this.depreAmount, rhs.depreAmount)
				.append(this.calTime, rhs.calTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recordId) 
						.append(this.workCapacity) 
				.append(this.depreAmount) 
				.append(this.calTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recordId", this.recordId) 
						.append("workCapacity", this.workCapacity) 
				.append("depreAmount", this.depreAmount) 
				.append("calTime", this.calTime) 
				.toString();
	}



}
