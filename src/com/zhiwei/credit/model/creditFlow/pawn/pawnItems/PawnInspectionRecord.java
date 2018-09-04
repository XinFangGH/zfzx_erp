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
 * PawnInspectionRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PawnInspectionRecord extends com.zhiwei.core.model.BaseModel {

    protected Long inspectionId;
	protected Long operateType;
	protected Long inspectorId;
	protected String inspectorName;
	protected java.util.Date matterDate;
	protected String remarks;
	protected String creatorName;
	protected java.util.Date createTime;
	protected Long pawnItemId;
	protected Long projectId;
	protected String businessType;
	protected String operateTypeValue;
	/**
	 * Default Empty Constructor for class PawnInspectionRecord
	 */
	public PawnInspectionRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PawnInspectionRecord
	 */
	public PawnInspectionRecord (
		 Long in_inspectionId
        ) {
		this.setInspectionId(in_inspectionId);
    }

    

	public String getOperateTypeValue() {
		return operateTypeValue;
	}

	public void setOperateTypeValue(String operateTypeValue) {
		this.operateTypeValue = operateTypeValue;
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
     * @hibernate.id column="inspectionId" type="java.lang.Long" generator-class="native"
	 */
	public Long getInspectionId() {
		return this.inspectionId;
	}
	
	/**
	 * Set the inspectionId
	 */	
	public void setInspectionId(Long aValue) {
		this.inspectionId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="operateType" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOperateType() {
		return this.operateType;
	}
	
	/**
	 * Set the operateType
	 */	
	public void setOperateType(Long aValue) {
		this.operateType = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="inspectorId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getInspectorId() {
		return this.inspectorId;
	}
	
	/**
	 * Set the inspectorId
	 */	
	public void setInspectorId(Long aValue) {
		this.inspectorId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="inspectorName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getInspectorName() {
		return this.inspectorName;
	}
	
	/**
	 * Set the inspectorName
	 */	
	public void setInspectorName(String aValue) {
		this.inspectorName = aValue;
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
		if (!(object instanceof PawnInspectionRecord)) {
			return false;
		}
		PawnInspectionRecord rhs = (PawnInspectionRecord) object;
		return new EqualsBuilder()
				.append(this.inspectionId, rhs.inspectionId)
				.append(this.operateType, rhs.operateType)
				.append(this.inspectorId, rhs.inspectorId)
				.append(this.inspectorName, rhs.inspectorName)
				.append(this.matterDate, rhs.matterDate)
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
				.append(this.inspectionId) 
				.append(this.operateType) 
				.append(this.inspectorId) 
				.append(this.inspectorName) 
				.append(this.matterDate) 
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
				.append("inspectionId", this.inspectionId) 
				.append("operateType", this.operateType) 
				.append("inspectorId", this.inspectorId) 
				.append("inspectorName", this.inspectorName) 
				.append("matterDate", this.matterDate) 
				.append("remarks", this.remarks) 
				.append("creatorName", this.creatorName) 
				.append("createTime", this.createTime) 
				.append("pawnItemId", this.pawnItemId) 
				.toString();
	}



}
