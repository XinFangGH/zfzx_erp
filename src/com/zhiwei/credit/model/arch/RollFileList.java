package com.zhiwei.credit.model.arch;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * RollFileList Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class RollFileList extends com.zhiwei.core.model.BaseModel {


	protected Long listId;
    //protected Long rollFileId;
	//protected Long fileId;
	protected Integer downLoads;
	protected Integer sn;
	protected String shortDesc;
	protected com.zhiwei.credit.model.arch.RollFile rollFile;
	protected com.zhiwei.credit.model.system.FileAttach fileAttach;
	
	
	public Long getFileId() {
		return getFileAttach().getFileId();
	}

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
	
	
	
	public com.zhiwei.credit.model.system.FileAttach getFileAttach() {
		return fileAttach;
	}

	public void setFileAttach(com.zhiwei.credit.model.system.FileAttach fileAttach) {
		this.fileAttach = fileAttach;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}
	public Integer getDownLoads() {
		return downLoads;
	}

	public void setDownLoads(Integer downLoads) {
		this.downLoads = downLoads;
	}









	/**
	 * Default Empty Constructor for class RollFileList
	 */
	public RollFileList () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RollFileList
	 */
	public RollFileList (
		 Long in_fileId
        ) {
		this.setListId(in_fileId);
    }

	
	public com.zhiwei.credit.model.arch.RollFile getRollFile () {
		return rollFile;
	}	
	
	public void setRollFile (com.zhiwei.credit.model.arch.RollFile in_rollFile) {
		this.rollFile = in_rollFile;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="fileId" type="java.lang.Long" generator-class="native"
	 */
	

	/**
	 * 卷内文件ID	 * @return Long
	 */
	public Long getRollFileId() {
		return this.getRollFile()==null?null:this.getRollFile().getRollFileId();
	}
	
	/**
	 * Set the rollFileId
	 */	
	public void setRollFileId(Long aValue) {
	    if (aValue==null) {
	    	rollFile = null;
	    } else if (rollFile == null) {
	        rollFile = new com.zhiwei.credit.model.arch.RollFile(aValue);
	        rollFile.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			rollFile.setRollFileId(aValue);
	    }
	}	

	/**
	 * 文件名称	 * @return String
	 * @hibernate.property column="fileName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */

	
	/**
	 * Set the fileName
	 * @spring.validator type="required"
	 */	


	/**
	 * 文件路径	 * @return String
	 * @hibernate.property column="filePath" type="java.lang.String" length="256" not-null="true" unique="false"
	 */

	
	/**
	 * Set the filePath
	 * @spring.validator type="required"
	 */	


	/**
	 * 文件类型	 * @return String
	 * @hibernate.property column="fileType" type="java.lang.String" length="128" not-null="false" unique="false"
	 */

	
	/**
	 * Set the fileType
	 */	
	






	/**
	 * 序号	 * @return Integer
	 * @hibernate.property column="sn" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSn() {
		return this.sn;
	}
	
	/**
	 * Set the sn
	 */	
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}	

	/**
	 * 概要	 * @return String
	 * @hibernate.property column="shortDesc" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getShortDesc() {
		return this.shortDesc;
	}
	
	/**
	 * Set the shortDesc
	 */	
	public void setShortDesc(String aValue) {
		this.shortDesc = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RollFileList)) {
			return false;
		}
		RollFileList rhs = (RollFileList) object;
		return new EqualsBuilder()
				
						
				
				
				.append(this.downLoads, rhs.downLoads)
				
				.append(this.sn, rhs.sn)
				.append(this.shortDesc, rhs.shortDesc)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				
				
				
				.append(this.downLoads) 
				
			
				.append(this.sn) 
				.append(this.shortDesc) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				
						
				
				
				.append("downLoads", this.downLoads) 
				
				.append("sn", this.sn) 
				.append("shortDesc", this.shortDesc) 
				.toString();
	}



}
