package com.zhiwei.credit.model.document;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * DocPrivilege Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������������������������������������������������������
 */
public class DocPrivilege extends com.zhiwei.core.model.BaseModel {

    protected Long privilegeId;
	protected Integer rights;
	protected Integer udrId;
	protected String udrName;
	protected Short flag;
	protected Short fdFlag;


	protected com.zhiwei.credit.model.document.Document document;
	protected com.zhiwei.credit.model.document.DocFolder docFolder;


	/**
	 * Default Empty Constructor for class DocPrivilege
	 */
	public DocPrivilege () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class DocPrivilege
	 */
	public DocPrivilege (
		 Long in_privilegeId
        ) {
		this.setPrivilegeId(in_privilegeId);
    }

	
	public com.zhiwei.credit.model.document.Document getDocument () {
		return document;
	}	
	
	public void setDocument (com.zhiwei.credit.model.document.Document in_document) {
		this.document = in_document;
	}
	
	public com.zhiwei.credit.model.document.DocFolder getDocFolder () {
		return docFolder;
	}	
	
	public void setDocFolder (com.zhiwei.credit.model.document.DocFolder in_docFolder) {
		this.docFolder = in_docFolder;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="privilegeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPrivilegeId() {
		return this.privilegeId;
	}
	
	/**
	 * Set the privilegeId
	 */	
	public void setPrivilegeId(Long aValue) {
		this.privilegeId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getFolderId() {
		return this.getDocFolder()==null?null:this.getDocFolder().getFolderId();
	}
	
	/**
	 * Set the folderId
	 */	
	public void setFolderId(Long aValue) {
	    if (aValue==null) {
	    	docFolder = null;
	    } else if (docFolder == null) {
	        docFolder = new com.zhiwei.credit.model.document.DocFolder(aValue);
	        docFolder.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			docFolder.setFolderId(aValue);
	    }
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getDocId() {
		return this.getDocument()==null?null:this.getDocument().getDocId();
	}
	
	/**
	 * Set the docId
	 */	
	public void setDocId(Long aValue) {
	    if (aValue==null) {
	    	document = null;
	    } else if (document == null) {
	        document = new com.zhiwei.credit.model.document.Document(aValue);
	        document.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			document.setDocId(aValue);
	    }
	}	

	/**
	 * 权限
            文档或目录的读写修改权限
            1=读
            2=修改
            4=删除

            权限值可以为上面的值之和
            如：3则代表进行读，修改的操作


            	 * @return Integer
	 * @hibernate.property column="rights" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getRights() {
		return this.rights;
	}
	
	/**
	 * Set the rights
	 * @spring.validator type="required"
	 */	
	public void setRights(Integer aValue) {
		this.rights = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="udrId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUdrId() {
		return this.udrId;
	}
	
	/**
	 * Set the udrId
	 */	
	public void setUdrId(Integer aValue) {
		this.udrId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="udrName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getUdrName() {
		return this.udrName;
	}
	
	/**
	 * Set the udrName
	 */	
	public void setUdrName(String aValue) {
		this.udrName = aValue;
	}	

	/**
	 * 1=user
            2=deparment
            3=role	 * @return Short
	 * @hibernate.property column="flag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getFlag() {
		return this.flag;
	}
	
	/**
	 * Set the flag
	 * @spring.validator type="required"
	 */	
	public void setFlag(Short aValue) {
		this.flag = aValue;
	}	
	
	public Short getFdFlag() {
		return fdFlag;
	}

	public void setFdFlag(Short fdFlag) {
		this.fdFlag = fdFlag;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DocPrivilege)) {
			return false;
		}
		DocPrivilege rhs = (DocPrivilege) object;
		return new EqualsBuilder()
				.append(this.privilegeId, rhs.privilegeId)
								.append(this.rights, rhs.rights)
				.append(this.udrId, rhs.udrId)
				.append(this.udrName, rhs.udrName)
				.append(this.flag, rhs.flag)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.privilegeId) 
								.append(this.rights) 
				.append(this.udrId) 
				.append(this.udrName) 
				.append(this.flag) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("privilegeId", this.privilegeId) 
								.append("rights", this.rights) 
				.append("udrId", this.udrId) 
				.append("udrName", this.udrName) 
				.append("flag", this.flag) 
				.toString();
	}



}
