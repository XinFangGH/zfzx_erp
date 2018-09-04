package com.zhiwei.credit.model.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ArchivesDep Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ArchivesDep extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主送部门 
	 */
	public final static Short RECEIVE_MAIN=1;
	/**
	 * 抄送部门
	 */
	public final static Short RECEIVE_COPY=0; 
	
	/**
	 * 已经签收
	 */
	public final static Short STATUS_SIGNED=1;
	/**
	 * 尚未签收
	 */
	public final static Short STATUS_UNSIGNED=0;
	
    protected Long archDepId;
	protected String signNo;
	protected String subject;
	protected Short status;
	protected java.util.Date signTime;
	protected String signFullname;
	protected Long signUserID;
	protected String handleFeedback;
	protected Short isMain;
	//protected com.zhiwei.credit.model.archive.ArchRecType archRecType;
	protected com.zhiwei.credit.model.archive.Archives archives;
	protected com.zhiwei.credit.model.system.Department department;


	/**
	 * Default Empty Constructor for class ArchivesDep
	 */
	public ArchivesDep () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ArchivesDep
	 */
	public ArchivesDep (
		 Long in_archDepId
        ) {
		this.setArchDepId(in_archDepId);
    }

	
//	public com.zhiwei.credit.model.archive.ArchRecType getArchRecType () {
//		return archRecType;
//	}	
//	
//	public void setArchRecType (com.zhiwei.credit.model.archive.ArchRecType in_archRecType) {
//		this.archRecType = in_archRecType;
//	}
	
	public com.zhiwei.credit.model.archive.Archives getArchives () {
		return archives;
	}	
	
	public void setArchives (com.zhiwei.credit.model.archive.Archives in_archives) {
		this.archives = in_archives;
	}
	
	public com.zhiwei.credit.model.system.Department getDepartment () {
		return department;
	}	
	
	public void setDepartment (com.zhiwei.credit.model.system.Department in_department) {
		this.department = in_department;
	}
    

	/**
	 * 主键	 * @return Long
     * @hibernate.id column="archDepId" type="java.lang.Long" generator-class="native"
	 */
	public Long getArchDepId() {
		return this.archDepId;
	}
	
	/**
	 * Set the archDepId
	 */	
	public void setArchDepId(Long aValue) {
		this.archDepId = aValue;
	}	

	/**
	 * 自编号	 * @return String
	 * @hibernate.property column="signNo" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getSignNo() {
		return this.signNo;
	}
	
	/**
	 * Set the signNo
	 */	
	public void setSignNo(String aValue) {
		this.signNo = aValue;
	}	

	/**
	 * 收文部门	 * @return Long
	 */
	public Long getDepId() {
		return this.getDepartment()==null?null:this.getDepartment().getDepId();
	}
	
	/**
	 * Set the depId
	 */	
	public void setDepId(Long aValue) {
	    if (aValue==null) {
	    	department = null;
	    } else if (department == null) {
	        department = new com.zhiwei.credit.model.system.Department(aValue);
	        department.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			department.setDepId(aValue);
	    }
	}	

	/**
	 * 所属公文	 * @return Long
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
	 * 公文标题	 * @return String
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
	 * 所属收文分类	 * @return Long
	 */
//	public Long getRecTypeId() {
//		return this.getArchRecType()==null?null:this.getArchRecType().getRecTypeId();
//	}
	
	/**
	 * Set the typeId
	 */	
//	public void setRecTypeId(Long aValue) {
//	    if (aValue==null) {
//	    	archRecType = null;
//	    } else if (archRecType == null) {
//	        archRecType = new com.zhiwei.credit.model.archive.ArchRecType(aValue);
//	        archRecType.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//			archRecType.setRecTypeId(aValue);
//	    }
//	}	

	/**
	 * 签收状态
            0=未签收
            1=已签收	 * @return Short
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
	 * 签收日期	 * @return java.util.Date
	 * @hibernate.property column="signTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSignTime() {
		return this.signTime;
	}
	
	/**
	 * Set the signTime
	 */	
	public void setSignTime(java.util.Date aValue) {
		this.signTime = aValue;
	}	

	/**
	 * 签收人	 * @return String
	 * @hibernate.property column="signFullname" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getSignFullname() {
		return this.signFullname;
	}
	
	/**
	 * Set the signFullname
	 */	
	public void setSignFullname(String aValue) {
		this.signFullname = aValue;
	}	

	public Long getSignUserID() {
		return signUserID;
	}

	public void setSignUserID(Long signUserID) {
		this.signUserID = signUserID;
	}

	/**
	 * 办理结果反馈	 * @return String
	 * @hibernate.property column="handleFeedback" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getHandleFeedback() {
		return this.handleFeedback;
	}
	
	/**
	 * Set the handleFeedback
	 */	
	public void setHandleFeedback(String aValue) {
		this.handleFeedback = aValue;
	}	

	/**
	 * 主送、抄送
            1=主送
            0=抄送	 * @return Short
	 * @hibernate.property column="isMain" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsMain() {
		return this.isMain;
	}
	
	/**
	 * Set the isMain
	 * @spring.validator type="required"
	 */	
	public void setIsMain(Short aValue) {
		this.isMain = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ArchivesDep)) {
			return false;
		}
		ArchivesDep rhs = (ArchivesDep) object;
		return new EqualsBuilder()
				.append(this.archDepId, rhs.archDepId)
				.append(this.signNo, rhs.signNo)
								.append(this.subject, rhs.subject)
						.append(this.status, rhs.status)
				.append(this.signTime, rhs.signTime)
				.append(this.signFullname, rhs.signFullname)
				.append(this.handleFeedback, rhs.handleFeedback)
				.append(this.isMain, rhs.isMain)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.archDepId) 
				.append(this.signNo) 
								.append(this.subject) 
						.append(this.status) 
				.append(this.signTime) 
				.append(this.signFullname) 
				.append(this.handleFeedback) 
				.append(this.isMain) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("archDepId", this.archDepId) 
				.append("signNo", this.signNo) 
								.append("subject", this.subject) 
						.append("status", this.status) 
				.append("signTime", this.signTime) 
				.append("signFullname", this.signFullname) 
				.append("handleFeedback", this.handleFeedback) 
				.append("isMain", this.isMain) 
				.toString();
	}



}
