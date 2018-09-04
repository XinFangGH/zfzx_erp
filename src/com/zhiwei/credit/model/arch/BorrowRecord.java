package com.zhiwei.credit.model.arch;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * BorrowRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BorrowRecord extends com.zhiwei.core.model.BaseModel {
	
	@Expose
	protected String borrowRemark;
	@Expose
	protected Long checkId;
	@Expose
	protected String checkName;
	@Expose
	protected String checkContent;
	@Expose
    protected Long recordId;
	@Expose
    protected java.util.Date borrowDate;
	@Expose
    protected String borrowType;
	@Expose
    protected String borrowReason;
	@Expose
    protected String checkUserName;
	@Expose
    protected java.util.Date checkDate;
	@Expose
    protected java.util.Date returnDate;
	@Expose
    protected Short returnStatus;
	@Expose
    protected String borrowNum;
	@Expose
    protected com.zhiwei.credit.model.system.AppUser appUser;

	protected java.util.Set borrowFileLists = new java.util.HashSet();
	
	
	
	
	
	public String getBorrowRemark() {
		return borrowRemark;
	}

	public void setBorrowRemark(String borrowRemark) {
		this.borrowRemark = borrowRemark;
	}

	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	/**
	 * Default Empty Constructor for class BorrowRecord
	 */
	public BorrowRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BorrowRecord
	 */
	public BorrowRecord (
		 Long in_recordId
        ) {
		this.setRecordId(in_recordId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}

	public java.util.Set getBorrowFileLists () {
		return borrowFileLists;
	}	
	
	public void setBorrowFileLists (java.util.Set in_borrowFileLists) {
		this.borrowFileLists = in_borrowFileLists;
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="borrowDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getBorrowDate() {
		return this.borrowDate;
	}
	
	/**
	 * Set the borrowDate
	 */	
	public void setBorrowDate(java.util.Date aValue) {
		this.borrowDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="borrowType" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getBorrowType() {
		return this.borrowType;
	}
	
	/**
	 * Set the borrowType
	 */	
	public void setBorrowType(String aValue) {
		this.borrowType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="borrowReason" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getBorrowReason() {
		return this.borrowReason;
	}
	
	/**
	 * Set the borrowReason
	 */	
	public void setBorrowReason(String aValue) {
		this.borrowReason = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getCheckUserId() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the checkUserId
	 */	
	public void setCheckUserId(Long aValue) {
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
	 * @hibernate.property column="checkUserName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCheckUserName() {
		return this.checkUserName;
	}
	
	/**
	 * Set the checkUserName
	 */	
	public void setCheckUserName(String aValue) {
		this.checkUserName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="checkDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getCheckDate() {
		return this.checkDate;
	}
	
	/**
	 * Set the checkDate
	 */	
	public void setCheckDate(java.util.Date aValue) {
		this.checkDate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="returnDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getReturnDate() {
		return this.returnDate;
	}
	
	/**
	 * Set the returnDate
	 */	
	public void setReturnDate(java.util.Date aValue) {
		this.returnDate = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="returnStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getReturnStatus() {
		return this.returnStatus;
	}
	
	/**
	 * Set the returnStatus
	 */	
	public void setReturnStatus(Short aValue) {
		this.returnStatus = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="borrowNum" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getBorrowNum() {
		return this.borrowNum;
	}
	
	/**
	 * Set the borrowNum
	 */	
	public void setBorrowNum(String aValue) {
		this.borrowNum = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BorrowRecord)) {
			return false;
		}
		BorrowRecord rhs = (BorrowRecord) object;
		return new EqualsBuilder()
				.append(this.recordId, rhs.recordId)
			
				.append(this.borrowRemark, rhs.borrowRemark)
				.append(this.checkId, rhs.checkId)
				.append(this.checkName, rhs.checkName)
				.append(this.checkContent, rhs.checkContent)
				
				.append(this.borrowDate, rhs.borrowDate)
				.append(this.borrowType, rhs.borrowType)
				.append(this.borrowReason, rhs.borrowReason)
						.append(this.checkUserName, rhs.checkUserName)
				.append(this.checkDate, rhs.checkDate)
				.append(this.returnDate, rhs.returnDate)
				.append(this.returnStatus, rhs.returnStatus)
				.append(this.borrowNum, rhs.borrowNum)
				.isEquals();
		
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recordId) 
				
				.append(this.borrowRemark) 
				.append(this.checkId) 
				.append(this.checkName) 
				.append(this.checkContent) 
				
				.append(this.borrowDate) 
				.append(this.borrowType) 
				.append(this.borrowReason) 
						.append(this.checkUserName) 
				.append(this.checkDate) 
				.append(this.returnDate) 
				.append(this.returnStatus) 
				.append(this.borrowNum) 
				.toHashCode();
		
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recordId", this.recordId) 
				
				.append("borrowRemark", this.borrowRemark) 
				.append("checkId", this.checkId) 
				.append("checkName", this.checkName) 
				.append("checkContent", this.checkContent) 
				
				.append("borrowDate", this.borrowDate) 
				.append("borrowType", this.borrowType) 
				.append("borrowReason", this.borrowReason) 
						.append("checkUserName", this.checkUserName) 
				.append("checkDate", this.checkDate) 
				.append("returnDate", this.returnDate) 
				.append("returnStatus", this.returnStatus) 
				.append("borrowNum", this.borrowNum) 
				.toString();

	}



}
