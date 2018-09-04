package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * BookSn Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BookSn extends com.zhiwei.core.model.BaseModel {

    protected Long bookSnId;
	protected String bookSN;
	protected Short status;
	protected com.zhiwei.credit.model.admin.Book book;

	protected java.util.Set bookBorRets = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class BookSn
	 */
	public BookSn () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BookSn
	 */
	public BookSn (
		 Long in_bookSnId
        ) {
		this.setBookSnId(in_bookSnId);
    }

	
	public com.zhiwei.credit.model.admin.Book getBook () {
		return book;
	}	
	
	public void setBook (com.zhiwei.credit.model.admin.Book in_book) {
		this.book = in_book;
	}

	public java.util.Set getBookBorRets () {
		return bookBorRets;
	}	
	
	public void setBookBorRets (java.util.Set in_bookBorRets) {
		this.bookBorRets = in_bookBorRets;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="bookSnId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBookSnId() {
		return this.bookSnId;
	}
	
	/**
	 * Set the bookSnId
	 */	
	public void setBookSnId(Long aValue) {
		this.bookSnId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getBookId() {
		return this.getBook()==null?null:this.getBook().getBookId();
	}
	
	/**
	 * Set the bookId
	 */	
	public void setBookId(Long aValue) {
	    if (aValue==null) {
	    	book = null;
	    } else if (book == null) {
	        book = new com.zhiwei.credit.model.admin.Book(aValue);
	        book.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			book.setBookId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="bookSN" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getBookSN() {
		return this.bookSN;
	}
	
	/**
	 * Set the bookSN
	 * @spring.validator type="required"
	 */	
	public void setBookSN(String aValue) {
		this.bookSN = aValue;
	}	

	/**
	 * 借阅状态0=未借出1=借出2=预订3=注销	 * @return Short
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
		if (!(object instanceof BookSn)) {
			return false;
		}
		BookSn rhs = (BookSn) object;
		return new EqualsBuilder()
				.append(this.bookSnId, rhs.bookSnId)
						.append(this.bookSN, rhs.bookSN)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.bookSnId) 
						.append(this.bookSN) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("bookSnId", this.bookSnId) 
						.append("bookSN", this.bookSN) 
				.append("status", this.status) 
				.toString();
	}



}
