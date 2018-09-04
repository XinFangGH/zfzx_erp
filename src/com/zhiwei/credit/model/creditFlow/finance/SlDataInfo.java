package com.zhiwei.credit.model.creditFlow.finance;
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
 * SlDataInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlDataInfo extends com.zhiwei.core.model.BaseModel {

    protected Long dataInfoId;
	protected String dataTypeName;
	protected java.util.Date createTime;
	protected String filePath;
	protected Long dataId;
	protected Short dataTypeStatus;

	/**
	 * Default Empty Constructor for class SlDataInfo
	 */
	public SlDataInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlDataInfo
	 */
	public SlDataInfo (
		 Long in_dataInfoId
        ) {
		this.setDataInfoId(in_dataInfoId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="dataInfoId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDataInfoId() {
		return this.dataInfoId;
	}
	
	/**
	 * Set the dataInfoId
	 */	
	public void setDataInfoId(Long aValue) {
		this.dataInfoId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="dataTypeName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getDataTypeName() {
		return this.dataTypeName;
	}
	
	/**
	 * Set the dataTypeName
	 */	
	public void setDataTypeName(String aValue) {
		this.dataTypeName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="filePath" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFilePath() {
		return this.filePath;
	}
	
	/**
	 * Set the filePath
	 */	
	public void setFilePath(String aValue) {
		this.filePath = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="dataId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getDataId() {
		return this.dataId;
	}
	
	/**
	 * Set the dataId
	 */	
	public void setDataId(Long aValue) {
		this.dataId = aValue;
	}	

	public Short getDataTypeStatus() {
		return dataTypeStatus;
	}

	public void setDataTypeStatus(Short dataTypeStatus) {
		this.dataTypeStatus = dataTypeStatus;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlDataInfo)) {
			return false;
		}
		SlDataInfo rhs = (SlDataInfo) object;
		return new EqualsBuilder()
				.append(this.dataInfoId, rhs.dataInfoId)
				.append(this.dataTypeName, rhs.dataTypeName)
				.append(this.createTime, rhs.createTime)
				.append(this.filePath, rhs.filePath)
				.append(this.dataId, rhs.dataId)
				.append(this.dataTypeStatus, rhs.dataTypeStatus)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.dataInfoId) 
				.append(this.dataTypeName) 
				.append(this.createTime) 
				.append(this.filePath) 
				.append(this.dataId) 
				.append(this.dataTypeStatus)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("dataInfoId", this.dataInfoId) 
				.append("dataTypeName", this.dataTypeName) 
				.append("createTime", this.createTime) 
				.append("filePath", this.filePath) 
				.append("dataId", this.dataId) 
				.append("dataTypeStatus",this.dataTypeStatus)
				.toString();
	}



}
