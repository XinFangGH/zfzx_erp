package com.zhiwei.credit.model.document;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Seal Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class Seal extends com.zhiwei.core.model.BaseModel {

    protected Long sealId;
	protected String sealName;
	protected String sealPath;
	protected Long belongId;
	protected String belongName;
	protected com.zhiwei.credit.model.system.FileAttach fileAttach;


	/**
	 * Default Empty Constructor for class Seal
	 */
	public Seal () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Seal
	 */
	public Seal (
		 Long in_sealId
        ) {
		this.setSealId(in_sealId);
    }

	
	public com.zhiwei.credit.model.system.FileAttach getFileAttach () {
		return fileAttach;
	}	
	
	public void setFileAttach (com.zhiwei.credit.model.system.FileAttach in_fileAttach) {
		this.fileAttach = in_fileAttach;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="sealId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSealId() {
		return this.sealId;
	}
	
	/**
	 * Set the sealId
	 */	
	public void setSealId(Long aValue) {
		this.sealId = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="sealName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getSealName() {
		return this.sealName;
	}
	
	/**
	 * Set the sealName
	 * @spring.validator type="required"
	 */	
	public void setSealName(String aValue) {
		this.sealName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="sealPath" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getSealPath() {
		return this.sealPath;
	}
	
	/**
	 * Set the sealPath
	 * @spring.validator type="required"
	 */	
	public void setSealPath(String aValue) {
		this.sealPath = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="belongId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getBelongId() {
		return this.belongId;
	}
	
	/**
	 * Set the belongId
	 * @spring.validator type="required"
	 */	
	public void setBelongId(Long aValue) {
		this.belongId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="belongName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getBelongName() {
		return this.belongName;
	}
	
	/**
	 * Set the belongName
	 * @spring.validator type="required"
	 */	
	public void setBelongName(String aValue) {
		this.belongName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Seal)) {
			return false;
		}
		Seal rhs = (Seal) object;
		return new EqualsBuilder()
				.append(this.sealId, rhs.sealId)
						.append(this.sealName, rhs.sealName)
				.append(this.sealPath, rhs.sealPath)
				.append(this.belongId, rhs.belongId)
				.append(this.belongName, rhs.belongName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.sealId) 
						.append(this.sealName) 
				.append(this.sealPath) 
				.append(this.belongId) 
				.append(this.belongName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("sealId", this.sealId) 
						.append("sealName", this.sealName) 
				.append("sealPath", this.sealPath) 
				.append("belongId", this.belongId) 
				.append("belongName", this.belongName) 
				.toString();
	}



}
