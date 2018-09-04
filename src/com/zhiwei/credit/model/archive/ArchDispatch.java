package com.zhiwei.credit.model.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ArchDispatch Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ArchDispatch extends com.zhiwei.core.model.BaseModel {

	public static Short HAVE_READ=1;//已读
	public static Short NOT_READ=0;//未读
	public static Short IS_UNDERTAKE=1; //是承办人
	public static Short IS_READER=0;//阅读
	public static Short IS_DISPATCH=2;//分发人或角色
	public static Short IS_LEADER=0;
	public static Short NOT_LEADER=1;
	public static Short IS_OVER=2;
    protected Long dispatchId;
	protected java.util.Date dispatchTime;
	protected Long userId;
	protected String fullname;
	protected Short isRead;
	protected String subject;
	protected String readFeedback;
	protected Short archUserType;
	protected Long disRoleId;
	protected String disRoleName;
	protected com.zhiwei.credit.model.archive.Archives archives;


	/**
	 * Default Empty Constructor for class ArchDispatch
	 */
	public ArchDispatch () {
		super();
	}
	
	
	
	
	public Long getDisRoleId() {
		return disRoleId;
	}




	public void setDisRoleId(Long disRoleId) {
		this.disRoleId = disRoleId;
	}




	public String getDisRoleName() {
		return disRoleName;
	}




	public void setDisRoleName(String disRoleName) {
		this.disRoleName = disRoleName;
	}




	/**
	 * Default Key Fields Constructor for class ArchDispatch
	 */
	public ArchDispatch (
		 Long in_dispatchId
        ) {
		this.setDispatchId(in_dispatchId);
    }

	
	public com.zhiwei.credit.model.archive.Archives getArchives () {
		return archives;
	}	
	
	public void setArchives (com.zhiwei.credit.model.archive.Archives in_archives) {
		this.archives = in_archives;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="dispatchId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDispatchId() {
		return this.dispatchId;
	}
	
	/**
	 * Set the dispatchId
	 */	
	public void setDispatchId(Long aValue) {
		this.dispatchId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getArchivesId() {
		return this.getArchives()==null?null:this.getArchives().getArchivesId();
	}
	
	/**
	 * Set the archivesId
	 */	
	public void setArchivesId(Long aValue) {
	    if (aValue==null) {
	    	archives = null;
	    } else if (archives == null) {
	        archives = new com.zhiwei.credit.model.archive.Archives(aValue);
	        archives.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			archives.setArchivesId(aValue);
	    }
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="dispatchTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getDispatchTime() {
		return this.dispatchTime;
	}
	
	/**
	 * Set the dispatchTime
	 * @spring.validator type="required"
	 */	
	public void setDispatchTime(java.util.Date aValue) {
		this.dispatchTime = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 * @spring.validator type="required"
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/**
	 * Set the fullname
	 */	
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isRead" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsRead() {
		return this.isRead;
	}
	
	/**
	 * Set the isRead
	 */	
	public void setIsRead(Short aValue) {
		this.isRead = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="subject" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Set the subject
	 */	
	public void setSubject(String aValue) {
		this.subject = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="readFeedback" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getReadFeedback() {
		return this.readFeedback;
	}
	
	/**
	 * Set the readFeedback
	 */	
	public void setReadFeedback(String aValue) {
		this.readFeedback = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isUndertake" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getArchUserType() {
		return this.archUserType;
	}
	
	/**
	 * Set the isUndertake
	 */	
	public void setArchUserType(Short aValue) {
		this.archUserType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ArchDispatch)) {
			return false;
		}
		ArchDispatch rhs = (ArchDispatch) object;
		return new EqualsBuilder()
				.append(this.dispatchId, rhs.dispatchId)
						.append(this.dispatchTime, rhs.dispatchTime)
				.append(this.userId, rhs.userId)
				.append(this.fullname, rhs.fullname)
				.append(this.isRead, rhs.isRead)
				.append(this.subject, rhs.subject)
				.append(this.readFeedback, rhs.readFeedback)
				.append(this.archUserType, rhs.archUserType)
				.append(this.disRoleId, rhs.disRoleId)
				.append(this.disRoleName, rhs.disRoleName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.dispatchId) 
						.append(this.dispatchTime) 
				.append(this.userId) 
				.append(this.fullname) 
				.append(this.isRead) 
				.append(this.subject) 
				.append(this.readFeedback) 
				.append(this.archUserType) 
				.append(this.disRoleId)
				.append(this.disRoleName)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("dispatchId", this.dispatchId) 
						.append("dispatchTime", this.dispatchTime) 
				.append("userId", this.userId) 
				.append("fullname", this.fullname) 
				.append("isRead", this.isRead) 
				.append("subject", this.subject) 
				.append("readFeedback", this.readFeedback) 
				.append("isUndertake", this.archUserType)
				.append("disRoleId",this.disRoleId)
				.append("disRoleName",this.disRoleName)
				.toString();
	}



}
