package com.zhiwei.credit.model.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * PhoneBook Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ���������
 */
public class PhoneBook extends com.zhiwei.core.model.BaseModel {

	@Expose
	protected Long phoneId;
	@Expose
	protected String fullname;
	@Expose
	protected String title;
	@Expose
	protected java.util.Date birthday;
	@Expose
	protected String nickName;
	@Expose
	protected String duty;
	@Expose
	protected String spouse;
	@Expose
	protected String childs;
	@Expose
	protected String companyName;
	@Expose
	protected String companyAddress;
	@Expose
	protected String companyPhone;
	@Expose
	protected String companyFax;
	@Expose
	protected String homeAddress;
	@Expose
	protected String homeZip;
	@Expose
	protected String mobile;
	@Expose
	protected String phone;
	@Expose
	protected String email;
	@Expose
	protected String qqNumber;
	@Expose
	protected String msn;
	@Expose
	protected String note;
	@Expose
	protected Short isShared;
	protected com.zhiwei.credit.model.system.AppUser appUser;
	@Expose
	protected com.zhiwei.credit.model.communicate.PhoneGroup phoneGroup;


	/**
	 * Default Empty Constructor for class PhoneBook
	 */
	public PhoneBook () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PhoneBook
	 */
	public PhoneBook (
		 Long in_phoneId
        ) {
		this.setPhoneId(in_phoneId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
	
	public com.zhiwei.credit.model.communicate.PhoneGroup getPhoneGroup () {
		return phoneGroup;
	}	
	
	public void setPhoneGroup (com.zhiwei.credit.model.communicate.PhoneGroup in_phoneGroup) {
		this.phoneGroup = in_phoneGroup;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="phoneId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPhoneId() {
		return this.phoneId;
	}
	
	/**
	 * Set the phoneId
	 */	
	public void setPhoneId(Long aValue) {
		this.phoneId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/**
	 * Set the fullname
	 * @spring.validator type="required"
	 */	
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}	

	/**
	 * 先生
            女士
            小姐	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 * @spring.validator type="required"
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="birthday" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	
	/**
	 * Set the birthday
	 */	
	public void setBirthday(java.util.Date aValue) {
		this.birthday = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="nickName" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getNickName() {
		return this.nickName;
	}
	
	/**
	 * Set the nickName
	 */	
	public void setNickName(String aValue) {
		this.nickName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="duty" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getDuty() {
		return this.duty;
	}
	
	/**
	 * Set the duty
	 */	
	public void setDuty(String aValue) {
		this.duty = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="spouse" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getSpouse() {
		return this.spouse;
	}
	
	/**
	 * Set the spouse
	 */	
	public void setSpouse(String aValue) {
		this.spouse = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="childs" type="java.lang.String" length="40" not-null="false" unique="false"
	 */
	public String getChilds() {
		return this.childs;
	}
	
	/**
	 * Set the childs
	 */	
	public void setChilds(String aValue) {
		this.childs = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="companyName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCompanyName() {
		return this.companyName;
	}
	
	/**
	 * Set the companyName
	 */	
	public void setCompanyName(String aValue) {
		this.companyName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="companyAddress" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCompanyAddress() {
		return this.companyAddress;
	}
	
	/**
	 * Set the companyAddress
	 */	
	public void setCompanyAddress(String aValue) {
		this.companyAddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="companyPhone" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getCompanyPhone() {
		return this.companyPhone;
	}
	
	/**
	 * Set the companyPhone
	 */	
	public void setCompanyPhone(String aValue) {
		this.companyPhone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="companyFax" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getCompanyFax() {
		return this.companyFax;
	}
	
	/**
	 * Set the companyFax
	 */	
	public void setCompanyFax(String aValue) {
		this.companyFax = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="homeAddress" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getHomeAddress() {
		return this.homeAddress;
	}
	
	/**
	 * Set the homeAddress
	 */	
	public void setHomeAddress(String aValue) {
		this.homeAddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="homeZip" type="java.lang.String" length="12" not-null="false" unique="false"
	 */
	public String getHomeZip() {
		return this.homeZip;
	}
	
	/**
	 * Set the homeZip
	 */	
	public void setHomeZip(String aValue) {
		this.homeZip = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mobile" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getMobile() {
		return this.mobile;
	}
	
	/**
	 * Set the mobile
	 */	
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="phone" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Set the phone
	 */	
	public void setPhone(String aValue) {
		this.phone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="32" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="QQ" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getQqNumber() {
		return this.qqNumber;
	}
	
	/**
	 * Set the qQ
	 */	
	public void setQqNumber(String aValue) {
		this.qqNumber = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="MSN" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getMsn() {
		return this.msn;
	}
	
	/**
	 * Set the mSN
	 */	
	public void setMsn(String aValue) {
		this.msn = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="note" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getNote() {
		return this.note;
	}
	
	/**
	 * Set the note
	 */	
	public void setNote(String aValue) {
		this.note = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getUserId() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
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
	 * 	 * @return Long
	 */
	public Long getGroupId() {
		return this.getPhoneGroup()==null?null:this.getPhoneGroup().getGroupId();
	}
	
	/**
	 * Set the groupId
	 */	
	public void setGroupId(Long aValue) {
	    if (aValue==null) {
	    	phoneGroup = null;
	    } else if (phoneGroup == null) {
	        phoneGroup = new com.zhiwei.credit.model.communicate.PhoneGroup(aValue);
	        phoneGroup.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			phoneGroup.setGroupId(aValue);
	    }
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isShared" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsShared() {
		return this.isShared;
	}
	
	/**
	 * Set the isShared
	 * @spring.validator type="required"
	 */	
	public void setIsShared(Short aValue) {
		this.isShared = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PhoneBook)) {
			return false;
		}
		PhoneBook rhs = (PhoneBook) object;
		return new EqualsBuilder()
				.append(this.phoneId, rhs.phoneId)
				.append(this.fullname, rhs.fullname)
				.append(this.title, rhs.title)
				.append(this.birthday, rhs.birthday)
				.append(this.nickName, rhs.nickName)
				.append(this.duty, rhs.duty)
				.append(this.spouse, rhs.spouse)
				.append(this.childs, rhs.childs)
				.append(this.companyName, rhs.companyName)
				.append(this.companyAddress, rhs.companyAddress)
				.append(this.companyPhone, rhs.companyPhone)
				.append(this.companyFax, rhs.companyFax)
				.append(this.homeAddress, rhs.homeAddress)
				.append(this.homeZip, rhs.homeZip)
				.append(this.mobile, rhs.mobile)
				.append(this.phone, rhs.phone)
				.append(this.email, rhs.email)
				.append(this.qqNumber, rhs.qqNumber)
				.append(this.msn, rhs.msn)
				.append(this.note, rhs.note)
								.append(this.isShared, rhs.isShared)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.phoneId) 
				.append(this.fullname) 
				.append(this.title) 
				.append(this.birthday) 
				.append(this.nickName) 
				.append(this.duty) 
				.append(this.spouse) 
				.append(this.childs) 
				.append(this.companyName) 
				.append(this.companyAddress) 
				.append(this.companyPhone) 
				.append(this.companyFax) 
				.append(this.homeAddress) 
				.append(this.homeZip) 
				.append(this.mobile) 
				.append(this.phone) 
				.append(this.email) 
				.append(this.qqNumber) 
				.append(this.msn) 
				.append(this.note) 
								.append(this.isShared) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("phoneId", this.phoneId) 
				.append("fullname", this.fullname) 
				.append("title", this.title) 
				.append("birthday", this.birthday) 
				.append("nickName", this.nickName) 
				.append("duty", this.duty) 
				.append("spouse", this.spouse) 
				.append("childs", this.childs) 
				.append("companyName", this.companyName) 
				.append("companyAddress", this.companyAddress) 
				.append("companyPhone", this.companyPhone) 
				.append("companyFax", this.companyFax) 
				.append("homeAddress", this.homeAddress) 
				.append("homeZip", this.homeZip) 
				.append("mobile", this.mobile) 
				.append("phone", this.phone) 
				.append("email", this.email) 
				.append("qqNumber", this.qqNumber) 
				.append("msn", this.msn) 
				.append("note", this.note) 
								.append("isShared", this.isShared) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "phoneId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Long getId() {
		return phoneId;
	}

}
