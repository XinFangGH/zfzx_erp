package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * CusLinkman Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ���������������
 */
public class CusLinkman extends com.zhiwei.core.model.BaseModel {

    protected Long linkmanId;
	protected String fullname;
	protected Short sex;
	protected String position;
	protected String phone;
	protected String mobile;
	protected String email;
	protected String msn;
	protected String qq;
	protected String fax;
	protected java.util.Date birthday;
	protected String homeAddress;
	protected String homeZip;
	protected String homePhone;
	protected String hobby;
	protected Short isPrimary;
	protected String notes;
	//protected Long customerId;
	protected com.zhiwei.credit.model.customer.Customer customer;


	/**
	 * Default Empty Constructor for class CusLinkman
	 */
	public CusLinkman () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CusLinkman
	 */
	public CusLinkman (
		 Long in_linkmanId
        ) {
		this.setLinkmanId(in_linkmanId);
    }

	
	public com.zhiwei.credit.model.customer.Customer getCustomer () {
		return customer;
	}	
	
	public void setCustomer (com.zhiwei.credit.model.customer.Customer in_customer) {
		this.customer = in_customer;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="linkmanId" type="java.lang.Long" generator-class="native"
	 */
	public Long getLinkmanId() {
		return this.linkmanId;
	}
	
	/**
	 * Set the linkmanId
	 */	
	public void setLinkmanId(Long aValue) {
		this.linkmanId = aValue;
	}	

	/**
	 * 所属客户	 * @return Long
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
	 * 姓名	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="32" not-null="true" unique="false"
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
	 * 性别	 * @return Short
	 * @hibernate.property column="sex" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getSex() {
		return this.sex;
	}
	
	/**
	 * Set the sex
	 * @spring.validator type="required"
	 */	
	public void setSex(Short aValue) {
		this.sex = aValue;
	}	

	/**
	 * 职位	 * @return String
	 * @hibernate.property column="position" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getPosition() {
		return this.position;
	}
	
	/**
	 * Set the position
	 */	
	public void setPosition(String aValue) {
		this.position = aValue;
	}	

	/**
	 * 电话	 * @return String
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
	 * 手机	 * @return String
	 * @hibernate.property column="mobile" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getMobile() {
		return this.mobile;
	}
	
	/**
	 * Set the mobile
	 * @spring.validator type="required"
	 */	
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}	

	/**
	 * Email	 * @return String
	 * @hibernate.property column="Email" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * MSN	 * @return String
	 * @hibernate.property column="MSN" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	/**
	 * QQ	 * @return String
	 * @hibernate.property column="QQ" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	

	/**
	 * 生日	 * @return java.util.Date
	 * @hibernate.property column="birthday" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Set the birthday
	 */	
	public void setBirthday(java.util.Date aValue) {
		this.birthday = aValue;
	}	

	/**
	 * 家庭住址	 * @return String
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
	 * 邮编	 * @return String
	 * @hibernate.property column="homeZip" type="java.lang.String" length="32" not-null="false" unique="false"
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
	 * 家庭电话	 * @return String
	 * @hibernate.property column="homePhone" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getHomePhone() {
		return this.homePhone;
	}
	
	/**
	 * Set the homePhone
	 */	
	public void setHomePhone(String aValue) {
		this.homePhone = aValue;
	}	

	/**
	 * 爱好	 * @return String
	 * @hibernate.property column="hobby" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getHobby() {
		return this.hobby;
	}
	
	/**
	 * Set the hobby
	 */	
	public void setHobby(String aValue) {
		this.hobby = aValue;
	}	

	/**
	 * 是否为主要联系人	 * @return Short
	 * @hibernate.property column="isPrimary" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsPrimary() {
		return this.isPrimary;
	}
	
	/**
	 * Set the isPrimary
	 * @spring.validator type="required"
	 */	
	public void setIsPrimary(Short aValue) {
		this.isPrimary = aValue;
	}	

	/**
	 * 备注	 * @return String
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
	
	
//	public Long getCustomerId() {
//		return customerId;
//	}
//
//	public void setCustomerId(Long customerId) {
//		this.customerId = customerId;
//	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CusLinkman)) {
			return false;
		}
		CusLinkman rhs = (CusLinkman) object;
		return new EqualsBuilder()
				.append(this.linkmanId, rhs.linkmanId)
						.append(this.fullname, rhs.fullname)
				.append(this.sex, rhs.sex)
				.append(this.position, rhs.position)
				.append(this.phone, rhs.phone)
				.append(this.mobile, rhs.mobile)
				.append(this.email, rhs.email)
				.append(this.msn, rhs.msn)
				.append(this.qq, rhs.qq)
				.append(this.fax, rhs.fax)
				.append(this.birthday, rhs.birthday)
				.append(this.homeAddress, rhs.homeAddress)
				.append(this.homeZip, rhs.homeZip)
				.append(this.homePhone, rhs.homePhone)
				.append(this.hobby, rhs.hobby)
				.append(this.isPrimary, rhs.isPrimary)
				.append(this.notes, rhs.notes)
				//.append(this.customerId, rhs.customerId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.linkmanId) 
						.append(this.fullname) 
				.append(this.sex) 
				.append(this.position) 
				.append(this.phone) 
				.append(this.mobile) 
				.append(this.email) 
				.append(this.msn) 
				.append(this.qq) 
				.append(this.fax)
				.append(this.birthday) 
				.append(this.homeAddress) 
				.append(this.homeZip) 
				.append(this.homePhone) 
				.append(this.hobby) 
				.append(this.isPrimary) 
				.append(this.notes) 
				//.append(this.customerId)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("linkmanId", this.linkmanId) 
						.append("fullname", this.fullname) 
				.append("sex", this.sex) 
				.append("position", this.position) 
				.append("phone", this.phone) 
				.append("mobile", this.mobile) 
				.append("email", this.email) 
				.append("msn", this.msn) 
				.append("qq", this.qq) 
				.append("fax",this.fax)
				.append("birthday", this.birthday) 
				.append("homeAddress", this.homeAddress) 
				.append("homeZip", this.homeZip) 
				.append("homePhone", this.homePhone) 
				.append("hobby", this.hobby) 
				.append("isPrimary", this.isPrimary) 
				.append("notes", this.notes) 
				//.append("customerId",this.customerId)
				.toString();
	}



}
