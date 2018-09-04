package com.zhiwei.credit.model.document;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/


import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * DocFolder Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class DocFolder extends com.zhiwei.core.model.BaseModel {
	public static final Short IS_SHARED=1;
	public static final Short IS_NOT_SHARED=0;
	@Expose
    protected Long folderId;
	@Expose
	protected String folderName;
	@Expose
	protected Long parentId;
	@Expose
	protected String path;
	@Expose
	protected Short isShared;
	@Expose
	protected String descp;
	@Expose
	protected com.zhiwei.credit.model.system.AppUser appUser;
//    protected Set<Document> documents=new HashSet<Document>();
	/**
	 * Default Empty Constructor for class DocFolder
	 */
	public DocFolder () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class DocFolder
	 */
	public DocFolder (
		 Long in_folderId
        ) {
		this.setFolderId(in_folderId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="folderId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFolderId() {
		return this.folderId;
	}
	
	/**
	 * Set the folderId
	 */	
	public void setFolderId(Long aValue) {
		this.folderId = aValue;
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

	
	
	
//	public Set<Document> getDocuments() {
//		return documents;
//	}
//
//	public void setDocuments(Set<Document> documents) {
//		this.documents = documents;
//	}

	/**
	 * 目录名称	 * @return String
	 * @hibernate.property column="folderName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getFolderName() {
		return this.folderName;
	}
	
	/**
	 * Set the folderName
	 * @spring.validator type="required"
	 */	
	public void setFolderName(String aValue) {
		this.folderName = aValue;
	}	

	/**
	 * 父目录	 * @return Long
	 * @hibernate.property column="parentId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getParentId() {
		return this.parentId;
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Long aValue) {
		this.parentId = aValue;
	}	

	/**
	 * 路径	 * @return String
	 * @hibernate.property column="path" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Set the path
	 */	
	public void setPath(String aValue) {
		this.path = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isShared" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsShared() {
		return this.isShared;
	}
	
	/**
	 * Set the isShared
	 * @spring.validator type="required"
	 */	
	public void setIsShared(Short aValue) {
		this.isShared = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DocFolder)) {
			return false;
		}
		DocFolder rhs = (DocFolder) object;
		return new EqualsBuilder()
				.append(this.folderId, rhs.folderId)
						.append(this.folderName, rhs.folderName)
				.append(this.parentId, rhs.parentId)
				.append(this.path, rhs.path)
				.append(this.isShared, rhs.isShared)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.folderId) 
						.append(this.folderName) 
				.append(this.parentId) 
				.append(this.path) 
				.append(this.isShared) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("folderId", this.folderId) 
						.append("folderName", this.folderName) 
				.append("parentId", this.parentId) 
				.append("path", this.path) 
				.append("isShared", this.isShared) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "folderId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Long getId() {
		return folderId;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

}
