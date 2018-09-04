package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * TypeKey Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TypeKey extends com.zhiwei.core.model.BaseModel {

    protected Long typekeyId;
	protected String typeName;
	protected String typeKey;
	protected Integer sn;


	/**
	 * Default Empty Constructor for class TypeKey
	 */
	public TypeKey () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TypeKey
	 */
	public TypeKey (
		 Long in_typekeyId
        ) {
		this.setTypekeyId(in_typekeyId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="typekeyId" type="java.lang.Long" generator-class="native"
	 */
	public Long getTypekeyId() {
		return this.typekeyId;
	}
	
	/**
	 * Set the typekeyId
	 */	
	public void setTypekeyId(Long aValue) {
		this.typekeyId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getTypeName() {
		return this.typeName;
	}
	
	/**
	 * Set the typeName
	 * @spring.validator type="required"
	 */	
	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="typeKey" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getTypeKey() {
		return this.typeKey;
	}
	
	/**
	 * Set the typeKey
	 * @spring.validator type="required"
	 */	
	public void setTypeKey(String aValue) {
		this.typeKey = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="sn" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getSn() {
		return this.sn;
	}
	
	/**
	 * Set the sn
	 * @spring.validator type="required"
	 */	
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TypeKey)) {
			return false;
		}
		TypeKey rhs = (TypeKey) object;
		return new EqualsBuilder()
				.append(this.typekeyId, rhs.typekeyId)
				.append(this.typeName, rhs.typeName)
				.append(this.typeKey, rhs.typeKey)
				.append(this.sn, rhs.sn)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.typekeyId) 
				.append(this.typeName) 
				.append(this.typeKey) 
				.append(this.sn) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("typekeyId", this.typekeyId) 
				.append("typeName", this.typeName) 
				.append("typeKey", this.typeKey) 
				.append("sn", this.sn) 
				.toString();
	}



}
