package com.zhiwei.credit.model.communicate;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * OutMailUserSeting Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
@SuppressWarnings("serial")
public class OutMailUserSeting extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long id;
	@Expose
	protected String userName;
	@Expose
	protected Long reuserId;
	@Expose
	protected String mailAddress;
	@Expose
	protected String mailPass;
	@Expose
	protected String smtpHost;
	@Expose
	protected String smtpPort;
	@Expose
	protected String popHost;
	@Expose
	protected String popPort;
	@Expose
	protected com.zhiwei.credit.model.system.AppUser appUser;
	@Expose
	protected java.util.Set<OutMail> outMails = new java.util.HashSet<OutMail>();

	/**
	 * Default Empty Constructor for class OutMailUserSeting
	 */
	public OutMailUserSeting () {
		super();
	}
	
	public Long getReuserId() {
		return reuserId;
	}

	public void setReuserId(Long reuserId) {
		this.reuserId = reuserId;
	}

	/**
	 * Default Key Fields Constructor for class OutMailUserSeting
	 */
	public OutMailUserSeting (
		 Long in_id
        ) {
		this.setId(in_id);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}

	public java.util.Set<OutMail> getOutMails () {
		return outMails;
	}	
	
	public void setOutMails (java.util.Set<OutMail> in_outMails) {
		this.outMails = in_outMails;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 用户ID	 * @return Long
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
	 * 用户名称	 * @return String
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
	 * 外部邮件地址	 * @return String
	 * @hibernate.property column="mailAddress" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getMailAddress() {
		return this.mailAddress;
	}
	
	/**
	 * Set the mailAddress
	 * @spring.validator type="required"
	 */	
	public void setMailAddress(String aValue) {
		this.mailAddress = aValue;
	}	

	/**
	 * 外部邮件密码	 * @return String
	 * @hibernate.property column="mailPass" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getMailPass() {
		return this.mailPass;
	}
	
	/**
	 * Set the mailPass
	 * @spring.validator type="required"
	 */	
	public void setMailPass(String aValue) {
		this.mailPass = aValue;
	}	

	/**
	 * smt主机	 * @return String
	 * @hibernate.property column="smtpHost" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getSmtpHost() {
		return this.smtpHost;
	}
	
	/**
	 * Set the smtpHost
	 * @spring.validator type="required"
	 */	
	public void setSmtpHost(String aValue) {
		this.smtpHost = aValue;
	}	

	/**
	 * smt端口	 * @return String
	 * @hibernate.property column="smtpPort" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getSmtpPort() {
		return this.smtpPort;
	}
	
	/**
	 * Set the smtpPort
	 * @spring.validator type="required"
	 */	
	public void setSmtpPort(String aValue) {
		this.smtpPort = aValue;
	}	

	/**
	 * pop主机	 * @return String
	 * @hibernate.property column="popHost" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getPopHost() {
		return this.popHost;
	}
	
	/**
	 * Set the popHost
	 * @spring.validator type="required"
	 */	
	public void setPopHost(String aValue) {
		this.popHost = aValue;
	}	

	/**
	 * pop端口	 * @return String
	 * @hibernate.property column="popPort" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getPopPort() {
		return this.popPort;
	}
	
	/**
	 * Set the popPort
	 * @spring.validator type="required"
	 */	
	public void setPopPort(String aValue) {
		this.popPort = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OutMailUserSeting)) {
			return false;
		}
		OutMailUserSeting rhs = (OutMailUserSeting) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
						.append(this.userName, rhs.userName)
				.append(this.mailAddress, rhs.mailAddress)
				.append(this.mailPass, rhs.mailPass)
				.append(this.smtpHost, rhs.smtpHost)
				.append(this.smtpPort, rhs.smtpPort)
				.append(this.popHost, rhs.popHost)
				.append(this.popPort, rhs.popPort)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
						.append(this.userName) 
				.append(this.mailAddress) 
				.append(this.mailPass) 
				.append(this.smtpHost) 
				.append(this.smtpPort) 
				.append(this.popHost) 
				.append(this.popPort) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
						.append("userName", this.userName) 
				.append("mailAddress", this.mailAddress) 
				.append("mailPass", this.mailPass) 
				.append("smtpHost", this.smtpHost) 
				.append("smtpPort", this.smtpPort) 
				.append("popHost", this.popHost) 
				.append("popPort", this.popPort) 
				.toString();
	}



}
