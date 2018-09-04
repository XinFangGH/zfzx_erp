package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Regulation Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class Regulation extends com.zhiwei.core.model.BaseModel {

	/**
	 * 状态：草稿
	 */
	public static final Short STATUS_DRAFT = 0;
	/**
	 * 状态：生效
	 */
	public static final Short STATUS_EFFECT = 1;
	
    protected Long regId;
	protected String subject;
	protected java.util.Date issueDate;
	protected Long issueUserId;
	protected String issueFullname;
	protected Long issueDepId;
	protected String issueDep;
	protected String recDeps;
	protected String recDepIds;
	protected String recUsers;
	protected String recUserIds;
	protected String content;
	protected String keywords;
	protected Short status;
	protected com.zhiwei.credit.model.system.GlobalType globalType;

	protected java.util.Set regAttachs = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Regulation
	 */
	public Regulation () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Regulation
	 */
	public Regulation (
		 Long in_regId
        ) {
		this.setRegId(in_regId);
    }

	
	public com.zhiwei.credit.model.system.GlobalType getGlobalType () {
		return globalType;
	}	
	
	public void setGlobalType (com.zhiwei.credit.model.system.GlobalType in_globalType) {
		this.globalType = in_globalType;
	}

	public java.util.Set getRegAttachs () {
		return regAttachs;
	}	
	
	public void setRegAttachs (java.util.Set in_regAttachs) {
		this.regAttachs = in_regAttachs;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="regId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRegId() {
		return this.regId;
	}
	
	/**
	 * Set the regId
	 */	
	public void setRegId(Long aValue) {
		this.regId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getProTypeId() {
		return this.getGlobalType()==null?null:this.getGlobalType().getProTypeId();
	}
	
	/**
	 * Set the proTypeId
	 */	
	public void setProTypeId(Long aValue) {
	    if (aValue==null) {
	    	globalType = null;
	    } else if (globalType == null) {
	        globalType = new com.zhiwei.credit.model.system.GlobalType(aValue);
	        globalType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			globalType.setProTypeId(aValue);
	    }
	}	

	/**
	 * 标题	 * @return String
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
	 * 发布日期	 * @return java.util.Date
	 * @hibernate.property column="issueDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getIssueDate() {
		return this.issueDate;
	}
	
	/**
	 * Set the issueDate
	 */	
	public void setIssueDate(java.util.Date aValue) {
		this.issueDate = aValue;
	}	

	/**
	 * 发布人ID	 * @return Long
	 * @hibernate.property column="issueUserId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getIssueUserId() {
		return this.issueUserId;
	}
	
	/**
	 * Set the issueUserId
	 */	
	public void setIssueUserId(Long aValue) {
		this.issueUserId = aValue;
	}	

	/**
	 * 发布人	 * @return String
	 * @hibernate.property column="issueFullname" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getIssueFullname() {
		return this.issueFullname;
	}
	
	/**
	 * Set the issueFullname
	 */	
	public void setIssueFullname(String aValue) {
		this.issueFullname = aValue;
	}	

	/**
	 * 发布部门ID	 * @return Long
	 * @hibernate.property column="issueDepId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getIssueDepId() {
		return this.issueDepId;
	}
	
	/**
	 * Set the issueDepId
	 */	
	public void setIssueDepId(Long aValue) {
		this.issueDepId = aValue;
	}	

	/**
	 * 发布部门	 * @return String
	 * @hibernate.property column="issueDep" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getIssueDep() {
		return this.issueDep;
	}
	
	/**
	 * Set the issueDep
	 */	
	public void setIssueDep(String aValue) {
		this.issueDep = aValue;
	}	

	/**
	 * 接收部门范围	 * @return String
	 * @hibernate.property column="recDeps" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getRecDeps() {
		return this.recDeps;
	}
	
	/**
	 * Set the recDeps
	 */	
	public void setRecDeps(String aValue) {
		this.recDeps = aValue;
	}	

	/**
	 * 接收部门范围ID	 * @return String
	 * @hibernate.property column="recDepIds" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getRecDepIds() {
		return this.recDepIds;
	}
	
	/**
	 * Set the recDepIds
	 */	
	public void setRecDepIds(String aValue) {
		this.recDepIds = aValue;
	}	

	/**
	 * 接收人范围	 * @return String
	 * @hibernate.property column="recUsers" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getRecUsers() {
		return this.recUsers;
	}
	
	/**
	 * Set the recUsers
	 */	
	public void setRecUsers(String aValue) {
		this.recUsers = aValue;
	}	

	/**
	 * 接收人范围ID	 * @return String
	 * @hibernate.property column="recUserIds" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getRecUserIds() {
		return this.recUserIds;
	}
	
	/**
	 * Set the recUserIds
	 */	
	public void setRecUserIds(String aValue) {
		this.recUserIds = aValue;
	}	

	/**
	 * 内容	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content
	 */	
	public void setContent(String aValue) {
		this.content = aValue;
	}	

	/**
	 * 关键字	 * @return String
	 * @hibernate.property column="keywords" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getKeywords() {
		return this.keywords;
	}
	
	/**
	 * Set the keywords
	 */	
	public void setKeywords(String aValue) {
		this.keywords = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Regulation)) {
			return false;
		}
		Regulation rhs = (Regulation) object;
		return new EqualsBuilder()
				.append(this.regId, rhs.regId)
						.append(this.subject, rhs.subject)
				.append(this.issueDate, rhs.issueDate)
				.append(this.issueUserId, rhs.issueUserId)
				.append(this.issueFullname, rhs.issueFullname)
				.append(this.issueDepId, rhs.issueDepId)
				.append(this.issueDep, rhs.issueDep)
				.append(this.recDeps, rhs.recDeps)
				.append(this.recDepIds, rhs.recDepIds)
				.append(this.recUsers, rhs.recUsers)
				.append(this.recUserIds, rhs.recUserIds)
				.append(this.content, rhs.content)
				.append(this.keywords, rhs.keywords)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.regId) 
						.append(this.subject) 
				.append(this.issueDate) 
				.append(this.issueUserId) 
				.append(this.issueFullname) 
				.append(this.issueDepId) 
				.append(this.issueDep) 
				.append(this.recDeps) 
				.append(this.recDepIds) 
				.append(this.recUsers) 
				.append(this.recUserIds) 
				.append(this.content) 
				.append(this.keywords) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("regId", this.regId) 
						.append("subject", this.subject) 
				.append("issueDate", this.issueDate) 
				.append("issueUserId", this.issueUserId) 
				.append("issueFullname", this.issueFullname) 
				.append("issueDepId", this.issueDepId) 
				.append("issueDep", this.issueDep) 
				.append("recDeps", this.recDeps) 
				.append("recDepIds", this.recDepIds) 
				.append("recUsers", this.recUsers) 
				.append("recUserIds", this.recUserIds) 
				.append("content", this.content) 
				.append("keywords", this.keywords) 
				.append("status", this.status) 
				.toString();
	}



}
