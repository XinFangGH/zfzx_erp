package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Demension Base Java Bean, base class for the.est.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class Demension extends com.zhiwei.core.model.BaseModel {
	/**
	 * 行政维度的id
	 */
	public final static Long ADMIN_DEMENSION_ID=1l;
	
    protected Long demId;
	protected String demName;
	protected String demDesc;
	protected Long demType;

	protected java.util.Set organizations = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Demension
	 */
	public Demension () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Demension
	 */
	public Demension (
		 Long in_demId
        ) {
		this.setDemId(in_demId);
    }


	public java.util.Set getOrganizations () {
		return organizations;
	}	
	
	public void setOrganizations (java.util.Set in_organizations) {
		this.organizations = in_organizations;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="DEM_ID" type="java.lang.Long" generator-class="native"
	 */
	public Long getDemId() {
		return this.demId;
	}
	
	/**
	 * Set the demId
	 */	
	public void setDemId(Long aValue) {
		this.demId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="DEM_NAME" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getDemName() {
		return this.demName;
	}
	
	/**
	 * Set the demName
	 * @spring.validator type="required"
	 */	
	public void setDemName(String aValue) {
		this.demName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="DEM_DESC" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getDemDesc() {
		return this.demDesc;
	}
	
	/**
	 * Set the demDesc
	 */	
	public void setDemDesc(String aValue) {
		this.demDesc = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="DEM_TYPE" type="java.lang.Long" length="22" not-null="true" unique="false"
	 */
	public Long getDemType() {
		return this.demType;
	}
	
	/**
	 * Set the demType
	 * @spring.validator type="required"
	 */	
	public void setDemType(Long aValue) {
		this.demType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Demension)) {
			return false;
		}
		Demension rhs = (Demension) object;
		return new EqualsBuilder()
				.append(this.demId, rhs.demId)
				.append(this.demName, rhs.demName)
				.append(this.demDesc, rhs.demDesc)
				.append(this.demType, rhs.demType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.demId) 
				.append(this.demName) 
				.append(this.demDesc) 
				.append(this.demType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("demId", this.demId) 
				.append("demName", this.demName) 
				.append("demDesc", this.demDesc) 
				.append("demType", this.demType) 
				.toString();
	}



}
