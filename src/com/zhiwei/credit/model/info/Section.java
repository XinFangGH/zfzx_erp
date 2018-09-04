package com.zhiwei.credit.model.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * Section Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class Section extends com.zhiwei.core.model.BaseModel {

	/**
	 * 状态:激活
	 */
	public static final Short STATUS_ENABLE = 1;
	/**
	 * 状态:禁用
	 */
	public static final Short STATUS_DISABLE = 0;
	/**
	 * 表示第一列
	 */
	public static final Integer COLUMN_ONE = 1;
	@Expose
    protected Long sectionId;
	@Expose
	protected String sectionName;
	@Expose
	protected String sectionDesc;
	@Expose
	protected java.util.Date createtime;
	@Expose
	protected Short sectionType;
	@Expose
	protected String username;
	@Expose
	protected Long userId;
	@Expose
	protected Integer colNumber;
	@Expose
	protected Integer rowNumber;
	@Expose
	protected Short status;
	
	protected java.util.Set newss = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Section
	 */
	public Section () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Section
	 */
	public Section (
		 Long in_sectionId
        ) {
		this.setSectionId(in_sectionId);
    }


	public java.util.Set getNewss () {
		return newss;
	}	
	
	public void setNewss (java.util.Set in_newss) {
		this.newss = in_newss;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="sectionId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSectionId() {
		return this.sectionId;
	}
	
	/**
	 * Set the sectionId
	 */	
	public void setSectionId(Long aValue) {
		this.sectionId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="sectionName" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getSectionName() {
		return this.sectionName;
	}
	
	/**
	 * Set the sectionName
	 * @spring.validator type="required"
	 */	
	public void setSectionName(String aValue) {
		this.sectionName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="sectionDesc" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getSectionDesc() {
		return this.sectionDesc;
	}
	
	/**
	 * Set the sectionDesc
	 */	
	public void setSectionDesc(String aValue) {
		this.sectionDesc = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 * @spring.validator type="required"
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="sectionType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getSectionType() {
		return this.sectionType;
	}
	
	/**
	 * Set the sectionType
	 * @spring.validator type="required"
	 */	
	public void setSectionType(Short aValue) {
		this.sectionType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="username" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Set the username
	 */	
	public void setUsername(String aValue) {
		this.username = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="colNumber" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getColNumber() {
		return this.colNumber;
	}
	
	/**
	 * Set the colNumber
	 */	
	public void setColNumber(Integer aValue) {
		this.colNumber = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="rowNumber" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRowNumber() {
		return this.rowNumber;
	}
	
	/**
	 * Set the rowNumber
	 */	
	public void setRowNumber(Integer aValue) {
		this.rowNumber = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Section)) {
			return false;
		}
		Section rhs = (Section) object;
		return new EqualsBuilder()
				.append(this.sectionId, rhs.sectionId)
				.append(this.sectionName, rhs.sectionName)
				.append(this.sectionDesc, rhs.sectionDesc)
				.append(this.createtime, rhs.createtime)
				.append(this.sectionType, rhs.sectionType)
				.append(this.username, rhs.username)
				.append(this.userId, rhs.userId)
				.append(this.colNumber, rhs.colNumber)
				.append(this.rowNumber, rhs.rowNumber)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.sectionId) 
				.append(this.sectionName) 
				.append(this.sectionDesc) 
				.append(this.createtime) 
				.append(this.sectionType) 
				.append(this.username) 
				.append(this.userId) 
				.append(this.colNumber) 
				.append(this.rowNumber) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("sectionId", this.sectionId) 
				.append("sectionName", this.sectionName) 
				.append("sectionDesc", this.sectionDesc) 
				.append("createtime", this.createtime) 
				.append("sectionType", this.sectionType) 
				.append("username", this.username) 
				.append("userId", this.userId) 
				.append("colNumber", this.colNumber) 
				.append("rowNumber", this.rowNumber) 
				.append("status", this.status) 
				.toString();
	}



}
