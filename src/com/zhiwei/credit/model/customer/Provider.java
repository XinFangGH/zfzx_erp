package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * Provider Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class Provider extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long providerId;
	@Expose
	protected String providerName;
	@Expose
	protected String contactor;
	@Expose
	protected String phone;
	@Expose
	protected String fax;
	@Expose
	protected String site;
	@Expose
	protected String email;
	@Expose
	protected String address;
	@Expose
	protected String zip;
	@Expose
	protected String openBank;
	@Expose
	protected String account;
	@Expose
	protected String notes;
	@Expose
	protected Integer rank;

	protected java.util.Set products = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Provider
	 */
	public Provider () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Provider
	 */
	public Provider (
		 Long in_providerId
        ) {
		this.setProviderId(in_providerId);
    }


	public java.util.Set getProducts () {
		return products;
	}	
	
	public void setProducts (java.util.Set in_products) {
		this.products = in_products;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="providerId" type="java.lang.Long" generator-class="native"
	 */
	public Long getProviderId() {
		return this.providerId;
	}
	
	/**
	 * Set the providerId
	 */	
	public void setProviderId(Long aValue) {
		this.providerId = aValue;
	}	

	/**
	 * ¹©Ó¦ÉÌÃû³Æ	 * @return String
	 * @hibernate.property column="providerName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getProviderName() {
		return this.providerName;
	}
	
	/**
	 * Set the providerName
	 * @spring.validator type="required"
	 */	
	public void setProviderName(String aValue) {
		this.providerName = aValue;
	}	

	/**
	 * ÁªÏµÈË	 * @return String
	 * @hibernate.property column="contactor" type="java.lang.String" length="128" not-null="true" unique="false"
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
	 * µç»°	 * @return String
	 * @hibernate.property column="phone" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Set the phone
	 * @spring.validator type="required"
	 */	
	public void setPhone(String aValue) {
		this.phone = aValue;
	}	

	/**
	 * ´«Õæ	 * @return String
	 * @hibernate.property column="fax" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getFax() {
		return this.fax;
	}
	
	/**
	 * Set the fax
	 */	
	public void setFax(String aValue) {
		this.fax = aValue;
	}	

	/**
	 * ÍøÖ·	 * @return String
	 * @hibernate.property column="site" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getSite() {
		return this.site;
	}
	
	/**
	 * Set the site
	 */	
	public void setSite(String aValue) {
		this.site = aValue;
	}	

	/**
	 * ÓÊ¼þ	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * µØÖ·	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 * @spring.validator type="required"
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	

	/**
	 * ÓÊ±à	 * @return String
	 * @hibernate.property column="zip" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getZip() {
		return this.zip;
	}
	
	/**
	 * Set the zip
	 */	
	public void setZip(String aValue) {
		this.zip = aValue;
	}	

	/**
	 * ¿ª»§ÐÐ	 * @return String
	 * @hibernate.property column="openBank" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getOpenBank() {
		return this.openBank;
	}
	
	/**
	 * Set the openBank
	 */	
	public void setOpenBank(String aValue) {
		this.openBank = aValue;
	}	

	/**
	 * ÕÊºÅ	 * @return String
	 * @hibernate.property column="account" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getAccount() {
		return this.account;
	}
	
	/**
	 * Set the account
	 */	
	public void setAccount(String aValue) {
		this.account = aValue;
	}	

	/**
	 * ±¸×¢	 * @return String
	 * @hibernate.property column="notes" type="java.lang.String" length="500" not-null="false" unique="false"
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

	/**
	 * ¹©Ó¦ÉÌµÈ¼¶
            1=Ò»¼¶¹©Ó¦ÉÌ
            2£½¶þ¼¶¹©Ó¦ÉÌ
            3£½Èý¼¶¹©Ó¦ÉÌ
            4£½ËÄ¼¶¹©Ó¦ÉÌ
            	 * @return Integer
	 * @hibernate.property column="rank" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRank() {
		return this.rank;
	}
	
	/**
	 * Set the rank
	 */	
	public void setRank(Integer aValue) {
		this.rank = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Provider)) {
			return false;
		}
		Provider rhs = (Provider) object;
		return new EqualsBuilder()
				.append(this.providerId, rhs.providerId)
				.append(this.providerName, rhs.providerName)
				.append(this.contactor, rhs.contactor)
				.append(this.phone, rhs.phone)
				.append(this.fax, rhs.fax)
				.append(this.site, rhs.site)
				.append(this.email, rhs.email)
				.append(this.address, rhs.address)
				.append(this.zip, rhs.zip)
				.append(this.openBank, rhs.openBank)
				.append(this.account, rhs.account)
				.append(this.notes, rhs.notes)
				.append(this.rank, rhs.rank)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.providerId) 
				.append(this.providerName) 
				.append(this.contactor) 
				.append(this.phone) 
				.append(this.fax) 
				.append(this.site) 
				.append(this.email) 
				.append(this.address) 
				.append(this.zip) 
				.append(this.openBank) 
				.append(this.account) 
				.append(this.notes) 
				.append(this.rank) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("providerId", this.providerId) 
				.append("providerName", this.providerName) 
				.append("contactor", this.contactor) 
				.append("phone", this.phone) 
				.append("fax", this.fax) 
				.append("site", this.site) 
				.append("email", this.email) 
				.append("address", this.address) 
				.append("zip", this.zip) 
				.append("openBank", this.openBank) 
				.append("account", this.account) 
				.append("notes", this.notes) 
				.append("rank", this.rank) 
				.toString();
	}



}
