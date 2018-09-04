package com.zhiwei.credit.model.hrm;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SalaryItem Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������������������
 */
public class SalaryItem extends com.zhiwei.core.model.BaseModel {

    protected Long salaryItemId;
	protected String itemName;
	protected java.math.BigDecimal defaultVal;


	/**
	 * Default Empty Constructor for class SalaryItem
	 */
	public SalaryItem () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SalaryItem
	 */
	public SalaryItem (
		 Long in_salaryItemId
        ) {
		this.setSalaryItemId(in_salaryItemId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="salaryItemId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSalaryItemId() {
		return this.salaryItemId;
	}
	
	/**
	 * Set the salaryItemId
	 */	
	public void setSalaryItemId(Long aValue) {
		this.salaryItemId = aValue;
	}	

	/**
	 * ÏîÄ¿Ãû³Æ	 * @return String
	 * @hibernate.property column="itemName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getItemName() {
		return this.itemName;
	}
	
	/**
	 * Set the itemName
	 * @spring.validator type="required"
	 */	
	public void setItemName(String aValue) {
		this.itemName = aValue;
	}	

	/**
	 * È±Ê¡Öµ	 * @return java.math.BigDecimal
	 * @hibernate.property column="defaultVal" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getDefaultVal() {
		return this.defaultVal;
	}
	
	/**
	 * Set the defaultVal
	 * @spring.validator type="required"
	 */	
	public void setDefaultVal(java.math.BigDecimal aValue) {
		this.defaultVal = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SalaryItem)) {
			return false;
		}
		SalaryItem rhs = (SalaryItem) object;
		return new EqualsBuilder()
				.append(this.salaryItemId, rhs.salaryItemId)
				.append(this.itemName, rhs.itemName)
				.append(this.defaultVal, rhs.defaultVal)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.salaryItemId) 
				.append(this.itemName) 
				.append(this.defaultVal) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("salaryItemId", this.salaryItemId) 
				.append("itemName", this.itemName) 
				.append("defaultVal", this.defaultVal) 
				.toString();
	}



}
