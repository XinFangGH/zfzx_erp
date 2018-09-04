package com.zhiwei.credit.model.creditFlow.pawn.pawnItems;
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
 * PawnCrkRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PawnCrkRecord extends com.zhiwei.core.model.BaseModel {

    protected Long crkRecordId;
	protected String operateType;
	protected Long personnelIssueId;
	protected String personnelIssueName;
	protected java.util.Date matterDate;
	protected String storageLocation;
	protected String remarks;
	protected String creatorName;
	protected java.util.Date createTime;
	protected Long pawnItemId;
	protected Long projectId;
	protected String businessType;

	/**
	 * Default Empty Constructor for class PawnCrkRecord
	 */
	public PawnCrkRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PawnCrkRecord
	 */
	public PawnCrkRecord (
		 Long in_crkRecordId
        ) {
		this.setCrkRecordId(in_crkRecordId);
    }

    

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="crkRecordId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCrkRecordId() {
		return this.crkRecordId;
	}
	
	/**
	 * Set the crkRecordId
	 */	
	public void setCrkRecordId(Long aValue) {
		this.crkRecordId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="operateType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getOperateType() {
		return this.operateType;
	}
	
	/**
	 * Set the operateType
	 */	
	public void setOperateType(String aValue) {
		this.operateType = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="personnelIssueId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPersonnelIssueId() {
		return this.personnelIssueId;
	}
	
	/**
	 * Set the personnelIssueId
	 */	
	public void setPersonnelIssueId(Long aValue) {
		this.personnelIssueId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="personnelIssueName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getPersonnelIssueName() {
		return this.personnelIssueName;
	}
	
	/**
	 * Set the personnelIssueName
	 */	
	public void setPersonnelIssueName(String aValue) {
		this.personnelIssueName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="matterDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getMatterDate() {
		return this.matterDate;
	}
	
	/**
	 * Set the matterDate
	 */	
	public void setMatterDate(java.util.Date aValue) {
		this.matterDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="storageLocation" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getStorageLocation() {
		return this.storageLocation;
	}
	
	/**
	 * Set the storageLocation
	 */	
	public void setStorageLocation(String aValue) {
		this.storageLocation = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemarks() {
		return this.remarks;
	}
	
	/**
	 * Set the remarks
	 */	
	public void setRemarks(String aValue) {
		this.remarks = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="creatorName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCreatorName() {
		return this.creatorName;
	}
	
	/**
	 * Set the creatorName
	 */	
	public void setCreatorName(String aValue) {
		this.creatorName = aValue;
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
	 * 	 * @return Long
	 * @hibernate.property column="pawnItemId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPawnItemId() {
		return this.pawnItemId;
	}
	
	/**
	 * Set the pawnItemId
	 */	
	public void setPawnItemId(Long aValue) {
		this.pawnItemId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PawnCrkRecord)) {
			return false;
		}
		PawnCrkRecord rhs = (PawnCrkRecord) object;
		return new EqualsBuilder()
				.append(this.crkRecordId, rhs.crkRecordId)
				.append(this.operateType, rhs.operateType)
				.append(this.personnelIssueId, rhs.personnelIssueId)
				.append(this.personnelIssueName, rhs.personnelIssueName)
				.append(this.matterDate, rhs.matterDate)
				.append(this.storageLocation, rhs.storageLocation)
				.append(this.remarks, rhs.remarks)
				.append(this.creatorName, rhs.creatorName)
				.append(this.createTime, rhs.createTime)
				.append(this.pawnItemId, rhs.pawnItemId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.crkRecordId) 
				.append(this.operateType) 
				.append(this.personnelIssueId) 
				.append(this.personnelIssueName) 
				.append(this.matterDate) 
				.append(this.storageLocation) 
				.append(this.remarks) 
				.append(this.creatorName) 
				.append(this.createTime) 
				.append(this.pawnItemId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("crkRecordId", this.crkRecordId) 
				.append("operateType", this.operateType) 
				.append("personnelIssueId", this.personnelIssueId) 
				.append("personnelIssueName", this.personnelIssueName) 
				.append("matterDate", this.matterDate) 
				.append("storageLocation", this.storageLocation) 
				.append("remarks", this.remarks) 
				.append("creatorName", this.creatorName) 
				.append("createTime", this.createTime) 
				.append("pawnItemId", this.pawnItemId) 
				.toString();
	}



}
