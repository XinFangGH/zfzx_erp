package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Book Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������
 */
public class Book extends com.zhiwei.core.model.BaseModel {

    protected Long bookId;
	protected String bookName;
	protected String author;
	protected String isbn;
	protected String publisher;
	protected java.math.BigDecimal price;
	protected String location;
	protected String department;
	protected Integer amount;
	protected Integer leftAmount;
	protected com.zhiwei.credit.model.admin.BookType bookType;

	protected java.util.Set bookSns = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Book
	 */
	public Book () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Book
	 */
	public Book (
		 Long in_bookId
        ) {
		this.setBookId(in_bookId);
    }

	
	public com.zhiwei.credit.model.admin.BookType getBookType () {
		return bookType;
	}	
	
	public void setBookType (com.zhiwei.credit.model.admin.BookType in_bookType) {
		this.bookType = in_bookType;
	}

	public java.util.Set getBookSns () {
		return bookSns;
	}	
	
	public void setBookSns (java.util.Set in_bookSns) {
		this.bookSns = in_bookSns;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="bookId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBookId() {
		return this.bookId;
	}
	
	/**
	 * Set the bookId
	 */	
	public void setBookId(Long aValue) {
		this.bookId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getTypeId() {
		return this.getBookType()==null?null:this.getBookType().getTypeId();
	}
	
	/**
	 * Set the typeId
	 */	
	public void setTypeId(Long aValue) {
	    if (aValue==null) {
	    	bookType = null;
	    } else if (bookType == null) {
	        bookType = new com.zhiwei.credit.model.admin.BookType(aValue);
	        bookType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			bookType.setTypeId(aValue);
	    }
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
	 * 	 * @return String
	 * @hibernate.property column="author" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Set the author
	 * @spring.validator type="required"
	 */	
	public void setAuthor(String aValue) {
		this.author = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isbn" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getIsbn() {
		return this.isbn;
	}
	
	/**
	 * Set the isbn
	 * @spring.validator type="required"
	 */	
	public void setIsbn(String aValue) {
		this.isbn = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="publisher" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getPublisher() {
		return this.publisher;
	}
	
	/**
	 * Set the publisher
	 */	
	public void setPublisher(String aValue) {
		this.publisher = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="price" type="java.math.BigDecimal" length="10" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * Set the price
	 * @spring.validator type="required"
	 */	
	public void setPrice(java.math.BigDecimal aValue) {
		this.price = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="location" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getLocation() {
		return this.location;
	}
	
	/**
	 * Set the location
	 * @spring.validator type="required"
	 */	
	public void setLocation(String aValue) {
		this.location = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="department" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getDepartment() {
		return this.department;
	}
	
	/**
	 * Set the department
	 * @spring.validator type="required"
	 */	
	public void setDepartment(String aValue) {
		this.department = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="amount" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getAmount() {
		return this.amount;
	}
	
	/**
	 * Set the amount
	 * @spring.validator type="required"
	 */	
	public void setAmount(Integer aValue) {
		this.amount = aValue;
	}	
	public Integer getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Integer leftAmount) {
		this.leftAmount = leftAmount;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Book)) {
			return false;
		}
		Book rhs = (Book) object;
		return new EqualsBuilder()
				.append(this.bookId, rhs.bookId)
						.append(this.bookName, rhs.bookName)
				.append(this.author, rhs.author)
				.append(this.isbn, rhs.isbn)
				.append(this.publisher, rhs.publisher)
				.append(this.price, rhs.price)
				.append(this.location, rhs.location)
				.append(this.department, rhs.department)
				.append(this.amount, rhs.amount)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.bookId) 
						.append(this.bookName) 
				.append(this.author) 
				.append(this.isbn) 
				.append(this.publisher) 
				.append(this.price) 
				.append(this.location) 
				.append(this.department) 
				.append(this.amount) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("bookId", this.bookId) 
						.append("bookName", this.bookName) 
				.append("author", this.author) 
				.append("isbn", this.isbn) 
				.append("publisher", this.publisher) 
				.append("price", this.price) 
				.append("location", this.location) 
				.append("department", this.department) 
				.append("amount", this.amount) 
				.toString();
	}



}
