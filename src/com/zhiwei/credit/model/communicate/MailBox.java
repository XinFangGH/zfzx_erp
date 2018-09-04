package com.zhiwei.credit.model.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * MailBox Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ���������
 */
public class MailBox extends com.zhiwei.core.model.BaseModel {

    protected Long boxId;
	protected java.util.Date sendTime;
	protected Short delFlag;
	protected Short readFlag;
	protected String note;
	protected com.zhiwei.credit.model.communicate.Mail mail;
	protected com.zhiwei.credit.model.system.AppUser appUser;
	protected com.zhiwei.credit.model.communicate.MailFolder mailFolder;
	protected Short replyFlag;


	/**
	 * Default Empty Constructor for class MailBox
	 */
	public MailBox () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class MailBox
	 */
	public MailBox (
		 Long in_boxId
        ) {
		this.setBoxId(in_boxId);
    }

	
	public com.zhiwei.credit.model.communicate.Mail getMail(){
		return mail;
	}	
	
	public void setMail (com.zhiwei.credit.model.communicate.Mail in_mail) {
		this.mail = in_mail;
	}
	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
	
	public com.zhiwei.credit.model.communicate.MailFolder getMailFolder () {
		return mailFolder;
	}	
	
	public void setMailFolder (com.zhiwei.credit.model.communicate.MailFolder in_mailFolder) {
		this.mailFolder = in_mailFolder;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="boxId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBoxId() {
		return this.boxId;
	}
	
	/**
	 * Set the boxId
	 */	
	public void setBoxId(Long aValue) {
		this.boxId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getMailId() {
		return this.getMail()==null?null:this.getMail().getMailId();
	}
	
	/**
	 * Set the mailId
	 */	
	public void setMailId(Long aValue) {
	    if (aValue==null) {
	    	mail = null;
	    } else if (mail == null) {
	        mail = new com.zhiwei.credit.model.communicate.Mail(aValue);
	        mail.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			mail.setMailId(aValue);
	    }
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getFolderId() {
		return this.getMailFolder()==null?null:this.getMailFolder().getFolderId();
	}
	
	/**
	 * Set the folderId
	 */	
	public void setFolderId(Long aValue) {
	    if (aValue==null) {
	    	mailFolder = null;
	    } else if (mailFolder == null) {
	        mailFolder = new com.zhiwei.credit.model.communicate.MailFolder(aValue);
	        mailFolder.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			mailFolder.setFolderId(aValue);
	    }
	}	

	/**
	 * 主键	 * @return Long
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="sendTime" type="java.util.Date" length="19" not-null="true" unique="false"
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
	 * del=1则代表删除	 * @return Short
	 * @hibernate.property column="delFlag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getDelFlag() {
		return this.delFlag;
	}
	
	/**
	 * Set the delFlag
	 * @spring.validator type="required"
	 */	
	public void setDelFlag(Short aValue) {
		this.delFlag = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="readFlag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getReadFlag() {
		return this.readFlag;
	}
	
	/**
	 * Set the readFlag
	 * @spring.validator type="required"
	 */	
	public void setReadFlag(Short aValue) {
		this.readFlag = aValue;
	}	

	/**
	 * note	 * @return String
	 * @hibernate.property column="note" type="java.lang.String" length="256" not-null="false" unique="false"
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

	
	public Short getReplyFlag() {
		return replyFlag;
	}

	public void setReplyFlag(Short replyFlag) {
		this.replyFlag = replyFlag;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof MailBox)) {
			return false;
		}
		MailBox rhs = (MailBox) object;
		return new EqualsBuilder()
				.append(this.boxId, rhs.boxId)
										.append(this.sendTime, rhs.sendTime)
				.append(this.delFlag, rhs.delFlag)
				.append(this.readFlag, rhs.readFlag)
				.append(this.note, rhs.note)
				.append(this.replyFlag, replyFlag)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.boxId) 
				.append(this.sendTime) 
				.append(this.delFlag) 
				.append(this.readFlag) 
				.append(this.note) 
				.append(this.replyFlag)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("boxId", this.boxId) 
				.append("sendTime", this.sendTime) 
				.append("delFlag", this.delFlag) 
				.append("readFlag", this.readFlag) 
				.append("note", this.note) 
				.append("replyFlag",this.replyFlag)
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "boxId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Long getId() {
		return boxId;
	}

}
