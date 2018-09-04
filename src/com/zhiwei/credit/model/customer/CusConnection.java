package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * CusConnection Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * business connection 
 */
public class CusConnection extends com.zhiwei.core.model.BaseModel {

    protected Long connId;
	protected String contactor;
	protected java.util.Date startDate;
	protected java.util.Date endDate;
	protected String content;
	protected String notes;
	protected String creator;
	protected com.zhiwei.credit.model.customer.Customer customer;


	/**
	 * Default Empty Constructor for class CusConnection
	 */
	public CusConnection () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CusConnection
	 */
	public CusConnection (
		 Long in_connId
        ) {
		this.setConnId(in_connId);
    }

	
	public com.zhiwei.credit.model.customer.Customer getCustomer () {
		return customer;
	}	
	
	public void setCustomer (com.zhiwei.credit.model.customer.Customer in_customer) {
		this.customer = in_customer;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="connId" type="java.lang.Long" generator-class="native"
	 */
	public Long getConnId() {
		return this.connId;
	}
	
	/**
	 * Set the connId
	 */	
	public void setConnId(Long aValue) {
		this.connId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getCustomerId() {
		return this.getCustomer()==null?null:this.getCustomer().getCustomerId();
	}
	
	/**
	 * Set the customerId
	 */	
	public void setCustomerId(Long aValue) {
	    if (aValue==null) {
	    	customer = null;
	    } else if (customer == null) {
	        customer = new com.zhiwei.credit.model.customer.Customer(aValue);
	        customer.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			customer.setCustomerId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="contactor" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getContactor() {
		return this.contactor;
	}
	
	/**
	 * Set the contactor
	 * @spring.validator type="required"
	 */	
	public void setContactor(String aValue) {
		this.contactor = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="startDate" type="java.util.Date" length="10" not-null="true" unique="false"
	 */
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Set the startDate
	 * @spring.validator type="required"
	 */	
	public void setStartDate(java.util.Date aValue) {
		this.startDate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="endDate" type="java.util.Date" length="10" not-null="true" unique="false"
	 */
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Set the endDate
	 * @spring.validator type="required"
	 */	
	public void setEndDate(java.util.Date aValue) {
		this.endDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="512" not-null="true" unique="false"
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content
	 * @spring.validator type="required"
	 */	
	public void setContent(String aValue) {
		this.content = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="notes" type="java.lang.String" length="1000" not-null="false" unique="false"
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

	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CusConnection)) {
			return false;
		}
		CusConnection rhs = (CusConnection) object;
		return new EqualsBuilder()
				.append(this.connId, rhs.connId)
						.append(this.contactor, rhs.contactor)
				.append(this.startDate, rhs.startDate)
				.append(this.endDate, rhs.endDate)
				.append(this.content, rhs.content)
				.append(this.notes, rhs.notes)
				.append(this.creator, rhs.creator)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.connId) 
						.append(this.contactor) 
				.append(this.startDate) 
				.append(this.endDate) 
				.append(this.content) 
				.append(this.notes) 
				.append(this.creator)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("connId", this.connId) 
						.append("contactor", this.contactor) 
				.append("startDate", this.startDate) 
				.append("endDate", this.endDate) 
				.append("content", this.content) 
				.append("notes", this.notes)
				.append("creator",this.creator)
				.toString();
	}



}
