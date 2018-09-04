package com.zhiwei.credit.model.document;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;
import com.zhiwei.credit.model.system.FileAttach;

/**
 * Document Base Java Bean, base class for the.credit.model, mapped directly to
 * database table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 * 
 */
@SuppressWarnings("serial")
public class Document extends com.zhiwei.core.model.BaseModel {
	public static final Short SHARED = 1;
	public static final Short NOT_SHARED = 0;

	public static final Short ONLINE_DOCUMENT = 2;

	public static final Short HAVE_ATTACH = 1;
	public static final Short NOT_HAVE_ATTACH = 0;
	@Expose
	protected Long docId;
	@Expose
	protected String docName;
	@Expose
	protected String content;
	@Expose
	protected java.util.Date createtime;
	@Expose
	protected java.util.Date updatetime;
	@Expose
	protected Short haveAttach;
	@Expose
	protected String sharedUserIds;
	@Expose
	protected String sharedUserNames;
	@Expose
	protected String sharedDepIds;
	@Expose
	protected String sharedDepNames;
	@Expose
	protected String sharedRoleIds;
	@Expose
	protected String sharedRoleNames;
	@Expose
	protected Short isShared;
	@Expose
	protected String fullname;
	@Expose
	protected String author;
	@Expose
	protected String keywords;
	@Expose
	protected String docType;
	@Expose
	protected String swfPath;
	@Expose
	protected com.zhiwei.credit.model.document.DocFolder docFolder;
	protected com.zhiwei.credit.model.system.AppUser appUser;
	@Expose
	protected Set<FileAttach> attachFiles = new HashSet<FileAttach>();

	/**
	 * Default Empty Constructor for class Document
	 */
	public Document() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Document
	 */
	public Document(Long in_docId) {
		this.setDocId(in_docId);
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public com.zhiwei.credit.model.document.DocFolder getDocFolder() {
		return docFolder;
	}

	public void setDocFolder(com.zhiwei.credit.model.document.DocFolder in_docFolder) {
		this.docFolder = in_docFolder;
	}

	public com.zhiwei.credit.model.system.AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="docId" type="java.lang.Long"
	 *               generator-class="native"
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
	 * * @return String
	 * 
	 * @hibernate.property column="docName" type="java.lang.String" length="100"
	 *                     not-null="true" unique="false"
	 */
	public String getDocName() {
		return this.docName;
	}

	/**
	 * Set the docName
	 * 
	 * @spring.validator type="required"
	 */
	public void setDocName(String aValue) {
		this.docName = aValue;
	}

	/**
	 * 内容 * @return String
	 * 
	 * @hibernate.property column="content" type="java.lang.String"
	 *                     length="65535" not-null="false" unique="false"
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
	 * 创建时间 * @return java.util.Date
	 * 
	 * @hibernate.property column="createtime" type="java.util.Date" length="19"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * Set the createtime
	 * 
	 * @spring.validator type="required"
	 */
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}

