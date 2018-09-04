package com.zhiwei.credit.model.communicate;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SmsMobile Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SmsMobile extends com.zhiwei.core.model.BaseModel {

	/**
	 * 短信已发送
	 */
	public static final Short STATUS_SENDED = (short)1;
	/**
	 * 短信未发送
	 */
	public static final Short STATUS_NOT_SENDED = (short)0;
	
    protected Long smsId;
	protected java.util.Date sendTime;
	protected String recipients;
	protected String phoneNumber;
	protected Long userId;
	protected String userName;
	protected String smsContent;
	protected Short status;


	/**
	 * Default Empty Constructor for class SmsMobile
	 */
	public SmsMobile () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SmsMobile
	 */
	public SmsMobile (
		 Long in_smsId
        ) {
		this.setSmsId(in_smsId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="smsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSmsId() {
		return this.smsId;
	}
	
	/**
	 * Set the smsId
	 */	
	public void setSmsId(Long aValue) {
		this.smsId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="sendTime" type="java.util.Date" length="10" not-null="true" unique="false"
	 */
	public java.util.Date getSendTime() {
		return this.sendTime;
	}
	
	/**
	 * Set the sendTime
	 * @spring.validator type="required"
	 */	
	public void setSendTime(java.util.Date aValue) {
		this.sendTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="recipients" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getRecipients() {
		return this.recipients;
	}
	
	/**
	 * Set the recipients
	 */	
	public void setRecipients(String aValue) {
		this.recipients = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="phoneNumber" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	/**
	 * Set the phoneNumber
	 * @spring.validator type="required"
	 */	
	public void setPhoneNumber(String aValue) {
		this.phoneNumber = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="userName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Set the userName
	 */	
	public void setUserName(String aValue) {
		this.userName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="smsContent" type="java.lang.String" length="1024" not-null="true" unique="false"
	 */
	public String getSmsContent() {
		return this.smsContent;
	}
	
	/**
	 * Set the smsContent
	 * @spring.validator type="required"
	 */	
	public void setSmsContent(String aValue) {
		this.smsContent = aValue;
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
		if (!(object instanceof SmsMobile)) {
			return false;
		}
		SmsMobile rhs = (SmsMobile) object;
		return new EqualsBuilder()
				.append(this.smsId, rhs.smsId)
				.append(this.sendTime, rhs.sendTime)
				.append(this.recipients, rhs.recipients)
				.append(this.phoneNumber, rhs.phoneNumber)
				.append(this.userId, rhs.userId)
				.append(this.userName, rhs.userName)
				.append(this.smsContent, rhs.smsContent)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.smsId) 
				.append(this.sendTime) 
				.append(this.recipients) 
				.append(this.phoneNumber) 
				.append(this.userId) 
				.append(this.userName) 
				.append(this.smsContent) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("smsId", this.smsId) 
				.append("sendTime", this.sendTime) 
				.append("recipients", this.recipients) 
				.append("phoneNumber", this.phoneNumber) 
				.append("userId", this.userId) 
				.append("userName", this.userName) 
				.append("smsContent", this.smsContent) 
				.append("status", this.status) 
				.toString();
	}



}
