package com.zhiwei.credit.model.admin;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * BookBorRet Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ���������������
 */
public class BookBorRet extends com.zhiwei.core.model.BaseModel {

    protected Long recordId;
	protected java.util.Date borrowTime;
	protected java.util.Date returnTime;
	protected java.util.Date lastReturnTime;
	protected String borrowIsbn;
	protected String bookName;
	protected String registerName;
    protected String fullname;



	protected com.zhiwei.credit.model.admin.BookSn bookSn;


	/**
	 * Default Empty Constructor for class BookBorRet
	 */
	public BookBorRet () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BookBorRet
	 */
	public BookBorRet (
		 Long in_recordId
        ) {
		this.setRecordId(in_recordId);
    }

	
	public com.zhiwei.credit.model.admin.BookSn getBookSn () {
		return bookSn;
	}	
	
	public void setBookSn (com.zhiwei.credit.model.admin.BookSn in_bookSn) {
		this.bookSn = in_bookSn;
	}
    
	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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
	public Long getBookSnId() {
		return this.getBookSn()==null?null:this.getBookSn().getBookSnId();
	}
	
	/**
	 * Set the bookSnId
	 */	
	public void setBookSnId(Long aValue) {
	    if (aValue==null) {
	    	bookSn = null;
	    } else if (bookSn == null) {
	        bookSn = new com.zhiwei.credit.model.admin.BookSn(aValue);
	        bookSn.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			bookSn.setBookSnId(aValue);
	    }
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="borrowTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getBorrowTime() {
		return this.borrowTime;
	}
	
	/**
	 * Set the borrowTime
	 * @spring.validator type="required"
	 */	
	public void setBorrowTime(java.util.Date aValue) {
		this.borrowTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="returnTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getReturnTime() {
		return this.returnTime;
	}
	
	/**
	 * Set the returnTime
	 * @spring.validator type="required"
	 */	
	public void setReturnTime(java.util.Date aValue) {
		this.returnTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="lastReturnTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getLastReturnTime() {
		return this.lastReturnTime;
	}
	
	/**
	 * Set the lastReturnTime
	 */	
	public void setLastReturnTime(java.util.Date aValue) {
		this.lastReturnTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="borrowIsbn" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getBorrowIsbn() {
		return this.borrowIsbn;
	}
	
	/**
	 * Set the borrowIsbn
	 * @spring.validator type="required"
	 */	
	public void setBorrowIsbn(String aValue) {
		this.borrowIsbn = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="bookName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getBookName() {
		return this.bookName;
	}
	
	/**
	 * Set the bookName
	 * @spring.validator type="required"
	 */	
	public void setBookName(String aValue) {
		this.bookName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BookBorRet)) {
			return false;
		}
		BookBorRet rhs = (BookBorRet) object;
		return new EqualsBuilder()
				.append(this.recordId, rhs.recordId)
						.append(this.borrowTime, rhs.borrowTime)
				.append(this.returnTime, rhs.returnTime)
				.append(this.lastReturnTime, rhs.lastReturnTime)
				.append(this.borrowIsbn, rhs.borrowIsbn)
				.append(this.bookName, rhs.bookName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recordId) 
						.append(this.borrowTime) 
				.append(this.returnTime) 
				.append(this.lastReturnTime) 
				.append(this.borrowIsbn) 
				.append(this.bookName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recordId", this.recordId) 
						.append("borrowTime", this.borrowTime) 
				.append("returnTime", this.returnTime) 
				.append("lastReturnTime", this.lastReturnTime) 
				.append("borrowIsbn", this.borrowIsbn) 
				.append("bookName", this.bookName) 
				.toString();
	}



}
