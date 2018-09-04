package com.zhiwei.credit.model.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.zhiwei.credit.model.system.AppUser;

/**
 * ArchivesDoc Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ArchivesDoc extends com.zhiwei.core.model.BaseModel {

	public static short STATUS_MODIFY = (short)0;//版本状态为修改
	public static short STATUS_MODIFY_END = (short)1;//版本状态为完成修改
	public static int ORI_VERSION = 1;//原始版本
	@Expose
    protected Long docId;
	@Expose
	protected String creator;
	@Expose
	protected Long creatorId;
	@Expose
	protected Long menderId;
	@Expose
	protected String mender;
	@Expose
	protected String docName;
	@Expose
	protected Short docStatus;
	@Expose
	protected Integer curVersion;
	@Expose
	protected String docPath;
	@Expose
	protected java.util.Date updatetime;
	@Expose
	protected java.util.Date createtime;
	@Expose
	protected com.zhiwei.credit.model.system.FileAttach fileAttach;
	
	protected com.zhiwei.credit.model.archive.Archives archives;
	
//	@Expose
	protected java.util.Set docHistorys = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class ArchivesDoc
	 */
	public ArchivesDoc () {
		super();
	}
	
	
	public void initUsers(AppUser curUser){
		setCreator(curUser.getFullname());
		setCreatorId(curUser.getUserId());
		
		setMender(curUser.getFullname());
		setMenderId(curUser.getUserId());
	}
	/**
	 * Default Key Fields Constructor for class ArchivesDoc
	 */
	public ArchivesDoc (
		 Long in_docId
        ) {
		this.setDocId(in_docId);
    }

	
	public com.zhiwei.credit.model.archive.Archives getArchives () {
		return archives;
	}	
	
	public void setArchives (com.zhiwei.credit.model.archive.Archives in_archives) {
		this.archives = in_archives;
	}
	
	public com.zhiwei.credit.model.system.FileAttach getFileAttach () {
		return fileAttach;
	}	
	
	public void setFileAttach (com.zhiwei.credit.model.system.FileAttach in_fileAttach) {
		this.fileAttach = in_fileAttach;
	}

	public java.util.Set getDocHistorys () {
		return docHistorys;
	}	
	
	public void setDocHistorys (java.util.Set in_docHistorys) {
		this.docHistorys = in_docHistorys;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="docId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDocId() {
		return this.docId;
	}
	
	/**
	 * Set the docId
	 */	
	public void setDocId(Long aValue) {
		this.docId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getFileId() {
		return this.getFileAttach()==null?null:this.getFileAttach().getFileId();
	}
	
	/**
	 * Set the fileId
	 */	
	public void setFileId(Long aValue) {
	    if (aValue==null) {
	    	fileAttach = null;
	    } else if (fileAttach == null) {
	        fileAttach = new com.zhiwei.credit.model.system.FileAttach(aValue);
	        fileAttach.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			fileAttach.setFileId(aValue);
	    }
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
	 * 拟稿人	 * @return String
	 * @hibernate.property column="creator" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * Set the creator
	 */	
	public void setCreator(String aValue) {
		this.creator = aValue;
	}	

	/**
	 * 拟稿人ID	 * @return Long
	 * @hibernate.property column="creatorId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCreatorId() {
		return this.creatorId;
	}
	
	/**
	 * Set the creatorId
	 */	
	public void setCreatorId(Long aValue) {
		this.creatorId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="menderId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMenderId() {
		return this.menderId;
	}
	
	/**
	 * Set the menderId
	 */	
	public void setMenderId(Long aValue) {
		this.menderId = aValue;
	}	

	/**
	 * 修改人	 * @return String
	 * @hibernate.property column="mender" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getMender() {
		return this.mender;
	}
	
	/**
	 * Set the mender
	 */	
	public void setMender(String aValue) {
		this.mender = aValue;
	}	

	/**
	 * 文档名称	 * @return String
	 * @hibernate.property column="docName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getDocName() {
		return this.docName;
	}
	
	/**
	 * Set the docName
	 * @spring.validator type="required"
	 */	
	public void setDocName(String aValue) {
		this.docName = aValue;
	}	

	/**
	 * 文档状态
            0=修改中
            1=修改完成	 * @return Short
	 * @hibernate.property column="docStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getDocStatus() {
		return this.docStatus;
	}
	
	/**
	 * Set the docStatus
	 * @spring.validator type="required"
	 */	
	public void setDocStatus(Short aValue) {
		this.docStatus = aValue;
	}	

	/**
	 * 当前版本
            取当前最新的版本	 * @return Integer
	 * @hibernate.property column="curVersion" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getCurVersion() {
		return this.curVersion;
	}
	
	/**
	 * Set the curVersion
	 * @spring.validator type="required"
	 */	
	public void setCurVersion(Integer aValue) {
		this.curVersion = aValue;
	}	

	/**
	 * 文档路径	 * @return String
	 * @hibernate.property column="docPath" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getDocPath() {
		return this.docPath;
	}
	
	/**
	 * Set the docPath
	 * @spring.validator type="required"
	 */	
	public void setDocPath(String aValue) {
		this.docPath = aValue;
	}	

	/**
	 * 更新时间	 * @return java.util.Date
	 * @hibernate.property column="updatetime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	/**
	 * Set the updatetime
	 * @spring.validator type="required"
	 */	
	public void setUpdatetime(java.util.Date aValue) {
		this.updatetime = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 * @spring.validator type="required"
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ArchivesDoc)) {
			return false;
		}
		ArchivesDoc rhs = (ArchivesDoc) object;
		return new EqualsBuilder()
				.append(this.docId, rhs.docId)
								.append(this.creator, rhs.creator)
				.append(this.creatorId, rhs.creatorId)
				.append(this.menderId, rhs.menderId)
				.append(this.mender, rhs.mender)
				.append(this.docName, rhs.docName)
				.append(this.docStatus, rhs.docStatus)
				.append(this.curVersion, rhs.curVersion)
				.append(this.docPath, rhs.docPath)
				.append(this.updatetime, rhs.updatetime)
				.append(this.createtime, rhs.createtime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.docId) 
								.append(this.creator) 
				.append(this.creatorId) 
				.append(this.menderId) 
				.append(this.mender) 
				.append(this.docName) 
				.append(this.docStatus) 
				.append(this.curVersion) 
				.append(this.docPath) 
				.append(this.updatetime) 
				.append(this.createtime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("docId", this.docId) 
								.append("creator", this.creator) 
				.append("creatorId", this.creatorId) 
				.append("menderId", this.menderId) 
				.append("mender", this.mender) 
				.append("docName", this.docName) 
				.append("docStatus", this.docStatus) 
				.append("curVersion", this.curVersion) 
				.append("docPath", this.docPath) 
				.append("updatetime", this.updatetime) 
				.append("createtime", this.createtime) 
				.toString();
	}



}
