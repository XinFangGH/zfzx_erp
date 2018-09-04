package com.zhiwei.credit.model.communicate;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OutMailFolder Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OutMailFolder extends com.zhiwei.core.model.BaseModel {

    protected Long folderId;
	protected String folderName;
	protected Long parentId;
	protected Integer depLevel;
	protected String path;
	
	protected Short folderType;
	protected com.zhiwei.credit.model.system.AppUser appUser;

	protected java.util.Set outMails = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class OutMailFolder
	 */
	public OutMailFolder () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OutMailFolder
	 */
	public OutMailFolder (
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

	public java.util.Set getOutMails () {
		return outMails;
	}	
	
	public void setOutMails (java.util.Set in_outMails) {
		this.outMails = in_outMails;
	}
    

	/**
	 * 文件夹编号	 * @return Long
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

	/**
	 * 文件夹名称	 * @return String
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
	 * 目录层	 * @return Integer
	 * @hibernate.property column="depLevel" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getDepLevel() {
		return this.depLevel;
	}
	
	/**
	 * Set the depLevel
	 * @spring.validator type="required"
	 */	
	public void setDepLevel(Integer aValue) {
		this.depLevel = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="path" type="java.lang.String" length="256" not-null="false" unique="false"
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
	 * 1=表示共享，则所有的员工均可以使用该文件夹
            0=私人文件夹	 * @return Short
	 * @hibernate.property column="isPublic" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	

	/**
	 * 文件夹类型
            1=收信箱
            2=发信箱
            3=草稿箱
            4=删除箱
            10=其他	 * @return Short
	 * @hibernate.property column="folderType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getFolderType() {
		return this.folderType;
	}
	
	/**
	 * Set the folderType
	 * @spring.validator type="required"
	 */	
	public void setFolderType(Short aValue) {
		this.folderType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OutMailFolder)) {
			return false;
		}
		OutMailFolder rhs = (OutMailFolder) object;
		return new EqualsBuilder()
				.append(this.folderId, rhs.folderId)
						.append(this.folderName, rhs.folderName)
				.append(this.parentId, rhs.parentId)
				.append(this.depLevel, rhs.depLevel)
				.append(this.path, rhs.path)
				
				.append(this.folderType, rhs.folderType)
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
				.append(this.depLevel) 
				.append(this.path) 
				
				.append(this.folderType) 
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
				.append("depLevel", this.depLevel) 
				.append("path", this.path) 
				
				.append("folderType", this.folderType) 
				.toString();
	}



}
