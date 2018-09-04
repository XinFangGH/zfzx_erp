package com.zhiwei.credit.model.hrm;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * StandSalaryItem Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������������������
 */
public class StandSalaryItem extends com.zhiwei.core.model.BaseModel {

    protected Long itemId;
	protected String itemName;
	protected java.math.BigDecimal amount;
	protected Long salaryItemId;
	protected com.zhiwei.credit.model.hrm.StandSalary standSalary;


	/**
	 * Default Empty Constructor for class StandSalaryItem
	 */
	public StandSalaryItem () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class StandSalaryItem
	 */
	public StandSalaryItem (
		 Long in_itemId
        ) {
		this.setItemId(in_itemId);
    }

	
	public com.zhiwei.credit.model.hrm.StandSalary getStandSalary () {
		return standSalary;
	}	
	
	public void setStandSalary (com.zhiwei.credit.model.hrm.StandSalary in_standSalary) {
		this.standSalary = in_standSalary;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="itemId" type="java.lang.Long" generator-class="native"
	 */
	public Long getItemId() {
		return this.itemId;
	}
	
	/**
	 * Set the itemId
	 */	
	public void setItemId(Long aValue) {
		this.itemId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getStandardId() {
		return this.getStandSalary()==null?null:this.getStandSalary().getStandardId();
	}
	
	/**
	 * Set the standardId
	 */	
	public void setStandardId(Long aValue) {
	    if (aValue==null) {
	    	standSalary = null;
	    } else if (standSalary == null) {
	        standSalary = new com.zhiwei.credit.model.hrm.StandSalary(aValue);
	        standSalary.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			standSalary.setStandardId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="itemName" type="java.lang.String" length="64" not-null="true" unique="false"
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="amount" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * Set the amount
	 * @spring.validator type="required"
	 */	
	public void setAmount(java.math.BigDecimal aValue) {
		this.amount = aValue;
	}	

	/**
	 * ËùÊô¹¤×Ê×é³ÉID
            Íâ¼ü£¬µ«²»ÐèÒªÔÚÊý¾Ý¿â²ã½¨Á¢Íâ¼ü	 * @return Long
	 * @hibernate.property column="salaryItemId" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof StandSalaryItem)) {
			return false;
		}
		StandSalaryItem rhs = (StandSalaryItem) object;
		return new EqualsBuilder()
				.append(this.itemId, rhs.itemId)
						.append(this.itemName, rhs.itemName)
				.append(this.amount, rhs.amount)
				.append(this.salaryItemId, rhs.salaryItemId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.itemId) 
						.append(this.itemName) 
				.append(this.amount) 
				.append(this.salaryItemId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("itemId", this.itemId) 
						.append("itemName", this.itemName) 
				.append("amount", this.amount) 
				.append("salaryItemId", this.salaryItemId) 
				.toString();
	}



}
