package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * SysDatabaseBR Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SysDatabaseBR extends com.zhiwei.core.model.BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String fileName;
	protected String createDate;
	protected Integer isAutoCreate; //0 atuo 1 other
    protected Integer fileSize;
    

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * Default Empty Constructor for class SysDatabaseBR
	 */
	public SysDatabaseBR () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SysDatabaseBR
	 */
	public SysDatabaseBR (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fileName" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	/**
	 * Set the fileName
	 */	
	public void setFileName(String aValue) {
		this.fileName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="createDate" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(String aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="isAutoCreate" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsAutoCreate() {
		return this.isAutoCreate;
	}
	
	/**
	 * Set the isAutoCreate
	 */	
	public void setIsAutoCreate(Integer aValue) {
		this.isAutoCreate = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysDatabaseBR)) {
			return false;
		}
		SysDatabaseBR rhs = (SysDatabaseBR) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.fileName, rhs.fileName)
				.append(this.createDate, rhs.createDate)
				.append(this.isAutoCreate, rhs.isAutoCreate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.fileName) 
				.append(this.createDate) 
				.append(this.isAutoCreate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("fileName", this.fileName) 
				.append("createDate", this.createDate) 
				.append("isAutoCreate", this.isAutoCreate) 
				.toString();
	}



}