	/**
	 * * @return java.util.Date
	 * 
	 * @hibernate.property column="updatetime" type="java.util.Date" length="19"
	 *                     not-null="false" unique="false"
	 */
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}

	/**
	 * Set the updatetime
	 */
	public void setUpdatetime(java.util.Date aValue) {
		this.updatetime = aValue;
	}

	/**
	 * * @return Long
	 */
	public Long getFolderId() {
		return this.getDocFolder() == null ? null : this.getDocFolder()
				.getFolderId();
	}

	/**
	 * Set the folderId
	 */
	public void setFolderId(Long aValue) {
		if (aValue == null) {
			docFolder = null;
		} else if (docFolder == null) {
			docFolder = new com.zhiwei.credit.model.document.DocFolder(aValue);
			docFolder.setVersion(new Integer(0));// set a version to cheat
													// hibernate only
		} else {
			docFolder.setFolderId(aValue);
		}
	}

	/**
	 * 主键 * @return Long
	 */
	public Long getUserId() {
		return this.getAppUser() == null ? null : this.getAppUser().getUserId();
	}

	/**
	 * Set the userId
	 */
	public void setUserId(Long aValue) {
		if (aValue == null) {
			appUser = null;
		} else if (appUser == null) {
			appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
			appUser.setVersion(new Integer(0));// set a version to cheat
												// hibernate only
		} else {
			appUser.setUserId(aValue);
		}
	}

	/**
	 * * @return Short
	 * 
	 * @hibernate.property column="haveAttach" type="java.lang.Short" length="5"
	 *                     not-null="false" unique="false"
	 */
	public Short getHaveAttach() {
		return this.haveAttach;
	}

	/**
	 * Set the haveAttach
	 */
	public void setHaveAttach(Short aValue) {
		this.haveAttach = aValue;
	}

	/**
	 * 共享员工ID * @return String
	 * 
	 * @hibernate.property column="sharedUserIds" type="java.lang.String"
	 *                     length="1000" not-null="false" unique="false"
	 */
	public String getSharedUserIds() {
		return this.sharedUserIds;
	}

	/**
	 * Set the sharedUserIds
	 */
	public void setSharedUserIds(String aValue) {
		this.sharedUserIds = aValue;
	}

	/**
	 * 共享部门ID * @return String
	 * 
	 * @hibernate.property column="sharedDepIds" type="java.lang.String"
	 *                     length="1000" not-null="false" unique="false"
	 */
	public String getSharedDepIds() {
		return this.sharedDepIds;
	}

	/**
	 * Set the sharedDepIds
	 */
	public void setSharedDepIds(String aValue) {
		this.sharedDepIds = aValue;
	}

	/**
	 * 共享角色ID * @return String
	 * 
	 * @hibernate.property column="sharedRoleIds" type="java.lang.String"
	 *                     length="1000" not-null="false" unique="false"
	 */
	public String getSharedRoleIds() {
		return this.sharedRoleIds;
	}

	/**
	 * Set the sharedRoleIds
	 */
	public void setSharedRoleIds(String aValue) {
		this.sharedRoleIds = aValue;
	}

	/**
	 * 是否共享 * @return Short
	 * 
	 * @hibernate.property column="isShared" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */
	public Short getIsShared() {
		return this.isShared;
	}

	/**
	 * Set the isShared
	 * 
	 * @spring.validator type="required"
	 */
	public void setIsShared(Short aValue) {
		this.isShared = aValue;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Document)) {
			return false;
		}
		Document rhs = (Document) object;
		return new EqualsBuilder().append(this.docId, rhs.docId)
				.append(this.docName, rhs.docName)
				.append(this.fullname, rhs.fullname)
				.append(this.content, rhs.content)
				.append(this.createtime, rhs.createtime)
				.append(this.updatetime, rhs.updatetime)
				.append(this.haveAttach, rhs.haveAttach)
				.append(this.sharedUserIds, rhs.sharedUserIds)
				.append(this.sharedDepIds, rhs.sharedDepIds)
				.append(this.sharedRoleIds, rhs.sharedRoleIds)
				.append(this.isShared, rhs.isShared)
				.append(this.swfPath, rhs.swfPath).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.docId)
				.append(this.docName).append(this.content)
				.append(this.createtime).append(this.updatetime)
				.append(this.fullname).append(this.haveAttach)
				.append(this.sharedUserIds).append(this.sharedDepIds)
				.append(this.sharedRoleIds).append(this.isShared)
				.append(this.swfPath).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("docId", this.docId)
				.append("docName", this.docName)
				.append("content", this.content)
				.append("fullname", this.fullname)
				.append("createtime", this.createtime)
				.append("updatetime", this.updatetime)
				.append("haveAttach", this.haveAttach)
				.append("sharedUserIds", this.sharedUserIds)
				.append("sharedDepIds", this.sharedDepIds)
				.append("sharedRoleIds", this.sharedRoleIds)
				.append("swfPath", this.swfPath)
				.append("isShared", this.isShared).toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "docId";
	}

	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Long getId() {
		return docId;
	}

	public Set<FileAttach> getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(Set<FileAttach> attachFiles) {
		this.attachFiles = attachFiles;
	}

	public String getSharedUserNames() {
		return sharedUserNames;
	}

	public void setSharedUserNames(String sharedUserNames) {
		this.sharedUserNames = sharedUserNames;
	}

	public String getSharedDepNames() {
		return sharedDepNames;
	}

	public void setSharedDepNames(String sharedDepNames) {
		this.sharedDepNames = sharedDepNames;
	}

	public String getSharedRoleNames() {
		return sharedRoleNames;
	}

	public void setSharedRoleNames(String sharedRoleNames) {
		this.sharedRoleNames = sharedRoleNames;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSwfPath() {
		return swfPath;
	}

	public void setSwfPath(String swfPath) {
		this.swfPath = swfPath;
	}

}
