package com.zhiwei.credit.model.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;

/**
 * Mail Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class Mail extends com.zhiwei.core.model.BaseModel {

    protected Long mailId;
	protected String sender;
	protected Short importantFlag;
	protected java.util.Date sendTime;
	protected String content;
	protected String subject;
	protected String copyToNames;
	protected String copyToIDs;
	protected String recipientNames;
	protected String recipientIDs;
	protected Short mailStatus;
	protected AppUser appSender;
	protected String fileIds;
	protected String filenames;

	protected Set<FileAttach> mailAttachs = new HashSet<FileAttach>();
	protected Set<MailBox> mailBoxs = new HashSet<MailBox>();

	/**
	 * Default Empty Constructor for class Mail
	 */
	public Mail () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Mail
	 */
	public Mail (
		 Long in_mailId
        ) {
		this.setMailId(in_mailId);
    }


	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public String getFilenames() {
		return filenames;
	}

	public void setFilenames(String filenames) {
		this.filenames = filenames;
	}

	public AppUser getAppSender() {
		return appSender;
	}

	public void setAppSender(AppUser appSender) {
		this.appSender = appSender;
	}
	
	public Set<FileAttach> getMailAttachs() {
		return mailAttachs;
	}

	public void setMailAttachs(Set<FileAttach> mailAttachs) {
		this.mailAttachs = mailAttachs;
	}

	public Set<MailBox> getMailBoxs() {
		return mailBoxs;
	}

	public void setMailBoxs(Set<MailBox> mailBoxs) {
		this.mailBoxs = mailBoxs;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="mailId" type="java.lang.Long" generator-class="native"
	 */
	public Long getMailId() {
		return this.mailId;
	}
	
	/**
	 * Set the mailId
	 */	
	public void setMailId(Long aValue) {
		this.mailId = aValue;
	}	


	



	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * 1=一般
            2=重要
            3=非常重要	 * @return Short
	 * @hibernate.property column="importantFlag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getImportantFlag() {
		return this.importantFlag;
	}
	
	/**
	 * Set the importantFlag
	 * @spring.validator type="required"
	 */	
	public void setImportantFlag(Short aValue) {
		this.importantFlag = aValue;
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
	 * 邮件内容	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="5000" not-null="true" unique="false"
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content
	 * @spring.validator type="required"
	 */	
	public void setContent(String aValue) {
		this.content = aValue;
	}	

	/**
	 * 邮件标题	 * @return String
	 * @hibernate.property column="subject" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Set the subject
	 * @spring.validator type="required"
	 */	
	public void setSubject(String aValue) {
		this.subject = aValue;
	}	

	/**
	 * 抄送人姓名列表	 * @return String
	 * @hibernate.property column="copyToNames" type="java.lang.String" length="1000" not-null="false" unique="false"
	 */
	public String getCopyToNames() {
		return this.copyToNames;
	}
	
	/**
	 * Set the copyToNames
	 */	
	public void setCopyToNames(String aValue) {
		this.copyToNames = aValue;
	}	

	/**
	 * 抄送人ID列表用','分开	 * @return String
	 * @hibernate.property column="copyToIDs" type="java.lang.String" length="1000" not-null="false" unique="false"
	 */
	public String getCopyToIDs() {
		return this.copyToIDs;
	}
	
	/**
	 * Set the copyToIDs
	 */	
	public void setCopyToIDs(String aValue) {
		this.copyToIDs = aValue;
	}	

	/**
	 * 收件人姓名列表	 * @return String
	 * @hibernate.property column="recipientNames" type="java.lang.String" length="1000" not-null="true" unique="false"
	 */
	public String getRecipientNames() {
		return this.recipientNames;
	}
	
	/**
	 * Set the recipientNames
	 * @spring.validator type="required"
	 */	
	public void setRecipientNames(String aValue) {
		this.recipientNames = aValue;
	}	

	/**
	 * 收件人ID列表用','分隔	 * @return String
	 * @hibernate.property column="recipientIDs" type="java.lang.String" length="1000" not-null="true" unique="false"
	 */
	public String getRecipientIDs() {
		return this.recipientIDs;
	}
	
	/**
	 * Set the recipientIDs
	 * @spring.validator type="required"
	 */	
	public void setRecipientIDs(String aValue) {
		this.recipientIDs = aValue;
	}	

	/**
	 * 邮件状态
            1=正式邮件
            0=草稿邮件	 * @return Short
	 * @hibernate.property column="mailStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getMailStatus() {
		return this.mailStatus;
	}
	
	/**
	 * Set the mailStatus
	 * @spring.validator type="required"
	 */	
	public void setMailStatus(Short aValue) {
		this.mailStatus = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Mail)) {
			return false;
		}
		Mail rhs = (Mail) object;
		return new EqualsBuilder()
				.append(this.mailId, rhs.mailId)
				.append(this.sender, rhs.sender)
				.append(this.importantFlag, rhs.importantFlag)
				.append(this.sendTime, rhs.sendTime)
				.append(this.content, rhs.content)
				.append(this.subject, rhs.subject)
				.append(this.copyToNames, rhs.copyToNames)
				.append(this.copyToIDs, rhs.copyToIDs)
				.append(this.recipientNames, rhs.recipientNames)
				.append(this.recipientIDs, rhs.recipientIDs)
				.append(this.mailStatus, rhs.mailStatus)
				.append(this.fileIds, rhs.fileIds)
				.append(this.filenames, rhs.filenames)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.mailId) 
				.append(this.sender) 
				.append(this.importantFlag) 
				.append(this.sendTime) 
				.append(this.content) 
				.append(this.subject) 
				.append(this.copyToNames) 
				.append(this.copyToIDs) 
				.append(this.recipientNames) 
				.append(this.recipientIDs) 
				.append(this.mailStatus)
				.append(this.fileIds)
				.append(this.filenames)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("mailId", this.mailId) 
				.append("sender", this.sender) 
				.append("importantFlag", this.importantFlag) 
				.append("sendTime", this.sendTime) 
				.append("content", this.content) 
				.append("subject", this.subject) 
				.append("copyToNames", this.copyToNames) 
				.append("copyToIDs", this.copyToIDs) 
				.append("recipientNames", this.recipientNames) 
				.append("recipientIDs", this.recipientIDs) 
				.append("mailStatus", this.mailStatus)
				.append("fileIds",this.fileIds)
				.append("filenames",this.filenames)
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "mailId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Long getId() {
		return mailId;
	}

}
