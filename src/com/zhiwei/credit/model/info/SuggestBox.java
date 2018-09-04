package com.zhiwei.credit.model.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SuggestBox Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SuggestBox extends com.zhiwei.core.model.BaseModel {

	/**
	 * 状态:草稿
	 */
	public static final Short STATUS_DRAFT = 0;

	/**
	 * 状态:提交
	 */
	public static final Short STATUS_SEND = 1;

	/**
	 * 状态:已回复
	 */
	public static final Short STATUS_AUDIT = 2;
    protected Long boxId;
	protected String subject;
	protected String content;
	protected java.util.Date createtime;
	protected Long recUid;
	protected String recFullname;
	protected Long senderId;
	protected String senderFullname;
	protected String senderIp;
	protected String phone;
	protected String email;
	protected Short isOpen;
	protected String replyContent;
	protected java.util.Date replyTime;
	protected Long replyId;
	protected String replyFullname;
	protected Short status;
	protected String queryPwd;


	/**
	 * Default Empty Constructor for class SuggestBox
	 */
	public SuggestBox () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SuggestBox
	 */
	public SuggestBox (
		 Long in_boxId
        ) {
		this.setBoxId(in_boxId);
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
	 * 意见标题	 * @return String
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
	 * 意见内容	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="4000" not-null="true" unique="false"
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
	 * 创建日期	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 接收人ID	 * @return Long
	 * @hibernate.property column="recUid" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getRecUid() {
		return this.recUid;
	}
	
	/**
	 * Set the recUid
	 */	
	public void setRecUid(Long aValue) {
		this.recUid = aValue;
	}	

	/**
	 * 接收人名	 * @return String
	 * @hibernate.property column="recFullname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getRecFullname() {
		return this.recFullname;
	}
	
	/**
	 * Set the recFullname
	 */	
	public void setRecFullname(String aValue) {
		this.recFullname = aValue;
	}	

	/**
	 * 发送人ID	 * @return Long
	 * @hibernate.property column="senderId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getSenderId() {
		return this.senderId;
	}
	
	/**
	 * Set the senderId
	 */	
	public void setSenderId(Long aValue) {
		this.senderId = aValue;
	}	

	/**
	 * 发送人名	 * @return String
	 * @hibernate.property column="senderFullname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getSenderFullname() {
		return this.senderFullname;
	}
	
	/**
	 * Set the senderFullname
	 */	
	public void setSenderFullname(String aValue) {
		this.senderFullname = aValue;
	}	

	/**
	 * 发送人IP	 * @return String
	 * @hibernate.property column="senderIp" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getSenderIp() {
		return this.senderIp;
	}
	
	/**
	 * Set the senderIp
	 */	
	public void setSenderIp(String aValue) {
		this.senderIp = aValue;
	}	

	/**
	 * 联系电话	 * @return String
	 * @hibernate.property column="phone" type="java.lang.String" length="64" not-null="false" unique="false"
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
	 * Email	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * 是否公开	 * @return Short
	 * @hibernate.property column="isOpen" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsOpen() {
		return this.isOpen;
	}
	
	/**
	 * Set the isOpen
	 */	
	public void setIsOpen(Short aValue) {
		this.isOpen = aValue;
	}	

	/**
	 * 回复内容	 * @return String
	 * @hibernate.property column="replyContent" type="java.lang.String" length="4000" not-null="false" unique="false"
	 */
	public String getReplyContent() {
		return this.replyContent;
	}
	
	/**
	 * Set the replyContent
	 */	
	public void setReplyContent(String aValue) {
		this.replyContent = aValue;
	}	

	/**
	 * 回复时间	 * @return java.util.Date
	 * @hibernate.property column="replyTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getReplyTime() {
		return this.replyTime;
	}
	
	/**
	 * Set the replyTime
	 */	
	public void setReplyTime(java.util.Date aValue) {
		this.replyTime = aValue;
	}	

	/**
	 * 回复人ID	 * @return Long
	 * @hibernate.property column="replyId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getReplyId() {
		return this.replyId;
	}
	
	/**
	 * Set the replyId
	 */	
	public void setReplyId(Long aValue) {
		this.replyId = aValue;
	}	

	/**
	 * 回复人名	 * @return String
	 * @hibernate.property column="replyFullname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getReplyFullname() {
		return this.replyFullname;
	}
	
	/**
	 * Set the replyFullname
	 */	
	public void setReplyFullname(String aValue) {
		this.replyFullname = aValue;
	}	

	/**
	 * 状态	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="queryPwd" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getQueryPwd() {
		return this.queryPwd;
	}
	
	/**
	 * Set the queryPwd
	 */	
	public void setQueryPwd(String aValue) {
		this.queryPwd = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SuggestBox)) {
			return false;
		}
		SuggestBox rhs = (SuggestBox) object;
		return new EqualsBuilder()
				.append(this.boxId, rhs.boxId)
				.append(this.subject, rhs.subject)
				.append(this.content, rhs.content)
				.append(this.createtime, rhs.createtime)
				.append(this.recUid, rhs.recUid)
				.append(this.recFullname, rhs.recFullname)
				.append(this.senderId, rhs.senderId)
				.append(this.senderFullname, rhs.senderFullname)
				.append(this.senderIp, rhs.senderIp)
				.append(this.phone, rhs.phone)
				.append(this.email, rhs.email)
				.append(this.isOpen, rhs.isOpen)
				.append(this.replyContent, rhs.replyContent)
				.append(this.replyTime, rhs.replyTime)
				.append(this.replyId, rhs.replyId)
				.append(this.replyFullname, rhs.replyFullname)
				.append(this.status, rhs.status)
				.append(this.queryPwd, rhs.queryPwd)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.boxId) 
				.append(this.subject) 
				.append(this.content) 
				.append(this.createtime) 
				.append(this.recUid) 
				.append(this.recFullname) 
				.append(this.senderId) 
				.append(this.senderFullname) 
				.append(this.senderIp) 
				.append(this.phone) 
				.append(this.email) 
				.append(this.isOpen) 
				.append(this.replyContent) 
				.append(this.replyTime) 
				.append(this.replyId) 
				.append(this.replyFullname) 
				.append(this.status) 
				.append(this.queryPwd) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("boxId", this.boxId) 
				.append("subject", this.subject) 
				.append("content", this.content) 
				.append("createtime", this.createtime) 
				.append("recUid", this.recUid) 
				.append("recFullname", this.recFullname) 
				.append("senderId", this.senderId) 
				.append("senderFullname", this.senderFullname) 
				.append("senderIp", this.senderIp) 
				.append("phone", this.phone) 
				.append("email", this.email) 
				.append("isOpen", this.isOpen) 
				.append("replyContent", this.replyContent) 
				.append("replyTime", this.replyTime) 
				.append("replyId", this.replyId) 
				.append("replyFullname", this.replyFullname) 
				.append("status", this.status) 
				.append("queryPwd", this.queryPwd) 
				.toString();
	}



}
